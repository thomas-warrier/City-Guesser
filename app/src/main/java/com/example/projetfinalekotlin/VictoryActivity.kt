package com.example.projetfinalekotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinalekotlin.databinding.ActivityVictoryBinding

class VictoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVictoryBinding

    companion object {
        const val WIN_EXTRA = "isWin"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVictoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val isWin = intent.getBooleanExtra(WIN_EXTRA, true)

        if (isWin) {
            binding.titre.text =
                "Bravo à vous patron, vous avez trouvé la capitale en moins de temps qu'il ne faut pour le dire !!"
        } else {
            binding.titre.text = "Désolé mais vous avez perdu !"
            binding.trophyImage.visibility = View.INVISIBLE
        }


        binding.menuButton.setOnClickListener {
            finish()
        }
    }
    override fun onBackPressed() {}
}