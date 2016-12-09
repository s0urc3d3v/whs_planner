package WHS_planner.Util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by matthewelbing on 08.12.16.
 */
public class XorTool { //encrypts string with key
    public static String encode(String data, String key){
        return base64Encode(XorTool.XorWithKey(data.getBytes(), key.getBytes()));
    }
    public static String decode(String data, String key){
        return new String(XorTool.XorWithKey(base64Decode(data), key.getBytes()));
    }
    private static byte[] XorWithKey(byte[] data, byte[] key){
        byte[] x = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            x[i] = (byte) (data[i] ^ key[i%key.length]);
        }
        return x;
    }
    private static byte[] base64Decode(String data){
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private static String base64Encode(byte[] data){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data).replaceAll("\\s", "");
    }

}
