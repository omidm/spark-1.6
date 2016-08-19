import java.util.Random;
import scala.Array.;
import scala.Function0;
import scala.MatchError;
import scala.Predef.;
import scala.Serializable;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.Range.Inclusive;
import scala.collection.immutable.StringOps;
import scala.collection.mutable.ArrayOps;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric.DoubleIsFractional.;
import scala.math.package.;
import scala.reflect.ClassTag.;
import scala.runtime.AbstractFunction0.mcV.sp;
import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractFunction1.mcDD.sp;
import scala.runtime.AbstractFunction1.mcDI.sp;
import scala.runtime.AbstractFunction1.mcII.sp;
import scala.runtime.AbstractFunction1.mcVI.sp;
import scala.runtime.AbstractFunction2;
import scala.runtime.AbstractFunction2.mcDDD.sp;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt.;
import scala.runtime.ScalaRunTime.;

public final class Gradient$
{
  public static final  MODULE$;

  static
  {
    new ();
  }

  public double prf(long input)
  {
    long x = input + 4698L;
    x = (x >> 16 ^ x) * 73244475L;
    x = (x >> 16 ^ x) * 73244475L;
    x = x >> 16 ^ x;
    return x;
  }

  public <R> R time(Function0<R> block) {
    long t0 = System.nanoTime();
    Object result = block.apply();
    long t1 = System.nanoTime();
    Predef..MODULE$.println(new StringBuilder().append("Elapsed time: ").append(BoxesRunTime.boxToDouble((t1 - t0) / 1000000.0D)).append("ms").toString());
    return result;
  }

  public double[] vector_add(double[] x, double[] y) {
    return (double[])Predef..MODULE$.refArrayOps((Object[])Predef..MODULE$.doubleArrayOps(x).zip(Predef..MODULE$.wrapDoubleArray(y), Array..MODULE$.canBuildFrom(ClassTag..MODULE$.apply(Tuple2.class)))).map(new AbstractFunction1() { public static final long serialVersionUID = 0L;

      public final double apply(Tuple2<Object, Object> x0$1) { Tuple2 localTuple2 = x0$1; if (localTuple2 != null) { double a = localTuple2._1$mcD$sp(); double b = localTuple2._2$mcD$sp(); double d1 = a + b; return d1; } throw new MatchError(localTuple2);
      }
    }
    , Array..MODULE$.canBuildFrom(ClassTag..MODULE$.Double()));
  }

  public double[] vector_sub(double[] x, double[] y) {
    return (double[])Predef..MODULE$.refArrayOps((Object[])Predef..MODULE$.doubleArrayOps(x).zip(Predef..MODULE$.wrapDoubleArray(y), Array..MODULE$.canBuildFrom(ClassTag..MODULE$.apply(Tuple2.class)))).map(new AbstractFunction1() { public static final long serialVersionUID = 0L;

      public final double apply(Tuple2<Object, Object> x0$2) { Tuple2 localTuple2 = x0$2; if (localTuple2 != null) { double a = localTuple2._1$mcD$sp(); double b = localTuple2._2$mcD$sp(); double d1 = a - b; return d1; } throw new MatchError(localTuple2);
      }
    }
    , Array..MODULE$.canBuildFrom(ClassTag..MODULE$.Double()));
  }

  public double vector_dot(double[] x, double[] y) {
    return BoxesRunTime.unboxToDouble(Predef..MODULE$.doubleArrayOps((double[])Predef..MODULE$.refArrayOps((Object[])Predef..MODULE$.doubleArrayOps(x).zip(Predef..MODULE$.wrapDoubleArray(y), Array..MODULE$.canBuildFrom(ClassTag..MODULE$.apply(Tuple2.class)))).map(new AbstractFunction1() { public static final long serialVersionUID = 0L;

      public final double apply(Tuple2<Object, Object> x0$3) { Tuple2 localTuple2 = x0$3; if (localTuple2 != null) { double a = localTuple2._1$mcD$sp(); double b = localTuple2._2$mcD$sp(); double d1 = a * b; return d1; } throw new MatchError(localTuple2);
      }
    }
    , Array..MODULE$.canBuildFrom(ClassTag..MODULE$.Double()))).sum(Numeric.DoubleIsFractional..MODULE$));
  }

  public double[] vector_mul(double x, double[] y) {
    return (double[])Predef..MODULE$.doubleArrayOps(y).map(new AbstractFunction1.mcDD.sp() { public static final long serialVersionUID = 0L;
      private final double x$3;

      public final double apply(double x$1) { return apply$mcDD$sp(x$1); } 
      public double apply$mcDD$sp(double x$1) { return this.x$3 * x$1; }

    }
    , Array..MODULE$.canBuildFrom(ClassTag..MODULE$.Double()));
  }

  public void print_help() {
    Predef..MODULE$.println("Usage: pass 3 arguments");
    Predef..MODULE$.println("   <Int dimension>");
    Predef..MODULE$.println("   <Int iteration_num>");
    Predef..MODULE$.println("   <Float sample_num in million>");
  }

  public void main(String[] args)
  {
    int i = args.length; switch (i) {
    default:
      print_help(); break;
    case 3:
    }
    Tuple3 localTuple32 = 
      new Tuple3(BoxesRunTime.boxToInteger(new StringOps(Predef..MODULE$.augmentString(args[0])).toInt()), BoxesRunTime.boxToInteger(new StringOps(Predef..MODULE$.augmentString(args[1])).toInt()), BoxesRunTime.boxToFloat(new StringOps(Predef..MODULE$.augmentString(args[2])).toFloat()));

    if ((localTuple32 instanceof Tuple3)) { Tuple3 localTuple33 = (Tuple3)localTuple32; Object kDimension = localTuple33._1();
      Object kIterationNum = localTuple33._2();
      Object kSampleNumInMillion = localTuple33._3();

      if ((kDimension instanceof Integer)) { int j = BoxesRunTime.unboxToInt(kDimension);
        if ((kIterationNum instanceof Integer)) { int k = BoxesRunTime.unboxToInt(kIterationNum);
          if ((kSampleNumInMillion instanceof Float)) { float f1 = BoxesRunTime.unboxToFloat(kSampleNumInMillion);

            Tuple3 localTuple34 = new Tuple3(BoxesRunTime.boxToInteger(j), BoxesRunTime.boxToInteger(k), BoxesRunTime.boxToFloat(f1)); Tuple3 localTuple31 = 
              localTuple34;

            IntRef kDimension = new IntRef(BoxesRunTime.unboxToInt(localTuple31._1()));
            int kIterationNum = BoxesRunTime.unboxToInt(localTuple31._2());
            float kSampleNumInMillion = BoxesRunTime.unboxToFloat(localTuple31._3());

            final IntRef kSampleNum = new IntRef((int)(kSampleNumInMillion * 1000000));

            Predef..MODULE$.println(new StringBuilder().append("** dimension    : ").append(BoxesRunTime.boxToInteger(kDimension.elem)).toString());
            Predef..MODULE$.println(new StringBuilder().append("** iteration num: ").append(BoxesRunTime.boxToInteger(kIterationNum)).toString());
            Predef..MODULE$.println(new StringBuilder().append("** sample num   : ").append(BoxesRunTime.boxToInteger(kSampleNum.elem)).toString());

            final ObjectRef samples = new ObjectRef((Gradient.Sample[])Array..MODULE$.tabulate(0, new AbstractFunction1() { public static final long serialVersionUID = 0L;

              public final Gradient.Sample apply(int x) { return new Gradient.Sample((double[])Array..MODULE$.tabulate(0, new AbstractFunction1.mcDI.sp() { public static final long serialVersionUID = 0L;

                  public final double apply(int x) { return apply$mcDI$sp(x); } 
                  public double apply$mcDI$sp(int x) { return 0.0D; }

                }
                , ClassTag..MODULE$.Double()), 0.0D);
              }
            }
            , ClassTag..MODULE$.apply(Gradient.Sample.class)));
            Predef..MODULE$.println("\nSamples generation: ");
            time(
              new AbstractFunction0.mcV.sp() { public static final long serialVersionUID = 0L;
              public final IntRef kDimension$1;
              private final IntRef kSampleNum$1;
              private final ObjectRef samples$1;

              public final void apply() { apply$mcV$sp(); } 
              public void apply$mcV$sp() { samples.elem = 
                  ((Gradient.Sample[])Predef..MODULE$.intArrayOps((int[])Array..MODULE$.tabulate(kSampleNum.elem, new AbstractFunction1.mcII.sp() { public static final long serialVersionUID = 0L;

                  public final int apply(int x) { return apply$mcII$sp(x); } 
                  public int apply$mcII$sp(int x) { return x; }

                }
                , ClassTag..MODULE$.Int()))
                  .map(new AbstractFunction1() { public static final long serialVersionUID = 0L;

                  public final Gradient.Sample apply(final int x) { int label = x % 2 == 0 ? -1 : 1;
                    double[] vec = (double[])Array..MODULE$.tabulate(Gradient..anonfun.main.1.this.kDimension$1.elem, new AbstractFunction1.mcDI.sp() { public static final long serialVersionUID = 0L;
                      private final int x$4;

                      public final double apply(int y) { return apply$mcDI$sp(y); } 
                      public double apply$mcDI$sp(int y) { return Gradient..MODULE$.prf(x * Gradient..anonfun.main.1..anonfun.apply.mcV.sp.2.this.Gradient$$anonfun$$anonfun$$$outer().kDimension$1.elem + y); }

                    }
                    , ClassTag..MODULE$.Double());
                    return new Gradient.Sample(vec, label);
                  }
                }
                , Array..MODULE$.canBuildFrom(ClassTag..MODULE$.apply(Gradient.Sample.class))));
              }
            });
            ObjectRef rand = new ObjectRef(new Random(42L));
            final ObjectRef w = new ObjectRef((double[])Array..MODULE$.tabulate(kDimension.elem, new AbstractFunction1.mcDI.sp() { public static final long serialVersionUID = 0L;
              private final ObjectRef rand$1;

              public final double apply(int i) { return apply$mcDI$sp(i); } 
              public double apply$mcDI$sp(int i) { return ((Random)this.rand$1.elem).nextDouble(); }

            }
            , ClassTag..MODULE$.Double()));

            final ObjectRef elapsed_list = new ObjectRef((double[])Array..MODULE$.tabulate(0, new AbstractFunction1.mcDI.sp() { public static final long serialVersionUID = 0L;

              public final double apply(int x) { return apply$mcDI$sp(x); } 
              public double apply$mcDI$sp(int x) { return 0.0D; }

            }
            , ClassTag..MODULE$.Double())); RichInt..MODULE$
              .to$extension0(Predef..MODULE$.intWrapper(1), kIterationNum).foreach$mVc$sp(new AbstractFunction1.mcVI.sp() { public static final long serialVersionUID = 0L;
              private final ObjectRef samples$1;
              public final ObjectRef w$1;
              private final ObjectRef elapsed_list$1;

              public final void apply(int i) { apply$mcVI$sp(i); } 
              public void apply$mcVI$sp(int i) { long t0 = System.nanoTime();
                double[] gradient = 
                  (double[])Predef..MODULE$.refArrayOps((Object[])Predef..MODULE$.refArrayOps((Object[])this.samples$1.elem)
                  .map(new AbstractFunction1() { public static final long serialVersionUID = 0L;

                  public final double[] apply(Gradient.Sample p) { double factor = (1 / (1 + package..MODULE$.exp(p.label() * Gradient..MODULE$.vector_dot((double[])Gradient..anonfun.main.2.this.w$1.elem, p.vec()))) - 1) * p.label();
                    return Gradient..MODULE$.vector_mul(factor, p.vec());
                  }
                }
                , Array..MODULE$.canBuildFrom(ClassTag..MODULE$.apply(ScalaRunTime..MODULE$.arrayClass(Double.TYPE)))))
                  .reduce(new AbstractFunction2() { public static final long serialVersionUID = 0L;

                  public final double[] apply(double[] x, double[] y) { return Gradient..MODULE$.vector_add(x, y); }

                });
                w.elem = Gradient..MODULE$.vector_add((double[])w.elem, gradient);
                long t1 = System.nanoTime();
                double elapsed = (t1 - t0) / 1000000.0D;
                Predef..MODULE$.println(new StringBuilder().append("Iteration ").append(BoxesRunTime.boxToInteger(i)).append(" elapsed time: ").append(BoxesRunTime.boxToDouble(elapsed)).append("(ms)").toString());
                elapsed_list.elem = ((double[])Predef..MODULE$.doubleArrayOps((double[])elapsed_list.elem).$colon$plus(BoxesRunTime.boxToDouble(elapsed), Array..MODULE$.canBuildFrom(ClassTag..MODULE$.Double())));
              }
            });
            double average = BoxesRunTime.unboxToDouble(Predef..MODULE$.doubleArrayOps((double[])elapsed_list.elem).reduce(new AbstractFunction2.mcDDD.sp() { public static final long serialVersionUID = 0L;

              public final double apply(double x, double y) { return apply$mcDDD$sp(x, y); } 
              public double apply$mcDDD$sp(double x, double y) { return x + y; }

            })) / ((double[])elapsed_list.elem).length;
            Predef..MODULE$.println(new StringBuilder().append("Average for ").append(BoxesRunTime.boxToInteger(((double[])elapsed_list.elem).length)).append(" iterations: ").append(BoxesRunTime.boxToDouble(average)).append("(ms)").toString());
            return;
          }
        }
      }
    }
    throw new MatchError(localTuple32);
  }

  private Gradient$()
  {
    MODULE$ = this;
  }
}



