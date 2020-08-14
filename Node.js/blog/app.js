const express = require('express');
// 创建服务器
const app = express();
// 连接数据库
require('./model/connect');
// 引入body-parser模块，获得Post请求参数
const bodyParser = require('body-parser');
// 配置bodyParser
app.use(bodyParser.urlencoded({ extended: false }));

// 引入日期处理
const dateformat = require('dateformat');
app.locals.dateformat = dateformat;
// 引入path模块
const path = require('path');
// 开放静态资源文件
app.use(express.static(path.join(__dirname, 'public')));
// 配置模板引擎
app.engine('art', require('express-art-template'));
// 配置模板引擎根目录
app.set('views', path.join(__dirname, 'views'));
// 配置模板引擎默认后缀
app.set('view engine', 'art');

// 引入session模块实现登录
const session = require('express-session');
// 配置session
app.use(session({
    secret: 'secret key',
    resave: false,
    saveUninitialized: false,
    cookie: {
        // 时、分、秒、毫秒，cookie的有效时长为15分钟
        maxAge: 1 * 15 * 60 * 1000
    }
}));

// 进行登录审查，必须在静态资源的后面，否则静态资源也会审查
const gate = require('./middleware/loginGate');
app.use('/admin', gate);

// 构建模块化路由
const home = require('./route/home');
const admin = require('./route/admin');
// 使用模块化路由
// 模板中的相对路径，是相对客户端url中请求的路径，非常不安全，可以使用绝对路径进行
// localhost/admin/login.art中的<link rel="stylesheet" href="css/base.css">相对路径就是/admin/ + css/base.css
// 解决方法，将login.art模板中的<link rel="stylesheet" href="/admin/css/base.css">使用绝对路径
// 这样请求的就是服务器上的(设置的static路径为public目录)/admin/css/base.css
app.use('/home', home);
app.use('/admin', admin);



// 添加监听端口
app.listen(80);
console.log('server is running...');