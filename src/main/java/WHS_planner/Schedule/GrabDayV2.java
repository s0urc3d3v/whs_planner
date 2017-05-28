package WHS_planner.Schedule;

import WHS_planner.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/*
* This is used to download the html required to parse the schedule
*
* The GrabDayV2 object takes in two strings: the username and the password
*
*/

public class GrabDayV2 {

    private String error;

    // Holds cookies
    private List<String> cookies;


    private String user, password;

    // connection variable
    private HttpsURLConnection connection;

    // browser stuff
    private final String USER_AGENT = "Mozilla/5.0";

    // login url
    String url = "https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html";

    // constructor
    public GrabDayV2(String user, String password) {
        this.user = user;
        this.password = password;

    }


    // used to test login credentials
    public boolean testConn() throws Exception
    {

        String url = "https://ipass.wayland.k12.ma.us/school/ipass/syslogin.html";


        // sets up cookies for use
        CookieHandler.setDefault(new CookieManager());
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);


        // grabber object
        Grabber grab = new Grabber();

        // downloads login page
        String page = grab.getPageContent(url);
        
        // figures out parameters
        String params = grab.getForm(page, user, password);

        // inputs parameters into page
        grab.send(url, params);

        // disconnects
        connection.disconnect();

        // if the username or password are incorrect, it will return false, otherwise it will be true
        // the error variable is set by the grabbers 'send' method
        return !error.contains("Invalid");
    }

    // writes the schedule html into index.html
    public void getSchedule() throws Exception{
        // cookie stuff
        CookieHandler.setDefault(new CookieManager());
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        // instantiates grabber
        Grabber g = new Grabber();

        // this string holds the entire page, using the url variable (login page)
        String html = g.getPageContent(url);
        // uses html string to get the form information, allowing the program to input username and password
        String params = g.getForm(html, user, password);
        // puts parameters in and logs in
        g.send(url, params);

        // grabs the schedule page, which shows the first quarter
        String s1 = g.getPageContent("https://ipass.wayland.k12.ma.us/school/ipass/samschedule.html");
        // sets up jsoup to parse it
        Document d = Jsoup.parse(s1);
        // this is finding all the anchors (links) on the page, and the second one (index of 1) is the link to 'print schedule'
        Element e = d.getElementsByTag("a").get(1);
        // turns it into a string
        String anchor = e.toString();

        // anchor variable finds the unique token to access the print page
        anchor = anchor.substring(anchor.indexOf("sam"), anchor.indexOf("')"));
        // gets the 'print schedule' page, using the token from the anchor variable
        String fin = g.getPageContent("https://ipass.wayland.k12.ma.us/school/ipass/"+anchor);

        // opens the output.html file
        File f = new File(Main.SAVE_FOLDER + File.separator +"output.html");
        // writes the html to the file
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(fin);
        // close writer
        bw.close();

        // disconnect
        connection.disconnect();

    }


    /*
    * Object used to download pages, find login parameters, and login
    *
    * Takes in no parameters
    */
    private class Grabber
    {
        // Empty constructor
        private Grabber()
        {

        }

        // downloads page, taking in the url as a parameter
        private String getPageContent(String url) throws Exception
        {
            // turns string url into a URL object
            URL ipass = new URL(url);
            // sets the connection to the given URL object
            connection = (HttpsURLConnection) ipass.openConnection();
            
            // Browser stuff
            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent", USER_AGENT);

            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");

            // Adds cookies to the request, so if you login, you can access pages 
            // like the schedule page if they require authentification
            if (cookies != null)
            {
                for (String cookie : cookies)
                {
                    //System.out.println(cookie);
                    connection.setRequestProperty("Cookie", cookie.split(";", 2)[1]);
                }
            }

            // holds response code for error checking
            int resp = connection.getResponseCode();

            //System.out.println("\nSending GET request to " + url);
            //System.out.println("Response code is: "+resp);

            // stream reads each line of html and adds it to string builder object
            InputStreamReader ir = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(ir);

            String inLine;
            StringBuilder response = new StringBuilder();

            // If it isn't the end of the file, add it to the string builder with a newline
            while((inLine = br.readLine()) != null)
            {
                response.append(inLine);
                response.append("\n");
            }
            // close stream objects
            br.close();
            ir.close();

            // return the stringbuilder as a string
            return response.toString();
        }


        // takes in the html string, the username, and password, and returns the parameters
        private String getForm(String html, String user, String pass) throws Exception
        {
            //System.out.println("Getting form data...");

            // allows html string to be parsed
            Document doc = Jsoup.parse(html);

            // Element loginform is the reference to the login button
            Element loginform = doc.getElementById("login");
            // These are all the form inputs
            Elements inputelements = loginform.getElementsByTag("input");

            // holds the parameters
            ArrayList<String> params = new ArrayList<>();

            // goes through each of the inputs, and if it finds the username and password, 
            // sets the value of the form to its respective element
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

            // adds each parameter to the stringbuffer object
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

            // returns as a normal string
            return buffer.toString();
        }


        // Uses the parameters and the url to deal with login
        private void send(String url, String params) throws Exception
        {
            //System.out.println("Attempting to send data");

            // creates url object and opens connection
            URL obj = new URL(url);

            connection = (HttpsURLConnection) obj.openConnection();

            // browser stuff
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");

            // adds cookies if they exist
            if(cookies != null) {
                for (String cookie : cookies) {
                    connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
                }
            }


            // uses the parameters and fills them in
            connection.setDoOutput(true);
            connection.setDoInput(true);

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            //System.out.println(params);
            dos.writeBytes(params);
            dos.flush();
            dos.close();


            // debug response code
            int response = connection.getResponseCode();

            //System.out.println("\nSending POST request to "+url);
           // System.out.println("Parameters : " + params); //Unsafe
           // System.out.println("Response code:" + response);

            // grabs response and assigns it to the error string
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer stringbuffer = new StringBuffer();

            while((input = br.readLine()) != null)
            {
                stringbuffer.append(input);
            }


            // adds the login cookie to the cookies variable
            setCookies(connection.getHeaderFields().get("Set-Cookie"));
            error = stringbuffer.toString();
            // closes buffer
            br.close();
        }

        // sets and grabs cookies from the cookies list
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
