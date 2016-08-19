
SPARK_ROOT = ../../..
SOURCE = Gradient.java
CLASS = Gradient
COMPILED_DIR=compiled/

default: build


build: $(SOURCE)
	mkdir -p $(COMPILED_DIR)
	javac -d $(COMPILED_DIR) $(SOURCE)

run: build
	java -ea -classpath $(COMPILED_DIR) $(CLASS) 10 50 0.34

run-par: build
	./run-parallel.sh

run-nojit:
	java -ea -classpath $(COMPILED_DIR) -XX:CompileThreshold=0 $(CLASS) 10 50 0.34

run-alljit: build
	java -ea -classpath $(COMPILED_DIR) -XX:CompileThreshold=1 $(CLASS) 10 50 0.34 

clean:
	rm -rf *.class $(COMPILED_DIR)/*.class

