package day_2

import java.io.File

data class Present(val length: Int, val width: Int, val height: Int)

class PresentCalculator {
    fun firstSide(present: Present): Int {
        return present.length*present.width
    }

    fun secondSide(present: Present): Int {
        return present.width*present.height
    }

    fun thirdSide(present: Present): Int {
        return present.height*present.length
    }

    fun smallestSide(present: Present): Int {
        return arrayOf(
                firstSide(present),
                secondSide(present),
                thirdSide(present)
        ).sortedArray().first()
    }

    fun calculateSurfaceArea(present: Present): Int {
        return  2*firstSide(present) +
                2*secondSide(present) +
                2*thirdSide(present) +
                smallestSide(present)
    }

    fun ribbonSize(present: Present): Int {
        return present.height*present.length*present.width
    }

    fun calculateRibbon(present: Present): Int {
        val sides = arrayOf(
                present.width,
                present.length,
                present.height
        ).sortedArray()
        val smallestSide = sides[0]
        val middleSide = sides[1]


        return 2*smallestSide + 2*middleSide + ribbonSize(present)
    }
}

fun main(array: Array<String>) {
    val presentCalculator = PresentCalculator()
    var sum_square_ft_paper = 0
    File("./src/day_2/input.txt").reader().forEachLine {
        val input = it.split("x")
        val present = Present(input[0].toInt(), input[1].toInt(), input[2].toInt())
        sum_square_ft_paper += presentCalculator.calculateSurfaceArea(present)
    }
    println("The elves need $sum_square_ft_paper ft^2 of paper")

    var sum_ft_ribbon = 0
    File("./src/day_2/input.txt").reader().forEachLine {
        val input = it.split("x")
        val present = Present(input[0].toInt(), input[1].toInt(), input[2].toInt())
        sum_ft_ribbon += presentCalculator.calculateRibbon(present)
    }
    println("The elves need $sum_ft_ribbon ft of ribbon")

}