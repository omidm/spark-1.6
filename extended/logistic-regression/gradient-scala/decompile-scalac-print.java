[[syntax trees at end of                   cleanup]] // Gradient.scala
package <empty> {
  object Gradient extends Object {
    def prf(input: Long): Double = {
      var x: Long = input.+(4698);
      x = x.>>(16).^(x).*(73244475);
      x = x.>>(16).^(x).*(73244475);
      x = x.>>(16).^(x);
      return x.toDouble()
    };
    def time(block: Function0): Object = {
      val t0: Long = java.this.lang.System.nanoTime();
      val result: Object = block.apply();
      val t1: Long = java.this.lang.System.nanoTime();
      scala.this.Predef.println("Elapsed time: ".+(scala.Double.box(t1.-(t0)./(1000000.0))).+("ms"));
      result
    };
    def vector_add(x: Array[Double], y: Array[Double]): Array[Double] = return scala.this.Predef.refArrayOps(scala.this.Predef.doubleArrayOps(x).zip(scala.this.Predef.wrapDoubleArray(y), scala.this.Array.canBuildFrom(ClassTag.apply(classOf[scala.Tuple2]))).$asInstanceOf[Array[Object]]()).map({
  (new anonymous class anonfun$vector_add$1(): Function1)
}, scala.this.Array.canBuildFrom(ClassTag.Double())).$asInstanceOf[Array[Double]]();
    def vector_sub(x: Array[Double], y: Array[Double]): Array[Double] = return scala.this.Predef.refArrayOps(scala.this.Predef.doubleArrayOps(x).zip(scala.this.Predef.wrapDoubleArray(y), scala.this.Array.canBuildFrom(ClassTag.apply(classOf[scala.Tuple2]))).$asInstanceOf[Array[Object]]()).map({
  (new anonymous class anonfun$vector_sub$1(): Function1)
}, scala.this.Array.canBuildFrom(ClassTag.Double())).$asInstanceOf[Array[Double]]();
    def vector_dot(x: Array[Double], y: Array[Double]): Double = return scala.Double.unbox(scala.this.Predef.doubleArrayOps(scala.this.Predef.refArrayOps(scala.this.Predef.doubleArrayOps(x).zip(scala.this.Predef.wrapDoubleArray(y), scala.this.Array.canBuildFrom(ClassTag.apply(classOf[scala.Tuple2]))).$asInstanceOf[Array[Object]]()).map({
  (new anonymous class anonfun$vector_dot$1(): Function1)
}, scala.this.Array.canBuildFrom(ClassTag.Double())).$asInstanceOf[Array[Double]]()).sum(scala.math.Numeric$DoubleIsFractional));
    def vector_mul(x: Double, y: Array[Double]): Array[Double] = return scala.this.Predef.doubleArrayOps(y).map({
  (new anonymous class anonfun$vector_mul$1(x): Function1)
}, scala.this.Array.canBuildFrom(ClassTag.Double())).$asInstanceOf[Array[Double]]();
    def print_help(): Unit = {
      scala.this.Predef.println("Usage: pass 3 arguments");
      scala.this.Predef.println("   <Int dimension>");
      scala.this.Predef.println("   <Int iteration_num>");
      scala.this.Predef.println("   <Float sample_num in million>")
    };
    def main(args: Array[String]): Unit = {
      <synthetic> private[this] val x$2: Tuple3 = {
        case <synthetic> val x1: Object = ({
          case <synthetic> val x1: Int = args.length();
          (x1: Int) match {
            case 3 => new Tuple3(scala.Int.box(new collection.immutable.StringOps(scala.this.Predef.augmentString(args.apply(0))).toInt()), scala.Int.box(new collection.immutable.StringOps(scala.this.Predef.augmentString(args.apply(1))).toInt()), scala.Float.box(new collection.immutable.StringOps(scala.this.Predef.augmentString(args.apply(2))).toFloat()))
            case _ => {
              Gradient.this.print_help();
              scala.runtime.BoxedUnit.UNIT
            }
          }
        }: Object);
        case8(){
          if (x1.$isInstanceOf[Tuple3]())
            {
              <synthetic> val x2: Tuple3 = (x1.$asInstanceOf[Tuple3](): Tuple3);
              {
                val kDimension: Object = x2._1();
                val kIterationNum: Object = x2._2();
                val kSampleNumInMillion: Object = x2._3();
                if (kDimension.$isInstanceOf[Int]())
                  {
                    <synthetic> val x3: Int = (scala.Int.unbox(kDimension): Int);
                    if (kIterationNum.$isInstanceOf[Int]())
                      {
                        <synthetic> val x4: Int = (scala.Int.unbox(kIterationNum): Int);
                        if (kSampleNumInMillion.$isInstanceOf[Float]())
                          {
                            <synthetic> val x5: Float = (scala.Float.unbox(kSampleNumInMillion): Float);
                            matchEnd7(new Tuple3(scala.Int.box(x3), scala.Int.box(x4), scala.Float.box(x5)))
                          }
                        else
                          case9()
                      }
                    else
                      case9()
                  }
                else
                  case9()
              }
            }
          else
            case9()
        };
        case9(){
          matchEnd7(throw new MatchError(x1))
        };
        matchEnd7(x: Tuple3){
          x
        }
      };
      var kDimension: runtime.IntRef = new runtime.IntRef(scala.Int.unbox(x$2._1()));
      var kIterationNum: Int = scala.Int.unbox(x$2._2());
      var kSampleNumInMillion: Float = scala.Float.unbox(x$2._3());
      var kSampleNum: runtime.IntRef = new runtime.IntRef(kSampleNumInMillion.toFloat().*(1000000).toInt());
      scala.this.Predef.println("** dimension    : ".+(scala.Int.box(kDimension.elem)));
      scala.this.Predef.println("** iteration num: ".+(scala.Int.box(kIterationNum)));
      scala.this.Predef.println("** sample num   : ".+(scala.Int.box(kSampleNum.elem)));
      var samples: runtime.ObjectRef = new runtime.ObjectRef(scala.Array.tabulate(0, {
  (new anonymous class anonfun$6(): Function1)
}, ClassTag.apply(classOf[Gradient$Sample])).$asInstanceOf[Array[Gradient$Sample]]());
      scala.this.Predef.println("\nSamples generation: ");
      Gradient.this.time({
        (new anonymous class anonfun$main$1(kDimension, kSampleNum, samples): Function0)
      });
      var rand: runtime.ObjectRef = new runtime.ObjectRef(new java.util.Random(42L));
      var w: runtime.ObjectRef = new runtime.ObjectRef(scala.Array.tabulate(kDimension.elem, {
  (new anonymous class anonfun$2(rand): Function1)
}, ClassTag.Double()).$asInstanceOf[Array[Double]]());
      var g: Array[Double] = scala.Array.tabulate(kDimension.elem, {
  (new anonymous class anonfun$3(rand): Function1)
}, ClassTag.Double()).$asInstanceOf[Array[Double]]();
      var elapsed_list: runtime.ObjectRef = new runtime.ObjectRef(scala.Array.tabulate(0, {
  (new anonymous class anonfun$4(): Function1)
}, ClassTag.Double()).$asInstanceOf[Array[Double]]());
      RichInt.this.to$extension0(scala.this.Predef.intWrapper(1), kIterationNum).foreach$mVc$sp({
        (new anonymous class anonfun$main$2(samples, w, elapsed_list): Function1)
      });
      val average: Double = scala.Double.unbox(scala.this.Predef.doubleArrayOps(elapsed_list.elem.$asInstanceOf[Array[Double]]()).reduce({
  (new anonymous class anonfun$5(): Function2)
}))./(elapsed_list.elem.$asInstanceOf[Array[Double]]().length());
      scala.this.Predef.println("Average for ".+(scala.Int.box(elapsed_list.elem.$asInstanceOf[Array[Double]]().length())).+(" iterations: ").+(scala.Double.box(average)).+("(ms)"))
    };
    def <init>(): Gradient.type = {
      Gradient.super.<init>();
      ()
    }
  };
  case class Gradient$Sample extends Object with Product with Serializable {
    <caseaccessor> <paramaccessor> private[this] val vec: Array[Double] = _;
    <stable> <caseaccessor> <accessor> <paramaccessor> def vec(): Array[Double] = Gradient$Sample.this.vec;
    <caseaccessor> <paramaccessor> private[this] val label: Double = _;
    <stable> <caseaccessor> <accessor> <paramaccessor> def label(): Double = Gradient$Sample.this.label;
    <synthetic> def copy(vec: Array[Double], label: Double): Gradient$Sample = new Gradient$Sample(vec, label);
    <synthetic> def copy$default$1(): Array[Double] = Gradient$Sample.this.vec();
    <synthetic> def copy$default$2(): Double = Gradient$Sample.this.label();
    override <synthetic> def productPrefix(): String = "Sample";
    <synthetic> def productArity(): Int = 2;
    <synthetic> def productElement(x$1: Int): Object = {
      case <synthetic> val x1: Int = x$1;
      (x1: Int) match {
        case 0 => Gradient$Sample.this.vec()
        case 1 => scala.Double.box(Gradient$Sample.this.label())
        case _ => throw new IndexOutOfBoundsException(scala.Int.box(x$1).toString())
      }
    };
    override <synthetic> def productIterator(): Iterator = runtime.this.ScalaRunTime.typedProductIterator(Gradient$Sample.this);
    <synthetic> def canEqual(x$1: Object): Boolean = x$1.$isInstanceOf[Gradient$Sample]();
    override <synthetic> def hashCode(): Int = {
      <synthetic> var acc: Int = -889275714;
      acc = Statics.this.mix(acc, Statics.this.anyHash(Gradient$Sample.this.vec()));
      acc = Statics.this.mix(acc, Statics.this.doubleHash(Gradient$Sample.this.label()));
      Statics.this.finalizeHash(acc, 2)
    };
    override <synthetic> def toString(): String = ScalaRunTime.this._toString(Gradient$Sample.this);
    override <synthetic> def equals(x$1: Object): Boolean = Gradient$Sample.this.eq(x$1).||({
  case <synthetic> val x1: Object = x$1;
  case5(){
    if (x1.$isInstanceOf[Gradient$Sample]())
      matchEnd4(true)
    else
      case6()
  };
  case6(){
    matchEnd4(false)
  };
  matchEnd4(x: Boolean){
    x
  }
}.&&({
      <synthetic> val Sample$1: Gradient$Sample = x$1.$asInstanceOf[Gradient$Sample]();
      Gradient$Sample.this.vec().==(Sample$1.vec()).&&(Gradient$Sample.this.label().==(Sample$1.label())).&&(Sample$1.canEqual(Gradient$Sample.this))
    }));
    def <init>(vec: Array[Double], label: Double): Gradient$Sample = {
      Gradient$Sample.this.vec = vec;
      Gradient$Sample.this.label = label;
      Gradient$Sample.super.<init>();
      scala.Product$class./*Product$class*/$init$(Gradient$Sample.this);
      ()
    }
  };
  <synthetic> object Gradient$Sample extends runtime.AbstractFunction2 with Serializable {
    final override <synthetic> def toString(): String = "Sample";
    case <synthetic> def apply(vec: Array[Double], label: Double): Gradient$Sample = new Gradient$Sample(vec, label);
    case <synthetic> def unapply(x$0: Gradient$Sample): Option = if (x$0.==(null))
      scala.this.None
    else
      new Some(new Tuple2(x$0.vec(), scala.Double.box(x$0.label())));
    <synthetic> private def readResolve(): Object = Gradient$Sample;
    case <synthetic> <bridge> def apply(v1: Object, v2: Object): Object = Gradient$Sample.this.apply(v1.$asInstanceOf[Array[Double]](), scala.Double.unbox(v2));
    def <init>(): Gradient$Sample.type = {
      Gradient$Sample.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$vector_add$1 extends runtime.AbstractFunction1 with Serializable {
    final def apply(x0$1: Tuple2): Double = {
      case <synthetic> val x1: Tuple2 = x0$1;
      case4(){
        if (x1.ne(null))
          {
            val a: Double = x1._1$mcD$sp();
            val b: Double = x1._2$mcD$sp();
            matchEnd3(a.+(b))
          }
        else
          case5()
      };
      case5(){
        matchEnd3(throw new MatchError(x1))
      };
      matchEnd3(x: Double){
        x
      }
    };
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$vector_add$1.this.apply(v1.$asInstanceOf[Tuple2]()));
    def <init>(): anonymous class anonfun$vector_add$1 = {
      Gradient$$anonfun$vector_add$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$vector_sub$1 extends runtime.AbstractFunction1 with Serializable {
    final def apply(x0$2: Tuple2): Double = {
      case <synthetic> val x1: Tuple2 = x0$2;
      case4(){
        if (x1.ne(null))
          {
            val a: Double = x1._1$mcD$sp();
            val b: Double = x1._2$mcD$sp();
            matchEnd3(a.-(b))
          }
        else
          case5()
      };
      case5(){
        matchEnd3(throw new MatchError(x1))
      };
      matchEnd3(x: Double){
        x
      }
    };
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$vector_sub$1.this.apply(v1.$asInstanceOf[Tuple2]()));
    def <init>(): anonymous class anonfun$vector_sub$1 = {
      Gradient$$anonfun$vector_sub$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$vector_dot$1 extends runtime.AbstractFunction1 with Serializable {
    final def apply(x0$3: Tuple2): Double = {
      case <synthetic> val x1: Tuple2 = x0$3;
      case4(){
        if (x1.ne(null))
          {
            val a: Double = x1._1$mcD$sp();
            val b: Double = x1._2$mcD$sp();
            matchEnd3(a.*(b))
          }
        else
          case5()
      };
      case5(){
        matchEnd3(throw new MatchError(x1))
      };
      matchEnd3(x: Double){
        x
      }
    };
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$vector_dot$1.this.apply(v1.$asInstanceOf[Tuple2]()));
    def <init>(): anonymous class anonfun$vector_dot$1 = {
      Gradient$$anonfun$vector_dot$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$vector_mul$1 extends runtime.AbstractFunction1$mcDD$sp with Serializable {
    final def apply(x$1: Double): Double = Gradient$$anonfun$vector_mul$1.this.apply$mcDD$sp(x$1);
    <specialized> def apply$mcDD$sp(x$1: Double): Double = Gradient$$anonfun$vector_mul$1.this.x$3.*(x$1);
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$vector_mul$1.this.apply(scala.Double.unbox(v1)));
    <synthetic> <paramaccessor> private[this] val x$3: Double = _;
    def <init>(x$3: Double): anonymous class anonfun$vector_mul$1 = {
      Gradient$$anonfun$vector_mul$1.this.x$3 = x$3;
      Gradient$$anonfun$vector_mul$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$6$$anonfun$apply$1 extends runtime.AbstractFunction1$mcDI$sp with Serializable {
    final def apply(x: Int): Double = Gradient$$anonfun$6$$anonfun$apply$1.this.apply$mcDI$sp(x);
    <specialized> def apply$mcDI$sp(x: Int): Double = 0.0;
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$6$$anonfun$apply$1.this.apply(scala.Int.unbox(v1)));
    def <init>($outer: anonymous class anonfun$6): anonymous class anonfun$apply$1 = {
      Gradient$$anonfun$6$$anonfun$apply$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$6 extends runtime.AbstractFunction1 with Serializable {
    final def apply(x: Int): Gradient$Sample = new Gradient$Sample(scala.Array.tabulate(0, {
  (new anonymous class anonfun$apply$1(Gradient$$anonfun$6.this): Function1)
}, ClassTag.Double()).$asInstanceOf[Array[Double]](), 0.0);
    final <bridge> def apply(v1: Object): Object = Gradient$$anonfun$6.this.apply(scala.Int.unbox(v1));
    def <init>(): anonymous class anonfun$6 = {
      Gradient$$anonfun$6.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$1 extends runtime.AbstractFunction1$mcII$sp with Serializable {
    final def apply(x: Int): Int = Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$1.this.apply$mcII$sp(x);
    <specialized> def apply$mcII$sp(x: Int): Int = x;
    final <bridge> def apply(v1: Object): Object = scala.Int.box(Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$1.this.apply(scala.Int.unbox(v1)));
    def <init>($outer: anonymous class anonfun$main$1): anonymous class anonfun$apply$mcV$sp$1 = {
      Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1 extends runtime.AbstractFunction1$mcDI$sp with Serializable {
    final def apply(y: Int): Double = Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.this.apply$mcDI$sp(y);
    <specialized> def apply$mcDI$sp(y: Int): Double = Gradient.this.prf(Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.this.x$4.*(Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.this.$outer.Gradient$$anonfun$$anonfun$$$outer().kDimension$1.elem).+(y).toLong());
    <synthetic> <paramaccessor> private[this] val $outer: anonymous class anonfun$apply$mcV$sp$2 = _;
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.this.apply(scala.Int.unbox(v1)));
    <synthetic> <paramaccessor> private[this] val x$4: Int = _;
    def <init>($outer: anonymous class anonfun$apply$mcV$sp$2, x$4: Int): anonymous class anonfun$1 = {
      if ($outer.eq(null))
        throw new NullPointerException()
      else
        Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.this.$outer = $outer;
      Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.this.x$4 = x$4;
      Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2$$anonfun$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2 extends runtime.AbstractFunction1 with Serializable {
    final def apply(x: Int): Gradient$Sample = {
      val label: Int = if (x.%(2).==(0))
        -1
      else
        1;
      val vec: Array[Double] = scala.Array.tabulate(Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2.this.$outer.kDimension$1.elem, {
  (new anonymous class anonfun$1(Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2.this, x): Function1)
}, ClassTag.Double()).$asInstanceOf[Array[Double]]();
      new Gradient$Sample(vec, label.toDouble())
    };
    <synthetic> <paramaccessor> private[this] val $outer: anonymous class anonfun$main$1 = _;
    <synthetic> <stable> def Gradient$$anonfun$$anonfun$$$outer(): anonymous class anonfun$main$1 = Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2.this.$outer;
    final <bridge> def apply(v1: Object): Object = Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2.this.apply(scala.Int.unbox(v1));
    def <init>($outer: anonymous class anonfun$main$1): anonymous class anonfun$apply$mcV$sp$2 = {
      if ($outer.eq(null))
        throw new NullPointerException()
      else
        Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2.this.$outer = $outer;
      Gradient$$anonfun$main$1$$anonfun$apply$mcV$sp$2.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$1 extends runtime.AbstractFunction0$mcV$sp with Serializable {
    final def apply(): Unit = Gradient$$anonfun$main$1.this.apply$mcV$sp();
    <specialized> def apply$mcV$sp(): Unit = Gradient$$anonfun$main$1.this.samples$1.elem = scala.this.Predef.intArrayOps(scala.Array.tabulate(Gradient$$anonfun$main$1.this.kSampleNum$1.elem, {
  (new anonymous class anonfun$apply$mcV$sp$1(Gradient$$anonfun$main$1.this): Function1)
}, ClassTag.Int()).$asInstanceOf[Array[Int]]()).map({
  (new anonymous class anonfun$apply$mcV$sp$2(Gradient$$anonfun$main$1.this): Function1)
}, scala.this.Array.canBuildFrom(ClassTag.apply(classOf[Gradient$Sample]))).$asInstanceOf[Array[Gradient$Sample]]();
    final <bridge> def apply(): Object = {
      Gradient$$anonfun$main$1.this.apply();
      scala.runtime.BoxedUnit.UNIT
    };
    <synthetic> <paramaccessor> val kDimension$1: runtime.IntRef = _;
    <synthetic> <paramaccessor> private[this] val kSampleNum$1: runtime.IntRef = _;
    <synthetic> <paramaccessor> private[this] val samples$1: runtime.ObjectRef = _;
    def <init>(kDimension$1: runtime.IntRef, kSampleNum$1: runtime.IntRef, samples$1: runtime.ObjectRef): anonymous class anonfun$main$1 = {
      Gradient$$anonfun$main$1.this.kDimension$1 = kDimension$1;
      Gradient$$anonfun$main$1.this.kSampleNum$1 = kSampleNum$1;
      Gradient$$anonfun$main$1.this.samples$1 = samples$1;
      Gradient$$anonfun$main$1.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$2 extends runtime.AbstractFunction1$mcDI$sp with Serializable {
    final def apply(i: Int): Double = Gradient$$anonfun$2.this.apply$mcDI$sp(i);
    <specialized> def apply$mcDI$sp(i: Int): Double = Gradient$$anonfun$2.this.rand$1.elem.$asInstanceOf[java.util.Random]().nextDouble();
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$2.this.apply(scala.Int.unbox(v1)));
    <synthetic> <paramaccessor> private[this] val rand$1: runtime.ObjectRef = _;
    def <init>(rand$1: runtime.ObjectRef): anonymous class anonfun$2 = {
      Gradient$$anonfun$2.this.rand$1 = rand$1;
      Gradient$$anonfun$2.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$3 extends runtime.AbstractFunction1$mcDI$sp with Serializable {
    final def apply(i: Int): Double = Gradient$$anonfun$3.this.apply$mcDI$sp(i);
    <specialized> def apply$mcDI$sp(i: Int): Double = Gradient$$anonfun$3.this.rand$1.elem.$asInstanceOf[java.util.Random]().nextDouble();
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$3.this.apply(scala.Int.unbox(v1)));
    <synthetic> <paramaccessor> private[this] val rand$1: runtime.ObjectRef = _;
    def <init>(rand$1: runtime.ObjectRef): anonymous class anonfun$3 = {
      Gradient$$anonfun$3.this.rand$1 = rand$1;
      Gradient$$anonfun$3.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$4 extends runtime.AbstractFunction1$mcDI$sp with Serializable {
    final def apply(x: Int): Double = Gradient$$anonfun$4.this.apply$mcDI$sp(x);
    <specialized> def apply$mcDI$sp(x: Int): Double = 0.0;
    final <bridge> def apply(v1: Object): Object = scala.Double.box(Gradient$$anonfun$4.this.apply(scala.Int.unbox(v1)));
    def <init>(): anonymous class anonfun$4 = {
      Gradient$$anonfun$4.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$2$$anonfun$7 extends runtime.AbstractFunction1 with Serializable {
    final def apply(p: Gradient$Sample): Array[Double] = {
      val factor: Double = 1./(1.+(scala.math.`package`.exp(p.label().*(Gradient.this.vector_dot(Gradient$$anonfun$main$2$$anonfun$7.this.$outer.w$1.elem.$asInstanceOf[Array[Double]](), p.vec()))))).-(1).*(p.label());
      Gradient.this.vector_mul(factor, p.vec())
    };
    <synthetic> <paramaccessor> private[this] val $outer: anonymous class anonfun$main$2 = _;
    final <bridge> def apply(v1: Object): Object = Gradient$$anonfun$main$2$$anonfun$7.this.apply(v1.$asInstanceOf[Gradient$Sample]());
    def <init>($outer: anonymous class anonfun$main$2): anonymous class anonfun$7 = {
      if ($outer.eq(null))
        throw new NullPointerException()
      else
        Gradient$$anonfun$main$2$$anonfun$7.this.$outer = $outer;
      Gradient$$anonfun$main$2$$anonfun$7.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$2$$anonfun$8 extends runtime.AbstractFunction2 with Serializable {
    final def apply(x: Array[Double], y: Array[Double]): Array[Double] = Gradient.this.vector_add(x, y);
    final <bridge> def apply(v1: Object, v2: Object): Object = Gradient$$anonfun$main$2$$anonfun$8.this.apply(v1.$asInstanceOf[Array[Double]](), v2.$asInstanceOf[Array[Double]]());
    def <init>($outer: anonymous class anonfun$main$2): anonymous class anonfun$8 = {
      Gradient$$anonfun$main$2$$anonfun$8.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$main$2 extends runtime.AbstractFunction1$mcVI$sp with Serializable {
    final def apply(i: Int): Unit = Gradient$$anonfun$main$2.this.apply$mcVI$sp(i);
    <specialized> def apply$mcVI$sp(i: Int): Unit = {
      val t0: Long = java.this.lang.System.nanoTime();
      val gradient: Array[Double] = scala.this.Predef.refArrayOps(scala.this.Predef.refArrayOps(Gradient$$anonfun$main$2.this.samples$1.elem.$asInstanceOf[Array[Gradient$Sample]]().$asInstanceOf[Array[Object]]()).map({
  (new anonymous class anonfun$7(Gradient$$anonfun$main$2.this): Function1)
}, scala.this.Array.canBuildFrom(ClassTag.apply(ScalaRunTime.this.arrayClass(java.lang.Double.TYPE)))).$asInstanceOf[Array[Object]]()).reduce({
  {
    (new anonymous class anonfun$8(Gradient$$anonfun$main$2.this): Function2)
  }
}).$asInstanceOf[Array[Double]]();
      Gradient$$anonfun$main$2.this.w$1.elem = Gradient.this.vector_add(Gradient$$anonfun$main$2.this.w$1.elem.$asInstanceOf[Array[Double]](), gradient);
      val t1: Long = java.this.lang.System.nanoTime();
      val elapsed: Double = t1.-(t0)./(1000000.0);
      scala.this.Predef.println("Iteration ".+(scala.Int.box(i)).+(" elapsed time: ").+(scala.Double.box(elapsed)).+("(ms)"));
      Gradient$$anonfun$main$2.this.elapsed_list$1.elem = scala.this.Predef.doubleArrayOps(Gradient$$anonfun$main$2.this.elapsed_list$1.elem.$asInstanceOf[Array[Double]]()).:+(scala.Double.box(elapsed), scala.this.Array.canBuildFrom(ClassTag.Double())).$asInstanceOf[Array[Double]]()
    };
    final <bridge> def apply(v1: Object): Object = {
      Gradient$$anonfun$main$2.this.apply(scala.Int.unbox(v1));
      scala.runtime.BoxedUnit.UNIT
    };
    <synthetic> <paramaccessor> private[this] val samples$1: runtime.ObjectRef = _;
    <synthetic> <paramaccessor> val w$1: runtime.ObjectRef = _;
    <synthetic> <paramaccessor> private[this] val elapsed_list$1: runtime.ObjectRef = _;
    def <init>(samples$1: runtime.ObjectRef, w$1: runtime.ObjectRef, elapsed_list$1: runtime.ObjectRef): anonymous class anonfun$main$2 = {
      Gradient$$anonfun$main$2.this.samples$1 = samples$1;
      Gradient$$anonfun$main$2.this.w$1 = w$1;
      Gradient$$anonfun$main$2.this.elapsed_list$1 = elapsed_list$1;
      Gradient$$anonfun$main$2.super.<init>();
      ()
    }
  };
  @SerialVersionUID(0) final <synthetic> class Gradient$$anonfun$5 extends runtime.AbstractFunction2$mcDDD$sp with Serializable {
    final def apply(x: Double, y: Double): Double = Gradient$$anonfun$5.this.apply$mcDDD$sp(x, y);
    <specialized> def apply$mcDDD$sp(x: Double, y: Double): Double = x.+(y);
    final <bridge> def apply(v1: Object, v2: Object): Object = scala.Double.box(Gradient$$anonfun$5.this.apply(scala.Double.unbox(v1), scala.Double.unbox(v2)));
    def <init>(): anonymous class anonfun$5 = {
      Gradient$$anonfun$5.super.<init>();
      ()
    }
  }
}

