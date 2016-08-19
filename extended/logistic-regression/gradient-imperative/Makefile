
SPARK_ROOT = ../../..
SOURCE = Gradient.scala
CLASS = Gradient
COMPILED_DIR=compiled/

default: build


build: $(SOURCE)
	mkdir -p $(COMPILED_DIR)
	# or use your own scalac. -omidm
	$(SPARK_ROOT)/build/scala-2.10.5/bin/scalac -d $(COMPILED_DIR) Gradient.scala

run: build
  # remove default java options scala sets (-Xmx256M -Xms32M) and then run scala
	# or use your own scala. -omidm
	export JAVA_OPTS=" "; $(SPARK_ROOT)/build/scala-2.10.5/bin/scala -bootclasspath $(COMPILED_DIR) $(CLASS) 10 50 0.34 

run-par: build
	./run-parallel.sh

run-java: build
	# use java directly to run the bytecode
	java -classpath $(SPARK_ROOT)/build/scala-2.10.5/lib/scala-library.jar:$(COMPILED_DIR) $(CLASS) 10 50 0.34 

run-alljit: build
  # set the jit compilation threashold and then run scala
	export JAVA_OPTS="-XX:CompileThreshold=1"; $(SPARK_ROOT)/build/scala-2.10.5/bin/scala -bootclasspath $(COMPILED_DIR) $(CLASS) 10 50 0.34 

run-nojit: build
  # set the jit compilation threashold and then run scala
	export JAVA_OPTS="-XX:CompileThreshold=0"; $(SPARK_ROOT)/build/scala-2.10.5/bin/scala -bootclasspath $(COMPILED_DIR) $(CLASS) 10 50 0.34 

clean:
	rm -rf *.class $(COMPILED_DIR)/*.class

