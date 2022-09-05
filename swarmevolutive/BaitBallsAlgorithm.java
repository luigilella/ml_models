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

Description :

The bait ball is a spontaneous generated spherical formation that appears when 
a group of fishes try to escape from predators. 
I developed a new optimization algorithm which is similar to other evolutionary 
algorithms (like  the genetic algorithm) and  it is based  on  the  bait balls 
strategy (https://en.wikipedia.org/wiki/Bait_ball)  where  the  predators  are 
represented by the higher values of a previsional error function.
In this way  it is possible to avoid local minima of the error function within 
the solution space. The  evolutionary bait balls model (EBBM) is described  by 
the following algorithm:

Input: Array of individuals I to be updated
Output:  The position vectors (binary vectors) of each individual in I will be 
changed.

1: call function to alter the positions of each individual
2:  for all i ϵ I do
3: 	 perform ZOR, ZOA, ZOO sets calculations
4:	 if individual detected in ZOR then
5:		 perform repulsion (R)
6:	 else if individual detected in ZOO then
7:	 	 perform orientation (O)
8:	 else if individual detected in ZOA then
9:	 	 perform attraction (A)
10:	 end if
11: end for

Where ZOR  is the Repulsion Zone:  an individual cannot occupy the position of 
another, that is, it cannot be represented by the same binary vector.  In this 
case it assumes another position at random (every single bit of the chromosome 
is changed with probability repulsionRate). ZOA is the Zone of Attraction:  an 
individual tends to approach  individuals  characterized by a greater  fitness 
(with probability attractionRate each single bit of the chromosome  can assume 
the  same  value  as  the  bit  at  the  same position  of the best performing 
individual in the ZOA set).  ZOO is the Orientation Zone:  an individual tends 
to get closer, among the individuals close to him, to the best  performing one 
(with  probability  orientationRate,  every single bit  of the chromosome  can 
assume the same value  as the bit at the same position of the  best performing 
neighbor).
For  each  individual, to  define ZOR, ZOA, ZOO sets of other chromosomes, the 
ZORrange, ZOArange  and  ZOOrange  parameters were introduced, which represent 
the maximum number of different bits between the considered chromosome and the 
individual belonging to the chromosome ZOR, ZOA and ZOO respectively.

Further information is available in the following article:
- Lella, L., Licata, I., Pristipino, C. (2022). Pima Indians Diabetes Database 
Processing through EBBM-Optimized UTM Model. In Nathalie Bier, Ana  L. N. Fred, 
Hugo Gamboa, editors, Proceedings  of the  15th International Joint Conference 
on  Biomedical  Engineering  Systems  and  Technologies, BIOSTEC  2022, vol 5: 
HEALTHINF,  Online  Streaming, February 9-11, 2022. pages  384-389, SCITEPRESS, 
2022.  

****************************************************************************/

package swarmevolutive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class BaitBallsAlgorithm {

    /* GA parameters */
	private static final int individualSize = 102;
	
	private static final double attractionRate =  0.1; 
    private static final double orientationRate = 0.5; 
    private static final double repulsionRate = 0.5; 
    private static final int ZOArange = 102; 
    private static final int ZOOrange = 5; 
    private static final int ZORrange = 0; 
    private static final boolean selfishHerdNucleus = false; //does not produce great effects. It works best without it.

    /* Public methods */
    
    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);
        
        // Keep the best individual (Herd Nucleus) in its position
        if (selfishHerdNucleus) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Selfish Herd Strategy
        int selfishHerdNucleusOffset;
        if (selfishHerdNucleus) {
        	selfishHerdNucleusOffset = 1;
        } else {
        	selfishHerdNucleusOffset = 0;
        }
        for (int i = selfishHerdNucleusOffset; i < pop.size(); i++) {
        	Population ZOR = ZORcalculation(pop.getIndividual(i),pop);
        	if (ZOR.size()>=1)
        	{
        		System.out.print("R");
        		newPopulation.saveIndividual(i,pop.getIndividual(i));
        		repulsion(newPopulation.getIndividual(i));
        	}
        	else
        	{
        		Population ZOO = ZOOcalculation(pop.getIndividual(i),pop);
        		if (ZOO.size()>=1)
            	{
        			System.out.print("O");
        			newPopulation.saveIndividual(i,orientation(pop.getIndividual(i),ZOO.getFittest()));
            	}
        		else
        		{
        			Population ZOA = ZOAcalculation(pop.getIndividual(i),pop);
        			if (ZOA.size()>=1)
        			{
        				System.out.print("A");
        				newPopulation.saveIndividual(i,attraction(pop.getIndividual(i),ZOA.getFittest()));
        			}
        			else
        			{
        				newPopulation.saveIndividual(i,pop.getIndividual(i));
        			}
        			
        		}
        		
        	}
        }

        return newPopulation;
    }
    
    // Attraction operator
    private static Individual attraction(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (Math.random() <= attractionRate) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }
    
    // Orientation operator
    private static Individual orientation(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (Math.random() <= orientationRate) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }
    
    // Repulsion operator
    private static void repulsion(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= repulsionRate) {
                // Create random gene
                byte gene = (byte) Math.round(Math.random());
                indiv.setGene(i, gene);
            }
        }
    }
    
    // Zone Of Repulsion (ZOR) calculation 
    private static Population ZORcalculation (Individual indiv, Population pop) {

        TreeMap<Integer, Individual> orderedMap = new TreeMap<Integer, Individual>();
        for (int i = 0; i < pop.size(); i++) {
        	if ((!indiv.equals(pop.getIndividual(i)))&&((individualSize-indiv.matchRate(pop.getIndividual(i)))<=ZORrange))
        		orderedMap.put((individualSize-indiv.matchRate(pop.getIndividual(i))),pop.getIndividual(i));
        }
        Collection<Individual> values = orderedMap.values();
        List<Individual> listValues = new ArrayList<Individual>( values );
        
        Population ZOR = new Population(listValues.size(), false);

        for (int i = 0; i < listValues.size(); i++) {
            ZOR.saveIndividual(i, listValues.get(i));
        }
        
        return ZOR;
    }
    
    // Zone Of Orientation (ZOO) calculation 
    private static Population ZOOcalculation (Individual indiv, Population pop) {

        TreeMap<Integer, Individual> orderedMap = new TreeMap<Integer, Individual>();
        for (int i = 0; i < pop.size(); i++) {
        	if ((!indiv.equals(pop.getIndividual(i)))&&((individualSize-indiv.matchRate(pop.getIndividual(i)))<=ZOOrange))
        		orderedMap.put((individualSize-indiv.matchRate(pop.getIndividual(i))),pop.getIndividual(i));
        }
        Collection<Individual> values = orderedMap.values();
        List<Individual> listValues = new ArrayList<Individual>( values );
        
        Population ZOO = new Population(listValues.size(), false);

        for (int i = 0; i < listValues.size(); i++) {
            ZOO.saveIndividual(i, listValues.get(i));
        }
        
        return ZOO;
    }
    
    // Zone Of Attraction (ZOA) calculation 
    private static Population ZOAcalculation (Individual indiv, Population pop) {

        TreeMap<Integer, Individual> orderedMap = new TreeMap<Integer, Individual>();
        for (int i = 0; i < pop.size(); i++) {
        	if ((!indiv.equals(pop.getIndividual(i)))&&((individualSize-indiv.matchRate(pop.getIndividual(i)))<=ZOArange))
        		orderedMap.put((individualSize-indiv.matchRate(pop.getIndividual(i))),pop.getIndividual(i));
        }
        Collection<Individual> values = orderedMap.values();
        List<Individual> listValues = new ArrayList<Individual>( values );

        Population ZOA = new Population(listValues.size(), false);
        
        for (int i = 0; i < listValues.size(); i++) {
            ZOA.saveIndividual(i, listValues.get(i));  
        }
        
        return ZOA;
    }

}
