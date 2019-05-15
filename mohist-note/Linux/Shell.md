# Shell

`#!` 是一个约定的标记，告诉系统这个脚本需要什么解释器来执行。

`echo`命令用于向窗口输出文本

`chmod +x ./xxx.sh`使脚本具有执行权限 

`/bin/sh xxx.sh` 作为解释器参数直接运行脚本内容，这样就不需在脚本文件第一行进行`#!`标记了

```shell
#!/bin/bash
echo "What is your name?"
read PERSON # read 命令从stdin获取输入并赋值给变量
echo "Hello, $PERSON"
```

## 变量

```shell
#!/bin/bash

variableName="value"

echo $variableName
echo ${variableName}

readonly variable_name # readonly 命令声明 只读 变量
unset variable_name # unset 命令可以删除变量
```

使用`set`命令显示所有本地定义的Shell变量

### 变量类型

运行shell时，会同事存在三种变脸：

1. 局部变量：局部变脸在脚本或命令中定义，仅在当前shell实例中有效，其它shell启动的程序不能访问局部变脸。
2. 环境变量：所有的程序，包括shell启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候shell脚本也可以定义环境变量。
3. shell特殊变量：shell变量是由shell程序设置的特殊变量。shell变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了shell的正常运行。

#### Shell特殊变量

| 名称 | 含义                                                         |
| :--- | :----------------------------------------------------------- |
| $0   | 当前脚本的文件名                                             |
| $#   | 传递给脚本或函数的参数个数                                   |
| $*   | 传递给脚本或函数的所有参数                                   |
| $@   | 传递给脚本或函数的所有参数。被双引号(" ")包含时，与$*稍有不同。 |
| $?   | 上个命令的退出状态，或函数的返回值。大部分命令执行成功返回 0，失败返回 1 |
| $$   | 当前进程的ID，对于shell脚本，就是这些脚本所在的进程ID        |
| $n   | 传递给脚本或函数的参数，n 是一个数字，表示第几个参数。如 $1, $2 分别表示第一个和第二个的参数 |

范例：

```shell
#!/bin/bash
echo "文件名：$0"
echo "第一个参数 : $1"
echo "第二个参数 : $2"
echo "所有参数: $@"
echo "所有参数: $*"
echo "参数个数: $#"
```

通过执行脚本命令： `./xxx.sh Hello World`

执行结果：

```
文件名： ./xxx.sh
第一个参数：Hello
第二个参数：World
所有参数：Hello World
所有参数：Hello World
参数个数：2
```

`$*` 和` $@` 都表示传递给函数或脚本的所有参数，不被双引号(" ")包含时，都以"$1" "$2" … "$n" 的形式输出所有参数。但是当它们被双引号(" ")包含时，"$*" 会将所有的参数作为一个整体，以"$1 $2 … $n"的形式输出所有参数；"$@" 会将各个参数分开，以"$1" "$2" … "$n" 的形式输出所有参数。

## 字符串

```shell
str1='abcdefg'
str2="ABCDEFG\$str1\!\n"
```

### Shell字符串的操作

| 表达式                           | 含义                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| ${#string}                       | $string的长度                                                |
| ${string:position}               | 在$string中，从位置$position开始提取子串                     |
| ${string:position:length}        | 在$string中，从位置$position开始提取长度为$length的子串      |
| ${string#substring}              | 从变量$string的开头，删除最短匹配$substring的子串            |
| ${string##substring}             | 从变量$string的开头，删除最长匹配$substring的子串            |
| ${string%substring}              | 从变量$string的结尾, 删除最短匹配$substring的子串            |
| ${string%%substring}             | 从变量$string的结尾, 删除最长匹配$substring的子串            |
| ${string/substring/replacement}  | 使用$replacement, 来代替第一个匹配的$substring               |
| ${string//substring/replacement} | 使用$replacement, 代替所有匹配的$substring                   |
| ${string/#substring/replacement} | 如果$string的前缀匹配$substring, 那么就用$replacement来代替匹配到的$substring |
| ${string/%substring/replacement} | 如果$string的后缀匹配$substring, 那么就用$replacement来代替匹配到的$substring |

## 数组

### Shell数组的定义

在Shell中，用括号来表示数组，数组元素之间用“空格”分割开。

定义素组的一般形式为：`array_name=(value1 ... valuen)`

```shell
array_name=(value0 value1 value2 value3)

array_name=(
value0
value1
value2
value3
)

array_name[0]=value0
array_name[1]=value1
array_name[2]=value2
```

### Shell数组的读取

```
${array_name[index]}

# 如
value=${array_name[2]}

# 使用 @ 或 * 可以获取数组中的所有元素
${array_name[*]}
${array_name[@]}
```

### Shell素组删除

通过 `unset` 数组[下标]可以清楚相应的元素，不带下标，清除整个数据。

### Shell数组的其他常用操作

1. Shell数组长度
   用`${#array_name[@或*]}`可以得到数组的长度

   ```shell
   # 取得数组元素的格式
   length=${#array_name[@]}
   # 或
   length=${#array_name[*]}
   # 取得数组单个元素的长度
   lengthn=${#array_name[n]}
   ```

2. Shell数组的分片
   用`${array_name[@或*]:起始位置:长度}`切片原先数组，返回是字符串，中间用“空格”分开，因此如果加上"()"，将得到切片数组

   ```shell
   a=(1 2 3 4 5)
   echo ${a[@]:0:3}
   >>1 2 3
   echo ${a[@]1:4}
   >>2 3 4 5
   c=(${a[@]:1:4})
   echo ${#c[@]}
   >>4
   echo ${c[*]}
   >>2 3 4 5
   ```

3. Shell数组的替换
   数组的替换方法是：`${array_name[@或*]/查找字符/替换字符}`该操作不会改变原先数组内容。

   ```shell
   a=(1 2 3 4 5)
   echo ${a[@]/3/100}
   >>1 2 100 4 5
   ```

## 输出

### echo命令

echo命令时Shell的一个内部指令，用于在屏幕上打印出指定的字符串

1. 命令格式：`echo arg`

2. 转义字符：`echo "\"It is a test\""`

3. 输出变量：

   ```shell
   name="shell"
   echo "${name} It is a test"
   ```

4. 输出换行：`echo "OK!\n"`

5. 输出重定向：shell可以使用右尖括号（">"）和两个右尖括号（">>"）来表示输出的重定向

   ```shell
   # 将字符串重定向入myfile这个文件中，myfile中原有内容会被清除
   echo "It is a test" > myfile
   # 将字符串重定向入myfile这个文件中，myfile中原有内容不会被清除，新内容会追加到文件结尾处
   echo "It is a test" >> myfile
   ```

   

6. 保持原样输出：echo命令使用单引号" ' "可以保持原样输出，不会对内容进行处理

7. 输出命令的执行结果：echo后加用 ```  ` ```号括起来的命令可以输出命令执行结果。```  ` ```这个符号是英文半角状态下键盘tab键上方和波浪线在一起的那个按键，将命令包含在```  ` ```符号中可以执行该命令，可以使用这一点来进行很多复查的操作。

   ```shell
   echo `date`
   ```

### printf命令

`printf`命令用于格式化输出，是`echo`命令的增强版。它与C语言的`printf`非常相似，但是语法上有些不同。由于`printf`命令时由`POSIX`标准所定义，所以移植性比`echo`好。

1. printf命令语法

   ```shell
   printf format-string [arguments...]
   ```

   `format-string`为格式控制字符串，`arguments`为参数列表。需要注意的一点是，`printf`不会自动换行，也就是说使用`printf`命令必须显示的使用`\n`

   shell `printf`命令与C语言`printf`命令的区别：

   - `printf`命令不用加括号。
   - `format-string`可以没有引号，但最好加上，单引号双引号均可。
   - 参数多于格式控制符(%)时，format-string可以重用，可以将所有参数都转换。
   - `arguments`使用空格分隔，不用逗号。

   ```shell
   # format-string为双引号
   $ printf "%d %s\n" 1 "abc"
   >>1 abc
   # 没有引号也可以输出
   $ printf %s abcdef
   >>abcdef
   # 格式只指定了一个参数，但多出的参数仍然会安装该格式输出，format-string被重用
   $ printf %s abc def
   >>abcdef
   $ printf "%s\n" abc def
   >>abc
   >>def
   $ printf "$s $s $s\n" a b c d e f g h i j
   >>a b c
   >>d e f
   >>g h i
   >>j
   # 如果没有arguments，那么 %s 用 NULL 代替，%d 用 0 代替
   ```

## printf命令详解

### Shell printf命令转义序列

| 序列 |                                                              |
| ---- | ------------------------------------------------------------ |
| \a   | 警告字符，通常为ASCII的BEL字符                               |
| \b   | 后退                                                         |
| \c   | 不显示输出结果中任何结尾的换行字符，而且任何留在参数里的字符、任何接下来的参数以及任何留在格式字符串中的字符都被忽略。 |
| \f   | 换页                                                         |
| \n   | 换行                                                         |
| \r   | 回车                                                         |
| \t   | 水平制表符                                                   |
| \v   | 垂直制表符                                                   |
| \\   | 反斜杠字符                                                   |

### Shell printf命令格式指示符

| 符号  | 说明                                     |
| ----- | ---------------------------------------- |
| %c    | ASCII字符，显示相对应参数的第一个字符    |
| %d,%i | 十进制整数                               |
| %E    | 浮点格式([-d].precisionE [+-dd])         |
| %e    | 浮点格式([-d].precisione [+-dd])         |
| %g    | %e或%f转换，看哪一个较短，则删除结尾的零 |
| %G    | %E或%f转换，看哪一个较短，则删除结尾的零 |
| %s    | 字符串                                   |
| %u    | 不带正负号的十进制                       |
| %x    | 不带正负号的十六进制，使用a至f表示10至15 |
| %X    | 不带正负号的十六进制，使用A至F表示10至15 |
| %%    | 字面意义的%                              |

### Shell printf命令精度格式指示符

| 符号              | 含义                                                         |
| ----------------- | ------------------------------------------------------------ |
| %d,%i,%o,%u,%x,%X | 要打印的最小位数，当值的位数少于此数字时，会在前面补领，默认精度为1 |
| %e,%E             | 要打印的最小尾数，当值的位数少于此数字时，会在小数点后面补领，默认为精度6，精度为0则表示不显示小数点右边的位数 |
| %f                | 小数点右边的位数                                             |
| %g,%G             | 有效位数的最大数目                                           |
| %s                | 要打印字符的最大数目                                         |

```shell
printf "%.5d\n" 15
>>00015
printf "%.10s\n" "a very long string"
>>a very lon
printf "%.2f\n" 123.4567
>>123.46
```

### Shell printf命令一些标识符

| 字符 | 说明                                                         |
| ---- | ------------------------------------------------------------ |
| -    | 将字段里已格式化的值向左对齐                                 |
| 空格 | 在正值前置一个空格，在负值前置一个负号                       |
| +    | 总是在数值之前放置一个正号或负号，即便是正值也是             |
| #    | 下列形式选择其一：%o有一个前置的o；%x与%X分别前置的0x与0X；%e,%E与%f总是在结果中有一个小数点；%g与%G为没有结尾的零。 |
| 0    | 以零填补输出，而非空白。这仅发生在字段宽度大于转换后的情况   |

## 判断 if ... else 用法

Shell有三种if else格式：

1. if ... fi 
2. if ... else ... fi
3. if ... elif ... else ... fi

### shell判断语法之if ... else 格式

```shell
if [ expression ]
then
	Statements(s) to be executed if expression is true
fi
```

最后必须以`fi`来结尾闭合`if`，`fi`就是`if`倒过来拼写。expression和方括号([])之间必须有空格。否则会有语法错误。

### shell判断语法之if ... else ... fi 格式

```shell
if [ expression ]
then
	Statement(s) to be exeuted if expression is true
else
	Statement(s) to be executed if expression is not true
fi
```

### shell判断语法之if ... elif ... fi格式

```shell
if [ expression 1 ]
then
	Statement(s) to be executed if expression 1 is true
elif [ expression 2 ]
then
	Statement(s) to be executed if expression 2 is true
elif [ expression 3 ]
then
 	Statement(s) to be executed if expression 3 is true
else
	Statement(s) to be executed if no expression is true
fi
```

## for循环

```shell
for 变量 in 列表
do
	command...
	...
done
```

范例：显示当前目录下的文件

```shell
for file in ./*
do
  echo $file
done
```

## while循环

```shell
while command
do
  Statement(s) to be executed if command is true
done
```

范例：

```shell
#!/bin/bash
COUNTER=0
while [ $COUNTER -lt 5 ]
do
  COUNTER=`expr $COUNTER + 1`
  echo $COUNTER
done
```

```shell
#!/bin/bash
declare -i i=1
declare -i sum=0
while ((i<=10))
do
  let sum+=i
  let ++i
done
echo $sum
```

## until循环

```shell
until command
do
  Statement(s) to be executed until command is true
done
```

until循环和while循环差不多，区别在于while的条件测试是测真值，until循环则是测假值。也就是说，在while循环中，如果条件测试结果为真(传回值为0)，就进入循环；在until循环中。如果条件测试结果为真（传回值为0），就跳出循环，如果测试结果为假（传回值不为0），则继续循环。

## 分支语句case...esac教程

```shell
case 值 in
模式1)
  command1
  command2
  command3
  ;;
模式1)
  command1
  command2
  command3
  ;;
*)
  command1
  command2
  command3
  ;;
esac
```

说明：case后为取值，值后为关键之in，接下来是匹配的各种模式，每一模式最后必须以右括号结束。

值可以为变量或常数。

模式支持正则表达式，可以用以下字符：

```shell
*	任意字串
?	任意字元
[abc] a,b,或c三字元其中之一
[a-n] 从a到n的任意字元
|	多重选择
```

匹配发现取值符合某一模式后，期间所有命令开始执行直至`;;`;

`;;`与其他语言中的`break`类似，意思是不执行接下来的语句而是跳到整个`case`语句的最后。

`*)`与`default`相似，如果上面没有匹配到的模式则执行`*)`里的内容。

范例：

```shell
#!/bin/bash

case $1 in
start | begin)
  echo "I am started!"
  ;;
stop | end)
  echo "I am stopped!"
  ;;
*)
  echo "Other command!"
  ;;
esac
```

## Select语句

```shell
select name [in list]
do
  statements that can use $name...
done
```

说明：select首先会产生list列表中的菜单选项，然后执行下方do...done之间的语句。用户选择的菜单项会保存在$name变量中。

另外：select命令使用`PS3`提示符，默认为(#?)；

在select使用中，可以他陪`PS3=‘string’` 来设置提示字符串。

范例：select配合case...esac语句一起使用

```shell
#!/bin/bash
PS3="Please choose your number:"
echo
select number in "one" "two" "three" "four" "five"
do
case $number in
one )
echo Hello one!
;;
two )
echo Hello two!
;;
*)
echo
echo "Your choose is $number."
echo
;;
esac
break
done
exit 0
```

## Shell函数简介

### Shell函数的语法

因为函数时脚本类语言，在执行时是逐行执行的，因此，shell函数必须先定义后使用。

```shell
[ function ] funname [()]
{
    command;
    [return int;]
}
```

说明：`function`关键词是可选项，可加可不加。

大括号内是函数体，最后是返回值，可以加`return`关键词来指定函数返回内容，如果不加，将以最后一条命令运行结果，作为返回值。return后跟数值n(0-255)。

### Shell函数参数处理

在函数体内部，通过$n的形式来获取参数的值，例如，$1表示第一个参数，$2表示第二参数，$0代表脚本本身。

```shell
#!/bin/sh
function fSum()
{
	echo "入参为："$1,$2
	return $(($1+$2))
}
fSum 5 7
total=$(fSum 3 2) # 赋值
echo "Return :"$total,$?
```

## Shell输入输出重定向

Linux的文件描述符可以理解为Linux跟踪打开文件，而分配的一个数字，这个数字有点类似c语言操作文件时候的句柄，通过句柄就可以实现文件的读写操作。

用户可以自定义文件描述符范围是：3-max，max跟用户的ulimit -n 定义数字没有关系，不能超过最大值。

Linux启动后，会默认打开3个文件描述符，分别是：

- 标准输入standard input —— 0
- 标准输出standard output —— 1
- 错误输出：error output —— 2

对于所有运行的Shell命令，都会有默认3个文件描述符。

在一个Shell命令执行时，会先有一个输入：可以从键盘输入，也可以从文件得到在命令执行完成后：成功了，会把成功结果输出到屏幕，正确输出默认是平那个吗。命令执行有错误：会把错误也输出到屏幕，错误输出默认也是指的屏幕。隐藏Shell输入输出重定向就是将上面讲述的默认的输入输出等重定向到别的位置。

### Shell输出重定向

Shell输出重定向主要用向右的尖括号来作为符号>, 主要有">"和">>"两种方式。

```shell
command-line1 [1-n] > file或文件操作符或设备
command-line1 [1-n] >> file或文件操作符或设备
```

当使用">"时，系统会判断右边文件是否存在，如果存在就先删除，并且创新文件。不存在则直接创建。因此无论左边命令执行是否成功，右边文件都会变为空。

使用">>"操作符时，系统会判断右边文件是否存在，如果不存在，先创建。然后以添加方式打开文件，系统会分配一个文件描述符与左边的标准输出【1】或错误输出【2】绑定。

当命令执行完以后，这条命令绑定文件的描述符也自动失效。0,1,2又会空闲。

当一条命令执行时，命令的输入，正确输出，错误输出，默认会分别绑定0,1,2文件描述符。

一条命令在执行前，先回检查输出是否正确，如果输出设备错误，将不会进行命令执行。

| 命令格式                   | 命令说明                                   |
| -------------------------- | ------------------------------------------ |
| Command > filename         | 把标准输出重定向到一个文件中               |
| Command > filename 2 > &1  | 把标准输出和错误一起冲顶到一个文件中       |
| Command 2 > filename       | 把标准错误冲顶到一个文件中                 |
| Command 2 >> filename      | 把标准输出重定向到一个文件中（追加）       |
| Command >> filename 2 > &1 | 把标准输出和错误一起重定向到一个文件(追加) |

### Shell输入重定向

Shell输入重定向主要用向左的尖括号（小于号）"<"表示

```shell
command-line [n] < file或文件描述符&设备
```

命令默认从键盘获得的输入，冲顶后改为从文件，或者其它打开文件以及设备输入。

这样，本来需要从键盘获取输入的命令会转移到文件读取内容。

## Shell文件包含

使用点好`.` + 文件名包含

```shell
.filename
```

source + 文件名

```shell
source filename
```

