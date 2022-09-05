/****************************************************************************

Copyright (C) (2022) Luigi Lella

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

package swarmevolutive;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FitnessCalc {
	
	final static int trainingSetSize = 460;
	
	static int[][] knownData = new int[trainingSetSize][25];
	static int classePrimoIngressoAttuale = 0;
	static int classeSecondoIngressoAttuale = 0;
	static int portaDaAssemblare = 0;
    
	/*
    * Ho bisogno di una matrice di boolean 50*25 knownData con i dati del database Diabetes PIMA da 
    * caricare con il metodo LoadData(). Ogni riga della matrice rappresenta un record del database che 
    * contiene 24 bit valorizzati a 0 o a 1 a seconda che il valore della variabile di studio considerata 
    * rientri o meno nella corrispondente classe, più un altro bit rappresentante l'esito della diagnosi
    * (positivo/negativo)
    * */
    static void loadData (String fileName) 
    {
    	System.out.println("LOADING DATA");
    	
    	File getCSVfile = new File(fileName);
    	Scanner sc;
    	
		try {
			sc = new Scanner(getCSVfile);
			int i = 0;
		
	    	while (sc.hasNext())
	    	{
	    		String record = sc.next();
	    		String[] values = record.split(",");
	    		
	    		for(int j=0; j<9; j++)
	    		{
    				switch(j) {
    				
    				//1 - pregnancies: 0-5; 6-10; 11-17
    				case 0: if ((new Float(values[0])).floatValue()<=5)
    				{
    					knownData[i][0]=1;
    					knownData[i][1]=0;
    					knownData[i][2]=0;
    				}
    				else if (((new Float(values[0])).floatValue()>5)&&((new Float(values[0])).floatValue()<=10))
    				{
    					knownData[i][0]=0;
    					knownData[i][1]=1;
    					knownData[i][2]=0;
    				}
    				else
    				{
    					knownData[i][0]=0;
    					knownData[i][1]=0;
    					knownData[i][2]=1;
    				}
    				break;
    				
    				//2 - glucose: 56-103; 104-150; 151-198
    				case 1: if ((new Float(values[1])).floatValue()<=103)
    				{
    					knownData[i][3]=1;
    					knownData[i][4]=0;
    					knownData[i][5]=0;
    				}
    				else if (((new Float(values[1])).floatValue()>103)&&((new Float(values[1])).floatValue()<=150))
    				{
    					knownData[i][3]=0;
    					knownData[i][4]=1;
    					knownData[i][5]=0;
    				}
    				else
    				{
    					knownData[i][3]=0;
    					knownData[i][4]=0;
    					knownData[i][5]=1;
    				}
					break;
					
					//3 - blood preassure: 24-52; 53-80; 81-110
    				case 2: if ((new Float(values[2])).floatValue()<=52)
    				{
    					knownData[i][6]=1;
    					knownData[i][7]=0;
    					knownData[i][8]=0;
    				}
    				else if (((new Float(values[2])).floatValue()>52)&&((new Float(values[2])).floatValue()<=80))
    				{
    					knownData[i][6]=0;
    					knownData[i][7]=1;
    					knownData[i][8]=0;
    				}
    				else
    				{
    					knownData[i][6]=0;
    					knownData[i][7]=0;
    					knownData[i][8]=1;
    				}
					break;
					
					//4 - skin thickness:  7-25; 26-43; 44-63
    				case 3: if ((new Float(values[3])).floatValue()<=25)
    				{
    					knownData[i][9]=1;
    					knownData[i][10]=0;
    					knownData[i][11]=0;
    				}
    				else if (((new Float(values[3])).floatValue()>25)&&((new Float(values[3])).floatValue()<=43))
    				{
    					knownData[i][9]=0;
    					knownData[i][10]=1;
    					knownData[i][11]=0;
    				}
    				else
    				{
    					knownData[i][9]=0;
    					knownData[i][10]=0;
    					knownData[i][11]=1;
    				}
					break;
					
					//5 - insulin: 14-291; 292-568; 569-846
    				case 4: if ((new Float(values[4])).floatValue()<=291)
    				{
    					knownData[i][12]=1;
    					knownData[i][13]=0;
    					knownData[i][14]=0;
    				}
    				else if (((new Float(values[4])).floatValue()>291)&&((new Float(values[4])).floatValue()<=568))
    				{
    					knownData[i][12]=0;
    					knownData[i][13]=1;
    					knownData[i][14]=0;
    				}
    				else
    				{
    					knownData[i][12]=0;
    					knownData[i][13]=0;
    					knownData[i][14]=1;
    				}
					break;
					
					//6 - bmi: 18-34; 35-50; 51-68
    				case 5: if ((new Float(values[5])).floatValue()<=34)
    				{
    					knownData[i][15]=1;
    					knownData[i][16]=0;
    					knownData[i][17]=0;
    				}
    				else if (((new Float(values[5])).floatValue()>34)&&((new Float(values[5])).floatValue()<=50))
    				{
    					knownData[i][15]=0;
    					knownData[i][16]=1;
    					knownData[i][17]=0;
    				}
    				else
    				{
    					knownData[i][15]=0;
    					knownData[i][16]=0;
    					knownData[i][17]=1;
    				}
					break;
					
					//7 - pedigree: 0-776; 777-1552; 1553-2330
    				case 6: if ((new Float(values[6])).floatValue()<=776)
    				{
    					knownData[i][18]=1;
    					knownData[i][19]=0;
    					knownData[i][20]=0;
    				}
    				else if (((new Float(values[6])).floatValue()>776)&&((new Float(values[6])).floatValue()<=1552))	
    				{
    					knownData[i][18]=0;
    					knownData[i][19]=1;
    					knownData[i][20]=0;
    				}
    				else
    				{
    					knownData[i][18]=0;
    					knownData[i][19]=0;
    					knownData[i][20]=1;
    				}
					break;
					
					//8 - age: 21-41; 42-61; 62-81
    				case 7: if ((new Float(values[7])).floatValue()<=41)
    				{
    					knownData[i][21]=1;
    					knownData[i][22]=0;
    					knownData[i][23]=0;
    				}
    				else if (((new Float(values[7])).floatValue()>41)&&((new Float(values[7])).floatValue()<=61))	
    				{
    					knownData[i][21]=0;
    					knownData[i][22]=1;
    					knownData[i][23]=0;
    				}
    				else
    				{
    					knownData[i][21]=0;
    					knownData[i][22]=0;
    					knownData[i][23]=1;
    				}
					break;
					
					//9 - Outcome
    				case 8: if ((new Float(values[8])).floatValue()==1)
    					knownData[i][24]=1;
    				else
    					knownData[i][24]=0;
    				break;
    				}
	    		}
	    		i++;
	    	}
	    	sc.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /* In questo metodo mi devo calcolare l'accuratezza predittiva della rete combinatoria di porte
     * NAND corrispondente al cromosoma preso in considerazione.
     * Per poterlo fare ho bisogno delle seguenti strutture dati:
     * - un ArrayList classiPrimiIngressi contenente gli Integer rappresentanti le classi di valori delle 
     * variabili di studio da utilizzare come primo ingresso; 
     * - un ArrayList classiSecondiIngressi con gli Integer rappresentanti le classi di valori delle 
     * variabili di studio da utilizzare come secondo ingresso;
     * - una matrice nandGates contenente le configurazioni delle 18 porte NAND da inserire nella rete 
     * combinatoria autoorganizzante.
     * 
     * Tali strutture vengono lette progressivamente per recuperare le classi di variabili e le porte 
     * NAND che devono ancora essere considerate. 
     * Per questo vengono utilizzate tre variabili int che sono classePrimoIngressoAttuale, 
     * classeSecondoIngressoAttuale e portaDaAssemblare. Tre indici che ovviamente non possono
     * superare le dimensioni delle relative strutture dati.
     */
    static int getFitness(Individual individual) {
    	int fitness = 0;
    	ArrayList<Integer> classiPrimiIngressi = new ArrayList<Integer>();
    	ArrayList<Integer> classiSecondiIngressi = new ArrayList<Integer>();
    	int[][] nandGates = new int[18][3];
    	
    	int nandNetOutput = 0;
    	
    	for (int i=0; i<individual.size(); i++)
    	{
    		switch(i) {
    		
    		/*
    		 * primo gruppo di 24 bit relativi alle classi di valori delle variabili di studio 
    		 * da utilizzare come primo ingresso 
    		*/
    		case 0:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(0));
        	}
    		break;
    		case 1:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(1));
        	}
    		break;
    		case 2:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(2));
        	}
    		break;
    		case 3:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(3));
        	}
    		break;
    		case 4:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(4));
        	}
    		break;
    		case 5:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(5));
        	}
    		break;
    		case 6:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(6));
        	}
    		break;
    		case 7:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(7));
        	}
    		break;
    		case 8:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(8));
        	}
    		break;
    		case 9:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(9));
        	}
    		break;
    		case 10:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(10));
        	}
    		break;
    		case 11:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(11));
        	}
    		break;
    		case 12:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(12));
        	}
    		break;
    		case 13:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(13));
        	}
    		break;
    		case 14:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(14));
        	}
    		break;
    		case 15:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(15));
        	}
    		break;
    		case 16:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(16));
        	}
    		break;
    		case 17:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(17));
        	}
    		break;
    		case 18:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(18));
        	}
    		break;
    		case 19:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(19));
        	}
    		break;
    		case 20:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(20));
        	}
    		break;
    		case 21:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(21));
        	}
    		break;
    		case 22:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(22));
        	}
    		break;
    		case 23:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiPrimiIngressi.add(new Integer(23));
        	}
    		break;
    		
    		/*
    		 * secondo gruppo di 24 bit relativi alle classi di valori delle variabili di studio 
    		 * da utilizzare come secondo ingresso 
    		*/
    		case 24:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(0));
        	}
    		break;
    		case 25:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(1));
        	}
    		break;
    		case 26:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(2));
        	}
    		break;
    		case 27:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(3));
        	}
    		break;
    		case 28:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(4));
        	}
    		break;
    		case 29:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(5));
        	}
    		break;
    		case 30:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(6));
        	}
    		break;
    		case 31:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(7));
        	}
    		break;
    		case 32:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(8));
        	}
    		break;
    		case 33:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(9));
        	}
    		break;
    		case 34:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(10));
        	}
    		break;
    		case 35:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(11));
        	}
    		break;
    		case 36:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(12));
        	}
    		break;
    		case 37:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(13));
        	}
    		break;
    		case 38:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(14));
        	}
    		break;
    		case 39:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(15));
        	}
    		break;
    		case 40:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(16));
        	}
    		break;
    		case 41:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(17));
        	}
    		break;
    		case 42:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(18));
        	}
    		break;
    		case 43:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(19));
        	}
    		break;
    		case 44:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(20));
        	}
    		break;
    		case 45:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(21));
        	}
    		break;
    		case 46:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(22));
        	}
    		break;
    		case 47:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			classiSecondiIngressi.add(new Integer(23));
        	}
    		break;
    		
    		/*
    		 * terzo gruppo di 54 bit con le configurazioni delle 9 porte NAND
    		*/
    		
    		//porta NAND di uscita della rete combinatoria
    		case 48:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[0][0]=1;
        	}
    		else
    		{
    			nandGates[0][0]=0;
    		}
    		break;
    		case 49:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[0][1]=1;
        	}
    		else
    		{
    			nandGates[0][1]=0;
    		}
    		break;
    		case 50:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[0][2]=1;
        	}
    		else
    		{
    			nandGates[0][2]=0;
    		}
    		break;
    		
    		//porta NAND 2
    		case 51:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[1][0]=1;
        	}
    		else
    		{
    			nandGates[1][0]=0;
    		}
    		break;
    		case 52:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[1][1]=1;
        	}
    		else
    		{
    			nandGates[1][1]=0;
    		}
    		break;
    		case 53:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[1][2]=1;
        	}
    		else
    		{
    			nandGates[1][2]=0;
    		}
    		break;
    		
    		//porta NAND 3
    		case 54:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[2][0]=1;
        	}
    		else
    		{
    			nandGates[2][0]=0;
    		}
    		break;
    		case 55:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[2][1]=1;
        	}
    		else
    		{
    			nandGates[2][1]=0;
    		}
    		break;
    		case 56:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[2][2]=1;
        	}
    		else
    		{
    			nandGates[2][2]=0;
    		}
    		break;
    		
    		//porta NAND 4
    		case 57:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[3][0]=1;
        	}
    		else
    		{
    			nandGates[3][0]=0;
    		}
    		break;
    		case 58:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[3][1]=1;
        	}
    		else
    		{
    			nandGates[3][1]=0;
    		}
    		break;
    		case 59:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[3][2]=1;
        	}
    		else
    		{
    			nandGates[3][2]=0;
    		}
    		break;
    		
    		//porta NAND 5
    		case 60:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[4][0]=1;
        	}
    		else
    		{
    			nandGates[4][0]=0;
    		}
    		break;
    		case 61:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[4][1]=1;
        	}
    		else
    		{
    			nandGates[4][1]=0;
    		}
    		break;
    		case 62:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[4][2]=1;
        	}
    		else
    		{
    			nandGates[4][2]=0;
    		}
    		break;
    		
    		//porta NAND 6
    		case 63:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[5][0]=1;
        	}
    		else
    		{
    			nandGates[5][0]=0;
    		}
    		break;
    		case 64:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[5][1]=1;
        	}
    		else
    		{
    			nandGates[5][1]=0;
    		}
    		break;
    		case 65:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[5][2]=1;
        	}
    		else
    		{
    			nandGates[5][2]=0;
    		}
    		break;
    		
    		//porta NAND 7
    		case 66:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[6][0]=1;
        	}
    		else
    		{
    			nandGates[6][0]=0;
    		}
    		break;
    		case 67:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[6][1]=1;
        	}
    		else
    		{
    			nandGates[6][1]=0;
    		}
    		break;
    		case 68:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[6][2]=1;
        	}
    		else
    		{
    			nandGates[6][2]=0;
    		}
    		break;
    		
    		//porta NAND 8
    		case 69:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[7][0]=1;
        	}
    		else
    		{
    			nandGates[7][0]=0;
    		}
    		break;
    		case 70:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[7][1]=1;
        	}
    		else
    		{
    			nandGates[7][1]=0;
    		}
    		break;
    		case 71:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[7][2]=1;
        	}
    		else
    		{
    			nandGates[7][2]=0;
    		}
    		break;
    		
    		//porta NAND 9
    		case 72:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[8][0]=1;
        	}
    		else
    		{
    			nandGates[8][0]=0;
    		}
    		break;
    		case 73:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[8][1]=1;
        	}
    		else
    		{
    			nandGates[8][1]=0;
    		}
    		break;
    		case 74:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[8][2]=1;
        	}
    		else
    		{
    			nandGates[8][2]=0;
    		}
    		break;
    		
    		
    		//porta NAND 10
    		case 75:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[9][0]=1;
        	}
    		else
    		{
    			nandGates[9][0]=0;
    		}
    		break;
    		case 76:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[9][1]=1;
        	}
    		else
    		{
    			nandGates[9][1]=0;
    		}
    		break;
    		case 77:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[9][2]=1;
        	}
    		else
    		{
    			nandGates[9][2]=0;
    		}
    		break;
    		
    		//porta NAND 11
    		case 78:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[10][0]=1;
        	}
    		else
    		{
    			nandGates[10][0]=0;
    		}
    		break;
    		case 79:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[10][1]=1;
        	}
    		else
    		{
    			nandGates[10][1]=0;
    		}
    		break;
    		case 80:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[10][2]=1;
        	}
    		else
    		{
    			nandGates[10][2]=0;
    		}
    		break;
    		
    		//porta NAND 12
    		case 81:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[11][0]=1;
        	}
    		else
    		{
    			nandGates[11][0]=0;
    		}
    		break;
    		case 82:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[11][1]=1;
        	}
    		else
    		{
    			nandGates[11][1]=0;
    		}
    		break;
    		case 83:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[11][2]=1;
        	}
    		else
    		{
    			nandGates[11][2]=0;
    		}
    		break;
    		
    		//porta NAND 13
    		case 84:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[12][0]=1;
        	}
    		else
    		{
    			nandGates[12][0]=0;
    		}
    		break;
    		case 85:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[12][1]=1;
        	}
    		else
    		{
    			nandGates[12][1]=0;
    		}
    		break;
    		case 86:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[12][2]=1;
        	}
    		else
    		{
    			nandGates[12][2]=0;
    		}
    		break;
    		
    		//porta NAND 14
    		case 87:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[13][0]=1;
        	}
    		else
    		{
    			nandGates[13][0]=0;
    		}
    		break;
    		case 88:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[13][1]=1;
        	}
    		else
    		{
    			nandGates[13][1]=0;
    		}
    		break;
    		case 89:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[13][2]=1;
        	}
    		else
    		{
    			nandGates[13][2]=0;
    		}
    		break;
    		
    		//porta NAND 15
    		case 90:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[14][0]=1;
        	}
    		else
    		{
    			nandGates[14][0]=0;
    		}
    		break;
    		case 91:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[14][1]=1;
        	}
    		else
    		{
    			nandGates[14][1]=0;
    		}
    		break;
    		case 92:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[14][2]=1;
        	}
    		else
    		{
    			nandGates[14][2]=0;
    		}
    		break;
    		
    		//porta NAND 16
    		case 93:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[15][0]=1;
        	}
    		else
    		{
    			nandGates[15][0]=0;
    		}
    		break;
    		case 94:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[15][1]=1;
        	}
    		else
    		{
    			nandGates[15][1]=0;
    		}
    		break;
    		case 95:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[15][2]=1;
        	}
    		else
    		{
    			nandGates[15][2]=0;
    		}
    		break;
    		
    		//porta NAND 17
    		case 96:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[16][0]=1;
        	}
    		else
    		{
    			nandGates[16][0]=0;
    		}
    		break;
    		case 97:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[16][1]=1;
        	}
    		else
    		{
    			nandGates[16][1]=0;
    		}
    		break;
    		case 98:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[16][2]=1;
        	}
    		else
    		{
    			nandGates[16][2]=0;
    		}
    		break;
    		
    		//porta NAND 18
    		case 99:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[17][0]=1;
        	}
    		else
    		{
    			nandGates[17][0]=0;
    		}
    		break;
    		case 100:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[17][1]=1;
        	}
    		else
    		{
    			nandGates[17][1]=0;
    		}
    		break;
    		case 101:
    		if (individual.getGene(i) == Byte.parseByte("1"))
        	{
    			nandGates[17][2]=1;
        	}
    		else
    		{
    			nandGates[17][2]=0;
    		}
    		break;
    		
    		}
    	}
    	
    	/* 
    	 * testo la configurazione di rete rappresentata dal cromosoma sui dati di test
    	 * conteggiando i casi di previsione corretta dell'outcome
    	 */
    	
    	
    	for (int i=0; i<trainingSetSize; i++)
    	{
    		//azzero i 3 contatori di posizioni della classe di ingresso 1, della classe di ingresso 2 e della porta da assemblare
    		classePrimoIngressoAttuale = 0;
    		classeSecondoIngressoAttuale = 0;
    		portaDaAssemblare = 0;
    		
    		//testo la configurazione di rete con il record i-esimo
    		nandNetOutput = getOutput(i,nandGates[portaDaAssemblare][0], nandGates[portaDaAssemblare][1], nandGates[portaDaAssemblare][2], classiPrimiIngressi, classiSecondiIngressi, nandGates);
    		
    		if (nandNetOutput==knownData[i][24])
    		{
    			fitness++;
    		}
    		
    	}
    	
    	return fitness;
    }
    
    //metodo ricorsivo per il calcolo dell'uscita della rete combinatoria
	static int getOutput(int recordPos, int input1, int input2, int cortoIngresso, ArrayList<Integer> classiPrimiIngressi, ArrayList<Integer> classiSecondiIngressi, int[][] nandGates)
	{
		
		int output = 0;
		boolean nandInserito = true;
		int posInput1 = 0;
		int posInput2 = 0;
		int valueInput1 = 0;
		int valueInput2 = 0;
		
		if (input1==1) 
		{
			if (classePrimoIngressoAttuale<classiPrimiIngressi.size())
			{
				//recupero la classe della variabile scelta come primo ingresso alla posizione classePrimoIngressoAttuale
				posInput1 = classiPrimiIngressi.get(classePrimoIngressoAttuale).intValue();
				
				//verifico il valore corrispondente nel record dei dati di test
				valueInput1 = knownData[recordPos][posInput1];
				classePrimoIngressoAttuale++;
			}
			else
				nandInserito = false;
		}
		else
		{
			if (portaDaAssemblare<17)
			{
				portaDaAssemblare++;
				valueInput1 = getOutput(recordPos,nandGates[portaDaAssemblare][0], nandGates[portaDaAssemblare][1], nandGates[portaDaAssemblare][2], classiPrimiIngressi, classiSecondiIngressi, nandGates);
			}
			else
				nandInserito = false;
		}
		
		if (cortoIngresso==1)
			valueInput2=valueInput1;
		else if (input2==1)
		{
			if (classeSecondoIngressoAttuale<classiSecondiIngressi.size())
			{
				//recupero la classe della variabile scelta come secondo ingresso alla posizione classeSecondoIngressoAttuale
				posInput2 = classiSecondiIngressi.get(classeSecondoIngressoAttuale).intValue();
				
				//verifico il valore corrispondente nel record dei dati di test
				valueInput2 = knownData[recordPos][posInput2];
				classeSecondoIngressoAttuale++;
			}
			else
				nandInserito = false;
		}
		else
		{
			if (portaDaAssemblare<17)
			{
				portaDaAssemblare++;
				valueInput2 = getOutput(recordPos,nandGates[portaDaAssemblare][0], nandGates[portaDaAssemblare][1], nandGates[portaDaAssemblare][2], classiPrimiIngressi, classiSecondiIngressi, nandGates);
			}
			else
				nandInserito = false;
		}
		
		/* 
		 * calcolati i valori dei due ingressi della porta mi calcolo la sua uscita solo se sono riuscito 
		 * ad inserirla in rete, altrimenti la sua uscita è automaticamente = 0
		 */
		if (nandInserito)
		{
			if (!(valueInput1==1 && valueInput2==1))
				output=1;
		}
		
		return output;
	}
    
}
