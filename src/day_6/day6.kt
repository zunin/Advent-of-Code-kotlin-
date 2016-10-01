package day_6

import java.io.File
import java.util.*

data class Coordinate(val x: Int, val y: Int)

enum class Command(val command: String) {
    ON("turn on"), OFF("turn off"), TOGGLE("toggle")
}

class LineParser {
    data class Instruction(val command: Command, val fromCoordinate: Coordinate, val toCoordinate: Coordinate)

    fun parse (input: String): Instruction {
        val command = Command.values().filter { input.startsWith(it.command) }.first()
        val coordinates = input.removePrefix(command.command+" ").split(" through ").map {
            it.split(",") .map(String::toInt)
        }.map {
            Coordinate(it.first(), it.last())
        }
        return Instruction(command, coordinates.first(), coordinates.last())
    }
}



open class Column<T>(val size: Int, val init: () -> T): Iterable<T> {
    private val column: HashMap<Int, T>

    init {
        column = HashMap<Int, T>()
    }

    operator fun get(i: Int): T {
        return column.getOrPut(i, init)
    }

    operator fun set(i: Int, value: T) {
        if (i < size) {
            column[i] = value
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    override fun iterator(): Iterator<T> {
        return column.values.iterator()
    }
}

class Row<T>(size: Int, val columnSize: Int, init: () -> T): Column<Column<T>>(size, { Column(columnSize, init) })

open class Grid<T>(xSize: Int, ySize: Int, init: () -> T): Iterable<Column<T>> {
    private val rows = Row(xSize, ySize, init)

    operator fun get(i: Int): Column<T> {
        return rows[i]
    }

    override fun iterator(): Iterator<Column<T>> {
        return rows.iterator()
    }
}

class SquareGrid<T>(size: Int, init: () -> T): Grid<T>(size, size, init)
class BooleanSquareGrid(size: Int): Grid<Boolean>(size, size, init = { false })


fun main(args: Array<String>) {
    val parser = LineParser()
    val instructions = File("./src/day_6/input.txt").reader().readLines().map {
        parser.parse(it)
    }

    SquareGrid(1000, init = {false}).let { grid ->
        instructions.forEach {
            for (x in it.fromCoordinate.x..it.toCoordinate.x) {
                for (y in it.fromCoordinate.y..it.toCoordinate.y) {
                    when (it.command) {
                        Command.ON -> {
                            grid[x][y] = true
                        }
                        Command.OFF -> {
                            grid[x][y] = false
                        }
                        Command.TOGGLE -> {
                            grid[x][y] = !grid[x][y]
                        }
                    }
                }
            }
        }

        var litLights = 0
        for (row in grid) {
            for (light in row) {
                if (light) {
                    litLights += 1
                }
            }
        }
        println("There are $litLights lit lights")
    }



    SquareGrid(1000, init = {0}).let { grid ->
        instructions.forEach {
            for (x in it.fromCoordinate.x..it.toCoordinate.x) {
                for (y in it.fromCoordinate.y..it.toCoordinate.y) {
                    when (it.command) {
                        Command.ON -> {
                            grid[x][y] += 1
                        }
                        Command.OFF -> {
                            if (grid[x][y] > 0) {
                                grid[x][y] -= 1
                            }
                        }
                        Command.TOGGLE -> {
                            grid[x][y] += 2
                        }
                    }
                }
            }
        }

        var brightnessSum = 0
        for (row in grid) {
            for (brightness in row) {
                brightnessSum += brightness
            }
        }
        println("Sum of brightness is $brightnessSum")
    }


}