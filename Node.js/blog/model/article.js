const mongoose = require('mongoose');
// 创建集合规则
const articleSchema = new mongoose.Schema({
    tittle: {
        type: String,
        minlength: 4,
        maxlength: 50,
        required: [true, '请填写文章标题']
    },
    author: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: [true, '请填写作者']
    },
    publishDate: {
        type: Date,
        default: Date.now()
    },
    cover: {
        type: String,
        default: null
    },
    content: {
        type: String,
        required: [true, '请填写文章内容']
    }
});
// 得到构造函数
const Article = mongoose.model('Article', articleSchema);

// 导出构造函数
module.exports = {
    Article
};