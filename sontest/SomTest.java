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

package sontest;

import sonet.Som;

public class SomTest 
{
    public Som net;
    
    /** Creates a new instance of SomTest */
    public SomTest(int inputNo) 
    {
        float[][] inputList = new float[inputNo][2];
        
        for(int i=0; i<inputNo; i++) // input creation
        {
            inputList[i][0] = (float) Math.random()*400;
            inputList[i][1] = (float) Math.random()*400;
            if ((inputList[i][0]<125)||(inputList[i][0]>275))
            {
                if ((inputList[i][1]>125)&&(inputList[i][1]<275))
                    i--;
            }
            else 
                i--;
        }
                
        net = new Som(7, 7, 2); // net creation and training 
        net.train(inputList, 1, 0.1, 0.5, 0.005, 10);       
    }
    
}
