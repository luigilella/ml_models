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

public class Code
{
  public float[] no;
  public Code(float[] c)
  {
    no = c;
  }
  public String toString()
  {
    return "code : "+no[0]+" "+no[1]+" "+no[2]+" "+no[3]+" "+no[4]+" "+no[5]+" "+no[6]
           +" "+no[7]+" "+no[8]+" "+no[9]+" "+no[10]+" "+no[11]+" "+no[12]+" "+no[13]+" || ";
           //+" "+no[14]+" "+no[15]+" "+no[16]+" "+no[17]+" "+no[18]+" "+no[19]+" "+no[20];
  }
}
