package day_3

import java.io.File
import java.util.*

enum class Direction(val direction: String) {
    NORTH("^"), SOUTH("v"), WEST("<"), EAST(">")
}

class Coordinate (val x: Int, val y: Int) {
    operator fun plus(direction: Direction): Coordinate {
        when (direction) {
            Direction.NORTH -> {
                return Coordinate(x, y - 1)
            }
            Direction.SOUTH -> {
                return Coordinate(x, y + 1)
            }
            Direction.EAST -> {
                return Coordinate(x + 1 , y)
            }
            Direction.WEST -> {
                return Coordinate(x - 1, y)
            }
        }
    }
}

class Santa(val gridOfHouses: GridOfHouses) {
    var position: Coordinate = Coordinate(0, 0)

    fun go(direction: Direction) {
        position += direction
        gridOfHouses[position] += 1
    }
}

class Column: HashMap<Int, Int>() {
    override operator fun get(key: Int): Int {
        val storedValue = super.get(key)
        if (storedValue == null) {
            val value = 0
            this[key] = value
            return value
        } else {
            return storedValue
        }
    }
}
class Row: HashMap<Int, Column>() {
    override operator fun get(key: Int): Column {
        val storedValue = super.get(key)
        if (storedValue == null) {
            val value = Column()
            this[key] = value
            return value
        } else {
            return storedValue
        }
    }
}

class GridOfHouses {
    var grid = Row()

    operator fun get(coordinate: Coordinate): Int {
        return grid[coordinate.x][coordinate.y]
    }

    operator fun set(coordinate: Coordinate, value: Int) {
        grid[coordinate.x][coordinate.y] = value
    }
}


fun main(args: Array<String>) {
    val grid = GridOfHouses()
    val santa = Santa(grid)

    santa.position = Coordinate(0, 0)

    File("./src/day_3/input.txt").reader().forEachLine {

        it.forEach {
            val letter = it.toString()
            val direction = Direction.values().filter { it.direction == letter }.first()
            santa.go(direction)

        }

    }


    val houseCount = grid.grid.values.sumBy { it.count { it.value >= 1 } }

    if (houseCount != 2572) {
        throw AssertionError("houseCount: $houseCount != 2572")
    }

    val grid2 = GridOfHouses()
    val santa2 = Santa(grid2)
    val roboSanta2 = Santa(grid2)

    File("./src/day_3/input.txt").reader().forEachLine {
        it.forEachIndexed { i, c ->
            val letter = c.toString()
            val direction = Direction.values().filter { it.direction == letter }.first()

            if (i % 2 == 0) {
                santa2.go(direction)
            } else {
                roboSanta2.go(direction)
            }
        }
    }

    val houseCount2 = grid2.grid.values.sumBy { it.count { it.value >= 1 } }

    if (houseCount2 != 2631) {
        throw AssertionError("houseCount2: $houseCount2 != 2631")
    }

}