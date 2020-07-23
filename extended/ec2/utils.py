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
SPARK_ROOT                 = '~/cloud/src/spark-1.6/'
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



def start_experiment(main_dns, main_p_dns, subordinate_dnss):
  assert(config.SLAVE_NUM <= len(subordinate_dnss))

  start_main(main_dns);

  for idx in range(0, config.SLAVE_NUM):
    dns = subordinate_dnss[idx]
    start_subordinate(main_p_dns, dns, idx + 1);

  time.sleep(10)
  submit_application(main_dns, main_p_dns);


def start_main(main_dns):
  main_command  = 'cd ' + SPARK_ROOT + ';'
  main_command += 'cp conf/log4j.properties.template conf/log4j.properties;'
  if (not config.ACTIVATE_SPARK_INFO_LOGING):
    main_command += 'sed -i \'s/log4j.rootCategory=INFO/log4j.rootCategory=WARN/g\' conf/log4j.properties;' 
  main_command += './sbin/start-main.sh'
  main_command += ' &> ' + STD_OUT_LOG

  print '** Starting main: ' + main_dns
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + main_dns, main_command])



def start_subordinate(main_p_dns, subordinate_dns, num):
  subordinate_command =  'cd ' + SPARK_ROOT + ';'
  subordinate_command += 'cp conf/log4j.properties.template conf/log4j.properties;'
  if (not config.ACTIVATE_SPARK_INFO_LOGING):
    subordinate_command += 'sed -i \'s/log4j.rootCategory=INFO/log4j.rootCategory=WARN/g\' conf/log4j.properties;' 
  subordinate_command += './sbin/start-subordinate.sh '
  subordinate_command += 'spark://' + main_p_dns +':7077 '
  subordinate_command += ' -c ' + str(config.SLAVE_CORE_NUM)
  subordinate_command += ' &> ' + str(num) + '_' + STD_OUT_LOG

  print '** Starting subordinate: ' + str(num)
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + subordinate_dns, subordinate_command])


def submit_application(main_dns, main_p_dns):
  main_command  = 'cd ' + SPARK_ROOT + ';'
  main_command += 'mkdir -p ' + REL_EVENT_LOG_DIR + ';'
  main_command += './bin/spark-submit '
  main_command += ' --class ' + APP_MAIN_CLASS 
  main_command += ' --main spark://' + main_p_dns +':7077'
  main_command += ' --deploy-mode client '
  main_command += ' --executor-memory ' + config.EXECUTOR_MEMORY
  if (not config.DEACTIVATE_EVENT_LOGING):
    main_command += ' --conf spark.eventLog.enabled=true '
    main_command += ' --conf spark.eventLog.dir=' + REL_EVENT_LOG_DIR
  main_command += ' ' + REL_APP_JAR
  main_command += ' ' + APP_OPTIONS
  main_command += ' &>> ' + STD_OUT_LOG

  print '** Submitting application **'
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + main_dns, main_command])


def stop_experiment(main_dns, subordinate_dnss):

  stop_main(main_dns);

  num = 0
  for dns in subordinate_dnss:
    num += 1
    stop_subordinate(dns, num);


def stop_main(main_dns):
  main_command  = 'cd ' + SPARK_ROOT + ';'
  main_command += './sbin/stop-main.sh'

  print '** Stopping main: ' + main_dns
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + main_dns, main_command])


def stop_subordinate(subordinate_dns, num):
  subordinate_command  = 'cd ' + SPARK_ROOT + ';'
  subordinate_command += './sbin/stop-subordinate.sh'

  print '** Stopping subordinate: ' + str(num)
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + subordinate_dns, subordinate_command])


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


def collect_logs(main_dns, subordinate_dnss):

  subprocess.call(['rm', '-rf', OUTPUT_PATH])
  subprocess.call(['mkdir', '-p', OUTPUT_PATH])

  subprocess.Popen(['scp', '-q', '-r', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + main_dns + ':' + SPARK_ROOT + STD_OUT_LOG,
      OUTPUT_PATH])

  subprocess.Popen(['scp', '-q', '-r', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + main_dns + ':' + SPARK_ROOT + REL_EVENT_LOG_DIR,
      OUTPUT_PATH])

  for dns in subordinate_dnss:
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


def clean_logs(main_dns, subordinate_dnss):
  command  =  'rm -rf ' + SPARK_ROOT + '*' + STD_OUT_LOG + ';'
  command +=  'rm -rf ' + SPARK_ROOT + REL_EVENT_LOG_DIR + ';'
  command +=  'rm -rf ' + SPARK_ROOT + 'work/;'
  command +=  'rm -rf ' + SPARK_ROOT + 'logs/;'

  print '** Cleaning main: ' + main_dns
  subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
      '-o', 'UserKnownHostsFile=/dev/null',
      '-o', 'StrictHostKeyChecking=no',
      'ubuntu@' + main_dns, command])


  for dns in subordinate_dnss:
    print '** Cleaning subordinate: ' + dns
    subprocess.Popen(['ssh', '-q', '-i', config.PRIVATE_KEY,
        '-o', 'UserKnownHostsFile=/dev/null',
        '-o', 'StrictHostKeyChecking=no',
        'ubuntu@' + dns, command])
  


