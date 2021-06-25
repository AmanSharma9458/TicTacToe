package com.example.android.tictactoe

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()
    lateinit var board:LinearLayout

    val possibleWins= arrayOf(
        //horizontal lines
    listOf(1,2,3),
    listOf(4,5,6),
    listOf(7,8,9),

        //vertical lines
    listOf(1,4,7),
    listOf(2,5,8),
    listOf(3,6,9),

        //diagonal lines
    listOf(1,5,9),
    listOf(3,5,7)
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board=findViewById(R.id.board)
        setupBoard()
    }

    private fun setupBoard() {
        var counter=1

        val params1=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0)

        val params2=LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT)

        for(i in 1..3)
        {
            val linearLayout=LinearLayout(this)
            linearLayout.orientation=LinearLayout.HORIZONTAL
            linearLayout.layoutParams=params1
            params1.weight=1.0F

            for (j in 1..3) {
                val button = Button(this)
                button.id = counter
                button.textSize = 42.0F
                button.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.design_default_color_primary
                    )
                )

                button.layoutParams = params2
                params2.weight = 1.0F

                button.setOnClickListener {
                    recordMove(it)
                }

                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)

            }
    }

    private fun recordMove(view: View){
        val button=view as Button
        val id=button.id

        if(playerOneTurn) {
            playerOneMoves.add(id)
            button.text="0"
            button.isEnabled=false

            if(checkWin(playerOneMoves)) {
                showWinMessage(player_one)
            }
            else {
                playerOneTurn=false
                togglePlayerTurn(player_two_label ,player_one_label)
            }
        }
        //<editor-fold desc="Description">
        else {
            playerTwoMoves.add(id)
            button.text="X"
            button.isEnabled=false
            if(checkWin(playerTwoMoves)) {
                showWinMessage(player_two)
            }
            else {
                playerOneTurn=true
                togglePlayerTurn(player_one_label,player_two_label)
            }
        }
        //</editor-fold>
    }

    private fun checkWin(moves:List<Int>):Boolean{
        var won=false
        if(moves.size>=3)
        {
            run loop@{this@MainActivity
            possibleWins.forEach {
                if(moves.containsAll(it))
                {
                    won=true
                    return@loop
                }
            }
            }
        }
        return won
    }

    private fun togglePlayerTurn(playerOn:TextView,playerOff:TextView)
    {
        playerOff.setTextColor(
            ContextCompat.getColor(this,R.color.design_default_color_error))
        playerOff.setTypeface(null, Typeface.NORMAL)
        playerOn.setTextColor(
            ContextCompat.getColor(this,R.color.design_default_color_secondary)
        )
        playerOn.setTypeface(null,Typeface.BOLD)

    }

    private fun showWinMessage(player:EditText)
    {
        var playerName=player.text.toString()
        if(playerName.isBlank())
        {
            playerName=player.hint.toString()
        }
        Toast.makeText(this,"Congratulations $playerName you won",Toast.LENGTH_SHORT).show()
    }
}