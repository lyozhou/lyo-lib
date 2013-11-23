# -*- coding: gb2312 -*-
import sys
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


def init(filename = 'dict.txt'):
    global trie,FREQ,DAG
    trie,FREQ,total = gen_trie(filename)
    #list_all_dict(trie,0)
    FREQ = dict(   [   (k,log(float(v)/total)) for k,v in FREQ.iteritems()   ]   )
    # k is chinese word, v = log (word's frequency / totalFrequency)
    sentence = '小S一两年一万二'.decode('gb2312')
    DAG = get_DAG(sentence)
    route={}
    ca =  calc(sentence,DAG,route)
    print route
    return ca

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

        
if __name__ == '__main__':
    init()



