package WHS_planner.Util;

/**
 * Created by spam on 11/18/2016.
 */
public class MemoryCredentials {
    private String ipassUsername;
    private String ipassPassword;

    public MemoryCredentials(String ipassUsername, String ipassPassword) {
        this.ipassUsername = ipassUsername;
        this.ipassPassword = ipassPassword;
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
}
