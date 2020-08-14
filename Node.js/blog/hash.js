// 引入crypto模块进行加解密
const crypto = require('crypto');

// 设置算法和加密密钥
const algorithm = 'aes-256-cbc';
const password = 'RedStar08';

// 改为使用异步的 `crypto.scrypt()` (密钥、盐值、密码的长度(Bytes))
// salt 应尽可能独特。 建议盐值是随机的并且至少 16 个字节长。
const key = crypto.scryptSync(password, 'liuhongxin.com.cn', 32);

// 使用 `crypto.randomBytes()` 生成随机的 iv向量，同时 iv向量 通常与密文一起传递用于解密。
// const iv = crypto.randomBytes(16);
// 此处为了简便，使用的静态的 iv向量 16个字节的全8
const iv = Buffer.alloc(16, 8); // 初始化向量。

// 通过算法、密钥和 iv向量 进行加密。
// const cipher = crypto.createCipheriv(algorithm, key, iv);

// 使用相同的算法、密钥和 iv向量 进行解密。
// const decipher = crypto.createDecipheriv(algorithm, key, iv);

/* // 处理要加密的数据
let encrypted = cipher.update('123456', 'utf8', 'hex');
encrypted += cipher.final('hex');
console.log(encrypted); // 打印: d8598432b898da5c31a62741541c1b4a2a28958b13002c830f024cd542be4e3a

// 处理要解密的数据
let decrypted = decipher.update(encrypted, 'hex', 'utf8');
decrypted += decipher.final('utf8');
console.log(decrypted); // 打印: 要加密的数据
 */

function encrypt(password) {
    // 通过算法、密钥和 iv向量 进行加密。
    const cipher = crypto.createCipheriv(algorithm, key, iv);
    // 对密码进行加密
    let encrypted = cipher.update(password, 'utf8', 'hex');
    encrypted += cipher.final('hex');
    return encrypted;
}

function decrypt(password) {
    // 使用相同的算法、密钥和 iv向量 进行解密。
    const decipher = crypto.createDecipheriv(algorithm, key, iv);
    // 对密码进行解密
    let decrypted = decipher.update(password, 'hex', 'utf8');
    decrypted += decipher.final('utf8');
    return decrypted;
}

// 加密解密函数
module.exports.encrypt = encrypt;
module.exports.decrypt = decrypt;