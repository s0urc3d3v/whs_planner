package WHS_planner.Schedule;

import WHS_planner.Core.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class grabDay
{
    final String USER_AGENT = "Mozilla/5.0";
    private HttpURLConnection connection;

    private String user;
    private String pass;

    private List<String> cookies;

    public grabDay(String user, String pass)
    {
        this.user = user;
        this.pass = pass;
    }

    public void grabData()
    {
        String url = "https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html";
        String calurl = "https://ipass.wayland.k12.ma.us/school/ipass/hello.html";

        CookieHandler.setDefault(new CookieManager());

        Grabber grabber = new Grabber();

        try
        {
            String page = grabber.getPageContent(url);
            String params = grabber.getForm(page, user, pass);

            grabber.send(url, params);

            String res = grabber.getPageContent(calurl+"?month=11&day=2&year=2016");
            System.out.println(res);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*public void buildCalendar() throws Exception
    {


        String url = "https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html";



        URL ipass = new URL("https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(ipass.openStream()));

        String inputline;
        while((inputline = br.readLine()) != null)
        {
            System.out.println(inputline);
        }

        br.close();
    }*/

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
            StringBuffer response = new StringBuffer();

            while((inLine = br.readLine()) != null)
            {
                response.append(inLine);
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

                params.add(key + URLEncoder.encode(val, "UTF-8"));
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

            for (String cookie : cookies)
            {
                connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }

            connection.setDoOutput(true);
            connection.setDoInput(true);

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(params);
            dos.flush();
            dos.close();

            int response = connection.getResponseCode();

            System.out.printf("\nSending POST request to "+url);
            System.out.println("Parameters : " + params);
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

