const gate = (req, res, next) => {
    // 没有登录请求其他页面进行拦截
    if (!req.session.username && req.url != '/login') {
        res.redirect('/admin/login');
    } else {
        // 已经登录，判断用户类型
        if (req.session.role == 'normal') {
            res.redirect('/home');
        }
        next();
    }
}

module.exports = gate;