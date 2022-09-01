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
import java.util.List;
import java.util.ListIterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.DataConversionException;

public class BufferV2
{
  @SuppressWarnings("rawtypes")
  private HashMap wordMap;
  @SuppressWarnings("rawtypes")
  public HashMap indexMap;
  private DataBase knowledge;
  public float[][] bufferInterface;
  public boolean firstTime = true;
  

  @SuppressWarnings("rawtypes")
  public BufferV2(DataBase database)
  {
    wordMap = new HashMap();
    knowledge = database;
    indexMap = new HashMap();
    
    try
    {
        makeIndex();
    }
    catch (DataConversionException e)
    {
        System.out.println("Data conversion not possible");
    }
    
    if ( indexMap.size() > 0 )
    {    
        bufferInterface = new float[indexMap.size()][indexMap.size()];
        
        try
        {
            makeInterface();
        }
        catch (DataConversionException e)
        {
            System.out.println("Data conversion not possible");
        }
        
        firstTime = false;
    } 
    
  }

  // Getter methods

  @SuppressWarnings("rawtypes")
  public Iterator begin()
  {
    Set keySet = wordMap.keySet();
    return keySet.iterator();
  }

  public float getFreq (String word)
  {
    Freq freq = (Freq) wordMap.get(word);
    return freq.no;
  }

  public int getSize ()
  {
    return wordMap.size();
  }
  
  
  @SuppressWarnings("rawtypes")
  public String[] keywordList()
  {
    Set keySet = wordMap.keySet();
    Iterator iterator = keySet.iterator();
    String results[] = new String[wordMap.size()];
    for (int n=0; n<wordMap.size(); n++)
      results[n] = (String) iterator.next();
    return results;
  }

  // Setter methods

  @SuppressWarnings("unchecked")
  public void memorize (String word)
  {
    Freq freq;
    freq = (Freq) wordMap.get(word);
    if (freq == null)
    {
      wordMap.put(word, new Freq(1f));
      System.out.println(word+" 1.0");
    }
    else
    {    
      wordMap.put(word, new Freq((float)(freq.no + 1)));
      System.out.println(word+" "+(freq.no + 1));
    }  
  }

  @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
  public void oblivion() throws DataConversionException
  {
    Set keySet = wordMap.keySet();
    Iterator iterator = keySet.iterator();
    Freq freq;
    Integer rate;
    String word;
    float[] f = new float[wordMap.size()];
    float correlationScore = 0;
    int n=0;
    Document knownInfo = knowledge.getDocument();
    Element knownLabel = null;
    HashMap knownTopics = knowledge.getMap();
    while (iterator.hasNext())
    {
      word = (String) iterator.next();
      freq = (Freq) wordMap.get(word);
      rate = (Integer)(knownTopics.get(word));
      knownLabel = knownInfo.getRootElement().getChild(word);
      correlationScore = ((knownLabel != null) ?
        (knownLabel.getAttribute("rate").getFloatValue()/
        rate.floatValue()):0f);
      f[n] = freq.no / 2f;
      if (f[n] < 1 )  
      {
        iterator.remove();
      }
      else
      {
        wordMap.put(word, new Freq(f[n]));
      }
      n++;
    }
  }

  
  @SuppressWarnings("rawtypes")
  public void makeIndex() throws DataConversionException
  {
      Document knownInfo = knowledge.getDocument();
      List labelsList = knownInfo.getRootElement().getChildren();
      List keyWordsList;
      ListIterator labelsIterator = labelsList.listIterator();
      ListIterator keyWordsIterator;
      Element examinedLabel = null, examinedKeyWord = null;
      
      while (labelsIterator.hasNext())
      {
          examinedLabel = (Element)labelsIterator.next();
          doIndex(examinedLabel.getName());
          keyWordsList = knownInfo.getRootElement().getChild(examinedLabel.getName()).getChildren();
          keyWordsIterator = keyWordsList.listIterator();
          while (keyWordsIterator.hasNext())
          {
              examinedKeyWord = (Element)keyWordsIterator.next();
              doIndex(examinedKeyWord.getName());
          }
      }
  }
  
  
  @SuppressWarnings("unchecked")
  public void doIndex(String word)
  {
      int index = indexMap.size();
      String indexStr = "" + index;
      
      if ( indexMap.containsKey(word) == false )
      {
          indexMap.put(word,indexStr);
      }    
  }
  
  @SuppressWarnings({ "rawtypes", "unused" })
  public void makeInterface() throws DataConversionException
  {
      Document knownInfo = knowledge.getDocument();
      List labelsList = knownInfo.getRootElement().getChildren();
      List keyWordsList;
      ListIterator labelsIterator = labelsList.listIterator();
      ListIterator keyWordsIterator;
      Element examinedLabel = null, examinedKeyWord = null;
      int i, j;
      boolean found = false;
      String app;
      
      for (i=0; i<indexMap.size(); i++)
          for (j=0; j<indexMap.size(); j++)
              if ( i==j )
              {    
                  bufferInterface[i][j] = 1f;
              }
              else
              {    
                  bufferInterface[i][j] = 0f;
              } 
      
      while (labelsIterator.hasNext())
      {
          examinedLabel = (Element)labelsIterator.next();
          i = Integer.parseInt(indexMap.get(examinedLabel.getName()).toString());
          keyWordsList = knownInfo.getRootElement().getChild(examinedLabel.getName()).getChildren();
          keyWordsIterator = keyWordsList.listIterator();
          while (keyWordsIterator.hasNext())
          {
              examinedKeyWord = (Element)keyWordsIterator.next();
              j = Integer.parseInt(indexMap.get(examinedKeyWord.getName()).toString());
              bufferInterface[i][j] = (examinedKeyWord.getAttribute("rate").getFloatValue()) / (examinedLabel.getAttribute("rate").getFloatValue());
          }
      }
      
  }
  
  
   @SuppressWarnings("rawtypes")
   public void activationDiffusion(List temporaryBuffer, float maxVar, float level)
   {
       float[] varValues = new float[indexMap.size()];
       float[] activation = new float[indexMap.size()];
       float[] newActivation = new float[indexMap.size()];
       Set keySet = indexMap.keySet();
       Iterator keyIteratorA = keySet.iterator();
       Iterator keyIteratorB = keySet.iterator();
       String word;
       boolean done = false;
       
       //Activation array creation
       while (keyIteratorA.hasNext())
       {
           word = (String)keyIteratorA.next();
           
           if ( temporaryBuffer.contains(word) == true )
           {
               activation[Integer.parseInt(indexMap.get(word).toString())] = 1f;
           }
           else
           {
               activation[Integer.parseInt(indexMap.get(word).toString())] = 0f;
           }
       }
       
       //Activation diffusion
       while ( done == false )
       {    
           newActivation = product(activation,bufferInterface);
           newActivation = normalize(newActivation);
           
           done = true;

           for(int i=0; i<activation.length; i++)
           {
               varValues[i] = Math.abs(newActivation[i]-activation[i]);
               
               if ( varValues[i] > maxVar )
               {    
                   done = false;
               }
           }
           
           if ( done == false )
           {
               activation = newActivation;
           }
       }
       
       //Long term working memory creation
       while (keyIteratorB.hasNext())
       {
           word = (String)keyIteratorB.next();
           if ( newActivation[Integer.parseInt(indexMap.get(word).toString())] > level )
           {
               memorize(word);
           }
       }
   }
   
   
   public float[] product(float[] vector, float[][] matrix)
   {
       float[] result = new float[vector.length];
       
       for(int i=0; i<vector.length; i++)
           result[i] = 0;
       
       for(int i=0; i<vector.length; i++)
           for(int j=0; j<vector.length; j++)
           {
               result[i] += vector[j]*matrix[j][i];
           }  
       
       return(result);  
   }
   
   
   public float[] normalize(float[] vector)
   {
       float squareSum = 0;
       
       for(int i=0; i<vector.length; i++)
           squareSum += Math.pow(vector[i],2);
       
       if ( squareSum != 0 )
       {    
           for(int i=0; i<vector.length; i++)
               vector[i] /= Math.sqrt(squareSum); 
       }
       
       return(vector);   
   }
  
}
