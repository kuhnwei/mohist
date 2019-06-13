# SPRINGBOOT-PACKAGE

`springboot-package`项目是举例如何使用`maven`插件打包项目时，原先默认是把第三方`jar`打包到程序自身的`jar`包里，照成`jar`文件占的内存过大，不方便拷贝到远程服务器中。而通过`maven-jar-plugin`插件可以配置，把`jar`文件里的`lib`目录抽离出来与项目`jar`文件同级。

1. 在`build`目录下添加`assembly.xml`文件；

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <assembly xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.3"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.3 http://maven.apache.org/xsd/assembly-1.1.3.xsd">
       <id>package</id>
       <formats>
           <format>zip</format>
       </formats>
       <includeBaseDirectory>true</includeBaseDirectory>
       <fileSets>
           <fileSet>
               <directory>src\bin</directory>
               <outputDirectory>\</outputDirectory>
           </fileSet>
           <fileSet>
               <directory>src\main\resources</directory>
               <outputDirectory>\</outputDirectory>
           </fileSet>
           <fileSet>
               <directory>src\main\resources\config</directory>
               <outputDirectory>\sql</outputDirectory>
           </fileSet>
           <fileSet>
               <directory>src\main\resources\config</directory>
               <outputDirectory>\config</outputDirectory>
           </fileSet>
           <fileSet>
               <directory>src\main\resources\static</directory>
               <outputDirectory>\static</outputDirectory>
           </fileSet>
           <fileSet>
               <directory>src\main\resources\templates</directory>
               <outputDirectory>\templates</outputDirectory>
           </fileSet>
           <fileSet>
               <directory>${project.build.directory}</directory>
               <outputDirectory>\</outputDirectory>
               <includes>
                   <include>*.jar</include>
               </includes>
           </fileSet>
       </fileSets>
       <dependencySets>
           <dependencySet>
               <outputDirectory>lib</outputDirectory>
               <!-- 将scope为runtime的依赖包打包到lib目录下。 -->
               <scope>runtime</scope>
               <excludes>
                   <exclude>${groupId}:${artifactId}</exclude>
               </excludes>
           </dependencySet>
       </dependencySets>
   </assembly>
   ```

2. 在`pom.xml`配置文件设置插件依赖

   ```xml
   <build>
       <finalName>mohist</finalName>
       <plugins>
           <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-jar-plugin</artifactId>
               <configuration>
                   <archive>
                       <manifest>
                           <mainClass>mohist.examples.spring.springboot.MohistApplication</mainClass>
                           <addClasspath>true</addClasspath>
                           <classpathPrefix>lib</classpathPrefix>
                       </manifest>
                       <manifestEntries>
                           <Class-Path>./</Class-Path>
                       </manifestEntries>
                   </archive>
                   <excludes>
                       <exclude>config/**</exclude>
                       <exclude>static/**</exclude>
                       <exclude>templates/**</exclude>
                       <exclude>sql/**</exclude>
                       <exclude>banner.txt</exclude>
                   </excludes>
               </configuration>
           </plugin>
           <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-assembly-plugin</artifactId>
               <version>2.6</version>
               <configuration>
                   <descriptor>src/build/assembly.xml</descriptor>
                   <recompressZippedFiles>false</recompressZippedFiles>
               </configuration>
               <executions>
                   <execution>
                       <phase>package</phase>
                       <goals>
                           <goal>single</goal>
                       </goals>
                   </execution>
               </executions>
           </plugin>
       </plugins>
   </build>
   ```

3. 在项目路径下通过命令打包项目：

   ```bash
   mvn package
   ```

4. 创建`bin`目录，编写运行脚本

   * `run.bat` 运行于`Windows`环境的脚本

     ```bash
     @echo off
     start java -jar mohist.jar
     ```

   * `start.sh` 运行于`Linux`环境的脚本

     ```shell
     #!/bin/bash
     ###启动
     moduleName="mohist"
     pidPath="$moduleName-pid"
     rm -f $pidPath
     nohup java -jar ./$moduleName.jar > ./run.log 2>&1 &
     echo $! > $pidPath
     ```

   * `stop.sh` 运行于`Linux`环境的脚本

     ```shell
     #!/bin/bash
     ###停止
     moduleName="mohist"
     pid=`cat $moduleName-pid | awk '{print $1}'`
     pid=`ps -aef | grep $pid | awk '{print $2}' |grep $pid`
     if [ ${pid} ]; then
     	kill -9 $pid
     fi
     ```

5. 打包成功后，在项目下的`target`目录下生成了`zip`文件
   `mohist-package.zip`

6. 解压`mohist-package.zip`，得到如下目录结构的

   ```
   mohist
    ├── config
    |    └── application.yml
    ├── lib
    ├── mohist.jar
    ├── run.bat
    ├── start.sh
    └── stop.sh
   ```

