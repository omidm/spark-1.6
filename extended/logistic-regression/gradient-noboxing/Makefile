
SPARK_ROOT = ../../..
SOURCE = Gradient.java
CLASS = Gradient
COMPILED_DIR=compiled/

default: build


build: $(SOURCE)
	mkdir -p $(COMPILED_DIR)
	javac -classpath ../../../build/scala-2.10.5/lib/scala-library.jar:. -d $(COMPILED_DIR) $(SOURCE)

run: build
	java -ea -classpath ../../../build/scala-2.10.5/lib/scala-library.jar:$(COMPILED_DIR) $(CLASS) 10 50 0.34 

run-par: build
	./run-parallel.sh

clean:
	rm -rf *.class $(COMPILED_DIR)/*.class

