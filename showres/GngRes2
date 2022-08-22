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

import sonet.Gng;
import sontest.GngTest2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GngRes2 extends JFrame
{
    public GngRes2() 
    {
        super("Growing Neural Gas");
        setSize (500,500);
        setBackground(Color.black);
        show();
    }
    
    public void paint (Graphics g)
    {
        g.setColor(Color.white); // draw testbench
        g.fillOval(50,50,400,400);
        g.setColor(Color.black);
        g.fillOval(150,150,200,200);
        
        GngTest2 test = new GngTest2(10000); // train network
        Gng net = test.net;
        
        int x = 0;
        int y = 0;
        int[] edgeList = new int[net.getSize()];
        g.setColor(Color.gray);
        for(int i=0; i<net.getSize(); i++) //draw centers
        {
            x = (int)net.getWeights(i)[0] + 50 - 2;
            y = (int)net.getWeights(i)[1] + 50 - 2;
            g.drawOval(x,y,5,5);
            edgeList = net.getEdges(i);
            for(int j=0; j<net.getSize(); j++)
                if(edgeList[j] != -1)
                    g.drawLine(50 + (int)net.getWeights(i)[0],
                        50 + (int)net.getWeights(i)[1],
                        50 + (int)net.getWeights(j)[0],
                        50 + (int)net.getWeights(j)[1]);
        }

    }

    public static void main (String args[])
    {
        GngRes2 app = new GngRes2();
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
