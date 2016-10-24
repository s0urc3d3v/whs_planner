package WHS_planner.News.html;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;



class HTMLScanner {

    String scanURL(URL link) {
        String content = null;
        URLConnection connection;
        try {
//            connection =  new URL("http://waylandstudentpress.com/50598/articles/maggie-fiske-i-felt-like-the-way-i-felt-in-my-bedroom-dancing-by-myself/").openConnection();
            connection = link.openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }


    String scanHTML(String content) {
        String link;
        if (content.contains("prettyPhoto[1]")) {
            content = content.substring(content.indexOf("prettyPhoto[1]") + 22, content.length());
            link = content.substring(0, content.indexOf("\""));
            return link;
        } else {
            return null;
        }
    }
}

