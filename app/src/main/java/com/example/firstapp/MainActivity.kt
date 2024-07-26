package com.example.firstapp

import android.os.Bundle

import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.firstapp.data.LottoData
import com.example.firstapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var number: Int = 1102
    private lateinit var binding: ActivityMainBinding
    private val lottoController by lazy { LottoController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchLottoData(number)
        with(binding) {
            searchButton.setOnClickListener {
                number = searchView.text.toString().toInt()
                fetchLottoData(number)
            }

            arrowLeft.setOnClickListener {
                number = number.dec()
                fetchLottoData(number)
            }

            arrowRight.setOnClickListener {
                number = number.inc()
                fetchLottoData(number)
            }
        }

        val randomBtn = binding.Generate

        randomBtn.setOnClickListener {
            val winNumbers = generateLottoNumbers().sorted()
            binding.numberOne.text = winNumbers[0].toString()
            binding.numberTwo.text = winNumbers[1].toString()
            binding.numberThree.text = winNumbers[2].toString()
            binding.numberFour.text = winNumbers[3].toString()
            binding.numberFive.text = winNumbers[4].toString()
            binding.numberSix.text = winNumbers[5].toString()
            binding.numberBonus.text = winNumbers[6].toString()
        }
    }





    private fun fetchLottoData(number: Int) {
        lottoController.getLottoNumber(number, { lottoData ->
            updateUI(lottoData)
        }, { _ ->
            Toast.makeText(
                this@MainActivity,
                "Failed to retrieve lottery information. Please try again later.",
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun updateUI(data: LottoData) {
        with(binding) {
            winResultTextView.text = getHtmlText(R.string.winning_result, number.toString())
            dateView.text = data.data
            val listWinNumberView =
                listOf(number1Text, number2Text, number3Text, number4Text, number5Text, number6Text)
            listWinNumberView.mapIndexed { index, tv ->
                val numbers = data.winNumbers
                tv.text = if (numbers.isNotEmpty()) numbers[index] else ""
            }
            numberBonusText.text = data.bonusNumber

            val prize = data.totalWinPrize.toBillion()
            winAmountText.text = getHtmlText(R.string.win_prize, prize)
            val prizeOne = data.winPrize.toBillion()
            win1AmountText.text = getHtmlText(R.string.win_prize_1, prizeOne)

        }
    }

    private fun String.toBillion(): String {
        return if (this != "") (this.toLong() / 100_000_000).toString() else this
    }

    private fun getHtmlText(@StringRes id: Int, data: String): Spanned {
        val text: String = getString(id, data)
        return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }


    private fun generateLottoNumbers(): Set<Int> {
        val numbers = mutableSetOf<Int>()
        while (numbers.size < 7) {
            val randomNumber = Random.nextInt(1, 46) // Random number between 1 and 45
            numbers.add(randomNumber)
        }
        return numbers
    }
}



