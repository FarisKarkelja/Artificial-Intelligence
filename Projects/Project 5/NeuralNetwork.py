import numpy as np
import matplotlib.pyplot as plt
from Data import readData
from NeuralNetworkHelper import *

#Function parameters
TRAINING_SET_PERCENTAGE = 0.7
VALIDATION_SET_PERCENTAGE = 0.1

hiddenLayerNeurons = 30
learningRate = 0.3
amountOfEpochs = 100

#Read and split the data into desired portions
images, labels = readData("C:\\Users\\Korisnik\\Desktop\\assignment 5\\assignment5.csv")

collectionOfSamples, collectionOfFeatures = images.shape

trainingDataX = images[:int(TRAINING_SET_PERCENTAGE * collectionOfSamples)].T
trainingDataY = labels[:int(TRAINING_SET_PERCENTAGE * collectionOfSamples)].T

validationDataX = images[int(TRAINING_SET_PERCENTAGE * collectionOfSamples):int((TRAINING_SET_PERCENTAGE + VALIDATION_SET_PERCENTAGE) * collectionOfSamples)].T
validationDataY = labels[int(TRAINING_SET_PERCENTAGE * collectionOfSamples):int((TRAINING_SET_PERCENTAGE + VALIDATION_SET_PERCENTAGE) * collectionOfSamples)].T

testingDataX = images[int((TRAINING_SET_PERCENTAGE + VALIDATION_SET_PERCENTAGE) * collectionOfSamples):].T
testingDataY = labels[int((TRAINING_SET_PERCENTAGE + VALIDATION_SET_PERCENTAGE) * collectionOfSamples):].T

#Initialize neural network weights and biases with the corresponding hidden layers
firstLayerWeight = np.random.randn(hiddenLayerNeurons, 784) * np.sqrt(2 / 784)
firstLayerBias = np.zeros((hiddenLayerNeurons, 1))
secondLayerWeight = np.random.randn(10, hiddenLayerNeurons) * np.sqrt(2 / hiddenLayerNeurons)
secondLayerBias = np.zeros((10, 1))

#Train the neural network and provide computed accuracies
firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias = trainNeuralNetwork(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, testingDataX, testingDataY, trainingDataX, trainingDataY, validationDataX, validationDataY, amountOfEpochs, learningRate)
firstLayer, firstLayerActivation, secondLayer, secondLayerActivation = performForwardPropagation(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, testingDataX)
testingAccuracy = calculateAccuracy(providePrediction(secondLayerActivation), providePrediction(testingDataY))
print("Testing Accuracy:", round(testingAccuracy * 100, 2), "%")

#Display an image from the test set and compare its prediction with the actual value
index = 183
imageData = testingDataX[:, index, None]
currentImage = imageData.reshape((28, 28)) * 255.0

plt.gray()
plt.imshow(currentImage, interpolation='nearest')
plt.show()