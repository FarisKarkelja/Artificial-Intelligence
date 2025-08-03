import csv
import numpy as np


#Read image data with corresponding labels from a csv file
def readData(filePath):
    with open(filePath, 'r') as file:
        fileReader = csv.reader(file)
        next(fileReader)

        collectionOfLabels = []
        collectionOfImages = []

        #Seperate labels from image pixels and manage them through arrays
        for row in fileReader:
            label = int(row[0])
            collectionOfLabels.append(label)
            collectionOfPixels = []

            for pixel in row[1:]:
                pixelValue = int(pixel)
                collectionOfPixels.append(pixelValue)
            collectionOfImages.append(collectionOfPixels)

    #Normalize image pixels to [0, 1] range
    collectionOfImages = np.array(collectionOfImages, dtype="float32") / 255.0

    #Manage digit classification and work with an array of zeros for successful encoding format
    amountOfClasses = 10
    collectionOfOneHotLabels = np.zeros((len(collectionOfLabels), amountOfClasses))

    for i in range(len(collectionOfLabels)):
        collectionOfOneHotLabels[i, collectionOfLabels[i]] = 1

    return collectionOfImages, collectionOfOneHotLabels