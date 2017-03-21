package WHS_planner.Util;

/**
 * Created by george_jiang on 3/21/17.
 */
public class UserLoggedIn {
    private static boolean loggedIn;

    public static boolean isUserLoggedIn() {
        return loggedIn;
    }

    public static void logIn() {
        loggedIn = true;
    }

    public static void logOut() {
        loggedIn = false;
    }
}
