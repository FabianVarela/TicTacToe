package com.developer.fabian.tictactoe;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.developer.fabian.tictactoe.game.TicTacToe;

public class GameActivity extends AppCompatActivity {

    private int humanResult;
    private int androidResult;
    private int tieResult;

    private TicTacToe mGame;

    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);

        mBoardButtons = new Button[TicTacToe.BOARD_SIZE];
        mBoardButtons[0] = findViewById(R.id.one);
        mBoardButtons[1] = findViewById(R.id.two);
        mBoardButtons[2] = findViewById(R.id.three);
        mBoardButtons[3] = findViewById(R.id.four);
        mBoardButtons[4] = findViewById(R.id.five);
        mBoardButtons[5] = findViewById(R.id.six);
        mBoardButtons[6] = findViewById(R.id.seven);
        mBoardButtons[7] = findViewById(R.id.eight);
        mBoardButtons[8] = findViewById(R.id.nine);

        mResultTextView = findViewById(R.id.results);
        mInfoTextView = findViewById(R.id.dialogue);

        mGame = new TicTacToe();

        startNewGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(R.string.menu_new_game);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame();
        return true;
    }

    private void startNewGame() {
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        mInfoTextView.setText(R.string.first_human);
        mResultTextView.setText(String.format(getString(R.string.result_text), humanResult, tieResult, androidResult));
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));

        if (player == TicTacToe.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }

    private void disableButtons() {
        for (Button mBoardButton : mBoardButtons)
            mBoardButton.setEnabled(false);
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()) {
                setMove(TicTacToe.HUMAN_PLAYER, location);

                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);

                    int move = mGame.getComputerMove();
                    setMove(TicTacToe.COMPUTER_PLAYER, move);

                    winner = mGame.checkForWinner();
                }

                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_human);
                } else if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    tieResult++;
                    disableButtons();
                } else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    humanResult++;
                    disableButtons();
                } else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    androidResult++;
                    disableButtons();
                }

                mResultTextView.setText(String.format(getString(R.string.result_text), humanResult, tieResult, androidResult));
            }
        }
    }
}
