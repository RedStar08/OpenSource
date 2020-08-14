const express = require('express');

// 引入hash算法处理密码
const hash = require('../hash');
// 获得User集合的构造函数
const { User, validateUser } = require('../model/user');

// 创建路由
const admin = express.Router();
// 渲染登录页面
admin.get('/login', (req, res) => {
    // 模板中的相对路径，是相对url中请求的路径
    res.render('admin/login');
});
// 处理post请求，实现登录功能
admin.post('/login', async(req, res) => {
    // 模板中的相对路径，是相对url中请求的路径
    // console.log(req.body);
    const { email, password } = req.body;
    if (email.trim().length == 0 || password.trim().length == 0) {
        // res.send('<script>alert("邮箱或密码错误，请重新输入！");location.herf="/admin/login"</script>');
        res.status(400).render('admin/error', { msg: '邮箱或密码错误，请重新输入！ 3s后跳转到登录页面...' });
    } else {
        // 查询用户是否存在
        let user = await User.findOne({ email: email });
        if (user) {
            // 查询到用户，匹配密码，对密码进行解密
            let decrypted = hash.decrypt(user.password);
            if (password == decrypted) {
                // 密码正确
                // 在服务器的session中保存用户名
                req.session.username = user.username;
                req.session.role = user.role;
                // 将用户信息暴露给所有的模板
                req.app.locals.userInfo = user;
                // 重定向到用户列表页面
                if (user.role == 'admin') {
                    // 判断用户的类型
                    res.redirect('/admin/user');
                } else {
                    res.redirect('/home');
                }
            } else {
                // 密码不正确
                res.status(400).render('admin/error', { msg: '邮箱或密码错误，请重新输入！ 3s后跳转到登录页面...' });
            }
        } else {
            // 查不到用户
            res.status(400).render('admin/error', { msg: '邮箱或密码错误，请重新输入！ 3s后跳转到登录页面...' });
        }
    }
});
// 渲染用户列表页面
admin.get('/user', async(req, res) => {
    // 切换侧边栏
    req.app.locals.currentPage = 'user';
    // 设置分页的页码和页面大小
    let pagesize = 5;
    let userCount = await User.countDocuments();
    let maxPage = Math.ceil(userCount / pagesize);
    // 判断页面是否合法
    let page = req.query.page;
    page = page > 0 ? page : 1;
    page = page < maxPage ? page : maxPage;
    // 查询所有用户，此处必须使用异步函数查询到结果
    let users = await User.find({}).limit(pagesize).skip((page - 1) * pagesize);
    // res.send(users);
    res.render('admin/user', { users: users, page: page, maxPage: maxPage, userCount: userCount });
});
// 实现登出功能
admin.get('/logout', (req, res) => {
    // 删除session
    req.session.destroy(function() {
        // 删除cookie
        res.clearCookie('connect.sid');
        // 清除用户信息
        req.app.locals.userInfo = null;
        // 重定向到用户登录页面
        res.redirect('/admin/login');
    });
});
// 渲染用户编辑页面
admin.get('/user-add', (req, res) => {
    // 切换侧边栏
    req.app.locals.currentPage = 'user';
    res.render('admin/user-edit', {
        link: '/admin/user-add',
        submit: '添加'
    });
});
// 实现添加用户功能
// 验证表单模块
admin.post('/user-add', async(req, res) => {
    // res.send(req.body);
    // 验证表单是否合法
    try {
        await validateUser(req.body);
    } catch (ex) {
        // 验证没有通过，提示错误信息
        return res.render('admin/user-edit', {
            errorInfo: ex.message,
            link: '/admin/user-add',
            submit: '添加'
        });
    }
    // 表单合法，继续判断是否存在用户
    let user = await User.findOne({ email: req.body.email });
    if (user) {
        // 存在用户，提示用户已经存在
        return res.render('admin/user-edit', {
            errorInfo: req.body.email + '邮箱已存在',
            link: '/admin/user-add',
            submit: '添加'
        });
    } else {
        // 用户不存在，对密码加密后插入到数据库
        let password = hash.encrypt(req.body.password);
        req.body.password = password;
        await User.create(req.body);
        // 重定向到用户列表页面
        res.redirect('/admin/user');
    }
});
// 展示修改用户的信息
admin.get('/user-edit', async(req, res) => {
    // 切换侧边栏
    req.app.locals.currentPage = 'user';
    // 查找用户
    const id = req.query.id;
    let user = await User.findOne({ _id: id });
    res.render('admin/user-edit', {
        user: user,
        link: '/admin/user-edit?id=' + id,
        submit: '修改'
    });
});
// 实现修改用户功能
admin.post('/user-edit', async(req, res) => {
    // res.send(req.body);
    // 查找用户
    const id = req.query.id;
    let user = await User.findOne({ _id: id });
    // 验证表单是否合法
    try {
        await validateUser(req.body);
    } catch (error) {
        // 验证没有通过，提示错误信息
        return res.render('admin/user-edit', {
            errorInfo: error.message,
            user: user,
            link: '/admin/user-edit?id=' + id,
            submit: '修改'
        });
    }
    // 表单合法，继续判断密码是否正确
    const { username, email, password, role, state } = req.body;
    const pass = hash.decrypt(user.password);
    if (pass == password) {
        // 密码比对成功，修改用户信息
        try {
            await User.updateOne({ _id: id }, {
                username: username,
                email: email,
                role: role,
                state: state
            });
        } catch (error) {
            //修改失败，提示错误信息，只有邮箱重复才会触发
            return res.render('admin/user-edit', {
                errorInfo: '邮箱已存在，请重新输入！',
                user: user,
                link: '/admin/user-edit?id=' + id,
                submit: '修改'
            });
        }
        // 修改成功，重定向到用户列表页面
        res.redirect('/admin/user');
    } else {
        // 密码比对失败，提示用户
        return res.render('admin/user-edit', {
            errorInfo: '密码不正确，修改用户信息失败',
            user: user,
            link: '/admin/user-edit?id=' + id,
            submit: '修改'
        });
    }
});
// 实现删除用户功能
admin.post('/user-delete', async(req, res) => {
    const id = req.body.deleteUserId;
    await User.findOneAndDelete({ _id: id });
    res.redirect('/admin/user');
});


// 文章管理
const { Article } = require('../model/article');
const formidable = require('formidable');
const path = require('path');
const pagination = require('mongoose-sex-page');
// 渲染文章列表页面
admin.get('/article', async(req, res) => {
    // 切换侧边栏
    req.app.locals.currentPage = 'article';
    // 查询所有文章
    const page = req.query.page;
    try {
        // let articles = await Article.find({}).populate('author');
        let articles = await pagination(Article).find().page(page).size(4).display(3).populate('author').exec();
        // 测试代码
        // console.log(req.app.locals);
        // res.send(articles);

        res.render('admin/article', {
            articles
        });
    } catch (error) {
        // res.send(error.message);
        res.status(500).render('admin/error', { msg: '服务器出错， 3s后跳转到登录页面...' });
    }
});
// 渲染文章添加页面
admin.get('/article-add', (req, res) => {
    // 切换侧边栏
    req.app.locals.currentPage = 'article';
    res.render('admin/article-edit', {
        link: '/admin/article-add',
        submit: '添加'
    });
});
// 实现文章添加功能
admin.post('/article-add', (req, res) => {
    // 1.创建解析表单的对象
    const form = new formidable.IncomingForm();
    // 2.配置上传的文件配置
    form.uploadDir = path.join(__dirname, '../', 'public', 'uploads');
    // 3.保留上传文件的后缀
    form.keepExtensions = true;
    // 4.解析表单
    form.parse(req, async(err, fileds, files) => {
        // 1.err保存解析失败的错误信息，解析成功err = null
        // 2.fileds 对象类型 保存普通的表单
        // 3.files 对象类型 保存上传的文件的数据
        if (!err) {
            // 表单解析成功
            // 将信息插入到数据库
            try {
                await Article.create({
                    tittle: fileds.tittle,
                    author: fileds.author,
                    publishDate: fileds.publishDate,
                    // 截取文件保存路径的后半段
                    cover: files.cover.path.split('public')[1],
                    content: fileds.content
                });
            } catch (error) {
                // 添加失败
                console.log(error.message);
                res.status(500).render('admin/error', { msg: '服务器出错， 3s后跳转到登录页面...' });
            }
            // 重定向到文章列表页面
            res.redirect('/admin/article');
        } else {
            res.status(500).render('admin/error', { msg: '服务器出错， 3s后跳转到登录页面...' });
        }
    });
});
// 渲染文章编辑页面
admin.get('/article-edit', async(req, res) => {
    const id = req.query.id;
    // 查找当前编辑的文章
    let articleInfo = await Article.findOne({ _id: id });
    res.render('admin/article-edit', {
        articleInfo,
        link: '/admin/article-edit',
        submit: '修改'
    });
});
// 实现文章编辑功能
admin.post('/article-edit', (req, res) => {
    // 1.创建解析表单的对象
    const form = new formidable.IncomingForm();
    // 2.配置上传的文件配置
    form.uploadDir = path.join(__dirname, '../', 'public', 'uploads');
    // 3.保留上传文件的后缀
    form.keepExtensions = true;
    // 4.解析表单
    form.parse(req, async(err, fileds, files) => {
        // 1.err保存解析失败的错误信息，解析成功err = null
        // 2.fileds 对象类型 保存普通的表单
        // 3.files 对象类型 保存上传的文件的数据
        if (!err) {
            // 表单解析成功
            // 将信息插入到数据库
            try {
                await Article.updateOne({
                    tittle: fileds.tittle,
                    publishDate: fileds.publishDate,
                    // 截取文件保存路径的后半段
                    cover: files.cover.path.split('public')[1],
                    content: fileds.content
                });
            } catch (error) {
                // 添加失败
                res.status(500).render('admin/error', { msg: '服务器出错， 3s后跳转到登录页面...' });
            }
            // 重定向到文章列表页面
            res.redirect('/admin/article');
        } else {
            res.status(500).render('admin/error', { msg: '服务器出错， 3s后跳转到登录页面...' });
        }
    });
});
// 实现文章删除功能
admin.post('/article-delete', async(req, res) => {
    const id = req.body.deleteArticleID;
    //    删除id对应的文章
    await Article.findOneAndDelete({ _id: id });
    // 重定向到文章列表
    res.redirect('/admin/article');

});


// 导出admin路由模块
module.exports = admin;