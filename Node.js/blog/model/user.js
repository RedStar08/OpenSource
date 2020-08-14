const mongoose = require('mongoose');
// 创建集合规则
const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        minlength: 2,
        maxlength: 20
    },
    email: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    role: {
        type: String,
        enum: ['admin', 'normal'],
        required: true
    },
    state: {
        type: Number,
        // 0默认是启用
        default: 0
    }
});
// 得到构造函数
const User = mongoose.model('User', userSchema);

// 测试代码
// 初始化一个用户
// User.create({
//     username: 'RedStar08',
//     email: 'redstar08@qq.com',
//     password: '123456',
//     role: 'admin',
//     state: 0
// });

// 验证表单
const Joi = require('joi');
const validateUser = user => {
    // 创建验证规则
    const validSchema = {
        username: Joi.string().min(2).max(20).required().error(new Error('用户名为2-10位')),
        email: Joi.string().email().required().error(new Error('请输入正确的邮箱地址')),
        password: Joi.string().regex(/^[a-zA-Z0-9]{6,16}$/).required().error(new Error('密码为6-16位的数字或字符')),
        role: Joi.string().valid('normal', 'admin').required().error(new Error('用户角色值非法')),
        state: Joi.number().valid(0, 1).required().error(new Error('用户状态值非法'))
    };
    // 对对象进行验证
    return Joi.validate(user, validSchema);
}



// 导出构造函数
module.exports = {
    User,
    validateUser
}