# webpack4入门

## 前提

- 已安装node（版本号>4.0，已自带NPM）

## 要实现的功能

- JSX语法，ES6语法，LESS语法编写的模块转化为浏览器可执行的模块
- 代码的混淆压缩
- 多页面多入口
- 提取公共文件

## 无webpack.config.js配置打包

1. 快速构建```package.json```文件。

   ```
   # 创建项目目录
   mkdir webpack-demo
   # 进入项目目录下
   cd webpack-demo
   # 初始化项目
   npm init -y
   ```

2. 安装webpack4及其命令行接口

   ```
   npm install webpack webpack-cli --save-dev
   ```

3. ```package.json```文件增加build参数

   ```json
   "scripts": {
       "build": "webpack"
   }
   ```

4. 创建```./src/index.js```文件

   增加内容

   ```js
   console.log("这时入口文件");
   ```

5. 终端执行```npm run build```

   目录下多了一个```./dist/main.js```文件。

   这个文件是```webpack```对```./src/index.js```的打包结果。

## production 和 development 模式

1. 修改```package.json```文件的```scripts```字段

   ```json
   "scripts": {
       "dev": "webpack --mode development",
       "build: "webpack --mode production"
   }
   ```

2. 分别执行```npm run dev```和```npm run build```

   你会看到```./dist/main.js```不同的变化。

   ```production```模式下，默认对打包的进行minification(文件压缩)，Tree Shaking(只导入有用代码)，scope hoisting(作用域提升)等等操作。

   总之是让打包文件更小。

   ```development```模式下，对打包文件不压缩，同事打包速度更快。

如果没指定任何模式，默认是```production```模式。

## ES6和React

1. 安装对应依赖包

   ```
   npm i babel-core babel-loader babel-preset-env react react-dom babel-preset-react --save-dev
   ```

   **注意：**如果有版本不兼容的问题，可在```package.json```找到对应的包，并修改其可兼容的版本号，然后重新```npm install```安装。

2. 新建```.babelrc```文件，进行相关配置

   ```json
   {
       "presets": ["env", "react"]
   }
   ```

3. 新建```webpack.config.js```文件，进行相关配置

   ```js
   module.exports = {
       module: {
           rules: [{
               test: /.\js$/,
               exclude: /node_modules/,
               use: {
                   loader: "babel-loader"
               }
           }]
       }
   };
   ```

4. 新增```./src/app.js```以及修改```./src/index.js```

   ```./src/app.js```内容如下：

   ```jsx
   import React from "react";
   import ReactDOM from "react-demo";
   const App = () => {
       return (
       	<div>
           	<p>React here!</p>
           </div>
       );
   };
   export default App;
   ReactDOM.render(<App/>, document.getElementById("app"));
   ```

   ```./src/index.js```内容如下：

   ```jsx
   import App from "./app";
   ```

5. 终端执行```npm run build```

## 使用html-webpack-plugin插件对html进行打包

新建```./src/index.html```文件，内容如下：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Webpack-Demo</title>
</head>
<body>
<div id="app">
</div>
</body>
</html>
```

安装依赖包。

```
npm i html-webpack-plugin html-loader --save-dev
```

修改```webpack.config.js```配置

```js
const HtmlWebPackPlugin = require("html-webpack-plugin");
module.exports = {
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: "babel-loader"
            }
        }, {
            test: /\.html$/,
            use: {
                loader: "html-loader",
                options: {
                    minimize: true
                }
            }
        }]
    }, 
    plugins: {
        new HtmlWebPackPlugin({
        	templates: "./src/index.html",
        	filename: "./index.html"
    	})
    }
}
```

终端执行```npm run build```命令。

你会看到项目多了个```./dist/index.html```文件。

## 使用webpack-dev-server插件

安装依赖包。

```
npm i webpack-dev-server --save-dev
```

修改```package.json```文件

```json
"scripts": {
    "start": "webpack-dev-server --mode development --open",
    "build": "webpack --mode production"
}
```

修改```webpack.config.js```文件，新增```devServer```配置。

```js
devServer: {
    contentBase: require('path').join(__dirname, "dist"),
    compress: true,
    port: 8080,
    host: "localhost"
}
```

终端执行```npm run start```便可以启动```webpack dev server```。

## 使用Hot Module Replacement

```Hot Module Replacement```有针对React，Vue，Redux，Angular，样式等等。

这里我们以```React Hot Loader```为例。

安装依赖包。

```
npm i react-hot-loader --save-dev
```

修改```.babelrc```文件，新增```plugins```选项。

```
{
    "plugins": ["react-hot-loader/babel"]
}
```

修改```webpack.config.js```文件

```javascript
const path = require('path');
const HtmlWebPackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
module.exports = {
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: "babel-loader"
            }
        }, {
            test: /\.html$/,
            use: {
                loader: "html-loader",
                options: {
                    minimize: true
                }
            }
        }]
    }, 
    devtool: 'inline-source-map',
    plugins: {
        new HtmlWebPackPlugin({
        	templates: "./src/index.html",
        	filename: "./index.html"
    	}),
    	new webpack.NamedModulesPlugin(),
    	new webpack.HotModuleReplacementPlugin()
    },
    devServer: {
        contentBase: path.join(__dirname, "dist"),
        compress: true,
        port: 8080,
        host: "localhost",
        hot: true
    }
};
```

修改```./src/app.js```文件内容如下：

```jsx
import React from "react";
import ReactDOM from "react-dom";
import { hot } from "react-hot-loader";

const App = () => {
    return (
    	<div>
        	<p>这时一个测试文件！真的是动态更新啊</p>
            <div>React here!</div>
        </div>
    );
};
export default hot(module)(App);
ReactDOM.render(<App />, document.getElementById("app"));
```

终端执行```npm run start```便可启动```webpack dev server```。

然后修改```./src/app.js```看下效果。

## 动态加模块

- 动态加载普通JS模块

  修改```.babelrc```文件，添加```"syntax-dynamic-import"```插件

  ```json
  {
      "presets": [
          "env",
          "react"
      ],
      "plugins": [
          "react-hot-loader/babel",
          "syntax-dynamic-import"
      ]
  }
  ```

  然后在业务代码文件使用```import```语法即可

  ```
  import('模块路径').then(mod => {
    someOperate(mod); //mod是module的简写，表示加载成功后的异步组件
  });
  ```

  如果没有使用`syntax-dynamic-import`插件会导致构建失败，并提示：

  ```
  Module build failed: SyntaxError: 'import' and 'export' may only appear at the top level
  ```

- 动态加载React模块

  如同`动态加载普通JS模块`，修改.babelrc文件添加"syntax-dynamic-import"插件。

  然后安装`react-loadable`这个NPM包

  ```
  npm install react-loadable --save
  ```

  接着如下方式引入React组件模块即可。

  ```jsx
  import Loadable from 'react-loadable';
  
  const LoadableBar = Loadable({
    loader: () => import('react组件模块路径'),
    loading() {
      return <div>Loading...</div>
    }
  });
  
  class MyComponent extends React.Component {
    render() {
      return <LoadableBar/>;
    }
  }
  ```

## 多页面多入口打包

修改`webpack.config.js`如下

```js
...
entry: {
    // 把html 加入 entry。目的是修改html文件也能hot reload。
    // 不要在生产环境这么做。因为这会导致 chunk 文件包含了无用的 html 片段。
    page0: ['./src/pages/page0/index.js','./src/template/page0.html'],
    page1: './src/pages/page1/index.js',
},
plugins: [
    new HtmlWebPackPlugin({
        template: "./src/template/page0.html",
        filename: "./page0.html",
        chunksSortMode: 'none',
        chunks: ["page0"],
    }),
    new HtmlWebPackPlugin({
        template: "./src/template/page1.html",
        filename: "./page1.html",
        chunksSortMode: 'none',
        chunks: ["page1"],
    }),
    new webpack.NamedModulesPlugin(),
    new webpack.HotModuleReplacementPlugin()
],  
...
```

## 第三方库和业务代码分开打包

基本策略是:

1. 提取样式文件

2. 分离公共文件（运行时，node_modules，业务的共同依赖）

3. 懒加载大文件

   react-loadable

### 简单分离公共文件和业务文件

```js
optimization: {
    splitChunks: {
        cacheGroups: {
            commons: {
                name: 'common',
                priority: 10,
                chunks: 'initial'
            }
        }
    }
}
```

### 分离业务文件，node_modules，业务的共同依赖，webpack运行时

```js
runtimeChunk: {
    name: 'manifest'
},
optimization: {
    splitChunks: {
        cacheGroups: {
            vendors: {
                test: /[\\/]node_modules[\\/]/,
                name: "vendors",
                chunks: "all"
            },
            commons: {
                chunks: "all",
                name: 'commons',
                /**
                 * minSize 默认为 30000
                 * 想要使代码拆分真的按照我们的设置来
                 * 需要减小 minSize
                 */
                minSize: 0,
                // 至少为两个 chunks 的公用代码
                minChunks: 2
            }
        }
    }
}
```

这里打包出来的文件有vendros.js(node_modules)，commons.js(业务的共同依赖)，manifest.js(webpack运行时)，业务文件

### 手动指定业务的共同依赖

```js
entry: {
    // html可实时刷新修改
    page0: ['./src/pages/page0/index.js', './src/template/page0.html'],
    page1: './src/pages/page1/index.js',
    page2: './src/pages/page2/index.js',
    pageCommon: ['./src/common/page0-1.js', './src/common/page0-2.js'],
},
optimization: {
    runtimeChunk: {
        name: 'manifest'
    },
    splitChunks: {
        cacheGroups: {
            vendors: {
                test: /[\\/]node_modules[\\/]/,
                name: "vendors",
                chunks: "all"
            },
        }
    }
},
plugins: [
    new HtmlWebPackPlugin({
        template: "./src/template/page0.html",
        filename: "./page0.html",
        chunksSortMode: 'none',
        chunks: ["page0", "pageCommon", "vendors","manifest"],
    }),
    new webpack.NamedModulesPlugin(),
    new webpack.HotModuleReplacementPlugin(),
],
```

手动指定共同依赖后，就不要再让webpack自动分析共同依赖，否则手动的共同依赖打包出来不生效。