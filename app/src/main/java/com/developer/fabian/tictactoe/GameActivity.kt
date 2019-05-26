package com.developer.fabian.tictactoe

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.developer.fabian.tictactoe.game.TicTacToe

class GameActivity : AppCompatActivity() {

    private var humanResult: Int = 0
    private var androidResult: Int = 0
    private var tieResult: Int = 0

    private var mGame: TicTacToe? = null

    private lateinit var mBoardButtons: Array<Button>
    private lateinit var mInfoTextView: TextView
    private lateinit var mResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_game)

        mBoardButtons = arrayOf(
                findViewById(R.id.one),
                findViewById(R.id.two),
                findViewById(R.id.three),
                findViewById(R.id.four),
                findViewById(R.id.five),
                findViewById(R.id.six),
                findViewById(R.id.seven),
                findViewById(R.id.eight),
                findViewById(R.id.nine)
        )

        mResultTextView = findViewById(R.id.results)
        mInfoTextView = findViewById(R.id.dialogue)

        mGame = TicTacToe()

        startNewGame()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(R.string.menu_new_game)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startNewGame()
        return true
    }

    private fun startNewGame() {
        mGame!!.clearBoard()

        for (i in mBoardButtons.indices) {
            mBoardButtons[i].text = ""
            mBoardButtons[i].isEnabled = true
            mBoardButtons[i].setOnClickListener(ButtonClickListener(i))
        }

        mInfoTextView.setText(R.string.first_human)
        mResultTextView.text = String.format(getString(R.string.result_text), humanResult, tieResult, androidResult)
    }

    private fun setMove(player: Char, location: Int) {
        mGame!!.setMove(player, location)
        mBoardButtons[location].isEnabled = false
        mBoardButtons[location].text = player.toString()

        if (player == TicTacToe.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0))
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0))
    }

    private fun disableButtons() {
        for (mBoardButton in mBoardButtons)
            mBoardButton.isEnabled = false
    }

    private inner class ButtonClickListener internal constructor(internal var location: Int) : View.OnClickListener {

        override fun onClick(view: View) {
            if (mBoardButtons[location].isEnabled) {
                setMove(TicTacToe.HUMAN_PLAYER, location)

                var winner = mGame!!.checkForWinner()
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer)

                    val move = mGame!!.computerMove
                    setMove(TicTacToe.COMPUTER_PLAYER, move)

                    winner = mGame!!.checkForWinner()
                }

                when (winner) {
                    0 -> mInfoTextView.setText(R.string.turn_human)
                    1 -> {
                        mInfoTextView.setText(R.string.result_tie)
                        tieResult++
                        disableButtons()
                    }
                    2 -> {
                        mInfoTextView.setText(R.string.result_human_wins)
                        humanResult++
                        disableButtons()
                    }
                    else -> {
                        mInfoTextView.setText(R.string.result_computer_wins)
                        androidResult++
                        disableButtons()
                    }
                }

                mResultTextView.text = String.format(getString(R.string.result_text), humanResult, tieResult, androidResult)
            }
        }
    }
}
