// 导入加密算法包
import util.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.security.MessageDigest;
public class Main {
    public static void main(String args[]) {
        //         DES 加密-解密
        String des_es = DES.encrypt("secret key", "redstar08");
        String des_ds = DES.decrypt("secret key", des_es);
        System.out.println(des_es);
        System.out.println(des_ds);
        String des_esfile = DES.encryptFile("secret key",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\cat.jpg",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\cat_es.jpg");
        String des_dsfile = DES.decryptFile("secret key",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\cat_es.jpg",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\cat_ds.jpg");
        System.out.println(des_esfile);
        System.out.println(des_dsfile);

        //         AES 加密-解密
        String aes_es = AES.encrypt("RedStarSecretKey", "redstar08");
        String aes_ds = AES.decrypt("RedStarSecretKey", aes_es);
        System.out.println(aes_es);
        System.out.println(aes_ds);
        String aes_esfile = DES.encryptFile("secret key",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\dog.jpg",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\dog_es.jpg");
        String aes_dsfile = DES.decryptFile("secret key",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\dog_es.jpg",
                "E:\\Codes\\JAVA\\Cryptograph\\src\\images\\dog_ds.jpg");
        System.out.println(aes_esfile);
        System.out.println(aes_dsfile);

        //         RSA 加密-解密
        try {
            Map<String, String> rsa_map = RSA.generateKeyPair();
            String rsa_pub = rsa_map.get("publicKey");
            String rsa_pri = rsa_map.get("privateKey");
            String rsa_data = "redstar08";
            System.out.println("public Key: " + rsa_pub);
            System.out.println("private Key: " + rsa_pri);
            String en_result = RSA.encrypt(rsa_data, rsa_pub);
            System.out.println("encrypt data: " + en_result);
            String de_result = RSA.decrypt(en_result, rsa_pri);
            System.out.println("decrypt data: " + de_result);
        }catch (Exception e) {
            // TOD0 Auto-generated catch block
            e.printStackTrace();
        }

        //         SHA256 信息摘要算法
        try {
            String sha256_data1 = "redstar08";
            String sha256_data2 = "redstar081";
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(sha256_data1.getBytes("UTF-8"));
            String sha256_info1 = new String(Base64.encode(messageDigest.digest()));
            messageDigest.update(sha256_data2.getBytes("UTF-8"));
            String sha256_info2 = new String(Base64.encode(messageDigest.digest()));
            System.out.println(sha256_info1);
            System.out.println(sha256_info2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        //         RSA 签名验证
        try {
            Map<String, String> map = RSA.generateKeyPair();
            String pub = map.get("publicKey");
            String pri = map.get("privateKey");
            String data = "redstar08";
            System.out.println("public Key: " + pub);
            System.out.println("private Key: " + pri);
            String sig = RSA.sign(data, pri);
            System.out.println("sign: " + sig);
            System.out.println(RSA.checkSign(data, sig, pub)); //succeed
            System.out.println(RSA.checkSign(data + "?", sig, pub)); //failed
        }catch (Exception e) {
            // TOD0 Auto-generated catch block
            e.printStackTrace();
        }
    }
}
