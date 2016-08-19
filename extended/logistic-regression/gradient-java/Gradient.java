
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

class Sample {
  double label_;
  double[] vector_;
}

class Gradient {

  public static void VectorAddWithScale(double[] acc, double[] add, double scale) {
    assert acc.length == add.length;
    int length = acc.length;

    for (int i = 0; i < length; ++i) {
      acc[i] = acc[i] + scale * add[i];
    }
  }

  public static double VectorDot(double[] v1, double[] v2) {
    assert v1.length == v2.length;
    int length = v1.length;

    double result = 0;
    for (int i = 0; i < length; ++i) {
      result += v1[i] * v2[i];
    }
    return result;
  }


  public static void PrintHelp() {
    System.out.println("Usage: pass 3 arguments");
    System.out.println("   <Int dimension>");
    System.out.println("   <Int iteration_num>");
    System.out.println("   <Float sample_num in million>");
  }

  public static void main(String[] args) {

    Integer dimension = 10;
    Integer iteration_num = 20;
    Double sample_num_m = 1.0;
    if (args.length != 3) {
      System.out.println("Error!");
      PrintHelp();
      return;
    } else {
      dimension = Integer.parseInt(args[0]);
      iteration_num = Integer.parseInt(args[1]);
      sample_num_m = Double.parseDouble(args[2]);
    }

    Integer sample_num = (Integer)(int)(sample_num_m * 1e6);

    System.out.println("dimension     : " + String.valueOf(dimension));
    System.out.println("iteration num : " + String.valueOf(iteration_num));
    System.out.println("sample num    : " + String.valueOf(sample_num));


    Sample[] samples = new Sample[sample_num];
    {
      long start_time = System.nanoTime();
      for (int i = 0; i < sample_num; ++i) {
        samples[i] = new Sample();
        samples[i].label_ = (double)(i % 2) * 2 - 1;
        samples[i].vector_ = new double[dimension];
        for (int j = 0; j < dimension; ++j) {
          samples[i].vector_[j] = 13.0;
        }
      }
      long end_time = System.nanoTime();
      System.out.println("Sample generation elapsed time : " + String.valueOf((end_time - start_time)/1e6) + " ms");
    }

    double[] weight = new double[dimension];
    for (int j = 0; j < dimension; ++j) {
      weight[j] = 1;
    }

    List<Double> elapsed_list = new ArrayList<Double>();
    int truncate = 10;

    for (int iter = 0; iter < iteration_num; ++iter) {
      long start_time = System.nanoTime();
      double[] gradient = new double[dimension];
      for (int j = 0; j < dimension; ++j) {
        gradient[j] = 0;
      }

      for (int i = 0; i < sample_num; ++i) {
        Sample s = samples[i];
        double scale =  (1.0 / (1.0 + Math.exp(s.label_ * (VectorDot(weight, s.vector_)))) - 1) * s.label_;
        VectorAddWithScale(gradient, s.vector_, scale);
      }
      VectorAddWithScale(weight, gradient, 1.0);

      long end_time = System.nanoTime();
      double elapsed = (end_time - start_time)/1e6;
      System.out.println("Iteration " + iter + " elapsed time : " + String.valueOf(elapsed) + "(ms)");
      if (iter >= truncate) {
        elapsed_list.add(elapsed);
      }
    }

    Double average = 0.0;
    {
      Iterator<Double> iter = elapsed_list.iterator();
      while(iter.hasNext()) {
        average += iter.next();
      }
    }
    average = average / elapsed_list.size();
    System.out.println("Average gradient for " + iteration_num + " iterations (excluding the first " + String.valueOf(truncate) + "): " + String.valueOf(average) + "(ms)");
  }
}


