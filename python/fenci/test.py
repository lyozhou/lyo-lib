# -*- coding: gb2312 -*-
from __future__ import with_statement
import sys
import marshal
from math import log
#reload(sys)
#sys.setdefaultencoding("gb2312")
trie = None
DAG = {}
FREQ={}

'''
visualize the trie tree
'''
def list_all_dict(dict_a,depth):
    if isinstance(dict_a,dict):
        for x in range(len(dict_a)):
            temp_key = dict_a.keys()[x]
            temp_value = dict_a[temp_key]
            if isinstance(temp_value,dict):
                print ' '*depth + temp_key.encode('gb2312')
                list_all_dict(temp_value,depth+4)
            else:
                print ' '*depth + temp_key.encode('gb2312') + " " + temp_value.encode('gb2312')


'''
According to Route, output cut word
@output : 你/ 现在/ 应该/ 去/ 幼儿园/ 了/
'''
def showCut(sentence):
    N = len(sentence)
    buf = ''
    x=0
    while x<N:
        y = route[x][1]+1
        tmpWord = sentence[x:y]
        if y-x == 1:    # just a char, put into buf to re-calc
            print tmpWord + '/',
            buf = ''
            x+=1
        else:
            print tmpWord+'/',
            x = y

      

'''
check
    1. un-recognized word (HMM)
    2. should be a word but split them apart
'''
def finalCut(sentence):
    print 'before HMM:',
    showCut(sentence)
    print ''
    print 'after HMM: ',
    N = len(sentence)
    buf = u''
    x = 0
    while x<N:
        y = route[x][1]+1
        tmpWord = sentence[x:y]
        if y-x ==1:
            buf += tmpWord
        else:
            if len(buf) > 0:
                if len(buf)==1:
                    print buf+'/',
                    buf = ''
                else:
                    if buf in FREQ:
                        print buf+'/',
                    else:
                        __hmmCut(buf)
                buf = ''
            print tmpWord+'/',
        x = y

    if len(buf) > 0:
        if len(buf) == 1:
            print buf
        else :
            if buf in FREQ:
                print buf+'/',
            else:
                __hmmCut(buf)
                
            
'''
using HMM to cut un-rcognized word
'''
PROB_START_P = "prob_start.p"
PROB_TRANS_P = "prob_trans.p"
PROB_EMIT_P = "prob_emit.p"
MIN_FLOAT = -3.14e10   # close to nagtive infinity
PrevStatus = {
    'B':('E','S'),
    'M':('M','B'),
    'S':('S','E'),
    'E':('B','M')
}

def load_model():
    # pi, init possibility of state
    start_p = {}
    with open(PROB_START_P, mode='rb') as f:
        start_p = marshal.load(f)
    f.closed

    # P(state A -> state B)
    trans_p = {}
    with open(PROB_TRANS_P, 'rb') as f:
        trans_p = marshal.load(f)
    f.closed

    # P(observation|hidden state)
    emit_p = {}
    with file(PROB_EMIT_P, 'rb') as f:
        emit_p = marshal.load(f)
    f.closed
    return start_p, trans_p, emit_p

def viterbi(obsSeq, states, start_p, trans_p, emit_p):
    delta = [{}]
    psi = {}
    #init
    for i in states: # {b,e,m,s}
        # because start/trans/emit matrix has been log, so not * but +
        delta[0][i] = start_p[i] + emit_p[i].get(obsSeq[0],MIN_FLOAT)
        psi[i] = [i]
    # recursion
    for t in range(1,len(obsSeq)):
        delta.append({})
        newPath = {}
        for i in states:    # when t = 2
            #print 'current state = ' + i
            maxValue = None
            for j in PrevStatus[i]: # when t = 1 and if current state = e, previous state is not all b,e,m,s
                #print 'prevstate = ' + j
                value = delta[t-1][j] + trans_p[j].get(i,MIN_FLOAT)
                #print 'value ' + str(value)
                if(maxValue is None or value>maxValue):
                    #print 'maxValue = ' + str(maxValue)
                    maxValue = value
                    newPath[i] = psi[j]+[i]
                    #print 'psi[j] = ',
                    #print psi[j] ,
                    #print 'newpath[i] = ',
                    #print newPath
            delta[t][i] = maxValue + emit_p[i].get(obsSeq[t],MIN_FLOAT)
            #print delta
        psi = newPath
        #print psi
    (posibiliy,state) = max([  (delta[len(obsSeq)-1][y],y) for y in ('E','S') ])
    #print posibiliy
    #print psi[state]
    return psi[state]


def __hmmCut(buf):
    start_p, trans_p, emit_p = load_model()
    psi = viterbi(sentence,('B','M','E','S'), start_p, trans_p, emit_p)
    begin, next = 0,0
    for i,char in enumerate(buf):
        pos = psi[i]
        if pos == 'B':
            begin = i
        elif pos == 'E':
            print buf[begin:i+1]+'/',
            next = i+1
        elif pos == 'S':
            print char+'/',
            next = i+1
    if next<len(buf):
        print buf[next:]+'/',
    return buf

'''
@return : dict,dict,num
'''
def gen_trie(filename):
    lfreq={}
    ltotal = 0
    trie={}
    f = open(filename,'r')
    lineno = 0
    for line in f.read().decode('utf-8').split('\n'):
        lineno+=1
        try:
            word,freq,_ = line.split(' ')
            lfreq[word] = freq
            ltotal += int(freq)
            p = trie
            for c in word:
                if not c in p:
                    p[c] = {}
                p = p[c]     #trie variable will also change 
            p['']=''         #ending flag, if use list_all_dict(),will print a space line
        except ValueError,e:
            print >>  sys.stderr, filename, ' at line', lineno, line
    return trie,lfreq,ltotal

'''
@return : dict
'''
def get_DAG(sentence):
    N = len(sentence)
    i,j=0,0
    p = trie
    DAG = {}
    while i<N:
        c = sentence[j]
        if c in p:
            p = p[c]
            if '' in p:
                if not i in DAG:
                    DAG[i] = []
                DAG[i].append(j)
              #  print "below"
              #  print DAG
            j+=1
            if j>=N:
                i+=1
                j=i
                p=trie
        else:
            p=trie
            i+=1
            j=i
    for i in xrange(len(sentence)):
        if not i in DAG:
            DAG[i] = [i]
    return DAG

def calc(sentence,DAG,route):
    N = len(sentence)
    route[N] = (0.0,"")
    for idx in xrange(N-1,-1,-1):   #strat,stop,step
        #print [(FREQ.get(sentence[idx:x+1], 0.0)) for x in DAG[idx]]
        candidates = [ (FREQ.get(sentence[idx:x+1], 0.0) + route[x+1][0], x)
                       for x in DAG[idx] ]
        #print candidates
        route[idx] = max(candidates)
        #print '***'
        #print route

def init(sentence):
    global trie,FREQ,DAG,route
    filename = 'dict.txt'
    trie,FREQ,total = gen_trie(filename)
    #list_all_dict(trie,0)
    FREQ = dict(   [   (k,log(float(v)/total)) for k,v in FREQ.iteritems()   ]   )
    # k is chinese word, v = log (word's frequency / totalFrequency)
    
    DAG = get_DAG(sentence)
    route={}
    ca =  calc(sentence,DAG,route)
    #showCut(sentence)
    finalCut(sentence)
    return route


        
if __name__ == '__main__':
    sentence = '周丽媛在读冰与火之歌的书'.decode('gb2312')
    init(sentence)
    #__hmmCut(sentence)

