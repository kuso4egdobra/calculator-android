package com.kusok_dobra.calculator

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

//class MainActivity : AppCompatActivity() {
//
//}

fun main() {
//    readArrNum()
    secondTask()
}

fun readArrNum() {
    // ---------- variant 1
    println("N: ")
    val N = readLine()!!.toInt()
    val list = ArrayList<Int>()
    for (i in 1..N) {
        println("list[${i-1}] = ")
        list.add(readLine()?.toInt() ?: 0)
    }
    println(list)

    // ---------- variant 2
    println("Введите массив чисел через пробел: ")
    val numbers = readLine()!!.split(" ").map { it.toInt() }
    println(numbers)
}

fun secondTask() {
    val listFigures = getListFigures()
    println(listFigures.sumOf { it.perimeter() })
    println(listFigures.sumOf { it.square() })

    val listSquares = listFigures.map { it.square() }
    val listPerimeter = listFigures.map { it.perimeter() }
    println(listPerimeter.maxOrNull())
    println(listSquares.maxOrNull())
}

fun getListFigures(): List<Figure> =
    listOf<Figure>(
        Rectangle(3.0, 5.0),
        Square(3.0),
        Circle(5.0),
        Triangle(3.0, 4.0, 5.0)
    )

abstract class Figure {
    abstract fun square(): Double
    abstract fun perimeter(): Double
}

open class Rectangle(val a: Double, val b: Double): Figure() {
    override fun square(): Double {
        return a * b
    }

    override fun perimeter(): Double {
        return 2 * a + 2 * b
    }

}

class Square(a: Double): Rectangle(a, a)

class Circle(val r: Double): Figure() {
    override fun square(): Double {
        return Math.PI * r.pow(2)
    }

    override fun perimeter(): Double {
        return 2 * Math.PI * r
    }

}

class Triangle(val a: Double, val b: Double, val c: Double): Figure() {
    override fun square(): Double {
        val p = perimeter() / 2
        return sqrt(p * (p - a) * (p - b) * (p - c))
    }

    override fun perimeter(): Double {
        return a + b + c
    }

}