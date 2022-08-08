/****************************************************************************

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

*****************************************************************************/

package sonet;

public class Node
{
  public int[] edgeList;
  public float error;
  public float[] weightList;
  
  public Node(int inDim, int netSize)
  {
      edgeList = new int[netSize];
      for(int i=0; i<netSize; i++)
          edgeList[i] = -1;
      
      error = 0;
      
      weightList = new float[inDim];
      for(int i=0; i<inDim; i++)
          weightList[i] = (float) Math.random()*400;
  }
}
