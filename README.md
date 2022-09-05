# ml_models

The "ml_models" repository contains java and python packages that implement and test machine learning models. 

The java package "sonet" implements some self organizing networks which are the SOM model (https://en.wikipedia.org/wiki/Self-organizing_map) and the GNG model (https://en.wikipedia.org/wiki/Neural_gas).
The java packages "sontest" and "showres" contain the classes to test the SOM and GNG self organizing models on a two-dimensional data sample and to display the results of their classification on the screen. 
The java package "sontest" provides also:

- the class AnalisiSDO.java, a GNG-based prediction model to evaluate the lenght of hospital stay (https://dlnext.acm.org/doi/10.1016/j.im.2020.103282);
- the package "text-comprehension", with a GNG-based simplified implementation of the W.Kintsch human text comprehension model (https://pubmed.ncbi.nlm.nih.gov/8203801/).

The "py_sonet" package contains a python implementation of SOM and GNG models.

The java package "swarmevolutive" contains examples of hybrid evolutive-swarm algorithms and their usage in real world datasets:
- an hybrid evolutionary-swarm algorithm that I called Evolutionary Bait Balls Model (BaitBallsAlgorithm.java)
- a new predictive model tested on the Pima Indians Diabetes Database (DiabetesTestUTMcomplete.java). This model represents a particular subclass of A-Type Unorganized Turing machine (UTM). It appears as a simple combinational network of NAND gates (it is not the more generic sequential type described by Turing, but it is enough to accomplish the examined predictive task). The Evolutionary Bait Balls Model (EBBM) was tested for this purpose. 
