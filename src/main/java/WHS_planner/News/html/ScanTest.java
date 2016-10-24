package WHS_planner.News.html;

import java.net.MalformedURLException;
import java.net.URL;

public class ScanTest {
    public static void main(String[] Args) throws MalformedURLException {

        URL testArticle = new URL("http://waylandstudentpress.com/50598/articles/maggie-fiske-i-felt-like-the-way-i-felt-in-my-bedroom-dancing-by-myself/");
        HTMLScanner ayychtee_em_el = new HTMLScanner();
        System.out.println(ayychtee_em_el.scanURL(testArticle));
    }

}
