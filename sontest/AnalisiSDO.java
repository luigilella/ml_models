/****************************************************************************

Copyright (C) (2015) Luigi Lella, Antonio Di Giorgio

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

Length of stay (LoS) prediction  is considered an important research field in 
Healthcare Informatics  as it can help  to improve hospital bed  and resource 
management. In LoS prediction tasks a Growing Neural Gas Model (GNG) can find 
how many  attributes  in the defined  input space  are necessary  to  predict 
exactly the LoS class attribute.
We considered the following set of non-class attributes: admission discipline,  
admission  division, provenance,  recovery  type, trauma, day  hosdpital care 
reason,  main  diagnosis,  main  intervention,  gender,  age,  marital status, 
qualification. 
The admission discipline and the admission division have 99 possible values. 
There are only 9 values expected for the provenance: recovery without general 
practictioner  suggestion, recovery  with  general practictioner  suggestion, 
recovery  programme, transfer  from  a  public  structure,  transfer  from  a 
accredited  private  structure ,  transfer  from  a  not  accredited  private 
structure, transfer  from another department  or recovery regimen  within the 
same institute, emergency medical service and other provenances. The recovery 
type can take 6 different values: recovery programme, urgent hospitalization, 
mandatory  medical treatment,  recovery programme  with  pre-hospitalization, 
voluntary hospitalization for medical treatment.  Trauma  attribute  codifies 
accidents, injuries  and  poisonings  through  9  possible values:  workplace 
accident, home accident, road  accident, violence  of  others,  self-harm  or 
suicide  attempt,  animal  or  insect  bite,  sports  accident, other type of 
accident  or  poisoning. The  day  hospital  care  reason  can  be one of the 
following:  day hospital,  day surgery,  day therapy, day rehabilitation. The 
main diagnosis follows the international ICD9-CM coding system. Also the main 
intervention is based on the ICD9-CM system, but  it considers just the first 
four digits of the code. Eight different  age classes are expected: 0-4, 5-14,  
15-44, 45-64, 65-74, 74+. Six different marital status  have been  considered:
celibate or unmarried,  married, single separated, divorced, widower or widow, 
not specified.  Six different  qualifications are provided: no qualifications, 
elementary school licence,  middle or vocational  school licence,  degree  of 
professional qualification, baccalaureate, bachelor's degree.
At last  the class attribute (LoS) is codified in five different classes: one 
day  hospital stay,  two day hospital stay,  three days hospital stay,  below 
regional threshold stay, over regional threshold stay.

Better results can be obtained by encoding both the "symbolic" part (LoS) and 
the "contextual" part (i.e. the  non-class attributes) in  a binary format as 
described in the following articles: 

L.Luigi,  A.Di Giorgio,  A.Dragoni,  “ Length of stay prediction and analysis 
through a growing neural gas model”, CEUR-WS, 2015

L.Lella, I.Licata, “Prediction  of  Length  of  Hospital Stay using a Growing 
Neural Gas Model”, Proc.IMCIC 2017, Orlando, Florida (USA).

L.Lella,  I.Licata, “Prevedere  i  periodi  di  degenza”,  Office  Automation, 
Aprile 2018 n.04, pp.50-51

Discrete variables having relatively few values can be encoded using a one-hot 
code system like  LoS variable. The main diagnosis  and  the main intervention 
attribute values can be transformed in bynary (base-2) representations.

****************************************************************************/

package analisiStatistica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import sonet.Gng;

public class AnalisiSDO {

	public static void main(String[] args) {
		
		String basePath = "C:/Users/Utente/Desktop";
		String pathFile = basePath.concat("/datiSDO.csv");
		
		//training set and test set
		ArrayList<float[]> dati = new ArrayList<float[]>();
		
		try {
			FileReader f = new FileReader(pathFile);
			BufferedReader in = new BufferedReader(f);
			
			//records reading
			String linea = "";
			
			while (linea!=null)
			{

				linea = in.readLine();
				
				//non-class fields
				float disciAmm = 0; //admission discipline
				float divisAmm = 0; //admission division
				float proven = 0; //provenance
				float tipoRic = 0; //recovery type
				float trauma = 0; //trauma
				float motivoDh = 0; //day hosdpital care reason
				float diaPrinc = 0; //main diagnosis
				float intPrinc = 0; //main intervention
				float sesso = 0; //gender
				float statoCiv = 0; //marital status
				float titStud = 0; //qualification
				float clEta = 0; //age
				
				if (linea!=null)
				{
					//non-class fields values loading
					String[] record = linea.split(","); 
					disciAmm = (new Float(record[4])).floatValue()/99f;
					divisAmm = (new Float(record[5])).floatValue()/99f;
					proven = (new Float(record[7])).floatValue()/9f;
					tipoRic = (new Float(record[8])).floatValue()/9f;
					trauma = (new Float(record[9])).floatValue()/9f;
					motivoDh = (new Float(record[14])).floatValue()/9f; 
					diaPrinc = (new Float(record[16])).floatValue()/90003f; 
					intPrinc = (new Float(record[17])).floatValue()/9999f;
					sesso = (new Float(record[18])).floatValue()/2f;
					statoCiv = (new Float(record[19])).floatValue()/6f;
					titStud = (new Float(record[23])).floatValue()/5f;
					clEta = (new Float(record[25])).floatValue()/6f;
					
					//class field (LoS) value loading
					int intDegenza = new Integer(record[26]).intValue();
					
					if (intDegenza==1)
					{
						float[] recordFloat = {disciAmm,divisAmm,proven,tipoRic,trauma,
							motivoDh,diaPrinc,intPrinc,sesso,statoCiv,titStud,
							clEta,1,0,0,0,0};
						dati.add(recordFloat);
					}
					else if (intDegenza==2)
					{
						float[] recordFloat = {disciAmm,divisAmm,proven,tipoRic,trauma,
							motivoDh,diaPrinc,intPrinc,sesso,statoCiv,titStud,
							clEta,0,1,0,0,0};
						dati.add(recordFloat);
					}
					else if (intDegenza==3)
					{
						float[] recordFloat = {disciAmm,divisAmm,proven,tipoRic,trauma,
							motivoDh,diaPrinc,intPrinc,sesso,statoCiv,titStud,
							clEta,0,0,1,0,0};
						dati.add(recordFloat);
					}
					else if (intDegenza==4)
					{
						float[] recordFloat = {disciAmm,divisAmm,proven,tipoRic,trauma,
							motivoDh,diaPrinc,intPrinc,sesso,statoCiv,titStud,
							clEta,0,0,0,1,0};
						dati.add(recordFloat);
					}
					else if (intDegenza==5)
					{
						float[] recordFloat = {disciAmm,divisAmm,proven,tipoRic,trauma,
							motivoDh,diaPrinc,intPrinc,sesso,statoCiv,titStud,
							clEta,0,0,0,0,1};
						dati.add(recordFloat);
					}
				}
			}
			f.close();
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		float[][] matriceInput = new float[dati.size()][17];
		
		for (int i=0; i<dati.size(); i++)
			for (int j=0; j<17; j++)
				matriceInput[i][j]=((float[])dati.get(i))[j];
		
		//model training with the first N samples
		final int N=1100;
		Gng rete = new Gng(17);
		
			//training matrix 
			float[][] matriceTraining = new float[N][17];
			for (int i=0; i<N; i++)
				matriceTraining [i] = matriceInput [i];
		
		rete.train(matriceInput,0.0001);
		
		//model testing with the remaining samples
		int corretti = 0;
		
		for (int i=N; i<dati.size(); i++)
		{
			//test progress view
			float statoAvanzamento = (i-N+1)*100f/(dati.size()-N);
			System.out.println("Stato avanzamento test: "+statoAvanzamento+" %");
			
			//input test array creation  
			float[] singleInput = new float[17];
			for (int j=0; j<12; j++)
				singleInput[j]=dati.get(i)[j];
			//The "symbolic" part of the input array (i.e. the one related to the LoS class attribute) is eliminated. 
			//Just the "contextual" part of the input array is kept.
			singleInput[12]=0;
			singleInput[13]=0;
			singleInput[14]=0;
			singleInput[15]=0;
			singleInput[16]=0;
			//GNG output retrieving
			int nodoVincitore = rete.findWinner(singleInput)[0]; //winner node
			float[] pesoVincitrice = rete.getWeights(nodoVincitore); //winner node weights array
			int rispostaClassificatore = 0;
			float valMax = 0f;
			for (int w=0; w<5; w++) //maximum weight value in the "symbolic" part of the winner node weights array
				if (pesoVincitrice[12+w]>valMax)
				{
					valMax=pesoVincitrice[12+w];
					rispostaClassificatore = w+1;
				}
			int effettivo = 0;
			//comparison with actual data
			for (int r=0; r<5; r++)
				if (dati.get(i)[12+r]==1)
				{
					effettivo = r+1;
				}
			if (rispostaClassificatore==effettivo)
				corretti ++;
		}
		
		//forecast accuracy of the model
		float accuratezza = corretti*100f/(dati.size()-N);
		System.out.println("Accuratezza della classificazione: "+accuratezza+" %");

	}

}
