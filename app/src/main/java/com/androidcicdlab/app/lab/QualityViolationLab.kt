package com.androidcicdlab.app.lab

import java.util.*

/**
 * Intentionally violates Detekt and Ktlint. Excluded from default quality tasks.
 *
 * Reproduce a quality-gate failure:
 *   ./gradlew detektFailureLab
 *   ./gradlew detekt ktlintCheck -Plab.failQuality=true
 *
 * Do not import this type from production UI code — it exists only for the lab.
 */
object QualityViolationLab {
    private val unusedSecret = 42

    fun messy(a: Int, b: Int, c: Int, d: Int, e: Int): Int {
        val dump = ArrayList<Int>()
        val cache = HashMap<String, Int>()
        dump.add(a)
        cache.put("k", b)
        if (a > 10 && b > 20 && c > 30 && d > 40 && e > 50 && a + b + c > 100) {
            return 999
        }
        return 0
    }

    fun one() {
        println("1")
    }

    fun two() {
        println("2")
    }

    fun three() {
        println("3")
    }

    fun four() {
        println("4")
    }

    fun five() {
        println("5")
    }

    fun six() {
        println("6")
    }

    fun seven() {
        println("7")
    }

    fun eight() {
        println("8")
    }

    fun nine() {
        println("9")
    }
}
