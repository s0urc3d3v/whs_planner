package WHS_planner.Util;

/**
 * Created by spam on 11/18/2016.
 */
public class MemoryCredentials {
    private String ipassUsername;
    private char[] ipassPassword;

    public MemoryCredentials(String ipassUsername, char[] ipassPassword) {
        this.ipassUsername = ipassUsername;
        this.ipassPassword = ipassPassword;
    }

    public String getIpassUsername() {
        return ipassUsername;
    }

    public void setIpassUsername(String ipassUsername) {
        this.ipassUsername = ipassUsername;
    }

    public char[] getIpassPassword() {
        return ipassPassword;
    }

    public void setIpassPassword(char[] ipassPassword) {
        this.ipassPassword = ipassPassword;
    }
}
