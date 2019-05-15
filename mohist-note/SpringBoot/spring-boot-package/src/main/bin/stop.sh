#!/bin/bash
###停止

moduleName="spring-boot-package"

tpid=`cat $moduleName-tpid | awk '{print $1}'`
tpid=`ps -aef | grep $tpid | awk '{print $2}' |grep $tpid`
if [ ${tpid} ]; then
kill -9 $tpid
fi