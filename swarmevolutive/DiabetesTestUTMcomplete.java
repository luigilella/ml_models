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

Pima Indian Diabetes Database is a data set with 768 observations  (i.e.  768 
records) and 9 variables.
(https://networkrepository.com/pima-indians-diabetes.php) 

*****************************************************************************/

package swarmevolutive;

public class DiabetesTestUTMcomplete {
	public static void main(String[] args) {

		// Carico solo una volta le informazioni del training set
    	FitnessCalc.loadData("C:\\Users\\luigi.lella\\Desktop\\TestBench per modelli AI e ML\\diabetes.csv");
		
		// Creo una popolazione iniziale di 50 cromosomi corrispondenti a 50 configurazioni di reti combinatorie di porte NAND
		Population myPop = new Population(50, true);
		
		// Evolvo la popolazione per 500 generazioni
		int generationCount = 0;
		
		System.out.println("INIZIO EVOLUZIONE POPOLAZIONE");

		for (int i=0; i<100000; i++)
		{
			generationCount++;
			myPop = BaitBallsAlgorithm.evolvePopulation(myPop);
			System.out.println(" - Generation: " + generationCount + " Fittest score: " + myPop.getFittest().getFitness());
		}
		System.out.println("Genes:");
		for (int i=0; i<50; i++)
        {
        	System.out.println(myPop.getIndividual(i)+" : "+myPop.getIndividual(i).getFitness());
        }

    }

}
