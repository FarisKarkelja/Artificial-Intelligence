#Terminate game if one of the players has no more stones in their pits
def gameOver(mancalaBoard):
    if sum(mancalaBoard[0:6]) == 0 or sum(mancalaBoard[7:13]) == 0:
        return True
    return False


#Decide which player has which possible moves based on stone per pit quantity
def defineMoves(mancalaBoard, playerTurn):
    playerMoves = []
    if playerTurn == 1:
        for index in range(0, 6):
            if mancalaBoard[index] > 0:
                playerMoves.append(index + 1)
    else:
        for index in range(7, 13):
            if mancalaBoard[index] > 0:
                playerMoves.append(index - 6)
    return playerMoves


#Make a copy of the mancala board to avoid changing the initial one
def constructMancalaBoardCopy(mancalaBoard):
    return mancalaBoard[:]


#Evaluate board state by taking store difference into consideration
def applyUtilityFunction(mancalaBoard, playerTurn):
    #Extract the number of stones in player stores
    firstPlayerStore, secondPlayerStore = mancalaBoard[6], mancalaBoard[13]
    #Compute the value of the utility function based on the current player's turn
    if playerTurn == 1:
        #Return the difference in stone stores from the first player's perspective
        return firstPlayerStore - secondPlayerStore
    else:
        #Return the difference in stone stores from the second player's perspective
        return secondPlayerStore - firstPlayerStore