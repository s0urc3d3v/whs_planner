package WHS_planner.Core;

/**
 * Created by matthewelbing on 15.11.16.
 */
public class Creditials {
    public String passUser;
    public char[] ipassPass; //char[] are immediatly removed from memory whereas strings are not.  DO NOT STORE THIS AS A STRING

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }

    public char[] getIpassPass() {
        return ipassPass;
    }

    public void setIpassPass(char[] ipassPass) {
        this.ipassPass = ipassPass;
    }
}
