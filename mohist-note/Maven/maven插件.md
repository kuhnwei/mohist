

##  yuicompressor-maven-plugin 压缩js或css文件

```xml
<plugin>
    <groupId>net.alchim31.maven</groupId>
    <artifactId>yuicompressor-maven-plugin</artifactId>
    <version>1.3.0</version>
    <executions>
        <execution>
            <goals>
                <goal>compress</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <!-- 读取js,css文件采用UTF-8编码 -->
        <encoding>UTF-8</encoding>
        <!-- 不显示js可能的错误 -->
        <jswarn>false</jswarn>
        <!-- 若存在已压缩的文件，会先对比源文件是否有改动。有改动便压缩，无改动就不压缩 -->
        <force>false</force>
        <!-- 在指定的列号后插入新行 -->
        <linebreakpos>-1</linebreakpos>
        <!-- 压缩之前先执行聚合文件操作 -->
        <preProcessAggregates>true</preProcessAggregates>
        <!-- 压缩后保存文件后缀 -->
        <suffix>.min</suffix>
        <!-- 源目录，即需压缩的根目录 -->
        <sourceDirectory>src/main/resources/static</sourceDirectory>
        <includes>
            <include>js/**/*.js</include>
            <include>js/*.js</include>
        </includes>
        <excludes>
            <exclude>js/**/**min.js</exclude>
        </excludes>
        <!-- 压缩后输出文件目录 -->
        <outputDirectory>src/main/resources/static</outputDirectory>
        <!-- 聚合文件 -->
        <aggregations>
            <aggregation>
                <!-- 合并每一个文件后插入一新行 -->
                <insertNewLine>true</insertNewLine>
                <!-- 需合并文件的根文件夹 -->
                <inputDir>src/main/resources/static/js</inputDir>
                <!-- 最终合并的输出文件 -->
                <output>src/main/resources/static/app.js</output>
                <!-- 把以下js文件合并成一个js文件，是按顺序合并的 -->
                <includes>
                    <include>libs/modernizr.custom.js</include>
                    <include>libs/TweenMax.min.js</include>
                </includes>
            </aggregation>
        </aggregations>
    </configuration>
</plugin>
```

##  replacer 替换资源文件的引用

```html
<script type="text/javascript" src="app/js/jquery-1.9.1.min.js"></script>
<!-- mergeTo:app.pack.js -->
<script type="text/javascript" src="app/js/app1.js"></script>
<script type="text/javascript" src="app/js/app2.js"></script>
<!-- mergeTo -->
```
```xml
<plugin>
    <groupId>com.google.code.maven-replacer-plugin</groupId>
    <artifactId>replacer</artifactId>
    <version>1.5.2</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>replace</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <file>${basedir}/src/main/resources/templates/index.html</file>
        <replacements>
            <replacement>
                <token>
                    <![CDATA[
        <!-- mergeTo:([^\s]*) -->(.*?)<!-- mergeTo -->
        ]]>
                </token>
                <value>
                    <![CDATA[
        <script type="text/javascript" src="/static/$1" th:src="@{/$1}" ></script>
        ]]>
                </value>
            </replacement>
        </replacements>
        <regexFlags>
            <regexFlag>CASE_INSENSITIVE</regexFlag>
            <regexFlag>DOTALL</regexFlag>
        </regexFlags>
    </configuration>
</plugin>
```