package WHS_planner.Util;

import java.security.Key;

/**
 * Created by spam on 11/18/2016.
 */
public class MemoryCredentials {
    private String ipassUsername;
    private String ipassPassword;
    private Key AES_KEY;

    public MemoryCredentials(String ipassUsername, String ipassPassword, Key
            AES_KEY) {
        this.ipassUsername = ipassUsername;
        this.ipassPassword = ipassPassword;
        this.AES_KEY = AES_KEY;
    }

    public String getIpassUsername() {
        return ipassUsername;
    }

    public void setIpassUsername(String ipassUsername) {
        this.ipassUsername = ipassUsername;
    }

    public String getIpassPassword() {
        return ipassPassword;
    }

    public void setIpassPassword(String ipassPassword) {
        this.ipassPassword = ipassPassword;
    }

    public Key getAES_KEY() {
        return AES_KEY;
    }

    public void setAES_KEY(Key AES_KEY) {
        this.AES_KEY = AES_KEY;
    }
}
