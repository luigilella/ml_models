"""
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
"""

import pandas as pd
import numpy as np
import random as rnd
import matplotlib.pyplot as plt
import time as tm

class FitnessCalc(object):
    
    trainingSetSize = 392 # of 392
    
    """
    * knownData is a boolean list with the encoded data of the PIMA Diabetes database  
    * Each row of the list represents a database record encoded by 24 bits valued at 
    * 0 or 1 depending on whether or not the value of the considered attribute falls 
    * within the corresponding class together with another bit representing the outcome 
    * of the diagnosis (positive/negative) 
    """

    def __init__(self, knownData):
        self.knownData = knownData
        self.firstInput = 0
        self.secondInput = 0
        self.gateToAssembly = 0
    
    """
    * This method calculates the predictive accuracy of the combinational network of NAND gates 
    * corresponding to the individual taken into consideration.
    * The following data structures are needed:
    * - a firstInputs list containing the integers representing the information to be
    * used to configure the first input of a NAND gate;
    * - a secondInputs list containing the integers representing the information to be 
    * used to configure the second input of a NAND gate;
    * - a nandGates matrix containing the configurations of the 18 NAND gates of the self-organizing 
    * combinational network.
    * 
    * These structures are read progressively to retrieve the variables that have yet to be considered 
    * by the use of other three int variables that are firstInput, secondInput, and gateToAssembly. 
    * These three indices obviously cannot exceed the size of their corresponding data structures.
    """
    
    def getFitness(self, individual):
        fitness = 0
        firstInputs = []
        secondInputs = []
        nandGates = []
        columns1 = []
        columns2 = []
        columns3 = []
        columns4 = []
        columns5 = []
        columns6 = []
        columns7 = []
        columns8 = []
        columns9 = []
        columns10 = []
        columns11 = []
        columns12 = []
        columns13 = []
        columns14 = []
        columns15 = []
        columns16 = []
        columns17 = []
        columns18 = []
        
        nandNetOutput = 0
        
        for i in range(len(individual)):
            
            if (i==0):  # first group of 24 bits related to the indices representing the direct connection of a first input to a codified input attribute
                if (individual[i]==1):
                    firstInputs.append(0)
            elif (i==1):
                if (individual[i]==1):
                    firstInputs.append(1)
            elif (i==2):
                if (individual[i]==1):
                    firstInputs.append(2)
            elif (i==3):
                if (individual[i]==1):
                    firstInputs.append(3)
            elif (i==4):
                if (individual[i]==1):
                    firstInputs.append(4)
            elif (i==5):
                if (individual[i]==1):
                    firstInputs.append(5)
            elif (i==6):
                if (individual[i]==1):
                    firstInputs.append(6)
            elif (i==7):
                if (individual[i]==1):
                    firstInputs.append(7)
            elif (i==8):
                if (individual[i]==1):
                    firstInputs.append(8)
            elif (i==9):
                if (individual[i]==1):
                    firstInputs.append(9)
            elif (i==10):
                if (individual[i]==1):
                    firstInputs.append(10)
            elif (i==11):
                if (individual[i]==1):
                    firstInputs.append(11)
            elif (i==12):
                if (individual[i]==1):
                    firstInputs.append(12)
            elif (i==13):
                if (individual[i]==1):
                    firstInputs.append(13)
            elif (i==14):
                if (individual[i]==1):
                    firstInputs.append(14)
            elif (i==15):
                if (individual[i]==1):
                    firstInputs.append(15)
            elif (i==16):
                if (individual[i]==1):
                    firstInputs.append(16)
            elif (i==17):
                if (individual[i]==1):
                    firstInputs.append(17)
            elif (i==18):
                if (individual[i]==1):
                    firstInputs.append(18)
            elif (i==19):
                if (individual[i]==1):
                    firstInputs.append(19)
            elif (i==20):
                if (individual[i]==1):
                    firstInputs.append(20)
            elif (i==21):
                if (individual[i]==1):
                    firstInputs.append(21)
            elif (i==22):
                if (individual[i]==1):
                    firstInputs.append(22)
            elif (i==23):
                if (individual[i]==1):
                    firstInputs.append(23)
            elif (i==24): # second group of 24 bits related to the indices representing the direct connection of a second input to a codified input attribute
                if (individual[i]==1):
                    secondInputs.append(0)
            elif (i==25):
                if (individual[i]==1):
                    secondInputs.append(1)
            elif (i==26):
                if (individual[i]==1):
                    secondInputs.append(2)
            elif (i==27):
                if (individual[i]==1):
                    secondInputs.append(3)
            elif (i==28):
                if (individual[i]==1):
                    secondInputs.append(4)
            elif (i==29):
                if (individual[i]==1):
                    secondInputs.append(5)
            elif (i==30):
                if (individual[i]==1):
                    secondInputs.append(6)
            elif (i==31):
                if (individual[i]==1):
                    secondInputs.append(7)
            elif (i==32):
                if (individual[i]==1):
                    secondInputs.append(8)
            elif (i==33):
                if (individual[i]==1):
                    secondInputs.append(9)
            elif (i==34):
                if (individual[i]==1):
                    secondInputs.append(10)
            elif (i==35):
                if (individual[i]==1):
                    secondInputs.append(11)
            elif (i==36):
                if (individual[i]==1):
                    secondInputs.append(12)
            elif (i==37):
                if (individual[i]==1):
                    secondInputs.append(13)
            elif (i==38):
                if (individual[i]==1):
                    secondInputs.append(14)
            elif (i==39):
                if (individual[i]==1):
                    secondInputs.append(15)
            elif (i==40):
                if (individual[i]==1):
                    secondInputs.append(16)
            elif (i==41):
                if (individual[i]==1):
                    secondInputs.append(17)
            elif (i==42):
                if (individual[i]==1):
                    secondInputs.append(18)
            elif (i==43):
                if (individual[i]==1):
                    secondInputs.append(19)
            elif (i==44):
                if (individual[i]==1):
                    secondInputs.append(20)
            elif (i==45):
                if (individual[i]==1):
                    secondInputs.append(21)
            elif (i==46):
                if (individual[i]==1):
                    secondInputs.append(22)
            elif (i==47):
                if (individual[i]==1):
                    secondInputs.append(23)
            elif (i==48): # third group of 54 bit representing the 9 NAND gates configurations
                if (individual[i]==1): # output NAND gate (NAND gate no.1)
                    columns1.append(1)
                else:
                    columns1.append(0)
            elif (i==49):
                if (individual[i]==1):
                    columns1.append(1)
                else:
                    columns1.append(0)
            elif (i==50):
                if (individual[i]==1):
                    columns1.append(1)
                else:
                    columns1.append(0)
            elif (i==51): # NAND gate no.2
                if (individual[i]==1): 
                    columns2.append(1)
                else:
                    columns2.append(0)
            elif (i==52):
                if (individual[i]==1):
                    columns2.append(1)
                else:
                    columns2.append(0)
            elif (i==53):
                if (individual[i]==1):
                    columns2.append(1)
                else:
                    columns2.append(0)
            elif (i==54): # NAND gate no.3
                if (individual[i]==1): 
                    columns3.append(1)
                else:
                    columns3.append(0)
            elif (i==55):
                if (individual[i]==1):
                    columns3.append(1)
                else:
                    columns3.append(0)
            elif (i==56):
                if (individual[i]==1):
                    columns3.append(1)
                else:
                    columns3.append(0)
            elif (i==57): # NAND gate no.4
                if (individual[i]==1): 
                    columns4.append(1)
                else:
                    columns4.append(0)
            elif (i==58):
                if (individual[i]==1):
                    columns4.append(1)
                else:
                    columns4.append(0)
            elif (i==59):
                if (individual[i]==1):
                    columns4.append(1)
                else:
                    columns4.append(0)
            elif (i==60): # NAND gate no.5
                if (individual[i]==1): 
                    columns5.append(1)
                else:
                    columns5.append(0)
            elif (i==61):
                if (individual[i]==1):
                    columns5.append(1)
                else:
                    columns5.append(0)
            elif (i==62):
                if (individual[i]==1):
                    columns5.append(1)
                else:
                    columns5.append(0)
            elif (i==63): # NAND gate no.6
                if (individual[i]==1): 
                    columns6.append(1)
                else:
                    columns6.append(0)
            elif (i==64):
                if (individual[i]==1):
                    columns6.append(1)
                else:
                    columns6.append(0)
            elif (i==65):
                if (individual[i]==1):
                    columns6.append(1)
                else:
                    columns6.append(0)
            elif (i==66): # NAND gate no.7
                if (individual[i]==1): 
                    columns7.append(1)
                else:
                    columns7.append(0)
            elif (i==67):
                if (individual[i]==1):
                    columns7.append(1)
                else:
                    columns7.append(0)
            elif (i==68):
                if (individual[i]==1):
                    columns7.append(1)
                else:
                    columns7.append(0)
            elif (i==69): # NAND gate no.8
                if (individual[i]==1): 
                    columns8.append(1)
                else:
                    columns8.append(0)
            elif (i==70):
                if (individual[i]==1):
                    columns8.append(1)
                else:
                    columns8.append(0)
            elif (i==71):
                if (individual[i]==1):
                    columns8.append(1)
                else:
                    columns8.append(0)
            elif (i==72): # NAND gate no.9
                if (individual[i]==1): 
                    columns9.append(1)
                else:
                    columns9.append(0)
            elif (i==73):
                if (individual[i]==1):
                    columns9.append(1)
                else:
                    columns9.append(0)
            elif (i==74):
                if (individual[i]==1):
                    columns9.append(1)
                else:
                    columns9.append(0)
            elif (i==75): # NAND gate no.10
                if (individual[i]==1): 
                    columns10.append(1)
                else:
                    columns10.append(0)
            elif (i==76):
                if (individual[i]==1):
                    columns10.append(1)
                else:
                    columns10.append(0)
            elif (i==77):
                if (individual[i]==1):
                    columns10.append(1)
                else:
                    columns10.append(0)
            elif (i==78): # NAND gate no.11
                if (individual[i]==1): 
                    columns11.append(1)
                else:
                    columns11.append(0)
            elif (i==79):
                if (individual[i]==1):
                    columns11.append(1)
                else:
                    columns11.append(0)
            elif (i==80):
                if (individual[i]==1):
                    columns11.append(1)
                else:
                    columns11.append(0)
            elif (i==81): # NAND gate no.12
                if (individual[i]==1): 
                    columns12.append(1)
                else:
                    columns12.append(0)
            elif (i==82):
                if (individual[i]==1):
                    columns12.append(1)
                else:
                    columns12.append(0)
            elif (i==83):
                if (individual[i]==1):
                    columns12.append(1)
                else:
                    columns12.append(0)
            elif (i==84): # NAND gate no.13
                if (individual[i]==1): 
                    columns13.append(1)
                else:
                    columns13.append(0)
            elif (i==85):
                if (individual[i]==1):
                    columns13.append(1)
                else:
                    columns13.append(0)
            elif (i==86):
                if (individual[i]==1):
                    columns13.append(1)
                else:
                    columns13.append(0)
            elif (i==87): # NAND gate no.14
                if (individual[i]==1): 
                    columns14.append(1)
                else:
                    columns14.append(0)
            elif (i==88):
                if (individual[i]==1):
                    columns14.append(1)
                else:
                    columns14.append(0)
            elif (i==89):
                if (individual[i]==1):
                    columns14.append(1)
                else:
                    columns14.append(0)
            elif (i==90): # NAND gate no.15
                if (individual[i]==1): 
                    columns15.append(1)
                else:
                    columns15.append(0)
            elif (i==91):
                if (individual[i]==1):
                    columns15.append(1)
                else:
                    columns15.append(0)
            elif (i==92):
                if (individual[i]==1):
                    columns15.append(1)
                else:
                    columns15.append(0)
            elif (i==93): # NAND gate no.16
                if (individual[i]==1): 
                    columns16.append(1)
                else:
                    columns16.append(0)
            elif (i==94):
                if (individual[i]==1):
                    columns16.append(1)
                else:
                    columns16.append(0)
            elif (i==95):
                if (individual[i]==1):
                    columns16.append(1)
                else:
                    columns16.append(0)
            elif (i==96): # NAND gate no.17
                if (individual[i]==1): 
                    columns17.append(1)
                else:
                    columns17.append(0)
            elif (i==97):
                if (individual[i]==1):
                    columns17.append(1)
                else:
                    columns17.append(0)
            elif (i==98):
                if (individual[i]==1):
                    columns17.append(1)
                else:
                    columns17.append(0)
            elif (i==99): # NAND gate no.18
                if (individual[i]==1): 
                    columns18.append(1)
                else:
                    columns18.append(0)
            elif (i==100):
                if (individual[i]==1):
                    columns18.append(1)
                else:
                    columns18.append(0)
            elif (i==101):
                if (individual[i]==1):
                    columns18.append(1)
                else:
                    columns18.append(0)      
        nandGates.append(columns1)
        nandGates.append(columns2)
        nandGates.append(columns3)
        nandGates.append(columns4)
        nandGates.append(columns5)
        nandGates.append(columns6)
        nandGates.append(columns7)
        nandGates.append(columns8)
        nandGates.append(columns9)
        nandGates.append(columns10)
        nandGates.append(columns11)
        nandGates.append(columns12)
        nandGates.append(columns13)
        nandGates.append(columns14)
        nandGates.append(columns15)
        nandGates.append(columns16)
        nandGates.append(columns17)
        nandGates.append(columns18)
        
        """ 
        * The fitness score of the network configuration represented by the considered individual 
        * is calculated counting the cases of correct prediction of the outcome
        """
        
        for i in range(self.trainingSetSize):
            # reset of the three counter variables 
            self.firstInput = 0
            self.secondInput = 0
            self.gateToAssembly = 0
            
            # testing the network configuration with the i-th record
            nandNetOutput = self.getOutput(i,nandGates[self.gateToAssembly][0], nandGates[self.gateToAssembly][1], nandGates[self.gateToAssembly][2], firstInputs, secondInputs, nandGates)
            
            if (nandNetOutput == self.knownData[i][24]):
                fitness+=1
        
        return fitness
    
    """
    * recursive method for calculating the output of the combinational network
    """
    def getOutput(self, recordPos, input1, input2, shortCircuitedInputs, firstInputs, secondInputs, nandGates):
        output = 0
        assembledGate = True
        posInput1 = 0
        posInput2 = 0
        valueInput1 = 0
        valueInput2 = 0
        
        if (input1==1): 
            if (self.firstInput<len(firstInputs)):
                #retrieving the first input value at the position firstInput
                posInput1 = firstInputs[self.firstInput]
                
                #retrieving the corresponding value in the test data set
                valueInput1 = self.knownData[recordPos][posInput1]
                self.firstInput+=1
            else:
                assembledGate = False
        else:
            if (self.gateToAssembly<17):
                self.gateToAssembly+=1
                valueInput1 = self.getOutput(recordPos,nandGates[self.gateToAssembly][0], nandGates[self.gateToAssembly][1], nandGates[self.gateToAssembly][2], firstInputs, secondInputs, nandGates)
            else:
                assembledGate = False
        
        if (shortCircuitedInputs==1):
            valueInput2=valueInput1
        elif (input2==1):
            if (self.secondInput<len(secondInputs)):
                #retrieving the second input value at the position secondtInput
                posInput2 = secondInputs[self.secondInput]
                
                #retrieving the corresponding value in the test data set
                valueInput2 = self.knownData[recordPos][posInput2]
                self.secondInput+=1
            else:
                assembledGate = False;
        else:
            if (self.gateToAssembly<17):
                self.gateToAssembly+=1
                valueInput2 = self.getOutput(recordPos,nandGates[self.gateToAssembly][0], nandGates[self.gateToAssembly][1], nandGates[self.gateToAssembly][2], firstInputs, secondInputs, nandGates)
            else:
                assembledGate = False
        
        """ 
        * if the gate is assembled, once the values of the two inputs are calculated the output of the gate is evaluated, 
        * otherwise its output is set to 0
        """
        if (assembledGate):
            if (not(valueInput1==1 and valueInput2==1)):
                output=1
        
        return output

class Individual(object):

    """
    * An individual is represented by 24 bits related to the variable classes to be taken into account as  
    * first inputs of the NAND gates, plus 24 bits related to the variable classes to be taken into account  
    * as second inputs of the NAND gates, plus 3 * 17 + 3 = 54 bits representing the configurations 
    * of the assembled NAND gates, starting with the one that returns the output of the self-organizing 
    * combinational network. The overall number of bits is 24+24+54=102.
    * 
    * If it is not possible to assembly one of the ports to be connected because there are no variable 
    * classes to be inserted as first input, or there are no variable classes to be inserted as a second 
    * input or there are no other NAND gates to assembly, a zero is considered as input (the equivalent 
    * of a missing link).
    """
    defaultGeneLength = 102

    # Create an individual with random genes 
    def __init__(self, knownData):
        # Loading dataset
        self.knownData = knownData
        self.genes = []
        self.fitness = 0
        for _ in range(self.defaultGeneLength):
            self.genes.append(int(round(rnd.uniform(0, 1),0)))
        #print(self.genes)  
    
    def getGene(self, index): 
        return self.genes[index]

    def setGene(self, index, value):
        self.genes[index] = value
        self.fitness = 0

    def getGenes(self):
        return self.genes

    def size(self):
        return self.defaultGeneLength
    
    def getFitness(self):
        if (self.fitness == 0):
            fCalc = FitnessCalc(self.knownData)
            self.fitness = fCalc.getFitness(self.genes)
        return self.fitness
    
    def matchRate(self, otherIndividual):
        result = 0
        for i in range(self.defaultGeneLength):
            if(self.genes[i] == otherIndividual.getGene(i)):
                result += 1
        return result

class Population(object):

    def __init__(self, populationSize, initialize, knownData, individuals = []):
        #self.individuals = individuals
        if (initialize):
            self.individuals = []
            for i in range(populationSize): 
                newIndividual = Individual(knownData)
                self.individuals.append(newIndividual)  
        else:
            self.individuals = individuals

    def clone(self):
        result = []
        for i in range(len(self.individuals)):
            result.append(self.individuals[i]) 
        return result              

    def getIndividual(self, index):
        return self.individuals[index]

    def getIndividuals(self):
        return self.individuals
    
    def getFittest(self):
        fittest = self.individuals[0]
        # Loop through individuals to find fittest
        for i in range (len(self.individuals)):
            if (fittest.getFitness() <= self.getIndividual(i).getFitness()):
                fittest = self.getIndividual(i)
        return fittest;  
    
    def getSecondFittest(self):    
        fittest = self.getFittest();
        secondFittest = self.individuals[0];
        # Loop through individuals to find fittest
        for i in range (len(self.individuals)):
            if ((secondFittest.getFitness() <= self.getIndividual(i).getFitness()) and (not self.getIndividual(i)==fittest)): 
                secondFittest = self.getIndividual(i)
        return secondFittest

    def size(self): 
        return len(self.individuals)

    def saveIndividual(self, index, indiv):
        self.individuals[index] = indiv

class GeneticAlgorithm(object):

    # Evolutionary algorithm parameters 
    uniformRate = 0.5
    mutationRate =  0.015 
    tournamentSize = 5 
    elitism = True

    def __init__(self, knownData):
      self.knownData = knownData

    # Crossover operator
    def crossover(self, indiv1, indiv2):
        newSol = Individual(self.knownData)
        # Loop through genes
        for i in range(indiv1.size()):
            # Crossover
            if (rnd.uniform(0,1) <= self.uniformRate):
                newSol.setGene(i, indiv1.getGene(i))
            else:
                newSol.setGene(i, indiv2.getGene(i))
        return newSol

    # Tournament Selection operator
    def tournamentSelection(self, pop):
        # Create a tournament population
        tournament = Population(self.tournamentSize, True, self.knownData)
        # For each place in the tournament get a random individual
        for i in range(self.tournamentSize):
            tournament.saveIndividual(i,pop.getIndividual(int(round(rnd.uniform(0, 1)*(pop.size()-1),0))))
        # Get the fittest
        fittest = tournament.getFittest()
        return fittest
    
    # Mutation operator
    def mutate(self, indiv):
        newSol = Individual(self.knownData)
        # Loop through genes
        for i in range(newSol.size()):
            if (rnd.uniform(0,1) > self.mutationRate):
                # Leave indiv gene
                newSol.setGene(i,indiv.getGene(i))
        return newSol
    
    # Evolve a population
    def evolvePopulation(self, pop):

        #import pdb; pdb.set_trace()

        newPopulation = Population(pop.size(), False, self.knownData, pop.clone())
        #newPopulation = Population(pop.size(), False, self.knownData, pop.getIndividuals())

        elitismOffset = 0

        # Keep the best individual
        if (self.elitism):
            elitismOffset = 1    

        # Loop over the population size and create new individuals by corssover operator
        for i in range (elitismOffset, pop.size()):
            indiv1 = self.tournamentSelection(pop)
            indiv2 = self.tournamentSelection(pop)
            newIndividual = self.crossover(indiv1, indiv2)
            newPopulation.saveIndividual(i, newIndividual)

        # Mutate random individuals
        for i in range (elitismOffset, pop.size()):  
            newPopulation.saveIndividual(i, self.mutate(newPopulation.getIndividual(i)))  

        return newPopulation

# Loading data
knownData = []

print("LOADING DATA")
        
# Three digits of precision for floating points
# always print floating point numbers using fixed point notation
np.set_printoptions(precision=3, suppress=True) 

# Importing PIMA Indians Diabetes Database in CSV format by pandas
pimaTrain = pd.read_csv("diabetesFiltered.csv", names=["Pregnancies", "OGTT", "BloodPressure", "SkinThickness", "Insulin", "BMI", "Age", "PedigreeDiabetesFunction", "TestResult"])

dataFrames = pd.DataFrame(pimaTrain)
npArray = np.array(dataFrames.values)

for i in range(len(pimaTrain)):
    columns=[]
    for j in range(9):
        if (j==0):
            #1 - pregnancies: 0-5; 6-10; 11-17
            if (npArray[i,j]<=5):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>5 and npArray[i,j]<=10):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==1):
            #2 - glucose: 56-103; 104-150; 151-198
            if (npArray[i,j]<=103):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>103 and npArray[i,j]<=150):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==2):
            #3 - blood preassure: 24-52; 53-80; 81-110
            if (npArray[i,j]<=52):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>52 and npArray[i,j]<=80):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==3):
            #4 - skin thickness:  7-25; 26-43; 44-63
            if (npArray[i,j]<=25):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>25 and npArray[i,j]<=43):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==4):
            #5 - insulin: 14-291; 292-568; 569-846
            if (npArray[i,j]<=291):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>291 and npArray[i,j]<=568):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==5):
            #6 - bmi: 18-34; 35-50; 51-68
            if (npArray[i,j]<=34):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>34 and npArray[i,j]<=50):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==6):
            #7 - pedigree: 0-776; 777-1552; 1553-2330
            if (npArray[i,j]<=776):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>776 and npArray[i,j]<=1552):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==7):
            #8 - age: 21-41; 42-61; 62-81
            if (npArray[i,j]<=41):
                columns.append(1)
                columns.append(0)
                columns.append(0)
            elif (npArray[i,j]>41 and npArray[i,j]<=61):
                columns.append(0)
                columns.append(1)
                columns.append(0)
            else:
                columns.append(0)
                columns.append(0)
                columns.append(1)
        elif (j==8):
            #9 - Outcome
            if (npArray[i,j]==1):
                columns.append(1)
            else:
                columns.append(0)
    knownData.append(columns) 

print("Known data size: "+str(len(knownData)))

# Creating an initial population of 50 individuals corresponding to 50 configurations of NAND gates combinational networks
myPop = Population(50, True, knownData)

# Evolving the population for 500 generations
print("START EVOLVING POPULATION")
evolution = GeneticAlgorithm(knownData)

x = []
y = []

t1 = tm.perf_counter()
t2 = 0

#for i in range(100000):
for i in range(15000):
    myPop = evolution.evolvePopulation(myPop)
    # print(" - Generation:" + str(i) + " pop size: " + str(myPop.size()))
    x.append(i)
    y.append(myPop.getFittest().getFitness()/len(knownData))
    if(i%100==0):
        t2 = tm.perf_counter()
        print(" - Generation:" + str(i) + " mins:" + str((t2-t1)/60) + " Accuracy:" + str(myPop.getFittest().getFitness()/len(knownData)))
        #for j in range(50):
        #    print(str(myPop.getIndividual(j).getGenes())+" : "+str(myPop.getIndividual(j).getFitness()/len(knownData)))

# Plot Learning Curve
fig, axs = plt.subplots(1,1,figsize=(15,5))
axs.plot(x, y)
axs.set_xlabel('Epochs', fontsize=20)
axs.set_ylabel('Accuracy', fontsize=20)
plt.show()

print("Genes:")
for i in range(50):
    print(str(myPop.getIndividual(i).getGenes())+" : "+str(myPop.getIndividual(i).getFitness()/len(knownData)))
