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
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.DataConversionException;

public class SlidingContextMining
{
  private final int CODESIZE = 8;
  private final float MAXVAR = 0.001f;
  private final float LEVEL = 0.8f;
  private WorkingMemory wm = new WorkingMemory(CODESIZE);
  private Vector<Code> aux = new Vector<Code>();
  private String subject;
  private LinkedList<String> temporaryBuffer = new LinkedList<String>();
  private ReducedStopList reducedStopList = new ReducedStopList(); 
  private BufferV2 buffer;

  public SlidingContextMining(String fileName, LongTermMemory ltm) throws FileNotFoundException, IOException, ClassNotFoundException, DataConversionException 
  {
    subject = ltm.getDataBase().getSubject();
    buffer = new BufferV2(ltm.getDataBase());
    String word = null;
    String[] subWord;
    boolean reset = false, text = false, suspended = false; 
    Code c;

	reducedStopList.load();

    StreamTokenizer in;
    FileInputStream fis = new FileInputStream(fileName);
    InputStreamReader isr = new InputStreamReader(fis);
    in = tokenizerSetUp(isr);
	
    text = true;
	         
	while (text)
	{
		if ((in.ttype == StreamTokenizer.TT_EOF))
		{
			text = false;
		}

		while ((reset == false) && (in.ttype == StreamTokenizer.TT_WORD) && text) 
		{
			word = in.sval;

			if(word.endsWith(".")||word.startsWith("<https://"))
				reset = true;
            
			if(word.equals("===")||word.startsWith("<https://")||word.endsWith(">"))
				suspended=!suspended;
			else if(!suspended)
			{
				if(!word.equals("..."))
				{
					word = cleanWord(word);
		            
					subWord = word.split(" ");
		
					for (int i=0; i<subWord.length; i++)
					{
						String singleWord = subWord[i].trim();
						
						if(singleWord.length()>1 && !reducedStopList.contains(singleWord))
						{
							temporaryBuffer.addLast(singleWord);
							buffer.memorize(singleWord);
						}

					}
				}
			}
            
			in.nextToken();
		}

		if (reset)
		{

			buffer.activationDiffusion(temporaryBuffer,MAXVAR,LEVEL);
			temporaryBuffer.clear();
            
            buffer.oblivion(); 
            if (buffer.getSize() >= 2)
            {
              wm.coding(buffer);
              codeInfo(buffer);
            }
            buffer.oblivion(); 
                        
            reset = false;
            suspended = false;
		}
		else
			in.nextToken();
	}

	fis.close();

    float[][] inputs = new float [aux.size()][2 * CODESIZE];
    for (int i=0; i<aux.size(); i++)
    {
      c = aux.get(i);
      inputs[i] = c.no;
    }
    wm.memorize(inputs);
    
    //LTM updating
    Document newInfo = getDocument();
    ltm.memorize(newInfo);
    ltm.getXML();
    ltm.save();
    
  }
    
    
    //Tokenizer flags initialization
    public StreamTokenizer tokenizerSetUp(InputStreamReader isr)
    {
        Reader r = new BufferedReader(isr);
        StreamTokenizer in = new StreamTokenizer(r);
        in.resetSyntax();
        
        // Punctuation
        in.wordChars(5,5);
        in.wordChars(33,33);
        in.wordChars(38,38);
        in.wordChars(45,47); 
        in.wordChars(58,95);
        in.wordChars(97,122);
        in.wordChars(128,169);
        in.wordChars(181,184);
        in.wordChars(198,199);
        in.wordChars(207,216);
        in.wordChars(222,222);
        in.wordChars(224,237);
        in.wordChars(243,243);
        
		//white spaces
        in.whitespaceChars(6,32);
        in.parseNumbers();
        in.eolIsSignificant(false);
        in.lowerCaseMode(true);
        in.slashStarComments(false);
        in.slashSlashComments(false);
        return in;
    }

    public String cleanWord(String word) 
    {    
        word = word.replace(';',' ');
        word = word.replace(':',' ');
        word = word.replace('?',' ');
        word = word.replace('!',' ');
        word = word.replace('.',' ');
        word = word.replace(',',' ');
        word = word.replace('=',' ');
        word = word.replace('[',' ');
        word = word.replace(']',' ');
        word = word.replace('^',' ');
        word = word.replace('<',' ');
        word = word.replace('>',' ');
        word = word.replace('/',' ');
        word = word.replace('-',' ');
        word = word.replace('_',' ');
        word = word.replace('|',' ');
        word = word.replace('%',' ');
        word = word.replace('“',' ');
        word = word.replace('”',' ');
        word = word.replace('–',' ');
        word = word.replace('&',' ');
        word = word.replace('·',' ');
        word = word.replace('â',' ');
        word = word.replace('€',' ');
        word = word.replace('0',' ');
        word = word.replace('1',' ');
        word = word.replace('2',' ');
        word = word.replace('3',' ');
        word = word.replace('4',' ');
        word = word.replace('5',' ');
        word = word.replace('6',' ');
        word = word.replace('7',' ');
        word = word.replace('8',' ');
        word = word.replace('9',' ');
        word = word.trim();

        return(word);
    }

  private void codeInfo(BufferV2 buffer)
  {
    String[] keyWord;
    double sum = 0;
    float freq = 0, tot = 0;
    float[] code = new float[CODESIZE];
    float[][] results = new float[buffer.getSize()][2*CODESIZE];
    keyWord = buffer.keywordList();
    for(int n=0; n<buffer.getSize(); n++)
    {
      code = wm.getCode(keyWord[n]);
      for(int i=0; i<CODESIZE; i++)
      {
        results[n][i] = code[i] * 0.2f;
        sum += Math.pow((double)results[n][i],2);
      }
      for(int i=CODESIZE; i<2*CODESIZE; i++)
        results[n][i] = 0;
      for(int m=0; m<buffer.getSize(); m++)
      {
        if(m != n)
        {
          code = wm.getCode(keyWord[m]);
          freq = buffer.getFreq(keyWord[m]);
          tot += freq;
          for(int i=0; i<CODESIZE; i++)
            results[n][CODESIZE + i] += code[i] * freq;
        }
      }
      for(int i=0; i<CODESIZE; i++)
      {
        results[n][CODESIZE+i] /= tot;
        sum += Math.pow((double)results[n][CODESIZE+i],2);
      }
      tot = 0;
      sum = Math.sqrt(sum);
      for (int i=0; i<2*CODESIZE; i++) // normalize input
        if (sum > 0)
          results[n][i] /= (float) sum;
      sum = 0;
      aux.add(new Code(results[n]));
    }
  }

  @SuppressWarnings("unchecked")
  public Document getDocument()
  {
    Document tree = new Document (new Element(subject));
    Element root = tree.getRootElement();
    String[] keyList = keywordList();
    String[] labels = labels();
	List<Element> auxList = root.getChildren();
    int pos;
    int[] edges = new int[mapSize()];
    for(int n=0; n<labels.length; n++)
      auxList.add(new Element(labels[n]));
    for(int n=0; n<labels.length; n++)
    {
      pos = winnerNode(labels[n]);
      auxList = root.getChild(labels[n]).getChildren();
      for(int i=0; i<keyList.length; i++)
        if ((keyList[i] != labels[n])&&(winnerNode(keyList[i]) == pos))
          auxList.add(new Element(keyList[i]));
      edges = getEdges(pos);
      for (int m=0; m<n; m++)
        if (edges[winnerNode(labels[m])] != -1)
        {
          auxList = root.getChild(labels[m]).getChildren();
          auxList.add(new Element(labels[n]));
          auxList = root.getChild(labels[n]).getChildren();
          auxList.add(new Element(labels[m]));
        }
    }
    return tree;
  }

  public int[] getEdges(int n)
  {
    return wm.getEdges(n);
  }
  public int getSize ()
  {
    return wm.getSize();
  }
  public String[] keywordList()
  {
    return wm.keywordList();
  }
  public String[] labels()
  {
    return wm.labels();
  }
  public int mapSize()
  {
    return wm.mapSize();
  }
  public int winnerNode(String word)
  {
    return wm.winnerNode(word);
  }
 
}
