import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ParseReviews extends ParseReviewsPage {

    public ParseReviews(String url) throws IOException {
        super(url);
    }

    public void parse_reviews(String url) throws IOException {
        System.out.println("url = " + url);
        Elements links = doc.select("div.g-i-tile-i-title").nextAll();
        int num = 0;
        if (!(doc.getElementsByClass("paginator-catalog-l-link").last() == null))
            num = Integer.parseInt(doc.getElementsByClass("paginator-catalog-l-link").last().text());
        List out = new ArrayList();
        for (int i = 0; i < num; i++) {
            String link = url + "page=" + i + "/";
            out.add(parse_reviews_page(link));
        }
        url = url.split("/")[4] + ".csv";
        System.out.println(url);
        PrintWriter pw = new PrintWriter(new File("data/" + url));
        pw.write(out.toString());
        pw.close();
        System.out.println("written");

    }

}
