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

Description :

Class Som defines a self organizing map.

The constructor takes these arguments :

  rowNum, colNum - integers that define the network's size;
           inDim - integer that sets the input dimension.

The following public methods are available :

  getRowNum(), getColNum(), getInDim(), getWeight(i, j, k) -  getter  methods
                   for retrieving the four data items of a Som object;
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


*****************************************************************************/

package sonet;

public class Som
{
  private float[][][] weight;
  private int rowNum, colNum, inDim;

  // SOM contructor
  public Som (int d1, int d2, int dx)
  {
    weight = new float[d1][d2][dx];
    for (int i=0; i<d1; i++)
      for (int j=0; j<d2; j++)
        for (int k=0; k<dx; k++)
          weight[i][j][k] = (float) Math.random()*400;
    rowNum = d1;
    colNum = d2;
    inDim = dx;
  }

  // Getter methods
  public int getRowNum ()
  {
    return rowNum;
  }
  public int getColNum ()
  {
    return colNum;
  }
  public int getInDim ()
  {
    return inDim;
  }
  public float getWeight (int i, int j, int k)
  {
    return weight [i][j][k];
  }

  // Setter method for training
  public void train (float[][] x, double sigmaIn, double sigmaFin,
  double epsilonIn, double epsilonFin, int epochs)
  {
    int[] winner = new int[2];
    float sigma, epsilon;
    
    for (int e=0; e<epochs; e++)
    {
      for (int t=0; t<x.length; t++)
      {
        sigma = (float) (sigmaIn * (Math.pow((sigmaFin/sigmaIn),(e*x.length+t+1)/
        (epochs * x.length))));
        epsilon = (float) (epsilonIn * (Math.pow((epsilonFin/epsilonIn),(e*x.length+t+1)/
        (epochs * x.length))));

        winner = findWinner (x[t]);

        // weights adjusting
          for (int i=0; i<rowNum; i++)
            for (int j=0; j<colNum; j++)
              for (int k=0; k<inDim; k++)
              {
                weight[i][j][k] += epsilon * (x[t][k] -
                weight[i][j][k]) * (float) Math.exp((- Math.pow
                (Math.abs(i - winner[0]) + Math.abs(j - winner[1]),2))
                /(2 * Math.pow(sigma,2)));
              }
      }   
    }
  }

  // winner searching
  public int[] findWinner (float[] singleInput)
  {
    int maxRow = -1, maxCol = -1;
    int[] result = new int[2];
    float dst, minDst = 3.4E38f;
    for (int i=0; i<rowNum; i++)
      for (int j=0; j<colNum; j++)
      {
        dst = 0;
        for (int k=0; k<inDim; k++)
          dst += Math.pow(singleInput[k] - weight[i][j][k],2);
        dst = (float) Math.sqrt(dst);
        if (dst < minDst)
        {
          maxRow = i;
          maxCol = j;
          minDst = dst;
        }
      }
    result[0] = maxRow;
    result[1] = maxCol;
    return result;
  }
}
