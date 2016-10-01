package day_1

import java.io.File

class StoryCounter () {
    var instructionCount = 0
    var story = 0
        set(value) {
            field = value
            instructionCount += 1
        }


}

fun main(args: Array<String>) {
    val input = File("./src/day_1/input.txt")

    val upInstruction = "("

    val firstStoryCounter = StoryCounter()
    val secondStoryCounter = StoryCounter()

    input.reader().use {
        for (line in it.readLines()) {

            for (letter in line) {
                if (letter.toString() == upInstruction) {
                    firstStoryCounter.story += 1
                } else {
                    firstStoryCounter.story -= 1
                }
            }
        }
    }

    input.reader().use {
        for (line in it.readLines()) {

            for (letter in line) {
                if (letter.toString() == upInstruction) {
                    secondStoryCounter.story += 1
                } else {
                    secondStoryCounter.story -= 1
                }

                if (secondStoryCounter.story == -1) {
                    break
                }
            }
        }
    }

    if (firstStoryCounter.story != 280) {
        throw AssertionError()
    }

    if (secondStoryCounter.instructionCount != 1797) {
        throw AssertionError()
    }



}