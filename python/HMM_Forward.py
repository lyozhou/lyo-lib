from __future__ import division
import  numpy as np


N = 3   # hidden state
M = 4   # observe state
T = 3   # observe sequence number

A = np.array([
    [0.5,0.375,0.125],
    [0.25,0.125,0.625],
    [0.25,0.375,0.375]])
B = np.array([
    [0.6,0.2,0.15,0.05],
    [0.25,0.25,0.25,0.25],
    [0.05,0.1,0.35,0.5]])
pi = np.array([0.63,0.17,0.2])

alpha = np.zeros((T,N),np.float)

O = np.array([0,2,3])   # calculate posiibility of O sequence


# initialize t = 1
def init_t1():
    for i in range(N):
        alpha[0,i] = pi[i]*B[i,O[0]]

def recursion():
    # t = 1 is ignore, because it has calculate in init_t1()
    for t in range(T-1):   # t = 2
        for i in range(N):  # hidden state when t = 2
            for j in range(N):  # hidden state when t = 1
                alpha[t+1,i] += alpha[t,j]*A[j,i]
            alpha[t+1,i] *= B[i,O[t+1]]
    #print alpha

if __name__ == '__main__':
    init_t1()
    recursion()
    P = 0
    for i in range(N):
        P += alpha[T-1,i]
    print P
