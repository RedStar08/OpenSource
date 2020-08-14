const express = require('express');
// 创建路由
const home = express.Router();
// 文章集合的构造函数
const { Article } = require('../model/article');
const pagination = require('mongoose-sex-page');

// 文章列表
home.get('/', async(req, res) => {
    const page = req.query.page;
    try {
        // 查询所有的文章
        let result = await pagination(Article).find().page(page).size(4).display(3).populate('author').exec();
        // res.send(result);
        // console.log(req.session.role);
        res.render('home/default', {
            result
        });
    } catch (error) {
        res.status(500).render('admin/error', { msg: '页面找不到了404， 3s后跳转到登录页面...' });
    }
});

const { Comment } = require('../model/comment');
// 文章详细页面
home.get('/article', async(req, res) => {
    // 查找文章
    const id = req.query.id;
    try {
        let articleInfo = await Article.findOne({ _id: id }).populate('author');
        // 查找评论
        let comments = await Comment.find({ aid: id }).populate('uid');
        // res.send(articleInfo);
        res.render('home/article', {
            articleInfo,
            comments
        });
    } catch (error) {
        console.log(error.meseage);
        res.status(500).render('admin/error', { msg: '页面找不到了404， 3s后跳转到登录页面...' });
    }
});

// 评论管理
home.post('/comment', async(req, res) => {
    // 添加评论
    const { uid, aid, comment } = req.body;
    await Comment.create({ uid: uid, aid: aid, comment: comment, commentDate: new Date() });
    // res.send(req.body);
    res.redirect('/home/article?id=' + aid);
});
// 导出home路由模块
module.exports = home;