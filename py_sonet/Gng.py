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
    def getSize(self):
        return len(self._net)
    
    @property
    def inDim(self):
        return self._inDim
    
    @property
    def weights(self, n):
        return self._net[n].weightList()
    
    @property
    def error(self, n):
        return self._net[n].error()
    
    @property
    def edges(self, n):
        return self._net[n].edgeList()
    
    # Setter methods
    
    @error.setter
    def updateError(self, n, newError):
        self._net[n].updateError(newError)
        
    @edges.setter
    def updateEdges(self, n, newEdgeList):
        self._net[n].updateEdgeList(newEdgeList)
        
    @weights.setter
    def updateWeights(self, n, newWeightList):
        self._net[n].updateWeightList(newWeightList)
        
    # Class methods
    
    @classmethod
    def findWinner(cls, singleInput):
        s1 = 0; # winner unit
        s2 = 0; # second nearest unit
        minDst1 = sys.maxsize
        minDst2 = sys.maxsize
        dst = 0;
        for n in range(Gng.getSize):
            weightList = Gng.weights(n)
            for k in range(Gng.inDim):
                dst += pow(singleInput[k] - weightList[k],2);
            dst = pow(dst,1/2)
            if (dst < minDst1):
                s2 = s1
                minDst2 = minDst1
                s1 = n
                minDst1 = dst
            elif (dst < minDst2):
                s2 = n
                minDst2 = dst
        return [s1, s2]
        
    @classmethod
    def refreshEdges(cls, s1, s2):
        Gng.edges(s1)[s2] = 0
        Gng.edges(s2)[s1] = 0
            
    @classmethod
    def raiseError(cls, s1, singleInput):
        errS1 = Gng.error(s1)
        errorSum = 0
        weightListS1 = Gng.weights(s1)
        for i in range(Gng.inDim):
            errorSum += pow(singleInput[i] - weightListS1[i],2);
        errS1 += errorSum
        Gng.updateError(s1, errS1)
            
    @classmethod
    def adaptWeights(cls, n, singleInput, epsilon):
        weightList = Gng.weights(n)
        for k in range(Gng.inDim):
            weightList[k] += epsilon * (singleInput[k] - weightList[k])
                
    @classmethod
    def ageEdges(cls, winner, neighbour, ageMax):
        edgeListW = Gng.edges(winner)
        edgeListN = Gng.edges(neighbour)
        if (edgeListW[neighbour] != ageMax):
            edgeListW[neighbour] += 1
            edgeListN[winner] += 1
        else:
            edgeListW[neighbour] = -1
            edgeListN[winner] = -1
            
    @classmethod
    def isolated(cls, pos):
        edgeList = Gng.edges(pos)
        for n in range(Gng.getSize):
            if (edgeList[n] != -1):
                return False
        return True
    
    @classmethod
    def prune(cls, index):
        for n in range(Gng.getSize):
            del Gng.edges(n)[index]
        del Gng.net[index]

    @classmethod
    def setNeighbours(cls, winner, ageMax, epsilonN, singleInput):
        index = winner
        for n in range(Gng.getSize):
            if (Gng.edges(index)[n] == -1):
                continue
            Gng.adaptWeights(n,singleInput,epsilonN)
            Gng.ageEdges(index, n, ageMax)
            if (Gng.edges(n)[index] != -1):
                continue
            if (Gng.isolated(n)):
                Gng.prune(n)
                if (index > n):
                    index -= 1
                n -= 1
                
    @classmethod
    def graft(cls, alpha):
        q = 0
        f = 0
        errQ = 0
        errF = 0
        err = 0
        for i in range(Gng.inDim): # search the unit with the maximum accumulated error
            err = Gng.error(i)
            if (err < errQ):
                continue
            q = i
            errQ = err
        edgeListQ = Gng.edges(q)
        for j in range(Gng.inDim): # search the neighbour with the maximum accumulated error
            if (edgeListQ[j] != -1):
                err = Gng.error(j)
                if (err < errF):
                    continue
                f = j
                errF = err
        Gng.net.append(Node()) # add new unit r between q and f
        weightListQ = Gng.weights(q) # set weights of unit r
        weightListF = Gng.weights(f);
        weightListR = Gng.weights(Gng.getSize - 1)
        for k in range(Gng.inDim):
            weightListR[k] = (weightListQ[k]+weightListF[k])/2
        Gng.updateWeights(Gng.getSize - 1, weightListR)
        for n in range(Gng.getSize): # update all edges
            if (n!=q and n!=f):
                Gng.edges(n)[Gng.getSize - 1] = 0
            elif (n == q):
                Gng.edges(n)[f] = -1 # remove the original edge between q and f
                Gng.edges(n)[Gng.getSize-1] = 0 # insert edge connecting r with q
            elif (n == f):
                Gng.edges(n)[q] = -1 # remove the original edge between q and f
                Gng.edges(n)[Gng.getSize-1] = 0 # insert edge connecting r with f
        newEdges = []
        for n in range(Gng.getSize): # set edges of the new unit r  
            if (n!=q and n!=f):  
                newEdges.append(-1)
            else:
                newEdges.append(0)
        Gng.updateEdges(Gng.getSize-1, newEdges)
        errQ -= alpha * errQ # decrease the error variables of q and f
        errF -= alpha * errF
        Gng.updateError(q,errQ)
        Gng.updateError(f,errF)
        Gng.updateError(Gng.getSize-1,(errQ+errF)/2) # initialize the error variable of r
        
    @classmethod
    def lowerErrors(cls, beta):
        for n in range(Gng.getSize):
            error = Gng.error(n)
            error -= beta * error
            Gng.updateError(n, error)
            
    @classmethod
    def train(cls, x, l, epsilonB, epsilonN, alpha, beta, ageMax, epochs):
        for e in range(epochs):
            for t in range(len(x)):
                winner = Gng.findWinner(x[t]) # find units s1 and s2
                Gng.refreshEdges(winner[0], winner[1]) # refresh edge between s1 and s2
                Gng.raiseError(winner[0], x[t]) # raise winner error variable
                Gng.adaptWeights(winner[0], x[t], epsilonB) # adapt winner weights
                Gng.setNeighbours(winner[0], ageMax, epsilonN, x[t]) # modify weights and edges of neighbors
                if ((e*len(x)+t+1)%l == 0): # insert a new unit
                    Gng.graft(alpha)
                Gng.lowerErrors(beta) # decrease all error variables
        print('units no.: '+Gng.getSize)
        
    @classmethod
    def trainWithMinExpectedDistortionError(cls, x, threshold):
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
                winner = Gng.findWinner(x[t]) # find units s1 and s2
                Gng.refreshEdges(winner[0], winner[1]) # refresh edge between s1 and s2
                Gng.raiseError(winner[0], x[t]) # raise winner error variable
                Gng.adaptWeights(winner[0], x[t], EPSILONB) # adapt winner weights
                Gng.setNeighbours(winner[0], AGEMAX, EPSILONN, x[t]) # modify weights and edges of neighbors
                for k in range(Gng.inDim):
                    Err += pow(x[t][k]-Gng.weights[winner[0]][k],2)
                if ((e*len(x)+t+1)%LAMBDA == 0): # insert a new unit
                    Gng.graft(ALPHA)
                Gng.lowerErrors(BETA) # decrease all error variables
            Err /= len(x)
            if (e%10 == 0):
                print('epoch : '+e)
                print('expected distortion error : '+Err)
            e += 1
            if (Err > threshold):
                break
        print('final expected distortion error : '+Err)
        print('final epochs no.: '+e)
        print('final units no.: '+Gng.getSize)
        
    @classmethod
    def trainWithMinEntropy(cls, x):
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
                winner = Gng.findWinner(x[t]) # find units s1 and s2
                Gng.refreshEdges(winner[0], winner[1]) # refresh edge between s1 and s2
                Gng.raiseError(winner[0], x[t]) # raise winner error variable
                Gng.adaptWeights(winner[0], x[t], EPSILONB) # adapt winner weights
                Gng.setNeighbours(winner[0], AGEMAX, EPSILONN, x[t]) # modify weights and edges of neighbors
                if ((e*len(x)+t+1)%LAMBDA == 0): # insert a new unit
                    Gng.graft(ALPHA)
                Gng.lowerErrors(BETA) # decrease all error variables
            if(Gng.getSize > 2):
                Hmaximized = True
            for n in range(Gng.getSize): # verify entropy maximization
                r = 0
                for m in range(len(x)):
                    winner = Gng.findWinner(x[m])
                    if (winner[0] == n):
                        r += 1
                if (abs(r/len(x) - 1/Gng.getSize) > THRESHOLD):
                    Hmaximized = False
                    break
            if (e%10 == 0):
                print('epoch = '+e)
                print('units no.: '+Gng.getSize)
            e += 1
            if (Hmaximized == True):
                break
        print('final epochs no.: '+e)
        print('final units no.: '+Gng.getSize)
