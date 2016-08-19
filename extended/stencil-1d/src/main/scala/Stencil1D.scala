
import java.util.Random
import org.apache.spark.{SparkConf, SparkContext}


object Stencil1D {

  def print_array(array : Array[Double]) : String = {
    var str = "["
    for (e <- array) {
      str += e.toString() + ", "
    }
    str += "]"
    return str
  }

  def print_key_array(kv : (Int, Array[Double])) : String = {
    var str = kv._1.toString
    str += " -> "
    str += print_array(kv._2)
    return str
  }

  def print_array_group(arrays : Iterable[Array[Double]]) : String = {
    var str = "{"
    for (a <- arrays) {
      str += print_array(a) + ", "
    }
    str += "}"
    return str
  }

  def print_key_array_group(kvs : (Int, Iterable[Array[Double]])) : String = {
    var str = kvs._1.toString
    str += " -> "
    str += print_array_group(kvs._2)
    return str
  }


  def spin_wait(wait_us: Int) = {
    // println("***** SpinWait us: " + wait_us)
    if (wait_us != 0) {
      var start = System.nanoTime()
      var stop  = System.nanoTime()
      var loop = 0
      while ((stop - start) < wait_us * 1000) {
        var x = 0;
        for (i <- 1 to 10) {
          x = x + i;
        }
        stop = System.nanoTime()
        loop = loop + 1
      }
      println("****** looped: " + loop)
    }
  }

  def print_help() {
    println("Usage: pass 4 arguments")
    println("   <Int iteration_num>")
    println("   <Int partition_num>")
    println("   <Int partition_size>")
    println("   <Int spin_wait>")
  }

  def main(args: Array[String]) {

    var (kIterationNum  : Int,
         kPartitionNum  : Int,
         kPartitionSize : Int,
         kSpinWait      : Int) =
    args.length match {
      case 4 => (args(0).toInt, args(1).toInt, args(2).toInt, args(3).toInt)
      case default => print_help();
    }

    val conf = new SparkConf().setAppName("Stencil1D")
    val sc = new SparkContext(conf)

    // Initialize the array
    val input_seed = Array.tabulate(kPartitionNum)(x => x)
    var input_arrays = sc.parallelize(input_seed, kPartitionNum)
                          .map(x => {
                                      val key = x
                                      val array = Array.tabulate(kPartitionSize)(y => y.toDouble)
                                      (key, array)
                                    }
                              )
    input_arrays.persist();


    // Apply Stencil
    for (i <- 1 to kIterationNum) {
      input_arrays = input_arrays
        .flatMap(kv => {
                    // Uncomment println to check the operations.
                    // println("*** Iteration " + i + " flatMap: " + print_key_array(kv))
                    val key = kv._1
                    val value = kv._2
                    if ((key == 0) && (kPartitionNum > 1)) {
                      Array(kv, (key + 1, Array(value(kPartitionSize - 1))))
                    } else if (key == (kPartitionNum - 1)) {
                      Array(kv, (key - 1, Array(value(0))))
                    } else {
                      Array(kv, (key + 1, Array(value(kPartitionSize - 1))), ( key - 1, Array(value(0))))
                    }
                  }
                )
        .groupByKey()
        .map(kvs => {
                    // Uncomment println to check the operations.
                    // println("*** Iteration " + i + " map: " + print_key_array_group(kvs))
                    val key = kvs._1
                    val values = kvs._2
                    var value = Array.empty[Double]
                    for (a <- values) {
                      if (a.length == kPartitionSize) {
                        value = a
                      }
                    }
                    spin_wait(kSpinWait)
                    (key, value)
                  }
            ) 

      // without the following action(s) none of the stencil computations happen.
      // input_arrays.foreach(println); // too expensive!
      input_arrays.count();

      println("*** Iteration " + i)
    }

    sc.stop()
  }
}
