
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



  public static double Mul(Double D1, Double D2) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    double d2 = BoxesRunTime.unboxToDouble(D2);
    return d1 * d2;
  }

  public static double AddWithScale(Double D1, Double D2, double scale) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    double d2 = BoxesRunTime.unboxToDouble(D2);
    return d1 + d2 * scale;
  }

  public static double Add(Double D1, Double D2) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    double d2 = BoxesRunTime.unboxToDouble(D2);
    return d1 + d2;
  }

  public static double Scale(Double D1, double scale) {
    double d1 = BoxesRunTime.unboxToDouble(D1);
    return d1 * scale;
  }


  public static double VectorDotProduct(double[] v1, double[] v2) {
    assert v1.length == v2.length;
    int length = v1.length;
    double result = 0.0;

    // naive case
    WrappedArray<Object> wv1 = Predef.wrapDoubleArray(v1);
    WrappedArray<Object> wv2 = Predef.wrapDoubleArray(v2);
    scala.collection.Iterator wv1_iter = wv1.iterator();
    scala.collection.Iterator wv2_iter = wv2.iterator();

    List<Tuple2<Double, Double> > zipped = new ArrayList<Tuple2<Double, Double> >();
    while (wv1_iter.hasNext()) {
      if (!wv1_iter.hasNext()) {
        assert false;
      }
      Double d1 =  BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
      Double d2 =  BoxesRunTime.boxToDouble((Double)(wv2_iter.next()));
      Tuple2<Double, Double> t = new Tuple2<Double, Double>(d1, d2);
      zipped.add(t);
    }

    Iterator<Tuple2<Double, Double> > zipped_iter = zipped.iterator();
    List<Double> d_result = new ArrayList<Double>();
    while (zipped_iter.hasNext()) {
      Tuple2<Double, Double> t = zipped_iter.next();
      d_result.add(BoxesRunTime.boxToDouble(Mul(t._1, t._2)));
    }

    double[] map_result = new double[length];
    Iterator<Double> r_iter = d_result.iterator();
    for (int i = 0; i < length; ++i) {
      map_result[i] = BoxesRunTime.unboxToDouble(r_iter.next());
    }

    for (int i = 0; i < length; ++i) {
      result += map_result[i];
    }



    // List<Tuple> zipped = new ArrayList<Tuple>();
    // List<Tuple> zipped = new ArrayList<Tuple>();
    // for (int i = 0; i < length; ++i) {
    //   Tuple t = new Tuple();
    //   t.d1_ =  BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
    //   t.d2_ =  BoxesRunTime.boxToDouble((Double)(wv2_iter.next()));
    //   zipped.add(t);
    // }
    // Iterator<Tuple> zipped_iter = zipped.iterator();
    // for (int i = 0; i < length; ++i) {
    //   Tuple t = zipped_iter.next();
    //   result += Mul(t.d1_, t.d2_);
    // }



    // // better case
    // Tuple[] zipped = new Tuple[length];
    // for (int i = 0; i < length; ++i) {
    //   zipped[i] = new Tuple();
    //   // zipped[i].d1_ = BoxesRunTime.boxToDouble(v1[i]);
    //   zipped[i].d1_ = BoxesRunTime.boxToDouble(v1[i]);
    //   zipped[i].d2_ = BoxesRunTime.boxToDouble(v2[i]);
    // }
    // for (int i = 0; i < length; ++i) {
    //   double d1 = BoxesRunTime.unboxToDouble(zipped[i].d1_);
    //   double d2 = BoxesRunTime.unboxToDouble(zipped[i].d2_);
    //   result += d1 * d2;
    // }

    return result;
  }

  public static void VectorAddWithScale(double[] acc, double[] add, double scale) {
    assert acc.length == add.length;
    int length = acc.length;
    double[] result = new double[length];

    // naive case
    WrappedArray<Object> wacc = Predef.wrapDoubleArray(acc);
    WrappedArray<Object> wadd = Predef.wrapDoubleArray(add);
    scala.collection.Iterator wacc_iter = wacc.iterator();
    scala.collection.Iterator wadd_iter = wadd.iterator();

    List<Tuple2<Double, Double> > zipped = new ArrayList<Tuple2<Double, Double> >();
    // for (int i = 0; i < length; ++i) {
    while (wacc_iter.hasNext()) {
      if (!wadd_iter.hasNext()) {
        assert false;
      }
      Double d1 =  BoxesRunTime.boxToDouble((Double)(wacc_iter.next()));
      Double d2 =  BoxesRunTime.boxToDouble((Double)(wadd_iter.next()));
      Tuple2<Double, Double> t = new Tuple2<Double, Double>(d1, d2);
      zipped.add(t);
    }
    Iterator<Tuple2<Double, Double> > zipped_iter = zipped.iterator();
    int i = 0;
    while (zipped_iter.hasNext()) {
      Tuple2<Double, Double> t = zipped_iter.next();
      result[i] = AddWithScale(t._1, t._2, scale);
      ++i;
    }


    // List<Tuple> zipped = new ArrayList<Tuple>();
    // for (int i = 0; i < length; ++i) {
    //   Tuple t = new Tuple();
    //   t.d1_ =  BoxesRunTime.boxToDouble((Double)(wacc_iter.next()));
    //   t.d2_ =  BoxesRunTime.boxToDouble((Double)(wadd_iter.next()));
    //   zipped.add(t);
    // }
    // Iterator<Tuple> zipped_iter = zipped.iterator();
    // for (int i = 0; i < length; ++i) {
    //   Tuple t = zipped_iter.next();
    //   result[i] = Add(t.d1_, t.d2_, scale);
    // }




    // // better case
    // Tuple[] zipped = new Tuple[length];
    // for (int i = 0; i < length; ++i) {
    //   zipped[i] = new Tuple();
    //   zipped[i].d1_ = BoxesRunTime.boxToDouble(acc[i]);
    //   zipped[i].d2_ = BoxesRunTime.boxToDouble(add[i]);
    // }
    // for (int i = 0; i < length; ++i) {
    //   double d1 = BoxesRunTime.unboxToDouble(zipped[i].d1_);
    //   double d2 = BoxesRunTime.unboxToDouble(zipped[i].d2_);
    //   acc[i] = d1 + scale * d2;
    //   result[i] = d1 + scale * d2;
    // }


    for (int j = 0; j < length; ++j) {
      acc[j] = result[j];
    }
  }





  public static double[] VectorAdd(double[] v1, double[] v2) {
    assert v1.length == v2.length;
    int length = v1.length;
    double[] result = new double[length];

    // naive case
    WrappedArray<Object> wv1 = Predef.wrapDoubleArray(v1);
    WrappedArray<Object> wv2 = Predef.wrapDoubleArray(v2);
    scala.collection.Iterator wv1_iter = wv1.iterator();
    scala.collection.Iterator wv2_iter = wv2.iterator();

    List<Tuple2<Double, Double> > zipped = new ArrayList<Tuple2<Double, Double> >();
    // for (int i = 0; i < length; ++i) {
    while (wv1_iter.hasNext()) {
      if (!wv1_iter.hasNext()) {
        assert false;
      }
      Double d1 =  BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
      Double d2 =  BoxesRunTime.boxToDouble((Double)(wv2_iter.next()));
      Tuple2<Double, Double> t = new Tuple2<Double, Double>(d1, d2);
      zipped.add(t);
    }
    Iterator<Tuple2<Double, Double> > zipped_iter = zipped.iterator();
    List<Double> d_result = new ArrayList<Double>();
    while (zipped_iter.hasNext()) {
      Tuple2<Double, Double> t = zipped_iter.next();
      d_result.add(BoxesRunTime.boxToDouble(Add(t._1, t._2)));
    }

    Iterator<Double> r_iter = d_result.iterator();
    for (int i = 0; i < length; ++i) {
      result[i] = BoxesRunTime.unboxToDouble(r_iter.next());
    }

    return result;
  }


  public static double[] VectorScale(double[] v1, double scale) {
    int length = v1.length;
    double[] result = new double[length];

    // naive case
    WrappedArray<Object> wv1 = Predef.wrapDoubleArray(v1);
    scala.collection.Iterator wv1_iter = wv1.iterator();

    List<Double> d_result = new ArrayList<Double>();
    while (wv1_iter.hasNext()) {
      Double d1 =  BoxesRunTime.boxToDouble((Double)(wv1_iter.next()));
      d_result.add(BoxesRunTime.boxToDouble(Scale(d1, scale)));
    }

    Iterator<Double> r_iter = d_result.iterator();
    for (int i = 0; i < length; ++i) {
      result[i] = BoxesRunTime.unboxToDouble(r_iter.next());
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


    List<Sample> samples = new ArrayList<Sample>();
    {
      long start_time = System.nanoTime();
      for (int i = 0; i < sample_num; ++i) {
        Sample s = new Sample();
        s.label_ = (double)(i % 2) * 2 - 1;
        s.vector_ = new double[dimension];
        for (int j = 0; j < dimension; ++j) {
          s.vector_[j] = 13.0;
        }
        samples.add(s);
      }
      long end_time = System.nanoTime();
      System.out.println("Sample generation elapsed time : " + String.valueOf((end_time - start_time)/1e6) + " ms");
    }

    double[] weight = new double[dimension];
    for (int j = 0; j < dimension; ++j) {
      weight[j] = 1;
    }

    double[] g = new double[dimension];
    for (int j = 0; j < dimension; ++j) {
      g[j] = 1;
    }

    List<Double> elapsed_list = new ArrayList<Double>();
    for (int i = 0; i < iteration_num; ++i) {
      long start_time = System.nanoTime();
      double[] gradient = new double[dimension];
      for (int j = 0; j < dimension; ++j) {
        gradient[j] = 0;
      }

      Iterator<Sample> iter = samples.iterator();
      while(iter.hasNext()) {
        Sample s = iter.next();
        double scale =  (1.0 / (1.0 + Math.exp(s.label_ * (VectorDotProduct(weight, s.vector_)))) - 1) * s.label_;


        // naive way
        double[] scaled_p = VectorScale(s.vector_, scale);

        List<Double> d_scaled = new ArrayList<Double>();
        for (int j = 0; j < dimension; ++j) {
          Double d1 =  BoxesRunTime.boxToDouble((Double)(scaled_p[j]));
          d_scaled.add(BoxesRunTime.boxToDouble(Scale(d1, scale)));
        }

        double[] scaled = new double[dimension];
        Iterator<Double> s_iter = d_scaled.iterator();
        for (int j = 0; j < dimension; ++j) {
          scaled[j] = BoxesRunTime.unboxToDouble(s_iter.next());
        }

        double[] gradient_p = VectorAdd(gradient, scaled);

        List<Double> d_gradient = new ArrayList<Double>();
        for (int j = 0; j < dimension; ++j) {
          Double d1 =  BoxesRunTime.boxToDouble((Double)(gradient_p[j]));
          d_gradient.add(BoxesRunTime.boxToDouble(Scale(d1, scale)));
        }

        Iterator<Double> g_iter = d_gradient.iterator();
        for (int j = 0; j < dimension; ++j) {
          gradient[j] = BoxesRunTime.unboxToDouble(g_iter.next());
        }




        // better way
        // VectorAddWithScale(gradient, s.vector_, scale);
      }
      VectorAddWithScale(weight, gradient, 1.0);

      // VectorAdd(weight, g);
      // VectorDotProduct(weight, g);

      long end_time = System.nanoTime();
      double elapsed = (end_time - start_time)/1e6;
      elapsed_list.add(elapsed);
      System.out.println("Iteration " + i + " elapsed time : " + String.valueOf(elapsed) + "(ms)");
    }

    Double average = 0.0;
    {
      Iterator<Double> iter = elapsed_list.iterator();
      while(iter.hasNext()) {
        average += iter.next();
      }
    }
    average = average / elapsed_list.size();
    System.out.println("Average gradient for " + iteration_num + " iterations : " + String.valueOf(average) + "(ms)");
  }
}




