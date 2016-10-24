package WHS_planner.News.html;

import java.net.MalformedURLException;
import java.net.URL;

public class ScanTest {
    public static void main(String[] Args) throws MalformedURLException {

        URL testArticle = new URL("http://waylandstudentpress.com/50598/articles/maggie-fiske-i-felt-like-the-way-i-felt-in-my-bedroom-dancing-by-myself/");
        HTMLScanner html_Body = new HTMLScanner();
//        System.out.println(html_Body.scanURL(testArticle));

        System.out.println(html_Body.scanHTML(html_Body.scanURL(testArticle)));

//        html_Body.scanHTML(html_Body.scanURL(testArticle));
//        html_Body.find(new Scanner(html_Body.scanURL(testArticle)), "prettyPhoto[1]");

    }

}
