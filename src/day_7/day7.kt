package day_7

import java.io.File

enum class Operator(val operator: String) {
    AND("AND"), LSHIFT("LSHIFT"), NOT("NOT"), OR("OR"), RSHIFT("RSHIFT")
}


data class Token(val token: String)
data class Instruction (val leftSide: Token?, val operator:Operator?, val rightSide: Token)


class Tokenizer() {
    var signalMap: MutableMap<Token, Instruction> = mutableMapOf()

    fun parse(line: String) {
        val tokens = line.split(" ").map(::Token)

        val assignee = tokens.last()
        val assiningTokens = tokens.subList(0, tokens.indexOf(Token("->")))

        var operator : Operator? = null
        val operatorToken = assiningTokens.getOrNull(assiningTokens.indexOf(assiningTokens.last())-1)?.token
        if (operatorToken != null) {
            operator = Operator.valueOf(operatorToken)
        }

        val instruction = Instruction(
                assiningTokens.getOrNull(assiningTokens.indexOf(assiningTokens.last())-2),
                operator,
                assiningTokens.last())



        signalMap.put(assignee, instruction)
    }
}

class SignalCalculator(val signalMap: Map<Token, Instruction>) {
    var calculatedSignalMap: MutableMap<Token, Int> = mutableMapOf()

    fun determineSignalValues() {
        for ((token, instruction) in signalMap) {
            val calculatedValue = getValueForToken(token)
            calculatedSignalMap.put(token, calculatedValue)
        }
    }

    fun deleteCalculatedValues() {
        calculatedSignalMap = mutableMapOf()
    }


    fun getValueForToken(token: Token): Int {
        val knownTokenValue = calculatedSignalMap[token]
        if (knownTokenValue != null) {
            return knownTokenValue
        } else {
            try {
                val literalValue = token.token.toInt()
                calculatedSignalMap.put(token, literalValue)
                return literalValue
            } catch (e: NumberFormatException) {}

            val instruction = signalMap[token]
            if (instruction != null) {
                val definition = getValueForInstruction(instruction)
                calculatedSignalMap.put(token, definition)
                return definition
            }

            throw UnknownError("$token is an unknown value")
        }
    }

    fun getValueForInstruction(instruction: Instruction): Int {
        if (instruction.operator == null && instruction.leftSide == null) {
            return getValueForToken(instruction.rightSide)
        }
        when(instruction.operator) {
            Operator.AND -> return BitWiseOperation.AND(getValueForToken(instruction.leftSide!!), getValueForToken(instruction.rightSide))
            Operator.LSHIFT -> return BitWiseOperation.LEFT_SHIFT(getValueForToken(instruction.leftSide!!), getValueForToken(instruction.rightSide))
            Operator.NOT -> return BitWiseOperation.NOT(getValueForToken(instruction.rightSide))
            Operator.OR -> return BitWiseOperation.OR(getValueForToken(instruction.leftSide!!), getValueForToken(instruction.rightSide))
            Operator.RSHIFT -> return BitWiseOperation.RIGHT_SHIFT(getValueForToken(instruction.leftSide!!), getValueForToken(instruction.rightSide))
        }
        throw UnknownError("$instruction is an unknown value")
    }

}

fun main(args: Array<String>) {
    val input = File("./src/day_7/input.txt").reader()

    val tokenizer = Tokenizer()

    input.forEachLine {
        tokenizer.parse(it)
    }

    val signalCalculator = SignalCalculator(tokenizer.signalMap)

    signalCalculator.determineSignalValues()

    val aSignal = signalCalculator.calculatedSignalMap[Token("a")]

    println("Signal 'a' has the value: $aSignal")

    val modifiedMap = tokenizer.signalMap

    modifiedMap[Token("b")] = Instruction(null, null, Token(aSignal.toString()))

    val secondSignalCalculator = SignalCalculator(modifiedMap)
    secondSignalCalculator.determineSignalValues()
    println("Signal 'a' has the value: ${secondSignalCalculator.calculatedSignalMap[Token("a")]}")


}