/**
 * JavaScript 实现贪吃蛇小游戏
 * Created by Kuhn on 2017/8/17.
 */


/** 像素单位字符串 */
var PX = "px";
/** div字符串，用于创建 div DOM */
var DIV = "div";
/** 黑色 */
var BLACK = "black";
/** 红色 */
var RED = "red";
/** 绝对定位 */
var POSITION_ABSOLUTE = "absolute";
/** setTimeout的毫秒参数 等同 snakeHead 移动的速度 */
var SPEED = 100;
/** setTimeout 方法的 ID， 作为 clearTimeout(TIMEOUT_ID) 的参数来停止当前的setTimeout  */
var TIMEOUT_ID;

/** 贪吃蛇的食物对象 */
var food;
/** 数组蛇对象 */
var snake = [];
/** 蛇身的长度 */
var SNAKE_LENGTH = 0;
/** 蛇(div)移动时的步长 */
var STEP = 20;
/** 每一节蛇身(div)的长度和宽度 */
var STEP_PX = STEP + PX;
/** 记录蛇头当前距离顶端的坐标值 */
var TOP_NUMBER = 0;
/** 记录蛇头当前距离左端的坐标值 */
var LEFT_NUMBER = 0;

/** 键盘UP ARROW键(↑)的键值为38 */
var UP_ARROW = 38;
/** 键盘DOWN ARROW键(↓)的键值为40 */
var DOWN_ARROW = 40;
/** 键盘LEFT ARROW 键(←)的键值为37 */
var LEFT_ARROW = 37;
/** 键盘RIGHT ARROW键(→)的键值为39 */
var RIGHT_ARROW = 39;
/** 键盘Esc的键值为27 */
var STOP = 27;
/** 方向标记，记录蛇当前的移动方向
 * <br/> 37:←
 * <br/> 38:↑
 * <br/> 39:→
 * <br/> 40:↓
 */
var DIRECTION_FLAG = -1;

/** 窗口的文档显示区的高度 */
var CLIENT_HEIGHT;
/** 窗口的文档显示区的宽度 */
var CLIENT_WIDTH;

/** 初始化 */
initSnake();

/**
 * 初始化
 * */
function initSnake() {

    // 初始化客户端窗口的大小
    initClientSize();

    // 生成贪吃蛇对象
    snake[SNAKE_LENGTH] = document.createElement(DIV);
    snake[SNAKE_LENGTH].style.backgroundColor = BLACK;
    snake[SNAKE_LENGTH].style.width = STEP_PX;
    snake[SNAKE_LENGTH].style.height = STEP_PX;
    snake[SNAKE_LENGTH].style.position = POSITION_ABSOLUTE;
    snake[SNAKE_LENGTH].style.top = initTopNumber() + PX;
    snake[SNAKE_LENGTH].style.left = initLeftNumber() + PX;
    document.body.appendChild(snake[SNAKE_LENGTH]);
    addSnakeBody();
    addSnakeBody();

    // 生成食物对象
    food = document.createElement(DIV);
    food.style.backgroundColor = RED;
    food.style.width = STEP_PX;
    food.style.height = STEP_PX;
    food.style.position = POSITION_ABSOLUTE;
    food.style.top = foodCoordinate().top;
    food.style.left = foodCoordinate().left;
    document.body.appendChild(food);

    // 隐藏窗口的滚动条
    document.body.style.overflowX = "hidden";
    document.body.style.overflowY = "hidden";
}

/**
 * 初始化客户端窗口的大小
 */
function initClientSize() {
    CLIENT_HEIGHT = window.innerHeight;
    CLIENT_WIDTH = window.innerWidth;
    // 浏览器兼容
    if (typeof CLIENT_WIDTH != 'number') {
        if (document.compatMode == 'CSS1Compat') {
            CLIENT_HEIGHT = document.documentElement.clientHeight;
            CLIENT_WIDTH = document.documentElement.clientWidth;
        } else {
            CLIENT_HEIGHT = document.body.clientHeight;
            CLIENT_WIDTH = document.body.clientWidth;
        }
    }
}

/**
 * 以STEP（步长）的倍数初始化蛇相对于窗口顶端的初始坐标值
 * @returns {number} TOP_NUMBER
 */
function initTopNumber() {
    // 估算为当前窗口高度的一半
    TOP_NUMBER = stepUtil(CLIENT_HEIGHT / 2);
    return TOP_NUMBER;
}

/**
 * 以STEP（步长）的倍数初始化蛇相对于窗口左端的初始坐标值
 * @returns {number} LEFT_NUMBER
 */
function initLeftNumber() {
    // 估算为当前窗口宽度的一半
    LEFT_NUMBER = stepUtil(CLIENT_WIDTH / 2);
    return LEFT_NUMBER;
}

/**
 * 添加蛇的长度
 */
function addSnakeBody() {
    SNAKE_LENGTH++;
    snake[SNAKE_LENGTH] = document.createElement(DIV);
    snake[SNAKE_LENGTH].style.backgroundColor = BLACK;
    snake[SNAKE_LENGTH].style.width = STEP_PX;
    snake[SNAKE_LENGTH].style.height = STEP_PX;
    snake[SNAKE_LENGTH].style.position = POSITION_ABSOLUTE;
    snake[SNAKE_LENGTH].style.top = snake[0].style.top;
    snake[SNAKE_LENGTH].style.left = snake[0].style.left;
    document.body.appendChild(snake[SNAKE_LENGTH]);
}

/**
 * 键盘监听事件
 * */
document.onkeydown = function () {
    // 上 ↑
    if (event.keyCode == UP_ARROW && DIRECTION_FLAG != DOWN_ARROW) {
        clearTimeout(TIMEOUT_ID);
        DIRECTION_FLAG = UP_ARROW;
        snakeMoveUp();
        return;
    }
    // 下 ↓
    if (event.keyCode == DOWN_ARROW && DIRECTION_FLAG != UP_ARROW) {
        clearTimeout(TIMEOUT_ID);
        DIRECTION_FLAG = DOWN_ARROW;
        snakeMoveDown();
        return;
    }
    // 左 ←
    if (event.keyCode == LEFT_ARROW && DIRECTION_FLAG != RIGHT_ARROW) {
        clearTimeout(TIMEOUT_ID);
        DIRECTION_FLAG = LEFT_ARROW;
        snakeMoveLeft();
        return;
    }
    // 右 →
    if (event.keyCode == RIGHT_ARROW && DIRECTION_FLAG != LEFT_ARROW) {
        clearTimeout(TIMEOUT_ID);
        DIRECTION_FLAG = RIGHT_ARROW;
        snakeMoveRight();
        return;
    }
    // 停止
    if (event.keyCode == STOP) {
        clearTimeout(TIMEOUT_ID);
        location.reload();
    }
};

/**
 * 向上移动
 */
function snakeMoveUp() {
    if (TOP_NUMBER <= 0) {
        var i = 0;
        while (1) {
            i++;
            if (STEP * i > CLIENT_HEIGHT) {
                TOP_NUMBER = STEP * i;
                break;
            }
        }
    }
    TOP_NUMBER -= STEP;
    snakeMoveProcess();
    TIMEOUT_ID = setTimeout("snakeMoveUp()", SPEED);
}

/**
 * 向下移动
 */
function snakeMoveDown() {
    TOP_NUMBER += STEP;
    if (TOP_NUMBER >= CLIENT_HEIGHT) {
        TOP_NUMBER = 0;
    }
    snakeMoveProcess();
    TIMEOUT_ID = setTimeout("snakeMoveDown()", SPEED);
}

/**
 * 向左移动
 */
function snakeMoveLeft() {
    if (LEFT_NUMBER <= 0) {
        var i = 0;
        while (1) {
            i++;
            if (STEP * i > CLIENT_WIDTH) {
                LEFT_NUMBER = STEP * i;
                break;
            }
        }
    }
    LEFT_NUMBER -= STEP;
    snakeMoveProcess();
    TIMEOUT_ID = setTimeout("snakeMoveLeft()", SPEED);
}

/**
 * 向右移动
 */
function snakeMoveRight() {
    LEFT_NUMBER += STEP;

    if (LEFT_NUMBER >= CLIENT_WIDTH) {
        LEFT_NUMBER = 0;
    }
    snakeMoveProcess();
    TIMEOUT_ID = setTimeout("snakeMoveRight()", SPEED);
}

/**
 * 蛇移动每一步时的循环赋值
 * <br/> 1.记录所有当前节点的位置坐标
 * <br/> 2.第一个节点根据 TOP_NUMBER 和 LEFT_NUMBER 的变化而赋新值
 * <br/> 3.把记录好的节点坐标分别赋值给后一个节点
 */
function snakeMoveProcess() {
    var top = new Array(SNAKE_LENGTH);
    var left = new Array(SNAKE_LENGTH);
    for (var i = 0; i < SNAKE_LENGTH; i++) {
        top[i] = snake[i].style.top;
        left[i] = snake[i].style.left;
    }
    snake[0].style.top = TOP_NUMBER + PX;
    snake[0].style.left = LEFT_NUMBER + PX;
    for (var j = 0; j < SNAKE_LENGTH; j++) {
        snake[j + 1].style.top = top[j];
        snake[j + 1].style.left = left[j];
        if (snake[0].style.top == snake[j + 1].style.top && snake[0].style.left == snake[j + 1].style.left) {
            clearTimeout(TIMEOUT_ID);
            confirm("GAME OVER !!!");
            location.reload();
        }
    }
    if (snake[0].style.top == food.style.top && snake[0].style.left == food.style.left) {
        addSnakeBody();
        food.style.top = foodCoordinate().top;
        food.style.left = foodCoordinate().left;
    }
}

/**
 * 计算食物的坐标，当食物出现时，坐标不能与蛇身重叠
 * @returns {{top: string, left: string}}
 */
function foodCoordinate () {
    while (1) {
        // 坐标值为STEP（步长）的倍数，这样才能和蛇的移动保持在同一数轴上
        var topTemp = Math.floor((Math.random() * (CLIENT_HEIGHT - STEP)) / STEP) * STEP + PX;
        var leftTemp = Math.floor((Math.random() * (CLIENT_WIDTH - STEP)) / STEP) * STEP + PX;
        var i;
        for (i = 0; i < SNAKE_LENGTH; i++) {
            if (topTemp == snake[i].style.top && leftTemp == snake[i].style.left) {
                break;
            }
        }
        if (i >= SNAKE_LENGTH) {
            return {top: topTemp, left: leftTemp}
        }
    }
}

/**
 * 根据指定数值作为上限范围，取STEP(步长)的倍数
 * @param _data 上限数值
 * @returns {number} STEP(步长)的倍数
 */
function stepUtil(_data) {
    var i = 0;
    while (1) {
        i++;
        if (STEP * i > _data) {
            return STEP * i - STEP;
        }
    }
}

/**
 * 监听客户端大小的变化
 */
window.onresize = function () {
    var heightTemp = CLIENT_HEIGHT;
    var widthTemp = CLIENT_WIDTH;
    initClientSize();
    // 如果窗口变小了 且 食物的坐标不在变小后的窗口之内 则重新给食物赋新的坐标值 移置窗口内
    if (heightTemp > CLIENT_HEIGHT && widthTemp > CLIENT_WIDTH
        && (parseInt(food.style.top) > CLIENT_HEIGHT || parseInt(food.style.left) > CLIENT_WIDTH)) {
        food.style.top = stepUtil(parseInt(food.style.top) / heightTemp * CLIENT_HEIGHT) + PX;
        food.style.left = stepUtil(parseInt(food.style.left) / widthTemp * CLIENT_WIDTH) + PX;

    }

    // 如果窗口大小改变后  蛇不在变化后的窗口视图内 则 重新给蛇赋新的坐标值 移置窗口内
    if (TOP_NUMBER > CLIENT_HEIGHT || LEFT_NUMBER > CLIENT_WIDTH) {
        TOP_NUMBER = stepUtil(CLIENT_HEIGHT / 2);
        LEFT_NUMBER = stepUtil(CLIENT_WIDTH / 2);
        snake[0].style.top = TOP_NUMBER + PX;
        snake[0].style.left = LEFT_NUMBER + PX;
    }
};

/*
 window.innerWidth 窗口的文档显示区的宽度
 window.innerHeight 窗口的文档显示区的高度
 offsetWidth DOM自己的宽度
 offsetHeight DOM自己的高度
 offsetLeft DOM与父DOM的左变距离（只读）
 offsetTop DOM与父DOM的顶端距离（只读）
 style.left DOM与父DOM的左变距离（可读写）
 style.top DOM与父DOM的顶端距离（可读写）
 */



