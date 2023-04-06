package com.swaptech.calculator.presentation.util

object MathExpressionsParser {

    fun calculate(expression: String): Result<Double> {
        return try {
            validateParentheses(expression)
            val polishNotation = applyShuntingYard(expression)
            val result = parseReversePolishNotation(polishNotation)
            Result.success(result)
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }

    private fun validateParentheses(expression: String) {
        val parenthesesStack = mutableListOf<Int>()
        for (i in expression.indices) {
            when (expression[i]) {
                '(' -> {
                    parenthesesStack.add(i)
                }
                ')' -> {
                    if (parenthesesStack.isEmpty()) throw Exception("Invalid expression")
                    if (i - parenthesesStack.removeLast() == 1) throw Exception("Invalid expression")
                }
                else -> {
                }
            }
        }
    }

    private fun calculate(operation: Char, vararg operands: Double): Double {
        val num1 = operands[0]

        val binaryOperations = mapOf(
            '+' to { op1: Double, op2: Double -> op1 + op2 },
            '-' to { op1: Double, op2: Double -> op1 - op2 },
            '*' to { op1: Double, op2: Double -> op1 * op2 },
            '/' to { op1: Double, op2: Double -> op1 / op2 },
            '%' to { op1: Double, op2: Double -> op1 % op2 },
        )

        val unaryOperations = mapOf(
            'n' to { op1: Double -> -1.0 * op1 }
        )

        return if (unaryOperations[operation] != null) {
            unaryOperations[operation]?.invoke(num1) ?: throw Exception("Unknown operation")
        } else {
            val num2 = operands[1]
            binaryOperations[operation]?.invoke(num1, num2) ?: throw Exception("Unknown operation")
        }
    }

    private fun parseReversePolishNotation(expression: String): Double {
        val nums = mutableListOf<Double>()
        var i = 0

        while (i < expression.length) {
            val char = expression[i]
            if (char.isWhitespace()) {
                ++i
                continue
            }
            if (char.isDigit()) {
                val num = StringBuilder()
                while (i < expression.length) {
                    val item = expression[i]
                    if (!item.isDigit() && item != '.') {
                        --i
                        break
                    }
                    num.append(item)
                    ++i
                }
                nums.add(num.toString().toDouble())
            } else {
                val right = nums.removeLast()
                val result = if (char == 'n') {
                    calculate(char, right)
                } else {
                    val left = nums.removeLast()
                    calculate(char, left, right)
                }
                nums.add(result)
            }
            ++i
        }
        return nums.first()
    }

    private fun applyShuntingYard(expression: String): String {
        val queue = ArrayDeque<String>()
        val stack = mutableListOf<Char>()
        //n - negative of number for example n3 is -3
        val priorities = mapOf(
            'n' to 3,
            '-' to 1, '+' to 1,
            '*' to 2, '/' to 2, '%' to 2
        )
        var i = 0
        val mutableExpression = StringBuilder(expression)
        for (k in mutableExpression.indices) {
            val char = mutableExpression[k]
            if (char == '-') {
                if (k - 1 < 0) {
                    mutableExpression[k] = 'n'
                } else {
                    if (!expression[k - 1].isDigit()) {
                        mutableExpression[k] = 'n'
                    }
                }
            }
        }
        while (i < mutableExpression.length) {
            val char = mutableExpression[i]
            if (char.isDigit()) {
                val num = StringBuilder()
                while (i < mutableExpression.length) {
                    val item = mutableExpression[i]
                    if (!item.isDigit() && item != '.') {
                        --i
                        break
                    }
                    num.append(item)
                    ++i
                }
                queue.addLast(" $num")
            } else {
                if (priorities[char] == null) {
                    if (char == '(') {
                        stack.add(char)
                    } else {
                        while (stack.isNotEmpty()) {
                            val top = stack.last()
                            if (top == '(') {
                                stack.removeLast()
                                break
                            }
                            queue.add(stack.removeLast().toString())
                        }
                    }
                } else {
                    while (stack.isNotEmpty()) {
                        val top = stack.last()
                        if (priorities[top] == null) {
                            stack.add(char)
                            break
                        }
                        when {
                            priorities[char]!! > priorities[top]!! -> {
                                stack.add(char)
                                break
                            }
                            priorities[char]!! == priorities[top]!! -> {

                                queue.add(stack.removeLast().toString())
                                stack.add(char)
                                break
                            }
                            else -> {
                                queue.add(stack.removeLast().toString())
                            }
                        }
                    }
                }
                if (stack.isEmpty()) {
                    stack.add(char)
                }
            }
            ++i
        }
        while (stack.isNotEmpty()) {
            val item = stack.removeLast()
            if (priorities[item] == null) continue
            queue.add(item.toString())
        }
        return queue.joinToString("")
    }
}

fun String.calculate(): Result<Double> = MathExpressionsParser.calculate(this)
