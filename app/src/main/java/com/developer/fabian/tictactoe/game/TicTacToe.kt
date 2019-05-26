package com.developer.fabian.tictactoe.game

import java.util.Random

class TicTacToe {

    companion object {
        const val BOARD_SIZE = 9

        const val HUMAN_PLAYER = 'X'
        const val COMPUTER_PLAYER = 'O'

        private const val OPEN_SPOT = ' '
    }

    private val mBoard = CharArray(BOARD_SIZE)
    private val mRand: Random = Random()

    val computerMove: Int
        get() {
            var move: Int

            for (i in 0 until BOARD_SIZE) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    val curr = mBoard[i]
                    setMove(COMPUTER_PLAYER, i)

                    if (checkForWinner() == 3)
                        return i
                    else
                        mBoard[i] = curr
                }
            }

            for (i in 0 until BOARD_SIZE) {
                if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                    val curr = mBoard[i]
                    setMove(HUMAN_PLAYER, i)

                    if (checkForWinner() == 2) {
                        mBoard[i] = COMPUTER_PLAYER
                        return i
                    } else {
                        mBoard[i] = curr
                    }
                }
            }

            do {
                move = mRand.nextInt(BOARD_SIZE)
            } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER)

            setMove(COMPUTER_PLAYER, move)

            return move
        }

    fun clearBoard() {
        for (i in 0 until BOARD_SIZE)
            mBoard[i] = OPEN_SPOT
    }

    fun setMove(player: Char, location: Int) {
        mBoard[location] = player
    }

    fun checkForWinner(): Int {
        run {
            var i = 0
            while (i <= 6) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER)
                    return 2

                if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER)
                    return 3

                i += 3
            }
        }

        for (i in 0..2) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER)
                return 2

            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER)
                return 3
        }

        if (mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER ||
                mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER) {
            return 2
        }

        if (mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER || mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER) {
            return 3
        }

        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0
        }

        return 1
    }
}
