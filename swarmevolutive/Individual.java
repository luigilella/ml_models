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

public class Individual {

	/*
	 * Il cromosoma è costituito da 24 bit con le classi di variabili di studio da prendere in
	 * considerazione come primo ingresso delle porte NAND, da 24 bit con le classi di variabili da 
	 * prendere in considerazione come secondo ingresso delle porte NAND, e da 3*8+3=27 con le 
	 * configurazioni delle porte NAND da collegare in rete a cominciare da quella che restituisce
	 * l'output della rete combinatoria autoorganizzante. Il numero totale di bit quindi è 
	 * 24+24+27=75.
	 * 
	 * La configurazione di una porta è rappresentata da 3 bit. I primi due indicano che tipologie di
	 * ingressi considerare. La configurazione (0,0) sta ad indicare che si devono utilizzare sia per
	 * l'ingresso 1 che per l'ingresso 2 le uscite di altre due porte NAND, la configurazione (1,0) sta
	 * ad indicare che si deve utilizzare per l'ingresso 1 una classe di variabile tra quelle da 
	 * prendere in considerazione come primo ingresso e per l'ingresso 2 un'altra porta NAND, la
	 * configurazione (0,1) sta ad indicare che si deve utilizzare per l'ingresso 1 un'altra porta NAND
	 * e per l'ingresso 2 una classe di variabile tra quelle da prendere in considerazione come
	 * secondo ingresso, la configurazione (1,1) sta ad indicare che si devono utilizzare per l'ingresso
	 * 1 una classe di variabile tra quelle da prendere in considerazione come primo ingresso e per
	 * l'ingresso 2 una classe di variabile tra quelle da prendere in considerazione come secondo 
	 * ingresso.
	 * 
	 * Se non è possibile assemblare una delle porte da collegare perchè mancano classi di variabili
	 * da inserire come primo ingresso, classi di variabili da inserire come secondo ingresso o altre
	 * porte NAND si inserisce come ingresso uno zero (l'equivalente di un collegamento mancante).
	 */
	static int defaultGeneLength = 102;
    
    private byte[] genes = new byte[defaultGeneLength];
    // Cache
    private int fitness = 0;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }
    
    public int getFitness() {
        if (fitness == 0) {
        	fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }
    
    public int matchRate(Individual other) {
    	int result = 0;
    	for (int i = 0; i < size(); i++) {
            if(this.getGene(i)==other.getGene(i))
            	result++;
        }
        return result;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
