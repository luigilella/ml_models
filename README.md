# ml_models

The "ml_models" repository contains java and python packages that implement and test machine learning models. 

The java package "sonet" implements some self organizing networks which are the SOM model (https://en.wikipedia.org/wiki/Self-organizing_map) and the GNG model (https://en.wikipedia.org/wiki/Neural_gas).
The java packages "sontest" and "showres" contain the classes to test the SOM and GNG self organizing models on a two-dimensional data sample and to display the results of their classification on the screen. 
The java package "sontest" provides also:

- the class AnalisiSDO.java, a GNG-based prediction model to evaluate the lenght of hospital stay (https://dlnext.acm.org/doi/10.1016/j.im.2020.103282);
- the package "text-comprehension", with a GNG-based simplified implementation of the W.Kintsch human text comprehension model (https://pubmed.ncbi.nlm.nih.gov/8203801/).

The "py_sonet" package contains a python implementation of SOM and GNG models.

The java package "swarmevolutive" contains examples of hybrid evolutionary-swarm algorithms and their usage in real world datasets:
- an hybrid evolutionary-swarm algorithm that I called Evolutionary Bait Balls Model (BaitBallsAlgorithm.java)
(https://pdfs.semanticscholar.org/148e/bf3f76f93a66a88241c24167232b30183c25.pdf?_ga=2.199879086.1425055701.1662369064-608064849.1656507500)
- a new predictive model (DiabetesTestUTMcomplete.java) tested on the Pima Indians Diabetes Database (https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database). This model represents a particular subclass of A-Type Unorganized Turing machine (www.alanturing.net/intelligent_machinery). It appears as a simple combinational network of NAND gates (it is not the more generic sequential type described by Turing, but it is enough to accomplish the examined predictive task). The optimal configuration of this network is identified by the use of the Evolutionary Bait Balls Model (EBBM). 

The package "tensorflow_models" contains machine learning models developed on Google Colaboratory to be used with Pima Indians Diabetes Database (https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database):
- MLP.py implements a Multilayer Perceptron model (https://www.sciencedirect.com/topics/computer-science/multilayer-perceptron) with 64 hidden layer nodes and 1 output node. Original file is located at https://colab.research.google.com/drive/1MbJnoxe_cYOmSn33XMifedphKA_xRK2J
