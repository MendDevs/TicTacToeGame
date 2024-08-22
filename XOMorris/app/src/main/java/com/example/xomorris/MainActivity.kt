package com.example.xomorris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Variables :
    private var scoreO: Int = 0 // Score joueur 0
    private var scoreX: Int = 0 // Score joueur X
    private var currentPlayer: String = "X"

    // Matrices
    private val matrixOfString: MutableList<String> = mutableListOf("", "", "", "", "", "", "", "", "")
    private val matrixOfButton: MutableList<Int> = mutableListOf(
        R.id.BO, R.id.B1, R.id.B2,
        R.id.B3, R.id.B4, R.id.B5,
        R.id.B6, R.id.B7, R.id.B8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Remplir les TextView des scores avec les valeurs de scoreO et scoreX
        updateScores()

        // Rendre le TextView du tour du joueur currentPlayer visible et l'autre invisible
        updateTurnDisplay()

        // Assigner la fonction de clic aux boutons
        matrixOfButton.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { cliqueButton(it) }
        }
    }

    // Fonction pour gérer le clic sur les boutons
    private fun cliqueButton(b: View) {
        val button = b as Button
        val position = matrixOfButton.indexOf(button.id)

        if (button.text.isEmpty()) {
            matrixOfString[position] = currentPlayer
            button.text = currentPlayer

            // Vérifier s'il y a un gagnant
            when (val winner = winner(matrixOfString)) {
                "X" -> {
                    scoreX++
                    Toast.makeText(this, "Player X wins!", Toast.LENGTH_SHORT).show()
                    updateScores()
                    restartActivity()
                }
                "O" -> {
                    scoreO++
                    Toast.makeText(this, "Player O wins!", Toast.LENGTH_SHORT).show()
                    updateScores()
                    restartActivity()
                }
                else -> {
                    if (!matrixOfString.contains("")) {
                        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
                        restartActivity()
                    } else {
                        togglePlayer()
                        updateTurnDisplay()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Choose another cell", Toast.LENGTH_SHORT).show()
        }
    }

    // Fonction pour changer de joueur
    private fun togglePlayer() {
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }

    // Fonction pour mettre à jour l'affichage du tour
    private fun updateTurnDisplay() {
        val player1TurnTextView = findViewById<TextView>(R.id.player1turn)
        val player2TurnTextView = findViewById<TextView>(R.id.player2turn)

        if (currentPlayer == "X") {
            player1TurnTextView.visibility = View.INVISIBLE
            player2TurnTextView.visibility = View.VISIBLE
        } else {
            player1TurnTextView.visibility = View.VISIBLE
            player2TurnTextView.visibility = View.INVISIBLE
        }
    }

    // Fonction pour redémarrer l'activité
    private fun restartActivity() {
        finish()
        startActivity(intent)
    }

    // Fonction pour déterminer le gagnant
    private fun winner(matrix: MutableList<String>): String {
        // Les lignes gagnantes possibles
        val winningCombinations = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Lignes
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Colonnes
            listOf(0, 4, 8), listOf(2, 4, 6) // Diagonales
        )

        for (combination in winningCombinations) {
            val (a, b, c) = combination
            if (matrix[a].isNotEmpty() && matrix[a] == matrix[b] && matrix[a] == matrix[c]) {
                return matrix[a]
            }
        }

        return ""
    }

    // Fonction pour mettre à jour les scores
    private fun updateScores() {
        findViewById<TextView>(R.id.player1score).text = "O Score: $scoreO"
        findViewById<TextView>(R.id.player2score).text = "X Score: $scoreX"
    }
}
