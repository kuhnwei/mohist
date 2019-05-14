# uber/kepler.gl

> [官网( http://kepler.gl/ )](http://kepler.gl/)

```bash
# 获取源码
git clone https://github.com/uber/kepler.gl.git
```

`config.json`

```json
// 22.626012, 113.812596  深圳宝安机场坐标
// kepler.gl 设置面板配置
{
    "version": "v1",
    "config": {
        "visState": {
            "filters": [],
            "layers": [
                {
                    
                }
            ],
            "interactionConfig": {},
            "layerBlending": "normal",
            "splitMaps": []
        },
        "mapState": { // 地图初始化状态
            "bearing": 0,
            "dragRotate": false,
            "latitude": 22.626012, // 纬度
            "longitude": 113.812596, // 经度
            "pitch": 0, // 倾斜角度， 取值返回小于90
            "zoom": 12.032736770460689, // 缩放，数值越大地图放大越大
            "isSplit": false // 是否把地图分割显示（true, 只在客户端窗口一半的区域显示）
        },
        "mapStyle": { // 初始化图层样式
            "styleType": "dark", // light, dark
            "topLayerGroups": {
                "label": true
            },
            "visibleLayerGroups": {
                "label": true,
                "road": true,
                "border": true,
                "building": true,
                "water": true,
                "land": true
            }
        },
    }
}
```

