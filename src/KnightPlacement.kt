
/**
 * Created by Simas on 2017 Sep 11.
 */

class KnightPlacement {

  companion object {
    inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int) -> INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array(sizeInner, innerInit) }

    @JvmStatic
    fun main(args: Array<String>) {
      KnightPlacement()
    }
  }

  /**
   * Board size.
   */
  private val N: Int
  /**
   * Chess board cells.
   */
  private val board: Array<Array<Int>>
  /**
   * Knight can move in 8 different directions.
   * These are the possible x values.
   */
  private val cx = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
  /**
   * Knight can move in 8 different directions.
   * These are the possible y values.
   */
  private val cy = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)

  /**
   * True when a knight's path that covers the whole board is found.
   */
  private var exists = false
  /**
   * Count of operations performed when searching for a covering path.
   */
  private var count = 0

  init {
    // Initialization
    println("1. DATA")
    if (cx.size != 8 || cy.size != 8) {
      throw IllegalStateException("Invalid direction count!")
    }
    N = 5
    board = array2d(N, N) { 0 }
    println("Board size $N x $N")

    // Initial knight's position is used as the first step
    val kx = 0
    val ky = 0
    val kl = 1
    board[kx][ky] = kl
    println("Initial knight's position: X=$kx, Y=$ky, L=$kl.")

    // Execution
    println("\n2. EXECUTION")
    go(kl+1, kx, ky)

    // Result
    println("\n 3. RESULTS")
    if (exists) {
      println("Path found.")
      println("Y, V ^")
      for (i in N-1 downTo 0) {
        print("   $i | ")
        for (j in 0 until N) {
          print(String.format("%2d ", board[j][i]))
        }
        println()
      }
      print("     ")
      print("-".repeat(3 * N+1))
      println("> X, U")
      print("       ")
      for (i in 0 until N) {
        print(String.format("%2d ", i))
      }
      println()
    } else {
      println("Path not found.")
    }
  }

  private fun go(l: Int, x: Int, y: Int) {
    // Knight's direction
    var k = 0

    do {
      val u = x + cx[k]
      val v = y + cy[k]
      ++k

      print(String.format("%4d) %sR%d. U=$u, V=$v, L=$l.", ++count, "-".repeated(l-2), k-1, u, v, l))

      if (u !in 0 until N || v !in 0 until N) {
        if (k == 8) {
          println(" Out of bounds. Backtrack.")
        } else {
          println(" Out of bounds.")
        }
        continue
      } else if (board[u][v] != 0) {
        println(" Already visited.")
        continue
      }

      // Mark position with the step number
      board[u][v] = l
      println(" Free. BOARD[$u][$v]=$l")

      // Check if all cells were visited
      if (l >= N*N) {
        exists = true
      } else {
        go(l +1, u, v)
        // If the recursive call did not lead to a covering path, clear the current board cell
        if (!exists) {
          board[u][v] = 0
        }
      }
    } while (!exists && k < 8)
  }

  private fun String.repeated(n: Int): String {
    return repeat(Math.max(n, 0))
  }

}