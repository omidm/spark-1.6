
import java.util.Random

object Gradient {

  case class Sample(vec: Array[Double], label: Double)

  // A psuedo random function.
  def prf(input: Long) : Double = {
    var x = input + 4698;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = ((x >> 16) ^ x);
    return x;
  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1e6 + "ms")
    result
  }

  def vector_add(x: Array[Double], y: Array[Double]) : Array[Double] = {
    return Array.tabulate(x.length){i => x(i) + y(i)};
  }

  def vector_sub(x: Array[Double], y: Array[Double]) : Array[Double] = {
    return Array.tabulate(x.length){i => x(i) - y(i)};
  }

  def vector_dot(x: Array[Double], y: Array[Double]) : Double = {
    var result: Double = 0
      for (i <- 0 until x.length)
        result += x(i) * y(i)
    return result
  }

  def vector_mul(x: Double, y:Array[Double]) : Array[Double] = {
    return Array.tabulate(y.length){i => y(i) * x}
  }

  def print_help() {
    println("Usage: pass 3 arguments")
    println("   <Int dimension>")
    println("   <Int iteration_num>")
    println("   <Float sample_num in million>")
  }

  def main(args: Array[String]) {
    var (kDimension          : Int,
         kIterationNum       : Int,
         kSampleNumInMillion : Float) =
    args.length match {
      case 3 => (args(0).toInt, args(1).toInt, args(2).toFloat)
      case default => print_help();
    }

    var kSampleNum          : Int = (kSampleNumInMillion.toFloat * 1000000).toInt

    println("** dimension    : " + kDimension)
    println("** iteration num: " + kIterationNum)
    println("** sample num   : " + kSampleNum)

    var samples = Array.tabulate(0)(x => Sample(Array.tabulate(0)(x => 0), 0))
    println("\nSamples generation: ")
    time{
      samples = Array.tabulate(kSampleNum)(x => x)
        .map(x => {
                    val label = if (x % 2 == 0) -1 else 1
                    val vec = Array.tabulate(kDimension)(y => prf(x*kDimension+y))
                    Sample(vec, label)
                  })
    }

    // initialize w to a random vector
    var rand = new Random(42)
    var w: Array[Double] = Array.tabulate(kDimension)(i => rand.nextDouble)

    var elapsed_list : Array[Double] = Array.tabulate(0)(x => 0)
    var truncate = 10;

    for (i <- 1 to kIterationNum) {
      val t0 = System.nanoTime()
      val gradient = samples
        .map(p => {
                    val factor =  (1 / (1 + math.exp(p.label * (vector_dot(w,p.vec)))) - 1) * p.label;
                    vector_mul(factor, p.vec)
                  })
        .reduce(vector_add)
        w = vector_add(w, gradient)

      val t1 = System.nanoTime()
      val elapsed = (t1 - t0)/1e6
      println("Iteration " + i + " elapsed time: " + elapsed + "(ms)")
      if (i > truncate) {
        elapsed_list = elapsed_list :+ elapsed
      }
    }

    val average = elapsed_list.reduce((x, y) => x + y) / elapsed_list.length
    println("Average for " + elapsed_list.length + " iterations (excluding the first " + truncate +  "): " + average + "(ms)")
  }
}

