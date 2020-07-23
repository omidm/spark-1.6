#!/usr/bin/env python

# Author: Omid Mashayekhi <omidm@stanford.edu>

import sys
import os
import time
import subprocess
import argparse

import ec2
import utils
import config


parser = argparse.ArgumentParser(description='Spark EC2 Manager.')
parser.add_argument(
    "-l", "--launch",
    dest="launch",
    action="store_true",
    help="launch ec2 instances")
parser.add_argument(
    "-t", "--terminate",
    dest="terminate",
    action="store_true",
    help="terminate ec2 instances")
parser.add_argument(
    "-m", "--monitor",
    dest="monitor",
    action="store_true",
    help="monitor ec2 instances")
parser.add_argument(
    "-s", "--start",
    dest="start",
    action="store_true",
    help="start the experiment")
parser.add_argument(
    "-e", "--end",
    dest="end",
    action="store_true",
    help="end the experiment")
parser.add_argument(
    "-d", "--download",
    dest="download",
    action="store_true",
    help="download the logs")
parser.add_argument(
    "-c", "--clean",
    dest="clean",
    action="store_true",
    help="clean the logs")
parser.add_argument(
    "-p", "--print",
    dest="printdns",
    action="store_true",
    help="print controller and subordinates dns")
parser.add_argument(
    "-w", "--wake_up",
    dest="wakeup",
    action="store_true",
    help="ssh test in to nodes for testing")
parser.add_argument(
    "-o", "--only_submit",
    dest="onlysubmit",
    action="store_true",
    help="only submit the application to main, main and workers are already up")

parser.add_argument(
    "-mdns", "--main_dns",
    dest="maindns",
    default="X-X-X-X",
    help="main dns")
parser.add_argument(
    "-mpdns", "--main_private_dns",
    dest="mainprivatedns",
    default="X-X-X-X",
    help="main private dns")
parser.add_argument(
    "-pdns", "--use_private",
    dest="useprivate",
    action="store_true",
    help="if specified will use the private dns for inter node communications")

args = parser.parse_args()


if (args.monitor):
  ec2.wait_for_instances_to_start(
      config.EC2_LOCATION,
      config.MASTER_NUM + config.SLAVE_NUM,
      placement_group=config.PLACEMENT_GROUP);

elif (args.launch):
  ans = raw_input("Are you sure you want to launch {} ec2 instances? (Enter 'yes' to proceed): ".format(config.MASTER_NUM + config.SLAVE_NUM))
  if (ans != 'yes'):
    print "Aborted"
    exit(0)

  print "Launching the instances ..."
  ec2.run_instances(
      config.EC2_LOCATION,
      config.SPARK_AMI,
      config.SLAVE_NUM,
      config.KEY_NAME,
      config.SECURITY_GROUP,
      config.PLACEMENT,
      config.PLACEMENT_GROUP,
      config.SLAVE_INSTANCE_TYPE);
  ec2.run_instances(
      config.EC2_LOCATION,
      config.SPARK_AMI,
      config.MASTER_NUM,
      config.KEY_NAME,
      config.SECURITY_GROUP,
      config.PLACEMENT,
      config.PLACEMENT_GROUP,
      config.MASTER_INSTANCE_TYPE);

elif (args.terminate):
  ans = raw_input("Are you sure you want to terminate all ec2 instances? (Enter 'yes' to proceed): ")
  if (ans != 'yes'):
    print "Aborted"
    exit(0)

  print "Terminating the instances ..."
  ec2.terminate_instances(
      config.EC2_LOCATION,
      placement_group=config.PLACEMENT_GROUP);

elif (args.start or args.end or args.download or args.clean or args.printdns or args.wakeup or args.onlysubmit):

  dns_names = ec2.get_dns_names(
      config.EC2_LOCATION,
      placement_group=config.PLACEMENT_GROUP);

  if (not args.maindns == "X-X-X-X"):
    main_dns   = args.maindns
    main_p_dns = args.mainprivatedns
  else:
    mdnss = ec2.get_dns_names(
        config.EC2_LOCATION,
        placement_group=config.PLACEMENT_GROUP,
        instance_type=config.MASTER_INSTANCE_TYPE);
    main_dns   = mdnss["public"][0]
    main_p_dns = mdnss["private"][0]
  
  subordinate_dnss = list(dns_names["public"])
  subordinate_dnss.remove(main_dns)
  
  if (not args.useprivate):
    main_p_dns = main_dns
    subordinate_p_dnss = list(subordinate_dnss)
  else:
    assert(not main_p_dns == "X-X-X-X") 
    subordinate_p_dnss = list(dns_names["private"])
    subordinate_p_dnss.remove(main_p_dns)

  if (args.printdns):
    print "Main DNS:         " + main_dns
    print "Main Private DNS: " + main_p_dns
    print "Subordinate DNSs:           " + str(subordinate_dnss)
    print "Subordinate Private DNSs:   " + str(subordinate_p_dnss)
  
  if (args.wakeup):
    utils.test_nodes(subordinate_dnss + [main_dns])
   
  if (args.start):
    utils.start_experiment(main_dns, main_p_dns, subordinate_dnss)
  
  if(args.download):
    utils.collect_logs(main_dns, subordinate_dnss)
  
  if (args.end):
    utils.stop_experiment(main_dns, subordinate_dnss)

  if (args.clean):
    utils.clean_logs(main_dns, subordinate_dnss)
  
  if (args.onlysubmit):
    utils.submit_application(main_dns, main_p_dns)
  
else :
  print "\n** Provide an action to perform!\n"
  print parser.print_help();


