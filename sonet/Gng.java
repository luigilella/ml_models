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

Class Gng defines a growing neural gas network.

The constructor takes only an argument that is an integer that sets the input
dimension.

The following public methods are available :

  getSize(),getInDim(),getWeights(n),getEdges(n),getError(n) - getter methods
                   for retrieving the five data items of a Gng object;
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


*****************************************************************************/

package sonet;

import java.util.ArrayList;

public class Gng
{
  private ArrayList<Node> net;
  private final int inDim;

  // GNG constructor
  public Gng(int dx)
  {
    net = new ArrayList<Node>();
    for(int i=0; i<2; i++) // create first nodes a and b
      net.add(new Node(dx,2));
    inDim = dx;
  }

  // Getter methods
  public int getSize()
  {
    return net.size();
  }
  public int getInDim()
  {
    return inDim;
  }
  public float[] getWeights(int n)
  {
    Node node = (Node) net.get(n);
    return node.weightList;
  }
  public int[] getEdges(int n)
  {
    Node node = (Node) net.get(n);
    return node.edgeList;
  }
  public float getError(int n)
  {
    Node node = (Node) net.get(n);
    return node.error;
  }

  // Setter methods

  private void setEdges(int n, int[] edgeList)
  {
    Node node = (Node) net.get(n);
    node.edgeList = edgeList;
  }

  private void setError(int n, float error)
  {
    Node node = (Node) net.get(n);
    node.error = error;
  }
  
  // Other methods

  public int[] findWinner(float[] singleInput)
  {
    int s1 = 0; // winner unit
    int s2 = 0; // second nearest unit
    int[] result = new int[2];
    float minDst1 = 3.4E38f;
    float minDst2 = 3.4E38f;
    float dst = 0;
    float[] weightList;
    
    for(int n=0; n<net.size(); n++)
    {
      weightList = getWeights(n);
      for(int k=0; k<inDim; k++)
        dst += Math.pow(singleInput[k] - weightList[k],2);
      dst = (float) Math.sqrt(dst);
      if (dst < minDst1)
      {
        s2 = s1;
        minDst2 = minDst1;
        s1 = n;
        minDst1 = dst;
      }
      else if (dst < minDst2)
      {
        s2 = n;
        minDst2 = dst;
      }
      dst = 0;
    }
    
    result[0] = s1;
    result[1] = s2;
    return result;
  }
  
  public void refreshEdges(int s1, int s2)
  {
      int[] edgeListS1 = getEdges(s1);
      int[] edgeListS2 = getEdges(s2);
      
      edgeListS1[s2] = 0;
      edgeListS2[s1] = 0;
  }
  
  public void raiseError(int s1, float[] singleInput)
  {
      float errS1 = getError(s1);
      float sum = 0;
      float[] weightListS1 = getWeights(s1);
      
      for(int i=0; i<inDim; i++)
          sum += Math.pow(singleInput[i]-weightListS1[i],2);
      errS1 += sum;
      setError(s1, errS1);
  }
  
  private void adaptWeights(int n, float[] input, double epsilon)
  {
    float[] weightList = getWeights(n);
    for(int k=0; k<inDim; k++)
      weightList[k] += epsilon * (input[k] - weightList[k]);
  }
  
  private void ageEdges(int winner, int neighbour, int ageMax)
  {
      int[] edgeListW = getEdges(winner);
      int[] edgeListN = getEdges(neighbour);
      
      if (edgeListW[neighbour] != ageMax)
      {
          edgeListW[neighbour] ++;
          edgeListN[winner] ++;
      }
      else
      {
          edgeListW[neighbour] = -1;
          edgeListN[winner] = -1;
      }
  }
  
  private boolean isolated (int pos)
  {
      int[] edgeList = getEdges(pos);
      boolean result = true;
      
      for (int n=0; n<net.size(); n++)
          if (edgeList[n] != -1)
          {
              result = false;
              break;
          }
      
      return result;
  }
  
  private void prune(int index)
  {
    int[][] newEdges = new int[net.size()][net.size()-1];
    
    for(int n=0; n<net.size(); n++) // update all edges
    {
      if (n == index) continue;
      System.arraycopy(getEdges(n),0,newEdges[n],0,index);
      System.arraycopy(getEdges(n),index+1,newEdges[n],index,net.size()-1-index);
      setEdges(n,newEdges[n]);
    }
    
    net.remove(index);
  }
  
  private void setNeighbours(int winner, int ageMax, double epsilonN, float[] singleInput)
  {
    int index = winner;
    
    for(int n=0; n<net.size(); n++) 
    {
      if (getEdges(index)[n] == -1) continue;
      
      adaptWeights(n,singleInput,epsilonN);

      ageEdges(index, n, ageMax);
      
      if(getEdges(n)[index] != -1) continue; 
      
      if(isolated(n))
      {
          prune(n); 
          if (index > n)
              index --;
          n --;
      }
    }
  }
  
  private void graft(double alpha)
  {
    int q = 0;
    int f = 0;
    float errQ = 0;
    float errF = 0;
    float err = 0;
    float[] weightListQ;
    float[] weightListF;
    float[] weightListR;
    int[] edgeListQ;
    
    for (int i=0; i<net.size(); i++) // search the unit with the maximum accumulated error
    {       
      err = getError(i);
      if (err < errQ) continue;
      q = i;
      errQ = err;
    }
    edgeListQ = getEdges(q);
    
    for(int j=0; j<net.size(); j++) // search the neighbour with the maximum accumulated error
      if (edgeListQ[j] != -1)
      {
        err = getError(j);
        if (err < errF) continue;
        f = j;
        errF = err;
      }
    
    net.add(new Node(inDim,net.size()+1)); // add new unit r between q and f
    
    weightListQ = getWeights(q); // set weights of unit r
    weightListF = getWeights(f);
    weightListR = getWeights(net.size()-1);
    for(int k=0; k<inDim; k++)
      weightListR[k] = (weightListQ[k]+weightListF[k])/2;

    int[][] newEdges = new int[net.size()][net.size()]; // update all edges
    for(int n=0; n<net.size()-1; n++)
    {
      System.arraycopy(getEdges(n),0,newEdges[n],0,net.size()-1);
      if ((n!=q)&&(n!=f))
        newEdges[n][net.size()-1] = -1;
      else if (n == q)
      {
        newEdges[n][f] = -1; // remove the original edge between q and f
        newEdges[n][net.size()-1] = 0; // insert edge connecting r with q
      }
      else if (n == f)
      {
        newEdges[n][q] = -1; // remove the original edge between q and f
        newEdges[n][net.size()-1] = 0; // insert edge connecting r with f
      }
      setEdges(n,newEdges[n]);
    }
    for(int n=0; n<net.size(); n++) // set edges of the new unit r
      newEdges[net.size()-1][n]=(((n!=q)&&(n!=f)) ? -1 : 0);
    setEdges(net.size()-1,newEdges[net.size()-1]);
    
    errQ -= alpha * errQ; // decrease the error variables of q and f
    errF -= alpha * errF;
    setError(q,errQ);
    setError(f,errF);
    
    setError(net.size()-1,(errQ+errF)/2); // initialize the error variable of r
  }
  
  private void lowerErrors(double beta)
  {
    float[] error = new float[net.size()];
    for(int n=0; n<net.size(); n++)
    {
      error[n] = getError(n);
      error[n] -= beta * error[n];
      setError(n,error[n]);
    }
  }
  
  public int[][] linksMatrix()
    {
        int[][] matrix = new int[net.size()][net.size()];
        //int pos;

        for(int i=0; i<net.size(); i++)
            for(int j=0; j<net.size();j++)
                if (((Node)net.get(i)).edgeList[j]>-1)
                    matrix[i][j]=1;
        
        /*for(int i=0; i<net.size(); i++)
        {
            for(int j=0; j<net.size(); j++)
                System.out.print(matrix[i][j]+" ");
            System.out.println();
        }*/
        
        return matrix;
    }

  public void train(float[][] x, int lambda, double epsilonB, double epsilonN,
  double alpha, double beta, int ageMax, int epochs)
  {
    int[] winner = new int[2];
    //int n = 0;
    //System.out.println("|------------|------------|------------|------------|");
    //System.out.print("*");
    
    for (int e=0; e<epochs; e++)
    {
      //n ++;
      /*if (n>(epochs/52))
      {
        System.out.print("*");
        n = 0;
      }*/
      for (int t=0; t<x.length; t++)
      {
        winner = findWinner(x[t]); // find units s1 and s2
        refreshEdges(winner[0], winner[1]); // refresh edge between s1 and s2
        raiseError(winner[0], x[t]); // raise winner error variable
        adaptWeights(winner[0], x[t], epsilonB); // adapt winner weights
        setNeighbours(winner[0], ageMax, epsilonN, x[t]); //modify weights and edges of neighbors
        if ((e*x.length+t+1)%lambda == 0) // insert a new unit
          graft(alpha); 
        lowerErrors(beta); // decrease all error variables
      }
    }
    //System.out.print("*");
    //System.out.println();
    System.out.println("units no.: " + net.size());
  }

  public void train(float[][] x, double threshold)
  {
    final int AGEMAX = 88;
    final int LAMBDA = 300;
    final double ALPHA = 0.5;
    final double BETA = 0.0005;
    final double EPSILONB = 0.05;
    final double EPSILONN = 0.0006;
    int e=0;
    int[] winner = new int[2];
    double Err;
    
    do 
    {
      Err = 0;
      for (int t=0; t<x.length; t++)
      {
        winner = findWinner(x[t]); // find units s1 and s2
        refreshEdges(winner[0], winner[1]); // refresh edge between s1 and s2
        raiseError(winner[0], x[t]); // raise winner error variable
        adaptWeights(winner[0], x[t], EPSILONB); // adapt winner weights
        setNeighbours(winner[0], AGEMAX, EPSILONN, x[t]); //modify weights and edges of neighbors
        for(int k=0; k<inDim; k++)
          Err += Math.pow(x[t][k]-getWeights(winner[0])[k],2);
        if ((e*x.length+t+1)%LAMBDA == 0)
          graft(ALPHA); // insert a new unit
        lowerErrors(BETA); // decrease all error variables
      }
      Err /= x.length;
      if (e%10 == 0)
      {
        System.out.print("Epochs = "+e+"; ");
        System.out.println("Expected distortion error = "+Err);
      }
      e++;
    }while (Err > threshold);
    System.out.println("Expected distortion error = "+Err);
    System.out.println();
    System.out.println("epochs no.: " + e);
    System.out.println("units no.: " + net.size());
  }

  public void train(float[][] x)
  {
    final int AGEMAX = 88;
    final int LAMBDA = 300;
    final double ALPHA = 0.5;
    final double BETA = 0.0005;
    final double EPSILONB = 0.05;
    final double EPSILONN = 0.0006;
    final double THRESHOLD = 0.01;
    boolean Hmaximized = false;
    int e=0, r=0;
    int[] winner = new int[2];
    
    while(Hmaximized == false)
    {
      for (int t=0; t<x.length; t++)
      {
        winner = findWinner(x[t]); // find units s1 and s2
        refreshEdges(winner[0], winner[1]); // refresh edge between s1 and s2
        raiseError(winner[0], x[t]); // raise winner error variable
        adaptWeights(winner[0], x[t], EPSILONB); // adapt winner weights
        setNeighbours(winner[0], AGEMAX, EPSILONN, x[t]); //modify weights and edges of neighbors
        if ((e*x.length+t+1)%LAMBDA == 0)
          graft(ALPHA); // insert a new unit
        lowerErrors(BETA); // decrease all error variables
      }
      
      if(net.size() > 2)
        Hmaximized = true;
      
      for(int n=0; n<net.size(); n++) // verify entropy maximization
      {
        r = 0;
        for(int m=0; m<x.length; m++)
        {
          winner = findWinner(x[m]);
          if (winner[0] == n)
            r++;
        }
        if (Math.abs((double)r/x.length - (double)1/net.size()) > THRESHOLD)
        {
          Hmaximized = false;
          break;
        }
      } 

      if (e%10 == 0)
      {
        System.out.print("Epochs = "+e+"; ");
        System.out.println("units no.: " + net.size());
      }
      e++;
    }
    
    System.out.println();
    System.out.println("epochs no.: " + e);
    System.out.println("units no.: " + net.size());
  }
}
