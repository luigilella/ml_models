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

package showres;

import sonet.Som;
import sontest.SomTest;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SomRes extends JFrame
{
    public SomRes() 
    {
        super("Self Organizing Map");
        setSize (500,500);
        setBackground(Color.black);
        show();
    }
    
    public void paint (Graphics g)
    {
        g.setColor(Color.white); // draw testbench
        g.fillRect(50,50,125,125);
        g.fillRect(50,325,125,125);
        g.fillRect(325,50,125,125);
        g.fillRect(325,325,125,125);
        
        SomTest test = new SomTest(30000); // train network
        Som net = test.net;
        
        int x = 0;
        int y = 0;
        g.setColor(Color.gray);
        for(int i=0; i<net.getRowNum(); i++) //draw centers
            for(int j=0; j<net.getColNum(); j++)
            {
                x = (int)net.getWeight(i,j,0) + 50 - 2;
                y = (int)net.getWeight(i,j,1) + 50 - 2;
                g.drawOval(x,y,5,5);
                if(i!=0)
                    g.drawLine(50 + (int)net.getWeight(i,j,0),
                        50 + (int)net.getWeight(i,j,1),
                        50 + (int)net.getWeight(i-1,j,0),
                        50 + (int)net.getWeight(i-1,j,1));
                if(j!=0)
                    g.drawLine(50 + (int)net.getWeight(i,j,0),
                        50 + (int)net.getWeight(i,j,1),
                        50 + (int)net.getWeight(i,j-1,0),
                        50 + (int)net.getWeight(i,j-1,1));
                if(i!=net.getRowNum()-1)
                    g.drawLine(50 + (int)net.getWeight(i,j,0),
                        50 + (int)net.getWeight(i,j,1),
                        50 + (int)net.getWeight(i+1,j,0),
                        50 + (int)net.getWeight(i+1,j,1));
                if(j!=net.getColNum()-1)
                    g.drawLine(50 + (int)net.getWeight(i,j,0),
                        50 + (int)net.getWeight(i,j,1),
                        50 + (int)net.getWeight(i,j+1,0),
                        50 + (int)net.getWeight(i,j+1,1));
            }

    }

    public static void main (String args[])
    {
        SomRes app = new SomRes();
        app.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            }
        );
    }
}
