// 引入mongoose模块
const mongoose = require('mongoose');
// 导入config模块
const config = require('config');
// 连接数据库
mongoose.connect(`mongodb://${config.get('db.user')}:520808@${config.get('db.host')}:${config.get('db.port')}/${config.get('db.name')}`, {
        useNewUrlParser: true,
        useUnifiedTopology: true,
        useCreateIndex: true
    })
    .then(() => {
        console.log('mongoDB is connected.');
    })
    .catch(() => {
        console.log('mongoDB connect failed.');
    });