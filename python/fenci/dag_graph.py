# -*- coding: utf-8 -*-
# python2.7
import marshal

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
                if i not in DAG:
                    DAG[i]=[]
                DAG[i].append(j)
            j+=1
            if j>=N:
                i+=1
                j=i
                p=trie
        else:
            p = trie
            i+=1
            j=i
    for i in xrange(len(sentence)):
        if i not in DAG:
            DAG[i] =[i]
    return DAG

def calc(sentence,DAG,idx,route):
    N = len(sentence)
    route[N] = (0.0,'')
    for idx in xrange(N-1,-1,-1):
        print [(FREQ.get(sentence[idx:x+1], 0.0)) for x in DAG[idx]]
        candidates = [ ( FREQ.get(sentence[idx:x+1],0.0) + route[x+1][0],x ) for x in DAG[idx] ]
      #  print candidates
      #  print '****'
        route[idx] = max(candidates)
        print route

if __name__=='__main__':
    sentence=u'小S一两年一万二'
    trie,FREQ,total,min_freq = marshal.load(open(u'D:\jieba.cache','rb'))#使用缓存载入重要变量
    rs=get_DAG(sentence)#获取DAG
    route={}
    calc(sentence,rs,0,route)#根据得分进行初步分词
    #print route
