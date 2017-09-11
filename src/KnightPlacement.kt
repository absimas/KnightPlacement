/**
 * Created by Simas on 2017 Sep 11.
 */

/**
 * Board size.
 */
private const val N = 5
/**
 * Cell count.
 */
private const val NN = N * N
@Suppress("MemberVisibilityCanPrivate")
class KnightPlacement {

  companion object {
    inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, noinline innerInit: (Int) -> INNER): Array<Array<INNER>>
        = Array(sizeOuter) { Array<INNER>(sizeInner, innerInit) }

    @JvmStatic
    fun main(args: Array<String>) {
      KnightPlacement()
    }
  }

  /**
   * Chess board cells.
   */
  val board = array2d(N, N) { 0 }
  val DIR_COUNT = 8
  /**
   * Knight can move in 8 different directions.
   * These are the possible x values.
   */
  val cy = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
  /**
   * Knight can move in 8 different directions.
   * These are the possible y values.
   */
  val cx = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)

  /**
   * True when a knight's path that covers the whole board is found.
   */
  var exists = false
  /**
   * Count of operations performed when calculating a covering path.
   */
  var count = 0

  init {
    if (cx.size != DIR_COUNT || cy.size != DIR_COUNT) {
      throw IllegalStateException("Invalid direction count!")
    }

    // Initial knight's position is used as the first step
    board[0][0] = 1

    // Recursive call
    go(2, 0, 0)

    if (exists) {
      for (i in N-1 downTo 0) {
        for (j in 0 until N) {
          print(String.format("%2d ", board[i][j]))
        }
        println()
      }
    } else {
      println("Path not found!")
    }
  }

  fun go(step: Int, x: Int, y: Int) {
    // Knight's direction
    var k = 0

    do {
      val u = x + cx[k]
      val v = y + cy[k]
      ++k

      if (u !in 0 until N || v !in 0 until N) {
        // OOB
        continue
      } else if (board[u][v] != 0) {
        // Already visited cell
        continue
      }

      // Mark position with the step number
      board[u][v] = step

      // Check if all cells were visited
      if (step >= NN) {
        exists = true
      } else {
        go(step+1, u, v)
        // If the recursive call did not lead to a covering path, clear the current board cell
        if (!exists) {
          board[u][v] = 0
        }
      }
    } while (!exists && k < DIR_COUNT)
  }

}