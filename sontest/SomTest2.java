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

/**
 *
 * @author  Luigi Lella
 */
public class SomTest2 
{
    public Som net;
    
    /** Creates a new instance of SomTest2 */
    public SomTest2(int inputNo) 
    {
        float[][] inputList = new float[inputNo][2];
        
        for(int i=0; i<inputNo; i++) // input creation
        {
            inputList[i][0] = (float) Math.random()*400;
            inputList[i][1] = (float) Math.random()*400;
            if ((Math.sqrt(Math.pow(inputList[i][0]-200,2)+Math.pow(inputList[i][1]-200,2))<100)||
                (Math.sqrt(Math.pow(inputList[i][0]-200,2)+Math.pow(inputList[i][1]-200,2))>200))
                    i--;
        }
                
        net = new Som(7, 7, 2); // net creation and training
        net.train(inputList, 1, 0.01, 0.1, 0.001, 10);

    }
    
}
