# Angular常用命令

## Angular CLI命令行工具

安装Angular CLI

```
npm install -g @angular/cli
```

使用CLI命令创建一个名叫my-app的新项目

```
ng new my-app
```

进入项目目录，并启动这个应用

```
cd my-app
ng serve --open
```

ng serve命令会构建本应用、启动开服服务器、监听源文件，并且当那些文件发生变化时重新构建本应用。

--open表示会打开浏览器，并访问http://localhost:4200/。

使用Angular CLI创建一个名为heroes的新组建。

```
ng generate component heroes
```

generate 可简写为 g ， 如创建heroes的新组建可以写命令

```
ng g component heroes
```

使用Angular CLI创建一个名为hero的服务

```
ng generate service hero
```

使用Angular CLI创建类

```
ng generate class hero
```

使用Angular CLI创建模块

```
ng generate module app-routing --flat --module=app
--flat 把这个文件放进了src/app中，而不是单独的目录中
--module=app告诉CLI把它注册到AppModule的imports数组中。
```

从npm中安装内存Web API爆

```
npm install angular-in-memory-web-api --save
```



## 部署

```
ng build
```

把输出目录（默认为dist/）下的每个文件都复制到服务器上的某个目录下。

如果要把文件部署到服务器上的某个子路径下，构建时还要添加`--base-href`（基地址）标识，并设置合适的`<base href>`。

比如，如果`index.html`位于服务器上的`/angular-forms/index.html`路径下，就要把base href设置为`<base href="/angular-forms/">`，就像这样：

```
ng build --base-href=/angular-forms/
```

为生产环境优化的构建

```
ng build --prod
```

同理，如果需要设置base href的话就需要添加 `--base-href`

```
ng build --prod --base-href=/angular-forms/
```

还可以添加`build-optimizer`标志来进一步缩减打包体积

```
ng build --prod --build-optimizer
```

## JSON-SERVER

```
npm install -g json-server
```

```
json-server ./src/app/.../data.json
```

## HTTP-SERVER

```
npm install -g http-server
```

```
# 在相关目录键入
http-server .
```

