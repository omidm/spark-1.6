
SPARK_ROOT = ../..
SOURCE = src/main/scala/KMeans.scala
CLASS = KMeans
HOST = $(shell hostname)

default: build


build: $(SOURCE)
	sbt package

run: build stop start
	$(SPARK_ROOT)/bin/spark-submit \
                  --class $(CLASS) \
                  --master spark://$(HOST):7077 \
                  --deploy-mode client \
                  --executor-memory 4g \
                  target/scala-2.10/kmeans_2.10-1.0.jar \
                  10 2 15 8 2.72 0 s

run-events: build stop start
	mkdir -p $(SPARK_ROOT)/log-dir
	$(SPARK_ROOT)/bin/spark-submit \
                  --conf spark.eventLog.enabled=true \
                  --conf spark.eventLog.dir=$(SPARK_ROOT)/log-dir \
                  --class $(CLASS) \
                  --master spark://$(HOST):7077 \
                  --deploy-mode client \
                  --executor-memory 4g \
                  target/scala-2.10/kmeans_2.10-1.0.jar \
                  10 2 15 8 2.72 0 s


run-multiple: build stop start
	mkdir -p $(SPARK_ROOT)/log-dir
	$(SPARK_ROOT)/bin/spark-submit \
                  --conf spark.eventLog.enabled=true \
                  --conf spark.eventLog.dir=$(SPARK_ROOT)/log-dir \
                  --class $(CLASS) \
                  --master spark://$(HOST):7077 \
                  --deploy-mode client \
                  --executor-memory 4g \
                  target/scala-2.10/kmeans_2.10-1.0.jar \
                  10 2 15 8 2.72 0 m

start:
	$(SPARK_ROOT)/sbin/start-master.sh
	$(SPARK_ROOT)/sbin/start-slave.sh spark://$(HOST):7077

stop:
	$(SPARK_ROOT)/sbin/stop-slave.sh
	$(SPARK_ROOT)/sbin/stop-master.sh

clean:
	sbt clean

clean-all:
	sbt clean
	cd $(SPARK_ROOT); rm -rf work/* log-dir/* logs/*;
