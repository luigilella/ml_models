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

"""

import random 

class Node(object):
    
    # Node constructor
    
    def __init__(self, inDim=1, netSize=1):
        edgeList = []
        for _ in range(netSize):
            edgeList.append(-1)
        error = 0
        weightList = []
        for _ in range(inDim):
            weightList.append(random.uniform(0,1))
        self._edgeList = edgeList
        self._error = error
        self._weightList = weightList
            
    # Getter methods
    
    @property
    def edgeList(self):
        return self._edgeList
    
    @property
    def error(self):
        return self._error
    
    @property
    def weightList(self):
        return self._weightList
    
    # Setter methods
    
    @edgeList.setter
    def updateEdgeList(self, newEdgeList):
        self._edgeList = newEdgeList
        
    @error.setter
    def updateError(self, newError):
        self._error = newError
      
    @weightList.setter    
    def updateWeightList(self, newWeightList):
        self._weightList = newWeightList
