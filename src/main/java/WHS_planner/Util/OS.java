package WHS_planner.Util;

/**
 * Created by jack on 10/31/16.
 */
public class OS {
    public enum type {
        WINDOWS,
        MAC,
        LINUX
    }

    public static type getOSType() {
        String OSDesc = System.getProperty("os.name");
        if(OSDesc.toLowerCase().contains("linux")) {
            return type.LINUX;
        } else if(OSDesc.toLowerCase().contains("windows")) {
            return type.WINDOWS;
        }
        return type.MAC;
    }
}
