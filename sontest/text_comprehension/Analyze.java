/****************************************************************************

Copyright (C) (2004) Luigi Lella

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

This class implements  the human  text comprehension model  defined by Walter 
Kintsch (https://pubmed.ncbi.nlm.nih.gov/8203801/).
According to this model  the working memory (WorkingMemory.java), that can be 
seen as a repository where all the knowledge  for the correct  reconstruction 
of the meaning  of the analysed information is stored, can be subdivided in a 
short term part (BufferV2.java, SlidingContextMining.java) that has a limited 
capacity  and  a long term  working memory  that is a part  of the  long term 
memory (LongTermMemory.java) here  represented  by an associative network  of 
words stored in XML format. The content of the short term part of the working 
memory automatically generates the LTWM, because some objects present  in the 
short term part of the working memory are linked to other  objects in the LTM  
by fixed and stable memory structures called “retrieval cues”.
A new  text  is analysed  paragraph by paragraph. The  buffer (BufferV2.java) 
contains not just  the words of the analysed paragraph, but also  other words 
retrieved  from the long term memory using an  activation diffusion procedure
which is similar to the one developed by McClelland and Rumelhart,  where the 
activation  signal  starts  from  the  nodes  in  the  long  term memory that 
represent the words in the paragraph.
A  stoplist (ReducedStopList.java)  is  used  to  filter  the  words  in  the 
paragraph. Jdom-1.0.jar  library  is used to update  the long term memory XML 
graph.

Both  the  Long Term Memory  and the Reduced Stop List have to be initialized 
by  the classes InitializeLTM.java and InitializeReducedStopList.java  before 
running Analyze.java.

A GNG object is used to obtain the working memory text representation. 
SentimentAnalysisGraph.java generates a direct graph which represents the LTM
The graph can be displayed on the screen through the index.html file.

An alternative lightweight implementation of the W.Kintsch model is presented
in the following articles:
- I.Licata, G.Tascini, L.Lella, A.Montesanto, W.Giordano, " Scale Free Graphs 
in  Dynamic  Knowledge  Acquisition",  Proceedings of A.I.R.S., Castel Ivano, 
2004. Published  in  "Systemics  of  Emergence  Research  and  Development" , 
G.Minati, E. Pessa, M. Abram (Eds.), Springer 2006;
- L.Lella,  A.F. Dragoni,  G.Giampieri,  "Enabling Knowledge Creation Through 
Associative Networks and Semantic Web Technologies", Proceedings  of F.O.M.I.
, Castelnuovo del Garda, 2005;
 
 ***************************************************************************/
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jdom.DataConversionException;

public class Analyze
{
				
	public static void main(String[] args) throws IOException 
	{
	   try
	   {
	   	
			LongTermMemory ltm = new LongTermMemory("LTM");
			ltm.save(); // LTM initialization
			
			int numberOfAnalyzedTexts = 13;
    	
			for (int i = 1; i <= numberOfAnalyzedTexts; i++)
			{	
				new SlidingContextMining("C:\\Users\\luigi.lella\\Desktop\\news\\news"+i+".txt",ltm);
			}
			ltm.load();
			ltm.getXML(); // XML LTM file generation
			
			//JASON LTM file generation
			new SentimentAnalysisGraph(ltm);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Class not found");
			System.exit(-1);
		}
		catch (DataConversionException e)
		{
			System.out.println("Data conversion not possible");
			System.exit(-1);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}
}
