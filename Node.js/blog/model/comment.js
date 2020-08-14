const mongoose = require('mongoose');
// 创建集合规则
const commentSchema = new mongoose.Schema({
    uid: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: [true, '请填写作者ID']
    },
    aid: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Article',
        required: [true, '请填写文章ID']
    },
    commentDate: {
        type: Date,
        default: new Date()
    },
    comment: {
        type: String,
        required: [true, '请填写评论']
    }
});
// 得到构造函数
const Comment = mongoose.model('Comment', commentSchema);

// 导出构造函数
module.exports = {
    Comment
};