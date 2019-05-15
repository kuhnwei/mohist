#!/bin/bash
###启动

moduleName="spring-boot-package"
pidPath="$moduleName-tpid"

rm -f $pidPath

nohup java -jar ./$moduleName.jar -server -Xms1024m -Xmx2048m -Xss256k > ./run.log 2>&1 &

echo $! > $pidPath