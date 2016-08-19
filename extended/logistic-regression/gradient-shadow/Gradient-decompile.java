import java.util.Random;
import scala.Array;
import scala.Function0;
import scala.MatchError;
import scala.Predef;
import scala.Serializable;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.immutable.Range.Inclusive;
import scala.collection.immutable.StringOps;
import scala.collection.mutable.ArrayOps;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric.DoubleIsFractional;
// import scala.math.package;
import scala.reflect.ClassTag;
// import scala.runtime.AbstractFunction0.mcV.sp;
import scala.runtime.AbstractFunction1;
// import scala.runtime.AbstractFunction1.mcDD.sp;
// import scala.runtime.AbstractFunction1.mcDI.sp;
// import scala.runtime.AbstractFunction1.mcII.sp;
// import scala.runtime.AbstractFunction1.mcVI.sp;
// import scala.runtime.AbstractFunction2;
// import scala.runtime.AbstractFunction2.mcDDD.sp;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt;
import scala.runtime.ScalaRunTime;

public final class Gradient$
{
  // public static final  Gradient$();

  public double prf(long input)
  {
    long x = input + 4698L;
    x = (x >> 16 ^ x) * 73244475L;
    x = (x >> 16 ^ x) * 73244475L;
    x = x >> 16 ^ x;
    return x;
  }

  public double[] vector_add(double[] x, double[] y) {
    return (double[])Predef.refArrayOps((Object[])Predef.doubleArrayOps(x).zip(Predef.wrapDoubleArray(y), Array.canBuildFrom(ClassTag.apply(Tuple2.class)))).map(new AbstractFunction1() { public static final long serialVersionUID = 0L;

      public final double apply(Tuple2<Object, Object> x0$1) { Tuple2 localTuple2 = x0$1; if (localTuple2 != null) { double a = localTuple2._1$mcD$sp(); double b = localTuple2._2$mcD$sp(); double d1 = a + b; return d1; } throw new MatchError(localTuple2);
      }
    }
    , Array.canBuildFrom(ClassTag.Double()));
  }

  public void print_help() {
    Predef.println("Usage: pass 3 arguments");
    Predef.println("   <Int dimension>");
    Predef.println("   <Int iteration_num>");
    Predef.println("   <Float sample_num in million>");
  }

  public void main(String[] args)
  {
    int i = 0;
    }

  // private Gradient$()
  // {
  //   Gradient$ = this;
  // }
}



