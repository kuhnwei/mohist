#!/bin/bash
###停止

moduleName="mohist"

pid=`cat $moduleName-pid | awk '{print $1}'`
pid=`ps -aef | grep $pid | awk '{print $2}' |grep $pid`
if [ ${pid} ]; then
kill -9 $pid
fi
