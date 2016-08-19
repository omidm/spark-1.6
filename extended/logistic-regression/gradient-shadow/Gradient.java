
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.Math;

import scala.runtime.BoxesRunTime;
import scala.Predef;
import scala.collection.mutable.WrappedArray;
import scala.collection.mutable.ArraySeq;
import scala.Tuple2;

class Sample {
  double label_;
  double[] vector_;
}

class Tuple {
  Double d1_;
  Double d2_;
}

// ArraySeq<Double> xy_zipped = new scala.collection.mutable.ArraySeq<Double>(length);
// scala.collection.Iterator z_iter = y.iterator();

class Gradient {

  public static double Add(Double D1, Double D2) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    double d2 = BoxesRunTime.unboxToDouble(D2);
    return d1 + d2;
  }

  public static double Mul(Double D1, Double D2) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    double d2 = BoxesRunTime.unboxToDouble(D2);
    return d1 * d2;
  }

  public static double Scale(Double D1, double scale) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    return d1 * scale;
  }

  public static double[] VectorAdd(double[] v1, double[] v2) {
    assert v1.length == v2.length;
    int length = v1.length;

    // box for zip
    WrappedArray<Object> wv1 = Predef.wrapDoubleArray(v1);
    WrappedArray<Object> wv2 = Predef.wrapDoubleArray(v2);
    scala.collection.Iterator wv1_iter = wv1.iterator();
    scala.collection.Iterator wv2_iter = wv2.iterator();
    List<Tuple2<Double, Double> > zipped = new ArrayList<Tuple2<Double, Double> >();
    while (wv1_iter.hasNext()) {
      if (!wv1_iter.hasNext()) {
        assert false;
      }
      Double D1 =  BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
      Double D2 =  BoxesRunTime.boxToDouble((Double)(wv2_iter.next()));
      Tuple2<Double, Double> t = new Tuple2<Double, Double>(D1, D2);
      zipped.add(t);
    }

    // allocate for map
    Iterator<Tuple2<Double, Double> > zipped_iter = zipped.iterator();
    List<Double> map_result = new ArrayList<Double>();
    while (zipped_iter.hasNext()) {
      Tuple2<Double, Double> t = zipped_iter.next();
      map_result.add(BoxesRunTime.boxToDouble(Add(t._1, t._2)));
    }

    // unbox results
    int idx = 0;
    double[] result = new double[length];
    Iterator<Double> map_iter = map_result.iterator();
    while (map_iter.hasNext()) {
      result[idx] = BoxesRunTime.unboxToDouble(map_iter.next());
      ++idx;
    }

    return result;
  }

  public static double VectorDot(double[] v1, double[] v2) {
    assert v1.length == v2.length;
    int length = v1.length;

    // box for zip
    WrappedArray<Object> wv1 = Predef.wrapDoubleArray(v1);
    WrappedArray<Object> wv2 = Predef.wrapDoubleArray(v2);
    scala.collection.Iterator wv1_iter = wv1.iterator();
    scala.collection.Iterator wv2_iter = wv2.iterator();
    List<Tuple2<Double, Double> > zipped = new ArrayList<Tuple2<Double, Double> >();
    while (wv1_iter.hasNext()) {
      if (!wv1_iter.hasNext()) {
        assert false;
      }
      Double D1 =  BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
      Double D2 =  BoxesRunTime.boxToDouble((Double)(wv2_iter.next()));
      Tuple2<Double, Double> t = new Tuple2<Double, Double>(D1, D2);
      zipped.add(t);
    }

    // allocate map
    Iterator<Tuple2<Double, Double> > zipped_iter = zipped.iterator();
    List<Double> map_result = new ArrayList<Double>();
    while (zipped_iter.hasNext()) {
      Tuple2<Double, Double> t = zipped_iter.next();
      map_result.add(BoxesRunTime.boxToDouble(Mul(t._1, t._2)));
    }

    // unbox for sum
    double sum = 0;
    Iterator<Double> map_iter = map_result.iterator();
    while (map_iter.hasNext()) {
      sum += BoxesRunTime.unboxToDouble(map_iter.next());
    }

    return sum;
  }


  public static double[] VectorScale(double[] v1, double scale) {
    int length = v1.length;

    // allocate for map
    WrappedArray<Object> wv1 = Predef.wrapDoubleArray(v1);
    scala.collection.Iterator wv1_iter = wv1.iterator();
    List<Double> map_result = new ArrayList<Double>();
    while (wv1_iter.hasNext()) {
      Double D1 = BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
      map_result.add(BoxesRunTime.boxToDouble(Scale(D1, scale)));
    }

    // unbox results
    int idx = 0;
    double[] result = new double[length];
    Iterator<Double> map_iter = map_result.iterator();
    while (map_iter.hasNext()) {
      result[idx] = BoxesRunTime.unboxToDouble(map_iter.next());
      ++idx;
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

        // vector dot
        double scale =  (1.0 / (1.0 + Math.exp(s.label_ * (VectorDot(weight, s.vector_)))) - 1) * s.label_;

        // vector scale
        double[] scaled_p = VectorScale(s.vector_, scale);

        // box for reduce
        List<Double> d_scaled = new ArrayList<Double>();
        for (int j = 0; j < dimension; ++j) {
          Double D1 =  BoxesRunTime.boxToDouble((Double)(scaled_p[j]));
          d_scaled.add(D1);
        }

        // unbox for reduce
        double[] scaled = new double[dimension];
        Iterator<Double> s_iter = d_scaled.iterator();
        for (int j = 0; j < dimension; ++j) {
          scaled[j] = BoxesRunTime.unboxToDouble(s_iter.next());
        }

        // reduce
        double[] gradient_p = VectorAdd(gradient, scaled);

        // box the intermediate for next reduce
        List<Double> d_gradient = new ArrayList<Double>();
        for (int j = 0; j < dimension; ++j) {
          Double D1 =  BoxesRunTime.boxToDouble((Double)(gradient_p[j]));
          d_gradient.add(D1);
        }

        // unbox the intermediate for next reduce
        Iterator<Double> g_iter = d_gradient.iterator();
        for (int j = 0; j < dimension; ++j) {
          gradient[j] = BoxesRunTime.unboxToDouble(g_iter.next());
        }
      }
      weight = VectorAdd(weight, gradient);

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
} // Gradient class



