"""

Copyright (C) (2003) Luigi Lella

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either  version 3 of the License, or  (at your option)  any later
version.

This program is distributed  in the hope that it will be useful,  but WITHOUT 
ANY WARRANTY, without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License  along with 
this program. If not, see <http://www.gnu.org/licenses/>.

Description :

Class Som defines a self organizing map.

The constructor takes these arguments :

  rowNum, colNum - integers that define the network's size;
           inDim - integer that sets the input dimension.

The following public methods are available :

  rowNum, colNum, inDim, weight(i, j, k) -  getter  methods
                   for retrieving the four parameters of a Som object;
  findWinner(singleInput) - public  method  that  returns  the best  matching
                   node with the float[inDim] array singleInput.
  train(x, sigmaIn, sigmaFin, epsilonIn, epsilonFin, epochs) - setter  method
                   for adjusting the weights according  to Kohonen's training
                   rule :

                   w(t+1) - w(t) = epsilon(t) * h(t) * (x(t)-w(t))

                   where h(t) is the adaptation function :

                                - (|r1-s1| + |r2-s2|)^2
                   h(t) = exp (-------------------------)
                                     2 * sigma(t)^2

                   r = (r1,r2) are the winner coordinates and s = (s1,s2) are
                   the coordinates of the actual node. Sigma and epsilon  are
                   updated according to the following rules :

                                        sigmaFIN      t
                   sigma(t) = sigmaIN (----------)^(------)
                                        sigmaIN      tMAX

                                            epsilonFIN      t
                   epsilon(t) = epsilonIN (------------)^(------)
                                            epsilonIN      tMAX

                   The first argument  of train  is a  float[N][inDim]  array
                   containing N float[inDim] input arrays.
                   The other  five arguments  are parameters  of the training
                   algorithm. The last one in particular  sets how much times
                   the input sequence is presented.


"""

import random
import sys
import math

class Som(object):

    # SOM constructor
    
    def __init__(self, rowNum=1, colNum=1, inDim=1):
        self._rowNum = rowNum
        self._colNum = colNum
        self._inDim = inDim
        weight = []
        for i in range(rowNum):
            weight.append([])
            for j in range(colNum):
                weight[i].append([])
                for _ in range(inDim):
                    weight[i][j].append(random.uniform(0,1))
        self._weight = weight

    # Getter methods
    
    @property
    def rowNum(self):
        return self._rowNum
    
    @property
    def colNum(self):
        return self._colNum
    
    @property
    def inDim(self):
        return self._inDim
    
    @property
    def weight(self, rowNum, colNum, inDim):
        return self._weight[rowNum, colNum, inDim]
    
    # Training functions
    
    def _findWinner(self, singleInput):
        maxRow = -1
        maxCol = -1
        minDst = sys.maxsize
        for i in range(self._rowNum):
            for j in range(self._colNum):
                dst = 0;
                for k in range(self._inDim):
                    dst+=math.pow(singleInput[k]-self._weight[i][j][k], 2) 
                dst = math.sqrt(dst)
                if (dst < minDst):
                    maxRow = i
                    maxCol = j
                    minDst = dst
        return [maxRow, maxCol]
    
    def train (self, x, sigmaIn, sigmaFin, epsilonIn, epsilonFin, epochs):
        for e in range(epochs):
            for t in range(len(x)):
                sigma = sigmaIn * math.pow(sigmaFin/sigmaIn,(e*len(x)+t+1)/(epochs * len(x)))
                epsilon = epsilonIn * math.pow(epsilonFin/epsilonIn,(e*len(x)+t+1)/(epochs * len(x)))
                
                winner = self._findWinner (x[t])
                
                # weights adjusting
                weight = []
                for i in range(self._rowNum):
                    weight.append([])
                    for j in range(self._colNum):
                        weight[i].append([])
                        for k in range(self._inDim):
                            weight[i][j].append(self._weight(i,j,k) + epsilon * (x[t][k] - self._weight(i,j,k)) * math.exp((- math.pow(abs(i - winner[0]) + abs(j - winner[1]),2)) /(2 * math.pow(sigma,2))))
                self._weight = weight
