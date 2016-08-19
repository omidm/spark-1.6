#!/usr/bin/env python

# Author: Omid Mashayekhi <omidm@stanford.edu>

import sys
import os
import subprocess
import time

import config
import ec2

# Logging configurations
STD_OUT_LOG                = 'ec2_log.txt'
OUTPUT_PATH                = 'output/'

# Path configuration
SPARK_ROOT                 = '~/cloud/src/spark/'
REL_EVENT_LOG_DIR          = 'log-dir/'


# Forming the worker arguments based on options

# lr
LR_MAIN_CLASS   = 'LogisticRegression'
LR_REL_APP_JAR  = 'extended/logistic-regression/target/scala-2.10/logistic-regression_2.10-1.0.jar'
LR_APP_OPTIONS  = ' ' 
LR_APP_OPTIONS += ' ' + str(config.DIMENSION)
LR_APP_OPTIONS += ' ' + str(config.ITERATION_NUM)
LR_APP_OPTIONS += ' ' + str(config.PARTITION_NUM)
LR_APP_OPTIONS += ' ' + str(config.SAMPLE_NUM_M)
LR_APP_OPTIONS += ' ' + str(config.SPIN_WAIT_US)

# k-means
KM_MAIN_CLASS   = 'KMeans'
KM_REL_APP_JAR  = 'extended/k-means/target/scala-2.10/kmeans_2.10-1.0.jar'
KM_APP_OPTIONS  = ' ' 
KM_APP_OPTIONS += ' ' + str(config.DIMENSION)
KM_APP_OPTIONS += ' ' + str(config.CLUSTER_NUM)
KM_APP_OPTIONS += ' ' + str(config.ITERATION_NUM)
KM_APP_OPTIONS += ' ' + str(config.PARTITION_NUM)
KM_APP_OPTIONS += ' ' + str(config.SAMPLE_NUM_M)
KM_APP_OPTIONS += ' ' + str(config.SPIN_WAIT_US)
if (config.KM_TWO_STAGE):
  KM_APP_OPTIONS += ' m'
else:
  KM_APP_OPTIONS += ' s'

# stencil-1d
SD_MAIN_CLASS   = 'Stencil1D'
SD_REL_APP_JAR  = 'extended/stencil-1d/target/scala-2.10/stencil-1d_2.10-1.0.jar'
SD_APP_OPTIONS  = ' ' 
SD_APP_OPTIONS += ' ' + str(config.ITERATION_NUM)
SD_APP_OPTIONS += ' ' + str(config.PARTITION_NUM)
SD_APP_OPTIONS += ' ' + str(config.PARTITION_SIZE)
SD_APP_OPTIONS += ' ' + str(config.SPIN_WAIT_US)


if (config.APPLICATION == 'lr'):
  APP_MAIN_CLASS = LR_MAIN_CLASS
  REL_APP_JAR     = LR_REL_APP_JAR
  APP_OPTIONS     = LR_APP_OPTIONS
elif (config.APPLICATION == 'k-means'):
  APP_MAIN_CLASS = KM_MAIN_CLASS
  REL_APP_JAR     = KM_REL_APP_JAR
  APP_OPTIONS     = KM_APP_OPTIONS
elif (config.APPLICATION == 'stencil-1d'):
  APP_MAIN_CLASS = SD_MAIN_CLASS
  REL_APP_JAR     = SD_REL_APP_JAR
  APP_OPTIONS     = SD_APP_OPTIONS
else:
  print "ERROR: Unknown application: " + config.APPLICATION
  exit(0)



def start_experiment(master_dns, master_p_dns, slave_dnss):
  assert(config.SLAVE_NUM <= len(slave_dnss))

  start_master(master_dns);

  for idx in range(0, config.SLAVE_NUM):
    dns = slave_dnss[idx]
    start_slave(master_p_dns, dns, idx + 1);

  time.sleep(10)
  submit_application(master_dns, master_p_dns);


def start_master(master_dns):
  master_command  = 'cd ' + SPARK_ROOT + ';'
  master_command += 'cp conf/log4j.properties.template conf/log4j.properties;'
  if (not config.ACTIVATE_SPARK_INFO_LOGING):
    master_command += 'sed -i \'s/log4j.rootCategory=INFO/log4j.rootCategory=WARN/g\' conf/log4j.properties;' 
  master_command += './sbin/start-master.sh'
  master_command += ' &> ' + STD_OUT_LOG

  print '** Starting master: ' + master_dns
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + master_dns, master_command])



def start_slave(master_p_dns, slave_dns, num):
  slave_command =  'cd ' + SPARK_ROOT + ';'
  slave_command += 'cp conf/log4j.properties.template conf/log4j.properties;'
  if (not config.ACTIVATE_SPARK_INFO_LOGING):
    slave_command += 'sed -i \'s/log4j.rootCategory=INFO/log4j.rootCategory=WARN/g\' conf/log4j.properties;' 
  slave_command += './sbin/start-slave.sh '
  slave_command += 'spark://' + master_p_dns +':7077 '
  slave_command += ' -c ' + str(config.SLAVE_CORE_NUM)
  slave_command += ' &> ' + str(num) + '_' + STD_OUT_LOG

  print '** Starting slave: ' + str(num)
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + slave_dns, slave_command])


def submit_application(master_dns, master_p_dns):
  master_command  = 'cd ' + SPARK_ROOT + ';'
  master_command += 'mkdir -p ' + REL_EVENT_LOG_DIR + ';'
  master_command += './bin/spark-submit '
  master_command += ' --class ' + APP_MAIN_CLASS 
  master_command += ' --master spark://' + master_p_dns +':7077'
  master_command += ' --deploy-mode client '
  master_command += ' --executor-memory ' + config.EXECUTOR_MEMORY
  if (not config.DEACTIVATE_EVENT_LOGING):
    master_command += ' --conf spark.eventLog.enabled=true '
    master_command += ' --conf spark.eventLog.dir=' + REL_EVENT_LOG_DIR
  master_command += ' ' + REL_APP_JAR
  master_command += ' ' + APP_OPTIONS
  master_command += ' &>> ' + STD_OUT_LOG

  print '** Submitting application **'
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + master_dns, master_command])


def stop_experiment(master_dns, slave_dnss):

  stop_master(master_dns);

  num = 0
  for dns in slave_dnss:
    num += 1
    stop_slave(dns, num);


def stop_master(master_dns):
  master_command  = 'cd ' + SPARK_ROOT + ';'
  master_command += './sbin/stop-master.sh'

  print '** Stopping master: ' + master_dns
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + master_dns, master_command])


def stop_slave(slave_dns, num):
  slave_command  = 'cd ' + SPARK_ROOT + ';'
  slave_command += './sbin/stop-slave.sh'

  print '** Stopping slave: ' + str(num)
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + slave_dns, slave_command])


def test_nodes(node_dnss):
  command  = 'cd ' + SPARK_ROOT + ';'
  command += 'pwd;'

  num = 0
  for dns in node_dnss:
    num += 1
    print '** Testing node: ' + str(num) + ' ip:' + dns
    subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
        '-o', 'UserKnownHostsFile=/dev/null',
        '-o', 'StrictHostKeyChecking=no',
        'ubuntu@' + dns, command])


def collect_logs(master_dns, slave_dnss):

  subprocess.call(['rm', '-rf', OUTPUT_PATH])
  subprocess.call(['mkdir', '-p', OUTPUT_PATH])

  subprocess.Popen(['scp', '-q', '-r', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + master_dns + ':' + SPARK_ROOT + STD_OUT_LOG,
      OUTPUT_PATH])

  subprocess.Popen(['scp', '-q', '-r', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + master_dns + ':' + SPARK_ROOT + REL_EVENT_LOG_DIR,
      OUTPUT_PATH])

  for dns in slave_dnss:
    subprocess.Popen(['scp', '-q', '-r', '-i', config.PRIVATE_KEY,
        '-o', 'UserKnownHostsFile=/dev/null',
        '-o', 'StrictHostKeyChecking=no',
        'ubuntu@' + dns + ':' + SPARK_ROOT + '*_' + STD_OUT_LOG,
        OUTPUT_PATH])

    subprocess.Popen(['scp', '-q', '-r', '-i', config.PRIVATE_KEY,
        '-o', 'UserKnownHostsFile=/dev/null',
        '-o', 'StrictHostKeyChecking=no',
        'ubuntu@' + dns + ':' + SPARK_ROOT + 'work/',
        OUTPUT_PATH])


def clean_logs(master_dns, slave_dnss):
  command  =  'rm -rf ' + SPARK_ROOT + '*' + STD_OUT_LOG + ';'
  command +=  'rm -rf ' + SPARK_ROOT + REL_EVENT_LOG_DIR + ';'
  command +=  'rm -rf ' + SPARK_ROOT + 'work/;'
  command +=  'rm -rf ' + SPARK_ROOT + 'logs/;'

  print '** Cleaning master: ' + master_dns
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + master_dns, command])


  for dns in slave_dnss:
    print '** Cleaning slave: ' + dns
    subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
        '-o', 'UserKnownHostsFile=/dev/null',
        '-o', 'StrictHostKeyChecking=no',
        'ubuntu@' + dns, command])
  


