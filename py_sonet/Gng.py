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

Class Gng defines a growing neural gas network.

The constructor takes only an argument that is an integer that sets the input
dimension.

The following public methods are available :

  net,getSize,inDim,weights(n),edges(n),error(n) - getter methods
                   for retrieving the six parameters of a Gng object;
  findWinner(singleInput) - public  method  that  returns  the best  matching
                   node with the float[inDim] array singleInput.
  train(x, lambda, epsilonB, epsilonN, alpha, beta, ageMax, epochs) -  setter
                   method  for adjusting  the weights  according to Fritzke's
                   algorithm :

                   0. Start with two units a and b at random positions wa and
                     wb;
                   1. Find the nearest unit s1 and the second nearest unit s2;
                   2. If  s1 and s2  are connected by an edge, set the age of
                     this edge to zero. If such an edge does not exist,create
                     it;
                   3. Add the squared distance  between  the input signal and
                     the winner s1 to a local counter variable :

                     error(s1, t2) - error(s1, t1) = ||wS1 - x||^2

                   4. Move s1 and its direct topological neighbours towards x
                     by fractions epsilonB and epsilonN, respectively, of the
                     total distance :

                     wS1(t2) - wS2(t1) = epsilonB(x - wS1(t1))
                     wSn(t2) - wSn(t1) = epsilonN(x - wSn(t1)) for all direct
                                                            neighbours n of s1;

                   5. Increment the age of all edges emanating from s1;
                   6. Remove  edges  with an age  larger than ageMax. If this
                     results in points having no emanating edges, remove them;
                   7. If the number of the input signals  generated so far is
                     an integer  multiple of a parameter lambda, insert a new
                     unit as follows :
                       - Determine  the unit q  with the maximum  accumulated
                         error;
                       - Insert  a  new unit  r  halfway  between  q  and its
                         neighbour f with the largest error variable;
                       - Insert edges connecting the new unit r  with units q
                         and f, and remove the original edge between q and f;
                       - Decrease   the  error  variables   of  q  and  f  by
                         multiplying them with a constant  alpha. Interpolate
                         the error variable of r from q and f :

                         error(r) = (error(q) - error(f))/2

                   8. Decrease the error variables of all units :

                     error(t2) - error(t1) = - beta * error(t1)

                   9. If a stopping criterion  is not yet fulfilled  continue
                     with step 2.

                   The first argument  of train  is a  float[N][inDim]  array
                   containing N float[inDim] input arrays.
                   The other  seven arguments are parameters  of the training
                   algorithm. The last one in particular  sets how much times
                   the input sequence is presented.


"""

import Node
import sys
import math

class Gng(object):
    
    # GNG constructor
    
    def __init__(self, inDim=1):
        net = []
        for _ in range(2): # create first nodes a and b
            net.append(Node(inDim))
        self._net = net
        self._inDim = inDim
        
    # Getter methods
    
    @property
    def net(self):
        return self._net
    
    @property
    def inDim(self):
        return self._inDim
        
    # Training functions
    
    def _findWinner(self, singleInput):
        s1 = 0; # winner unit
        s2 = 0; # second nearest unit
        minDst1 = sys.maxsize
        minDst2 = sys.maxsize
        dst = 0;
        for n in range(len(self._net)):
            weightList = self._net[n].weightList()
            for k in range(self._inDim):
                dst += math.pow(singleInput[k] - weightList[k],2);
            dst = math.sqrt(dst)
            if (dst < minDst1):
                s2 = s1
                minDst2 = minDst1
                s1 = n
                minDst1 = dst
            elif (dst < minDst2):
                s2 = n
                minDst2 = dst
        return [s1, s2]
        
    def _refreshEdges(self, s1, s2):
        newEdgeListS1 = self._net[s1].edgeList()
        newEdgeListS2 = self._net[s2].edgeList()
        newEdgeListS1[s2] = 0 
        newEdgeListS2[s1] = 0
        self._net[s1].updateEdgeList(newEdgeListS1) 
        self._net[s2].updateEdgeList(newEdgeListS2)
            
    def _raiseError(self, s1, singleInput):
        errS1 = self._net[s1].error()
        weightListS1 = self._net[s1].weightList()
        for i in range(self._inDim):
            errS1 += math.pow(singleInput[i] - weightListS1[i],2);
        self._net[s1].updateError(errS1)
            
    def _adaptWeights(self, n, singleInput, epsilon):
        weightList = self._net[n].weightList()
        for k in range(self._inDim):
            weightList[k] += epsilon * (singleInput[k] - weightList[k])
        self._net[n].updateWeightList(weightList)
                
    def _ageEdges(self, winner, neighbour, ageMax):
        edgeListW = self._net[winner].edgeList()
        edgeListN = self._net[neighbour].edgeList()
        if (edgeListW[neighbour] != ageMax):
            edgeListW[neighbour] += 1
            edgeListN[winner] += 1
        else:
            edgeListW[neighbour] = -1
            edgeListN[winner] = -1
        self._net[winner].updateEdgeList(edgeListW)
        self._net[neighbour].updateEdgeList(edgeListN)
            
    def _isolated(self, pos):
        edgeList = self._net[pos].edgeList()
        for n in range(len(self._net)):
            if (edgeList[n] != -1):
                return False
        return True
    
    def _prune(self, index):
        for n in range(len(self._net)):
            newEdgeList = self._net[n].edgeList()
            del newEdgeList[index]
            self._net[n].updateEdgeList(newEdgeList)
        del self._net[index]

    def _setNeighbours(self, winner, ageMax, epsilonN, singleInput):
        index = winner
        for n in range(len(self._net)):
            if (self._net[index].edgeList(n) == -1):
                continue
            self._adaptWeights(n,singleInput,epsilonN)
            self._ageEdges(index, n, ageMax)
            if (self._net[n].edgeList(index) != -1):
                continue
            if (self._isolated(n)):
                self._prune(n)
                if (index > n):
                    index -= 1
                n -= 1
                
    def _graft(self, alpha):
        q = 0
        f = 0
        errQ = 0
        errF = 0
        err = 0
        for i in range(self._inDim): # search the unit with the maximum accumulated error
            err = self._net[i].error()
            if (err < errQ):
                continue
            q = i
            errQ = err
        edgeListQ = self._net[q].edgeList()
        for j in range(self._inDim): # search the neighbour with the maximum accumulated error
            if (edgeListQ[j] != -1):
                err = self._net[j].error()
                if (err < errF):
                    continue
                f = j
                errF = err
        newSize = len(self._net) + 1
        self._net.append(Node(self._inDim,newSize)) # add new unit r between q and f
        weightListQ = self._net[q].weightList() 
        weightListF = self._net[f].weightList()
        weightListR = self._net[len(self._net)-1].weightList()
        for k in range(self._inDim):
            weightListR[k] = (weightListQ[k]+weightListF[k])/2 
        self._net[len(self._net)-1].updateWeightList(weightListR) # set weights of unit r
        for n in range(len(self._net)): # update all edges
            newEdgeList = self._net[n].edgeList()
            if (n!=q and n!=f and n!=len(self._net)-1):
                newEdgeList.append(-1)
            elif (n == q):
                newEdgeList[f] = -1 # remove the original edge between q and f
                newEdgeList.append(0) # insert edge connecting q with r
            elif (n == f):
                newEdgeList[q] = -1 # remove the original edge between q and f
                newEdgeList.append(0) # insert edge connecting f with r
            elif (n == len(self._net)-1):
                newEdgeList[q] = 0 # insert edge connecting r with q
                newEdgeList[f] = 0 # insert edge connecting r with f
            self._net[n].updateEdgeList(newEdgeList)
        errQ -= alpha * errQ # decrease the error variables of q and f
        errF -= alpha * errF
        self._net[q].updateError(errQ)
        self._net[f].updateError(errF)
        self._net[len(self._net)-1].updateError((errQ+errF)/2) # initialize the error variable of r
        
    def _lowerErrors(self, beta):
        for n in range(len(self._net)):
            error = self._net[n].error()
            error -= beta * error
            self._net[n].updateError(error)
            
    def train(self, x, l, epsilonB, epsilonN, alpha, beta, ageMax, epochs):
        for e in range(epochs):
            for t in range(len(x)):
                winner = self._findWinner(x[t]) # find units s1 and s2
                self._refreshEdges(winner[0], winner[1]) # refresh edge between s1 and s2
                self._raiseError(winner[0], x[t]) # raise winner error variable
                self._adaptWeights(winner[0], x[t], epsilonB) # adapt winner weights
                self._setNeighbours(winner[0], ageMax, epsilonN, x[t]) # modify weights and edges of neighbors
                if ((e*len(x)+t+1)%l == 0): # insert a new unit
                    self._graft(alpha)
                self._lowerErrors(beta) # decrease all error variables
        print('units no.: '+len(self._net))
        
    def trainWithMinExpectedDistortionError(self, x, threshold):
        AGEMAX = 88
        LAMBDA = 300
        ALPHA = 0.5
        BETA = 0.0005
        EPSILONB = 0.05
        EPSILONN = 0.0006
        e = 0 
        while True:
            Err = 0
            for t in range(len(x)):
                winner = self._findWinner(x[t]) # find units s1 and s2
                self._refreshEdges(winner[0], winner[1]) # refresh edge between s1 and s2
                self._raiseError(winner[0], x[t]) # raise winner error variable
                self._adaptWeights(winner[0], x[t], EPSILONB) # adapt winner weights
                self._setNeighbours(winner[0], AGEMAX, EPSILONN, x[t]) # modify weights and edges of neighbors
                w0 = winner[0]
                weightList = self._net[w0].weightList()
                for k in range(Gng.inDim):
                    Err += math.pow(x[t][k]-weightList[k],2)
                if ((e*len(x)+t+1)%LAMBDA == 0): # insert a new unit
                    self._graft(ALPHA)
                self._lowerErrors(BETA) # decrease all error variables
            Err /= len(x)
            if (e%10 == 0):
                print('epoch : '+e)
                print('expected distortion error : '+Err)
            e += 1
            if (Err > threshold):
                break
        print('final expected distortion error : '+Err)
        print('final epochs no.: '+e)
        print('final units no.: '+len(self._net))
        
    def trainWithMinEntropy(self, x):
        AGEMAX = 88
        LAMBDA = 300
        ALPHA = 0.5
        BETA = 0.0005
        EPSILONB = 0.05
        EPSILONN = 0.0006
        THRESHOLD = 0.01
        Hmaximized = False
        e = 0 
        while True:
            for t in range(len(x)):
                winner = self._findWinner(x[t]) # find units s1 and s2
                self._refreshEdges(winner[0], winner[1]) # refresh edge between s1 and s2
                self._raiseError(winner[0], x[t]) # raise winner error variable
                self._adaptWeights(winner[0], x[t], EPSILONB) # adapt winner weights
                self._setNeighbours(winner[0], AGEMAX, EPSILONN, x[t]) # modify weights and edges of neighbors
                if ((e*len(x)+t+1)%LAMBDA == 0): # insert a new unit
                    self._graft(ALPHA)
                self._lowerErrors(BETA) # decrease all error variables
            if(len(self._net) > 2):
                Hmaximized = True
            for n in range(len(self._net)): # verify entropy maximization
                r = 0
                for m in range(len(x)):
                    winner = self._findWinner(x[m])
                    if (winner[0] == n):
                        r += 1
                if (abs(r/len(x) - 1/len(self._net)) > THRESHOLD):
                    Hmaximized = False
                    break
            if (e%10 == 0):
                print('epoch = '+e)
                print('units no.: '+len(self._net))
            e += 1
            if (Hmaximized == True):
                break
        print('final epochs no.: '+e)
        print('final units no.: '+len(self._net))
