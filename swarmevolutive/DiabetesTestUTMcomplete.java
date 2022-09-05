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

Description:

This class tests a single state A-type model of  Unorganized  Turing  Machine 
(UTM)  consisting  of a  combinational network  of  NAND gates  whose optimal 
configuration  is identified  by evolving a population of chromosomes each of
which represents the coding of a UTM configuration. 
UTM is generated “in a unsystematic and random way” from  a set  of two-input 
NAND gates. Turing  chose  a NAND gate because every other logical operations 
can be  accomplished  by a set of  NAND  units.  A Turing  A-type unorganised 
machine can be considered “a kind of Boolean neural network without a layered 
structure,  due to the fact  that recurrent connections  are allowed  with no 
constraints”.
The  optimal  architecture  of  this  network  is identified by the use of an 
evolutionary algorithm. In particular an  hybrid evolutionary-swarm algorithm 
that I called Evolutionary Bait Balls Model (EBBM) was tested for this purpose.

Further information is available in the following articles:
-Teuscher C., Sanchez E. (2000). A Revival of Turing’s Forgotten Connectionist 
Ideas:  Exploring  Unorganized  Machines.  In  Proceedings  of the  6th Neural 
Computation and Psychology Workshop, NCPW6, University of Lige. 
- Turing A. (1948). Intelligent Machinery. In Collected Works  of A.M. Turing: 
Mechanical Intelligence. Edited by D.C. Ince. Elsevier Science Publishers, 1992.
- Lella, L., Licata, I., Pristipino, C. (2022). Pima Indians Diabetes Database 
Processing through EBBM-Optimized UTM Model. In Nathalie Bier, Ana  L. N. Fred, 
Hugo Gamboa, editors, Proceedings  of the  15th International Joint Conference 
on  Biomedical  Engineering  Systems  and  Technologies, BIOSTEC  2022, vol 5: 
HEALTHINF,  Online  Streaming, February 9-11, 2022. pages  384-389, SCITEPRESS, 
2022. 

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
