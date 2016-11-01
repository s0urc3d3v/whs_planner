package WHS_planner.Util;

/**
 * Created by jack on 10/31/16.
 */
public class OS {
    public enum OSType {
        WINDOWS,
        MAC,
        LINUX
    }

    public static OSType getOSType() {
        String OSDesc = System.getProperty("os.name");
        if(OSDesc.toLowerCase().contains("linux")) {
            return OSType.LINUX;
        } else if(OSDesc.toLowerCase().contains("windows")) {
            return OSType.WINDOWS;
        }
        return OSType.MAC;
    }
}
