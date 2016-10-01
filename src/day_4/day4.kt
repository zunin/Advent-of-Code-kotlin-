package day_4

import java.math.BigInteger
import java.security.MessageDigest

operator fun String.times(multiplier: Int): String {
    var res = ""
    while (res.length < this.length * multiplier) {
        res += this
    }
    return res
}

fun String.toMD5(): String {
    val digest = MessageDigest.getInstance("MD5")
    digest.update(this.toByteArray(), 0, this.length)
    val result = BigInteger(1, digest.digest()).toString(16)
    return ("0" * (32 - result.length)) + result
}

fun main(args: Array<String>) {
    val secretKey = "yzbqklnj"

    // task 1
    var answer = 0
    while (!"$secretKey$answer".toMD5().startsWith("0"*5)) {
        answer += 1
    }
    println("Answer is \"$answer\"")

    // task 2
    // don't reset as it doesn't start with 6 zeroes earlier if it doesn't start with 5
    while (!"$secretKey$answer".toMD5().startsWith("0"*6)) {
        answer += 1
    }
    println("Answer is \"$answer\"")
}
