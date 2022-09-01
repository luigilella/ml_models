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

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import sonet.Gng;

public class WorkingMemory
{
  private int codeSize;
  @SuppressWarnings("rawtypes")
  private HashMap codeMap;
  private Gng gng;

  @SuppressWarnings("rawtypes")
  public WorkingMemory(int cSize) 
  {
    codeSize = cSize;
    codeMap = new HashMap();
    gng = new Gng(2*codeSize);
  }

  // Setter methods

  @SuppressWarnings("unchecked")
  public void coding (BufferV2 buffer)
  {
    int n = 0;
    float remainder, member;
    float[][] code = new float[buffer.getSize()][codeSize];
    String word;
    @SuppressWarnings("rawtypes")
	Iterator iterator = buffer.begin();
    member = (float) codeMap.size();
    while (iterator.hasNext())
    {
      word = (String) iterator.next();
      if (codeMap.get(word) == null) // uncodified word
      {
        if (member > Math.pow(2,(codeSize-1)))
          System.out.println("Warning : code size too short.");

        remainder = member;
        for(int i=0; i<codeSize; i++)
        {
          code[n][i] = (float)((int)(remainder/Math.pow(2,(codeSize-1-i))));
          remainder %= (float)Math.pow(2,(codeSize-1-i));
        }
        codeMap.put(word, new Code(code[n]));
        member += 1f;
      }
      n++;
    }
  }

  public void memorize (float[][] x)
  {
    //gng.train(x,0.00001);
    //gng.train(x,0.001);
	//gng.train(x,0.01);
  	gng.train(x,0.1); //<===
  }

  // Getter methods
  
  @SuppressWarnings("rawtypes")
  public Iterator begin()
  {
    Set keySet = codeMap.keySet();
    return keySet.iterator();
  }

  public float[] getCode (String word)
  {
    Code code;
    code = (Code) codeMap.get(word);
    return code.no;
  }

  public int[] getEdges(int n)
  {
    return gng.getEdges(n);
  }

  public int getSize ()
  {
    return codeMap.size();
  }

  public int mapSize()
  {
    return gng.getSize();
  }

  // Other methods

  public boolean contains (String word)
  {
    if(codeMap.get(word) == null)
      return false;
    else
      return true;
  }
  
  @SuppressWarnings("unchecked")
  public String[] labels ()
  {
    @SuppressWarnings("rawtypes")
	HashMap error = new HashMap();
    @SuppressWarnings("rawtypes")
	HashMap membershipRate = new HashMap();
    @SuppressWarnings("rawtypes")
	Set keySet = codeMap.keySet();
    @SuppressWarnings("rawtypes")
	Iterator iteratorA = keySet.iterator();
    @SuppressWarnings("rawtypes")
	Iterator iteratorB;
    @SuppressWarnings("rawtypes")
	Vector aux = new Vector();
    Code code,eA,eB,f;
    boolean labeled = false;
    final int dim = gng.getSize();
    int pos;
    float value = 0;
    float sum = 0;
    float max = 0;
    float[] nullcode = new float[codeSize];
    float[] weight = new float[2 * codeSize];
    float[] input = new float[2 * codeSize];
    float[][] errValue = new float [codeMap.size()][dim];
    float[][] mRateValue = new float [codeMap.size()][dim];
    String word;

    for (int i=0; i<codeSize; i++) // create nullcode
      nullcode[i]=0;

    // ERRORS  COMPUTING

    for (int n=0; n<codeMap.size(); n++)
    {
      word = (String) iteratorA.next();
      code = (Code) codeMap.get(word);
      System.arraycopy(code.no,0,input,0,codeSize);
      System.arraycopy(nullcode,0,input,codeSize,codeSize);
      for(int i=0; i<dim; i++)
      {
        weight = gng.getWeights(i);
        for (int k=0; k<(2 * codeSize); k++) // computing errors
          sum += (float) Math.pow((weight[k] - input[k]),2);
        errValue[n][i] = sum;   // errors of n(th) keyword
        sum = 0;
      }
      error.put(word, new Code(errValue[n]));    
    }

    // MEMBERSHIP RATES COMPUTING

    keySet = error.keySet();
    iteratorA = keySet.iterator();
    iteratorB = keySet.iterator();
    for (int n=0; n<error.size(); n++)
    {
      word = (String) iteratorA.next();
      eA = (Code)error.get(word);
      for(int i=0; i<dim; i++)
      {
        for (int m=0; m<error.size(); m++)
        {
          eB = (Code)error.get((String)iteratorB.next());  
          sum += (float) (1/eB.no[i]);
        }
        mRateValue[n][i]=(float)((1/eA.no[i])/sum); // membership rates of n(th) keyword
        if (sum == Double.NaN) // remove NaNs
          mRateValue[n][i] = 0;
        else if (eA.no[i] == 0)
          mRateValue[n][i] = 3.4E38f;
        iteratorB = keySet.iterator();
        sum = 0;
      }
      membershipRate.put(word, new Code(mRateValue[n]));
    }

    // GOODNESS RATES COMPUTING AND LABELS SELECTION

    for(int I=0; I<dim; I++)
    {
      keySet = membershipRate.keySet();
      iteratorA = keySet.iterator();
      for (int n=0; n<membershipRate.size(); n++)
      {
        word = (String) iteratorA.next();
        f = (Code) membershipRate.get(word);
        for(int i=0; i<dim; i++)
          sum += f.no[i];
        value = (float)((Math.pow(f.no[I],2))/sum); // goodness rate of n(th) keyword
        sum = 0;
        if (max <= value)
        {
          pos = winnerNode(word);
          if (pos==I)
          {
            if (labeled == false)
            {
              aux.add((String) word);
              labeled = true;
            }
            else
              aux.set(aux.size()-1,word);
            max = value;
          }
        }
      }
      max = 0;
      labeled = false;
    }
    String[] results = new String[aux.size()];
    for(int l=0; l<aux.size(); l++)
      results[l] = (String) aux.get(l);
    return results;
  }

  public String[] keywordList()
  {
    @SuppressWarnings("rawtypes")
	Set keySet = codeMap.keySet();
    @SuppressWarnings("rawtypes")
	Iterator iterator = keySet.iterator();
    String results[] = new String[codeMap.size()];
    for (int n=0; n<codeMap.size(); n++)
    {
      results[n] = (String) iterator.next();
    }
    return results;
  }

  public synchronized int winnerNode (String word)
  {
    int position;
    float[] input = new float[2 * codeSize];
    float[] nullcode = new float[codeSize];
    for(int i=0; i<codeSize; i++) // create nullcode
      nullcode[i] = 0;
    System.arraycopy(getCode(word),0,input,0,codeSize);
    System.arraycopy(nullcode,0,input,codeSize,codeSize);
    position = gng.findWinner(input)[0];
    return position;
  }
}
