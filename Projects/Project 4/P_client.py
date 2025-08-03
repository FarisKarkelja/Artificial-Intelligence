import socket
import time
from multiprocessing.pool import ThreadPool
from datetime import date
from mancalaHelper import *


DEPTH_LIMIT = 3
MAXIMIZING_PLAYER = float('-inf')
MINIMIZING_PLAYER = float('inf')


#Define the best possible move for the current player who is using its current strategy
def decide_move(boardIn, playerTurnIn):
    playerMoves = defineMoves(boardIn, playerTurnIn)

    bestPlayerMove = None
    if playerTurnIn == 1:
        bestPlayerValue = MAXIMIZING_PLAYER
    else:
        bestPlayerValue = MINIMIZING_PLAYER

    for playerMove in playerMoves:
        mancalaBoardCopy = constructMancalaBoardCopy(boardIn)
        freshNewBoard, nextPlayerTurn = play(playerTurnIn, playerMove, mancalaBoardCopy)
        playerMoveScore = performMinimaxAlgorithm(freshNewBoard, nextPlayerTurn, DEPTH_LIMIT, MAXIMIZING_PLAYER, MINIMIZING_PLAYER, playerTurnIn)

        if (playerTurnIn == 1 and playerMoveScore > bestPlayerValue) or (playerTurnIn == 2 and playerMoveScore < bestPlayerValue):
            bestPlayerValue = playerMoveScore
            bestPlayerMove = playerMove
    return str(bestPlayerMove), "botName"


#Apply minimax algorithm by determining the best value for the current player while managing redundant search
def performMinimaxAlgorithm(mancalaBoard, playerTurn, depthLimit, alpha, beta, maximizingPlayer):
    if depthLimit == 0 or gameOver(mancalaBoard):
        return applyUtilityFunction(mancalaBoard, playerTurn)

    playerMoves = defineMoves(mancalaBoard, playerTurn)
    if not playerMoves:
        return applyUtilityFunction(mancalaBoard, playerTurn)

    if playerTurn == maximizingPlayer:
        maximumValue = MAXIMIZING_PLAYER
        for playerMove in playerMoves:
            mancalaBoardCopy = constructMancalaBoardCopy(mancalaBoard)
            freshNewBoard, nextPlayerTurn = play(playerTurn, playerMove, mancalaBoardCopy)
            playerMoveScore = performMinimaxAlgorithm(freshNewBoard, nextPlayerTurn, depthLimit - 1, alpha, beta, maximizingPlayer)
            maximumValue = max(maximumValue, playerMoveScore)
            alpha = max(alpha, playerMoveScore)
            if beta <= alpha:
                break
        return maximumValue
    else:
        minimumValue = MINIMIZING_PLAYER
        for playerMove in playerMoves:
            mancalaBoardCopy = constructMancalaBoardCopy(mancalaBoard)
            freshNewBoard, nextPlayerTurn = play(playerTurn, playerMove, mancalaBoardCopy)
            playerMoveScore = performMinimaxAlgorithm(freshNewBoard, nextPlayerTurn, depthLimit - 1, alpha, beta, maximizingPlayer)
            minimumValue = min(minimumValue, playerMoveScore)
            beta = min(beta, playerMoveScore)
            if beta <= alpha:
                break
        return minimumValue


def play(playerTurn: int, playerMove: int, boardGame):
    if not correctPlay(playerMove, boardGame, playerTurn):
        print("Illegal move! break")
        return

    idx = playerMove - 1 + (playerTurn - 1) * 7
    numStones: int = boardGame[idx]
    boardGame[idx] = 0
    hand: int = numStones
    while hand > 0:
        idx = (idx + 1) % 14
        if idx == 13 - 7 * (playerTurn - 1):
            continue
        boardGame[idx] += 1
        hand -= 1

    nextTurn = 3 - playerTurn
    if idx == 6 + 7 * (playerTurn - 1):
        nextTurn = playerTurn

    if boardGame[idx] == 1 and idx in range((playerTurn - 1) * 7, 6 + (playerTurn - 1) * 7):
        boardGame[idx] -= 1
        boardGame[6 + (playerTurn - 1) * 7] += 1
        boardGame[6 + (playerTurn - 1) * 7] += boardGame[12 - idx]
        boardGame[12 - idx] = 0
    return boardGame, nextTurn


def correctPlay(playerMove: int, board, playerTurn):
    correct = 0
    if playerMove in range(1, 7) and board[playerMove - 1 + (playerTurn - 1) * 7] > 0:
        correct = 1
    return correct


def countScorePlayer1(boardGame):
    (p1s, p2s) = countPoints(boardGame)
    return int(p1s - p2s)


def countPoints(boardGame):
    return boardGame[6], boardGame[13]


def receive(socket):
    msg = ''.encode()
    try:
        data = socket.recv(1024)
        msg += data
    except:
        pass
    return msg.decode()


def send(socket, msg):
    socket.sendall(msg.encode())


startTime = date(2020, 11, 9)
playerName = 'Faris_Karkelja'
host = '127.0.0.1'
port = 30000
s = socket.socket()
pool = ThreadPool(processes=1)
gameEnd = False
MAX_RESPONSE_TIME = 20
print('The player: ' + playerName + ' starts!')
s.connect((host, port))
print('The player: ' + playerName + ' connected!')

while not gameEnd:
    asyncResult = pool.apply_async(receive, (s,))
    startTime = time.time()
    currentTime = 0
    received = 0
    data = []
    while received == 0 and currentTime < MAX_RESPONSE_TIME:
        time.sleep(0.01)
        if asyncResult.ready():
            data = asyncResult.get()
            received = 1
        currentTime = time.time() - startTime
    if received == 0:
        print('No response in ' + str(MAX_RESPONSE_TIME) + ' sec')
        gameEnd = 1
    if data == 'N':
        send(s, playerName)
    if data == 'E':
        gameEnd = 1
    if len(data) > 1:
        board = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        playerTurn = int(data[0])
        i = 0
        j = 1
        while i <= 13:
            board[i] = int(data[j]) * 10 + int(data[j + 1])
            i += 1
            j += 2
        (move, botname) = decide_move(board, playerTurn)
        send(s, move)