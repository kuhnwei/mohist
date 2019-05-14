# DECKGL API简介

## Deck

`Deck`是一个类，它接受deck.gl图层实例和视图参数，将这些图层渲染为透明叠加层，并处理事件。

### 用法

```javascript
import {Deck, ScatterplotLayer} from 'deck.gl';

const App = (viewState, data) => {
    const deck = new Deck({
        layers: [new ScatterplotLayer({data})]
    });
    deck.setProps({viewState});
};
```

### 构造函数

创建一个新的Deck实例

```js
const deck = new Deck(props);
```

### 属性

- `width`(Number, required)：画布的宽度
- `height`(Number, required)：画布的高度
- `layers`(Array, required)：要渲染的deck.gl图层数组。此数组应该是deck.gl图层新分配实例的数组，使用从当前应用程序状态派生的更新属性创建
- `layerFilter`(optional)：获取```({layer, viewport, isPicking}) => Boolean```在渲染图层之前调用的函数。使应用程序有机会在渲染或拾取过程中从图层列表中过滤掉图层。可以按视图或图层或两者进行过滤。这样可以启用一些技术，例如在拾取过程中添加辅助层作为遮罩，但在渲染过程中不会显示。即使使用此prop过滤掉一个图层，所有生命周期方法仍然会被触发
- `views`：单个实例`View`或`View`实例数组（可选地与`Viewport`实例混合，但不推荐使用后者）。如果未提供，`MapView`将创建一个。如果提供了空数组，`View`则不会显示no。
- `viewState`(Object)：地图空间`viewState`通常包含以下字段：
  - `latitude`(Number, optional)：当前纬度，用于定义墨卡托投影（如果`viewport`未提供）。
  - `longitude`(Number, optional)：当前经度，用于定义墨卡托投影（如果`viewport`未提供）。
  - `zoom`（Number, optional)：当前缩放，。。。
  - `bearing`(Number, optional)：当前方位，。。。
  - `pitch`(Number, optional)：当前倾斜度，。。。
- `initialViewState`(Object)：如果`initialViewState`提供，则`Deck`组件将跟踪`controller`使用内部状态的任何附加视图状态更改，`initialViewState`并将其作为其初始视图状态。

#### 配置属性

- `id`(String, optional)：Canvas ID允许在CSS中进行样式自定义
- `style`(Object, optional)：deckgl-canvas的CSS样式
- `pickingRadius`(Number, optional)：在选择时，指针周围要包含的额外像素。当呈现的对象难以定位时，例如形状不规则的图标、小的移动圆圈或通过触摸进行交互，这是很有用的。默认为0
- `useDevicePixels`(Boolean, optional)：如果为`true`，设备的全部分辨率将用于呈现，这个值可以根据帧变化，比如在屏幕之间移动窗口或改变浏览器的缩放级别。默认为`true`
  - `false`除非需要高分辨率，否则请考虑设置，因为它会影响渲染性能。
- `gl`(Object, optional)：gl context，如果没有提供，将自动处理。
- `parameters`(Object, optional)：

#### 事件回调

- `onWebGLInitialized`(Function, optional)：回调，一旦启动WebGL上下文就调用
  - 回调参数 ：`gl` - WebGL上下文
- `onViewStateChange`(Function)：当用户与deck.gl的画布进行交互时，将触发`onViewStateChange`，例如使用鼠标、键盘或触摸。
  - `onViewStateChange({viewState})`：`viewState`更新的视图状态含有的参数，例如`longitude`,`latitude`,`zoom`等
  - 返回：可以返回更新的视图状态，如果返回视图状态，将使用它来代替传入`viewState`来更新应用恒旭的内部视图状态
- `onLayerHover`(Function, optional)：回调--当指针下的对象发生变化时调用。回调参数：
  - `info`：坐标处最顶层拾取图层的对象，未拾取对象时为null
  - `pickedInfos`：手影响的所有可选图层的信息对象素组。
  - `event`：元素的MouseEvent对象
- `onLayerClick`(Function, optional)：回调--单击图层时调用。回调参数：
  - `info`：坐标处最顶层拾取图层的对象，未拾取对象时为null
  - `pickedInfos`：手影响的所有可选图层的信息对象素组。
  - `event`：元素的MouseEvent对象
- `onLoad`(Funtion, optional)：GL上下文和deck组件被创建时触发。

### 方法

`finalize`
立即释放与此对象关联的资源（而不是等待垃圾回收）。

`deck.finalize()`

`setProps`
更新属性。

```
deck.setProps({...});
```

`pickObject`
在给定的屏幕坐标获取最接近的可拾取和可见对象。

```
deck.pickObject({x, y, radius, layerIds})
```

- `x`(Number) - X位置（以像素为单位）
- `y`(Number) - Y位置 （以像素为单位）
- `radius`(Number, optional) - 半径（以像素为单位）默认0
- `layerIds`(Array, optional) - 要查询的图层ID列表。如果未指定，则查询所有可选择和可见的图层。

返回：

- 单个`info`对象，或者`null`

`pickMultipleObjects`
执行深度条线。在给定的屏幕坐标处查找所有关闭的可选对象和可见对象，即使这些对象被其他对象遮挡也是如此。

## Layer Class

`Layer`类是deck.gl图层中的基础类，并提供了一些在所有层中可用的基本属性。

### 静态成员

`layerName`(String, required)
图层的名称，通常是图层类的名称。默认作为Layer ID

`defaultProps`(Object, optional)
所有delc.gl图层都定义了一个`defaultProps`静态成员，列出了它们的默认值。

