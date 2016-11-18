package WHS_planner.Util;

import javax.crypto.Cipher;

/**
 * Created by spam on 11/18/2016.
 */
public class encryptPasswordWithDes {
    public static String encryptPassword(String password){

        try {
            Cipher eCypher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            byte[] utf8ByteArr = password.getBytes();

            byte[] encryptedData = eCypher.doFinal(utf8ByteArr);

            return new sun.misc.BASE64Encoder().encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptPassword(String password){

        try {
            Cipher dCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            byte[] data = new sun.misc.BASE64Decoder().decodeBuffer(password);

            byte[] utf8ByteArr = dCipher.doFinal();
            return new String(utf8ByteArr, "UTF8");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
