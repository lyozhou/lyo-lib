from __future__ import division
import  numpy as np


N = 3   # hidden state
M = 2   # observe state
T = 10   # observe sequence number

A = np.array([
    [0.333,0.333,0.333],
    [0.333,0.333,0.333],
    [0.333,0.333,0.333]])

B = np.array([
    [0.5,0.5],
    [0.75,0.25],
    [0.25,0.75]])

pi = np.array([0.333,0.333,0.333])

delta = np.zeros((T,N),np.float)

psi = np.zeros((T,N),np.int)

O = np.array([0,0,0,0,1,0,1,1,1,1])   # calculate posiibility of O sequence


# initialize t = 1
def init_t1():
    for i in range(N):
        delta[0,i] = pi[i]*B[i,O[0]]

def recursion():
    global psi
    # t = 1 is ignore, because it has calculate in init_t1()
    for t in range(T-1):   # t = 2
        t += 1
        for i in range(N):  # hidden state when t = 2
            maxValue = 0.0
            maxRoute = 0
            for j in range(N):  # hidden state when t = 1
                value = delta[t-1,j]*A[j,i]
                if(value>maxValue):
                     maxValue = value
                     maxRoute = j
            delta[t,i] = maxValue * B[i,O[t]]
            psi[t,i] = maxRoute
    return maxRoute

if __name__ == '__main__':
    init_t1()
    lastRoute = recursion()
    for t in range(T-1):
        print str(psi[t+1,N-1])+' , ',
    print lastRoute
