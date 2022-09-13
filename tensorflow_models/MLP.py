# -*- coding: utf-8 -*-
"""PimaProcessing.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1MbJnoxe_cYOmSn33XMifedphKA_xRK2J
"""

from google.colab import files

uploaded = files.upload()

for fn in uploaded.keys():
  print('User uploaded file "{name}" with length {length} bytes'.format(
      name=fn, length=len(uploaded[fn])))

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

Description:

This Colab TensorFlow notebook implements an MLP model to be trained with the 
PIMA Indians Diabetes Database plotting its learning curve.
Records with 0 values in Pima Indians Diabetes Database were not considered.

"""

import pandas as pd
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
import numpy as np 
from tensorflow import keras
from matplotlib import pyplot as plt
from IPython.display import clear_output

class PlotLearning(tf.keras.callbacks.Callback):
    
    """
    Callback to plot the learning curves of the model during training.
    """
    
    def on_train_begin(self, logs={}):
        self.metrics = {}
        for metric in logs:
            self.metrics[metric] = []
            

    def on_epoch_end(self, epoch, logs={}):
        # Storing metrics
        for metric in logs:
            if metric in self.metrics:
                self.metrics[metric].append(logs.get(metric))
            else:
                self.metrics[metric] = [logs.get(metric)]
      
        # Plotting
        metrics = [x for x in logs]
        
        f, axs = plt.subplots(1, len(metrics), figsize=(15,5))

        clear_output(wait=True)

        for i, metric in enumerate(metrics):
            axs[i].plot(range(1, epoch + 2), 
                        self.metrics[metric], 
                        label=metric)
                
            axs[i].legend()
            axs[i].grid()
        
        plt.tight_layout()

        plt.show()

# Three digits of precision for floating points
# always print floating point numbers using fixed point notation
np.set_printoptions(precision=3, suppress=True) 

# Importing PIMA Indians Diabetes Database in CSV format by pandas
pimaTrain = pd.read_csv("diabetesFiltered.csv", names=["Pregnancies", "OGTT", "BloodPressure", "SkinThickness", "Insulin", "BMI", "Age", "PedigreeDiabetesFunction", "TestResult"])

pimaFeatures = pimaTrain.copy()
pimaIndependentVariables = pimaFeatures.pop("TestResult") # 'TestResult' is the class variable

# Normalizing input data to reduce the effects of outliers in the training phase
normalizedData = tf.keras.layers.Normalization()
normalizedData.adapt(pimaFeatures)

# Defining  an MLP with 2 layers.  A 64 node layer  as middle layer  with a Relu 
# activation function and a 1 node layer as output layer.
# Relu activation function is used in the middle layer to ensure a real 0 output 
# and to speed up the learning process.
# A sigmoidal activation function is used in the output layer to assure a binary
# output.

norm_pima_model = tf.keras.Sequential([
    normalizedData,
    tf.keras.layers.Dense(64, activation="relu"),
    tf.keras.layers.Dense(1, activation="sigmoid")
])

callbacks_list = [PlotLearning()]

# Training model and plotting the learning curve
# Shuffling the order of the input before starting each epoch
# Verbosity mode: 0 = silent, 1 = progress bar, 2 = one line per epoch.
# Gradient descent is used as optimization algorithm in training

norm_pima_model.compile(loss = tf.losses.MeanSquaredError(), optimizer = tf.optimizers.Adam(), metrics=['accuracy'])
norm_pima_model.fit(pimaFeatures, pimaIndependentVariables, epochs=150, verbose=2, shuffle=True, callbacks=callbacks_list)
