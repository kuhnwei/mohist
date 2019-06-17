#!/bin/bash
###启动

moduleName="mohist"
pidPath="$moduleName-pid"

rm -f $pidPath

nohup java -jar ./$moduleName.jar > ./run.log 2>&1 &

echo $! > $pidPath
