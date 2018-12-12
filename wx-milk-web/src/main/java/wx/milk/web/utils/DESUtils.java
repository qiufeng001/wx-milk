package wx.milk.web.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * auther: kiven on 2018/7/18/018 9:52
 * try it bast!
 */
public class DESUtils {
    protected static Log logger = LogFactory.getLog(DESUtils.class);

    /**
     * 对字符串加密
     *
     * @param str
     * @return

     */
    public static byte[] encrytor(String str, String key){
        byte[] bytes = null;
        try{
            if (str == null || key == null)
                return null;
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
            bytes = cipher.doFinal(str.getBytes("utf-8"));

        }catch (Exception e) {
            logger.error(e);
        }
        return bytes;
    }

    /**
     * 使用base64解决乱码
     *
     * @param secretKey
     *            加密后的字节码
     */
    public static String jdkBase64String(byte[] secretKey) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(secretKey);
    }

    /**
     * 使用jdk的base64 解密字符串 返回为null表示解密失败
     *
     * @throws IOException
     */
    public static byte[] jdkBase64Decoder(String str) {
        byte[] bytes = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            bytes = decoder.decodeBuffer(str);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
