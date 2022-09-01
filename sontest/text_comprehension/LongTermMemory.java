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

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.DataConversionException;
import org.jdom.output.XMLOutputter;

public class LongTermMemory
{
  private DataBase database;

  public LongTermMemory(String name)
  {
    database = new DataBase(name);
  }

  public DataBase getDataBase()
  {
    return database;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized void memorize (Document newInfo) throws DataConversionException,
    IOException, ClassNotFoundException
  {
    Document knownInfo = database.getDocument();
    HashMap knownTopics = database.getMap();
    List newLabels = newInfo.getRootElement().getChildren();
    List knownLabels = knownInfo.getRootElement().getChildren();
    List newChildren = null, knownChildren = null;
    String labelName = null, childName = null;
    Element knownLabel = null, knownChild = null, examinedLabel = null,
      examinedChild = null;
    ListIterator labelsIterator = newLabels.listIterator();
    ListIterator childrenIterator = null;
    Integer rate = null;
    while(labelsIterator.hasNext())
    {
      examinedLabel = (Element)labelsIterator.next();
      labelName = examinedLabel.getName();
      rate = (Integer)(knownTopics.get(labelName));
      if(rate != null)
        knownTopics.put(labelName,new Integer(rate.intValue()+1));
      else knownTopics.put(labelName,new Integer((int)1));
      newChildren = newInfo.getRootElement().getChild(labelName).getChildren();
      knownLabel = knownInfo.getRootElement().getChild(labelName);
      if(knownLabel == null)
      {
        knownLabels.add(new Element(labelName));
        knownLabel = knownInfo.getRootElement().getChild(labelName);
        knownLabel.setAttribute("rate","1");
      }
      else knownLabel.setAttribute("rate",(new Integer
        (knownLabel.getAttribute("rate").getIntValue()+1)).toString());
      knownChildren = knownInfo.getRootElement().getChild(labelName).getChildren();
      childrenIterator = newChildren.listIterator();
      while(childrenIterator.hasNext())
      {
        examinedChild = (Element) childrenIterator.next();
        childName = examinedChild.getName();
        rate = (Integer)knownTopics.get(childName);
        if(rate != null)
          knownTopics.put(childName,new Integer(rate.intValue()+1));
        else knownTopics.put(childName,new Integer((int)1));
        knownChild = knownInfo.getRootElement().getChild(labelName).
          getChild(childName);
        if(knownChild == null)
        {
          knownChildren.add(new Element(childName));
          knownChild = knownInfo.getRootElement().getChild(labelName).
            getChild(childName);
          knownChild.setAttribute("rate","1");
        }
        else knownChild.setAttribute("rate",(new Integer
          (knownChild.getAttribute("rate").getIntValue()+1)).toString());
      }
    }
  }

  public synchronized void load() throws IOException,ClassNotFoundException
  {
    String subject = database.getSubject();
    File inFile = new File("C:\\Users\\luigi.lella\\Desktop\\news", subject.concat(".data"));
    FileInputStream inFileStream = new FileInputStream(inFile);
    ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);
    database = (DataBase) inObjectStream.readObject();
    inObjectStream.close();
  }

  public synchronized void save() throws IOException
  {
    String subject = database.getSubject();
    File outFile = new File("C:\\Users\\luigi.lella\\Desktop\\news",subject.concat(".data"));
    FileOutputStream outFileStream = new FileOutputStream(outFile);
    ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);
    outObjectStream.writeObject(database);
    outObjectStream.close();
  }

  public synchronized void getXML() throws IOException
  {
    String subject = database.getSubject();
    File outFile = new File("C:\\Users\\luigi.lella\\Desktop\\news",subject.concat(".xml"));
    FileOutputStream outFileStream = new FileOutputStream(outFile);
    XMLOutputter outStream = new XMLOutputter();
    outStream.output(database.getDocument(),outFileStream);
    outFileStream.close();
  }
  
  //metodi per la visualizzazione
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public HashSet nodeSet()
  {
	HashSet nodes = new HashSet();
	List FirstLevel = database.getDocument().getRootElement().getChildren();
	Iterator it1lvl = FirstLevel.iterator();
	Iterator it2lvl;
	List SecondLevel;
	Element e1, e2;
	
    while (it1lvl.hasNext())
    {
	  e1 =(Element) it1lvl.next();
	  
	  nodes.add(e1.getName());

	  SecondLevel = e1.getChildren();
	  it2lvl = SecondLevel.iterator();
	  while (it2lvl.hasNext())
	  {
	  	e2 =(Element) it2lvl.next();

	  	nodes.add(e2.getName());
	  }
    }    
    return nodes;
  }
  
  @SuppressWarnings("rawtypes")
  public String[] WordsList( HashSet h )
  {
    int i=0;
	String[] b= new String[h.size()];
	Iterator it;
	it= h.iterator();
	while (it.hasNext())
	{
      b[i]= (String) it.next();
      i++;
	}
	return b;	
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public HashMap indexWords(String[] s) 
  {
	HashMap map=new HashMap();
				
	for (int i = 0; i < s.length; i++) 
	{			
		map.put(s[i],new Integer(i));			
	}
	return map;
  }
  
  @SuppressWarnings({ "rawtypes", "unused" })
  public int[][] linksMatrix()
  {
	  	int [][] k;
	  	HashSet s=nodeSet();
	  	String[] word=WordsList(s);
	  	k=new int[word.length][word.length];
		
		List FirstLevel = database.getDocument().getRootElement().getChildren();
		
		Iterator it;
		List SecondLevel;
		Element e,e1;
		String s1;
		HashMap map1=new HashMap();
		map1=indexWords(word);
		Integer j;
		int j1;
		
		for (int i = 0; i < word.length; i++) 
		{
				e=database.getDocument().getRootElement().getChild(word[i]);
				if (e!=null)
				{
					SecondLevel=e.getChildren();
					it=SecondLevel.iterator();
					while (it.hasNext())
					{
						e1 =(Element) it.next();
						s1 = e1.getName();
						j  = ((Integer) map1.get(s1));
						
						if (j!=null)
						{
							j1=Integer.parseInt(j.toString());
							if (i!=j1)
								k[i][j1]=Integer.parseInt(e1.getAttributeValue("rate"));
						}
				}
				
			}
		}	
		return k;
  }	
}
