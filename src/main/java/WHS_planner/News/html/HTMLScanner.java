package WHS_planner.News.html;

public class HTMLScanner {

    public String scanDescription(String content) {
        String link;
        if (content.contains("src")) {
            content = content.substring(content.indexOf("src=") + 5, content.length());
            link = content.substring(0, content.indexOf("\""));
            return link;
        } else {
            return null;
        }
    }

//    public String scanURL(URL link) {
//        String content = null;
//        URLConnection connection;
//        try {
////          connection is a url
//            connection = link.openConnection();
//            Scanner scanner = new Scanner(connection.getInputStream());
//            scanner.useDelimiter("\\Z");
//            content = scanner.next();
//            scanner.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return content;
//    }


//    public String scanHTML(String content) {
//        String link;
//        if (content.contains("prettyPhoto[1]")) {
//            content = content.substring(content.indexOf("prettyPhoto[1]") + 22, content.length());
//            link = content.substring(0, content.indexOf("\""));
//            System.out.println(link);
//            return link;
//        } else {
//            return null;
//        }
//    }



}

