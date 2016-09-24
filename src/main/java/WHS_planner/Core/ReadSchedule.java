package WHS_planner.Core;

import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Created by matthewelbing on 23.09.16.
 */
public class ReadSchedule {
    private String username;
    private String password;
    public ReadSchedule (){
        //getUserName and getPass gets users user and pass for ipass
    }
    private boolean auth(String user, String pass) throws Exception {
        URLConnection connection = authWithIpass("https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html", user, pass);
        return false;
    }
    public static URLConnection authWithIpass(String url_v, String username, String password) throws Exception {

        String data = URLEncoder.encode("Usuario", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
        data += "&" + URLEncoder.encode("Contrase", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

        URL url = new URL(url_v);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
        wr.close();
        return conn;
    }

}
