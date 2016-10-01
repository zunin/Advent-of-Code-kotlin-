package day_5

import java.io.File
import java.util.*

abstract class Validator() {
    abstract fun isNice(input: String): Boolean
}

class ThreeVowelValidator: Validator() {
    val legalVowels = "aeiou"
    override fun isNice(input: String): Boolean {
        return input.count { legalVowels.contains(it, true) } >= 3
    }
}

class DoubleLetterValidator: Validator() {
    override fun isNice(input: String): Boolean {
        var lastCharacter: Char? = null
        for (character in input) {
            if (lastCharacter == character) {
                return true
            }
            lastCharacter = character
        }
        return false
    }
}

class IllegalSubStringValidator: Validator() {
    val illegalSubStrings = arrayOf("ab", "cd", "pq", "xy")

    override fun isNice(input: String): Boolean {
        for (illegalSubString in illegalSubStrings) {
            if (input.contains(illegalSubString, true)) {
                return false
            }
        }

        return true
    }
}

class DoublePairValidator: Validator() {
    override fun isNice(input: String): Boolean {
        val pairs = HashMap<Int, String>()

        for (entry in input.withIndex()) {
            if (entry.index + 1 >= input.length) {
                break
            }
            val pair = "${entry.value}${input[entry.index+1]}"
            pairs[entry.index] = pair
        }

        val sortedPairs = pairs.entries.sortedByDescending { it.value }

        var lastPair = Pair(-1, "")
        for (entry in sortedPairs) {
            if (lastPair.second == entry.value) {
                if (lastPair.first != entry.key + 1 && lastPair.first != entry.key - 1) {
                    return true
                }
            }
            lastPair = Pair(entry.key, entry.value)
        }

        return false
    }
}

class DoubleSpacedLetterValidator: Validator() {
    override fun isNice(input: String): Boolean {
        var twoLettersAgo = ""
        var oneLetterAgo = ""

        for (character in input) {
            if (twoLettersAgo == character.toString()) {
                return true
            }
            twoLettersAgo = oneLetterAgo
            oneLetterAgo = character.toString()
        }

        return false
    }
}

class NaughtyOrNiceStringClassifier(val validators: Array<Validator>) {
    fun isNice(input: String): Boolean {
        for (validator in validators) {
            if (!validator.isNice(input)) {
                return false
            }
        }
        return true
    }
}


fun main(args: Array<String>) {
    val classifier = NaughtyOrNiceStringClassifier(arrayOf(
            ThreeVowelValidator(),
            DoubleLetterValidator(),
            IllegalSubStringValidator()
    ))

    val newClassifier = NaughtyOrNiceStringClassifier(arrayOf(
            DoublePairValidator(),
            DoubleSpacedLetterValidator()
    ))


    var niceStrings = 0
    var newNiceStrings = 0
    File("./src/day_5/input.txt").reader().forEachLine {
        if (classifier.isNice(it)) {
            niceStrings += 1
        }

        if (newClassifier.isNice(it)) {
            newNiceStrings += 1
        }
    }


    println("There are $niceStrings nice strings")
    println("In the newer version there are $newNiceStrings nice strings")

}