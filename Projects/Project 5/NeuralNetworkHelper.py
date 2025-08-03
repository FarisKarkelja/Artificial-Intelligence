import numpy as np
import matplotlib.pyplot as plt


#Relu activation function to remove negative values providing non-linearity
def applyReluFunction(x):
    return np.maximum(0, x)


#Softmax activation function to convert input into a probability
def computeSoftmaxActivation(softmaxInput):
    exponentialInput = np.exp(softmaxInput - np.max(softmaxInput, axis=0, keepdims=True))
    softmaxOutput = exponentialInput / exponentialInput.sum(axis=0)
    return softmaxOutput


#Propagate forward through two neural network layers
def performForwardPropagation(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, inputData):
    firstLayer = np.dot(firstLayerWeight, inputData) + firstLayerBias
    firstLayerActivation = applyReluFunction(firstLayer)
    secondLayer = np.dot(secondLayerWeight, firstLayerActivation) + secondLayerBias
    secondLayerActivation = computeSoftmaxActivation(secondLayer)
    return firstLayer, firstLayerActivation, secondLayer, secondLayerActivation


#Propagate backward, computing and normalizing loss function gradients
def performBackwardPropagation(firstLayer, firstLayerActivation, secondLayerWeight, secondLayerActivation, inputData, actualValue):
    secondLayerGradient = 2 * (secondLayerActivation - actualValue)
    secondWeightGradient = np.dot(secondLayerGradient, firstLayerActivation.T) / inputData.shape[1]
    secondBiasGradient = np.sum(secondLayerGradient, axis=1, keepdims=True) / inputData.shape[1]
    firstLayerGradient = np.dot(secondLayerWeight.T, secondLayerGradient) * (firstLayer > 0)
    firstWeightGradient = np.dot(firstLayerGradient, inputData.T) / inputData.shape[1]
    firstBiasGradient = np.sum(firstLayerGradient, axis=1, keepdims=True) / inputData.shape[1]
    return firstWeightGradient, firstBiasGradient, secondWeightGradient, secondBiasGradient


#Update the model parameters applying gradient descent
def manageParameters(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, firstWeightGradient, firstBiasGradient, secondWeightGradient, secondBiasGradient, learningRate):
    firstLayerWeight -= learningRate * firstWeightGradient
    firstLayerBias -= learningRate * np.reshape(firstBiasGradient, (firstBiasGradient.shape[0], 1))
    secondLayerWeight -= learningRate * secondWeightGradient
    secondLayerBias -= learningRate * np.reshape(secondBiasGradient, (secondBiasGradient.shape[0], 1))
    return firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias


#Provide the predicted class label by selecting the final output's highest probability index
def providePrediction(finalActivationOutput):
    return np.argmax(finalActivationOutput, axis=0)


#Calculate the model accuracy by comparing predicted values with actual values
def calculateAccuracy(predictedValues, actualValues):
    return np.sum(predictedValues == actualValues) / actualValues.shape[0]


#Train the neural network by applying necessary propagation and updating weights and biases
def trainNeuralNetwork(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, testingDataX, testingDataY, trainingDataX, trainingDataY, validationDataX, validationDataY, amountOfEpochs, learningRate):
    validationAccuracyHistory = []
    for epoch in range(amountOfEpochs):
        firstLayer, firstLayerActivation, secondLayer, secondLayerActivation = performForwardPropagation(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, trainingDataX)
        firstWeightGradient, firstBiasGradient, secondWeightGradient, secondBiasGradient = performBackwardPropagation(firstLayer, firstLayerActivation, secondLayerWeight, secondLayerActivation, trainingDataX, trainingDataY)
        firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias = manageParameters(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, firstWeightGradient, firstBiasGradient, secondWeightGradient, secondBiasGradient, learningRate)
        if (epoch % 10 == 0):
            trainingAccuracy = calculateAccuracy(providePrediction(secondLayerActivation), providePrediction(trainingDataY))
            firstLayer, firstLayerActivation, secondLayer, secondLayerActivation = performForwardPropagation(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, validationDataX)
            validationAccuracy = calculateAccuracy(providePrediction(secondLayerActivation), providePrediction(validationDataY))
            validationAccuracyHistory.append(validationAccuracy)
            firstLayer, firstLayerActivation, secondLayer, secondLayerActivation = performForwardPropagation(firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias, testingDataX)
            testingAccuracy = calculateAccuracy(providePrediction(secondLayerActivation), providePrediction(testingDataY))
            print(f"Epoch: {epoch} ---> Training Accuracy: {round(trainingAccuracy, 2)}, Validation Accuracy: {round(validationAccuracy, 2)}, Testing Accuracy: {round(testingAccuracy, 2)}")

    plt.plot(range(0, amountOfEpochs, 10), validationAccuracyHistory, label="Validation Accuracy")
    plt.xlabel('Class')
    plt.ylabel('Accuracy')
    plt.title('Training Process Validation Accuracy')
    plt.legend()
    plt.show()

    return firstLayerWeight, firstLayerBias, secondLayerWeight, secondLayerBias