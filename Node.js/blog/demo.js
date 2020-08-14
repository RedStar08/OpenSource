// 引入hash.js
const hash = require('./hash.js');
const cipher = hash.cipher;
const decipher = hash.decipher;


let decrypted = decipher.update('44aa503b7d04b504fa1b4cb20ac82292eb509d0aaa7e6cc61bc51e082dc11e47', 'hex', 'utf8');
decrypted += decipher.final('utf8');
console.log(decrypted); // 打印: 要加密的数据