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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class SentimentAnalysisGraph {
	private int[][] k;
	private int y;
	private String[] word;
	
	@SuppressWarnings("rawtypes")
	public SentimentAnalysisGraph(LongTermMemory ltm) 
	{
		HashSet hs;
	    hs=ltm.nodeSet();
	    word=ltm.WordsList(hs);
		k= ltm.linksMatrix();
		y=k.length;
		
		try {
				Path currentRelativePath = Paths.get("");
				String filePath = currentRelativePath.toAbsolutePath().toString();
				
				File file = new File(filePath+"\\WebContent\\sentimentanalysis.json");
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				
				writer.write('{');
				writer.newLine();
				
				//Direct graph nodes creation
				writer.write("  \"nodes\": [");
				writer.newLine();
				for(int i=0;i<y;i++)
				{
					int group = (i/(y/3))+1;
					if(i!=y-1)
						writer.write("    {\"id\": \""+cleanWord(word[i])+"\", \"group\": "+group+"},");
					else
						writer.write("    {\"id\": \""+cleanWord(word[i])+"\", \"group\": "+group+"}");
					writer.newLine();
				}
				writer.write("  ],");
				writer.newLine();
				
				int maxI = 0;
				int maxJ = 0;
				for(int i=0;i<y;i++)
				{
					for(int j=0;j<y;j++)
					{
					   if (k[i][j]>=1)
					   {
						   maxI=i;
						   maxJ=j;
					   }
					}
				}

				writer.write("  \"links\": [");
				writer.newLine();
				for(int i=0;i<y;i++)
				{
					for(int j=0;j<y;j++)
					{
					   if ((i!=j)&&(k[i][j]>=1))
					   {
						   if(!(i==maxI&&j==maxJ))
							   writer.write("    {\"source\": \""+cleanWord(word[i])+"\", \"target\": \""+cleanWord(word[j])+"\", \"value\": "+k[i][j]+"},");
						   else
							   writer.write("    {\"source\": \""+cleanWord(word[i])+"\", \"target\": \""+cleanWord(word[j])+"\", \"value\": "+k[i][j]+"}");
						   writer.newLine();
					   }
					}
				}
				writer.write("  ]");
				writer.newLine();
				
				writer.write('}');
				writer.newLine();
				
				writer.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	public String cleanWord(String word) 
    {
		word = word.replace('à','a');
        word = word.replace('è','e');
        word = word.replace('é','e');
        word = word.replace('ì','i');
        word = word.replace('ò','o');
        word = word.replace('ù','u');
        return(word);
    }

}
