package WHS_planner.Core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by matthewelbing on 23.09.16.
 * Grabs Schedule from IPass
 *
 * RIP Selenium we will never forget you
 */
public class ReadSchedule {
    private String username;
    private String password;
    private StringBuffer verificationErrors = new StringBuffer();
    private boolean acceptNextAlert = true;
    private String pageSource;
    public ReadSchedule (){}

    private HttpURLConnection connection;
    private List<String> cookies;
    private final String USER_AGENT = "Mozilla/5.0";

    public void authAndFindTableWithIpass(String user, String pass) throws Exception {
        String url = "https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html";
        CookieHandler.setDefault(new CookieManager());
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        Grabber grabber = new Grabber();
        try {
            String page = grabber.getPageContent(url);
            String params = grabber.getForm(page, user, pass);
            grabber.send(url, params);
            //String res = grabber.getPageContent("https://ipass.wayland.k12.ma.us/school/ipass/samschedule.html");
            File output = new File("raw.html");
            FileWriter fileWriter = new FileWriter(output);
            fileWriter.write(grabber.getPageContent("https://ipass.wayland.k12.ma.us/school/ipass/samschedule.html?m=506&amp;pr=19&amp;dt=09261649872"));
            connection.disconnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected class Grabber
    {
        private Grabber()
        {

        }

        private String getPageContent(String url) throws Exception
        {
            URL ipass = new URL(url);
            connection = (HttpsURLConnection) ipass.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent", USER_AGENT);

            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");

            if (cookies != null)
            {
                for (String cookie : cookies)
                {
                    connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
                }
            }

            int resp = connection.getResponseCode();

            System.out.println("\nSending GET request to " + url);
            System.out.println("Response code is: "+resp);

            InputStreamReader ir = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(ir);

            String inLine;
            StringBuilder response = new StringBuilder();

            while((inLine = br.readLine()) != null)
            {
                response.append(inLine);
                response.append("\n");
            }
            br.close();
            ir.close();

            return response.toString();
        }

        private String getForm(String html, String user, String pass) throws Exception
        {
            System.out.println("Getting form data...");

            Document doc = Jsoup.parse(html);

            Element loginform = doc.getElementById("login");
            Elements inputelements = loginform.getElementsByTag("input");

            ArrayList<String> params = new ArrayList<String>();

            for(Element el : inputelements)
            {
                String key = el.attr("name");
                String val = el.attr("value");

                if(key.equals("userid"))
                {
                    val = user;
                }
                else if(key.equals("password"))
                {
                    val = pass;
                }

                params.add(key + "=" + URLEncoder.encode(val, "UTF-8"));
            }

            StringBuffer buffer = new StringBuffer();

            for(String param : params)
            {
                if(buffer.length() == 0)
                {
                    buffer.append(param);
                }
                else
                {
                    buffer.append("&" + param);
                }
            }

            return buffer.toString();
        }

        private void send(String url, String params) throws Exception
        {
            System.out.println("Attempting to send data");

            URL obj = new URL(url);

            connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");



            connection.setDoOutput(true);
            connection.setDoInput(true);

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(params);
            dos.flush();
            dos.close();


            int response = connection.getResponseCode();

            System.out.println("\nSending POST request to "+url);
//            System.out.println("Parameters : " + params); //Not safe plz don't use
            System.out.println("Response code:" + response);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer stringbuffer = new StringBuffer();

            while((input = br.readLine()) != null)
            {
                stringbuffer.append(input);
            }
            br.close();

        }

        private List<String> getCookies()
        {
            return cookies;
        }

        private void setCookies(List<String> cs)
        {
            cookies = cs;
        }
    }
}