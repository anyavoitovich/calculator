package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding // Импортируйте класс привязки

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Создайте переменную для хранения экземпляра класса привязки
    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Инициализируйте переменную привязки
        setContentView(binding.root)

        // Установите обработчик кликов на кнопку "="
        binding.equalsButton.setOnClickListener {
            equalsAction(it)
        }
    }


    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal && !binding.workingsTV.text.contains(".")) {
                    binding.workingsTV.append(view.text)
                }
                canAddDecimal = false
            } else {
                binding.workingsTV.append(view.text)
            }
            canAddOperation = true
        }
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            binding.workingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        binding.workingsTV.text = ""
        binding.resultsTV.text = ""
    }

    fun backSpaceAction(view: View) {
        val length = binding.workingsTV.length()
        if (length > 0)
            binding.workingsTV.text = binding.workingsTV.text.subSequence(0, length - 1)
    }

    fun equalsAction(view: View) {
        binding.resultsTV.text = calculateResults()
    }
    // Добавляем функционал для обработки отрицательных чисел
    fun toggleSign(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty() && currentText != "0") {
            binding.workingsTV.text = if (currentText[0] == '-') {
                currentText.substring(1)
            } else {
                "-$currentText"
            }
        }
    }
    fun percentageAction(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty()) {
            val result = currentText.toFloat() / 100
            binding.workingsTV.text = result.toString()
        }
    }

    fun squareRootAction(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty()) {
            val number = currentText.toFloat()
            if (number >= 0) {
                val result = Math.sqrt(number.toDouble()).toFloat()
                binding.workingsTV.text = result.toString()
            } else {
                binding.workingsTV.text = "Ошибка: корень отрицательного числа"
            }
        }
    }


    fun sinAction(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty()) {
            val number = currentText.toFloat()
            val result = Math.sin(Math.toRadians(number.toDouble()))
            binding.workingsTV.text = result.toString()
        }
    }

    fun cosAction(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty()) {
            val number = currentText.toFloat()
            val result = Math.cos(Math.toRadians(number.toDouble()))
            binding.workingsTV.text = result.toString()
        }
    }
    fun tanAction(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty()) {
            val number = currentText.toFloat()
            val result = Math.tan(Math.toRadians(number.toDouble()))
            binding.workingsTV.text = result.toString()
        }
    }

    fun cotAction(view: View) {
        val currentText = binding.workingsTV.text.toString()
        if (currentText.isNotEmpty()) {
            val number = currentText.toFloat()
            val result = 1 / Math.tan(Math.toRadians(number.toDouble()))
            binding.workingsTV.text = result.toString()
        }
    }

    // Добавляем проверку на деление на ноль
    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        if (result == Float.POSITIVE_INFINITY || result == Float.NEGATIVE_INFINITY) {
            return "Ошибка: деление на ноль"
        }
        return result.toString()
    }

//    private fun calculateResults(): String {
//        val digitsOperators = digitsOperators()
//        if (digitsOperators.isEmpty()) return ""
//
//        val timesDivision = timesDivisionCalculate(digitsOperators)
//        if (timesDivision.isEmpty()) return ""
//
//        val result = addSubtractCalculate(timesDivision)
//        return result.toString()
//    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('×') || list.contains('/')) {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator) {
                    '×' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }

                    '/' -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }

                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if (i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        var prevChar: Char? = null // Хранит предыдущий символ

        for (character in binding.workingsTV.text) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else if (character == '-') {
                // Если предыдущий символ - оператор, добавляем минус к текущему числу
                if (prevChar == null || prevChar in listOf('+', '-', '×', '/')) {
                    currentDigit += character
                } else {
                    // Если предыдущий символ не оператор, добавляем текущее число к списку
                    if (currentDigit.isNotEmpty()) {
                        list.add(currentDigit.toFloat())
                        currentDigit = ""
                    }
                    list.add(character)
                }
            } else {
                // Если текущий символ не является цифрой, добавляем текущее число к списку
                if (currentDigit.isNotEmpty()) {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                }
                list.add(character)
            }
            prevChar = character // Обновляем предыдущий символ
        }

        // Добавляем последнее число, если оно было
        if (currentDigit.isNotEmpty())
            list.add(currentDigit.toFloat())

        return list
    }

}