import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseReviewsPage{
    Document doc;
    String url;
    public ParseReviewsPage(String url) throws IOException {
        setDoc(url);
        this.url = url;
    }

    public void setDoc(String url) throws IOException {
        this.doc = Jsoup.connect(url).get();
    }

    public Document getDoc() {
        return doc;
    }
    public static List<String> parse_reviews_page(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements reviews = doc.getElementsByClass("pp-review-i").select("article");
        List out = new ArrayList();
        for (Element review: reviews
                ) {

            String star = review.getElementsByClass("g-rating-stars-i").select("span").attr("content");
            Elements comments = review.getElementsByClass("pp-review-text").select("div");
            out.add(star);
            out.add(comments.first().getElementsByClass("pp-review-text-i").text());
        }
        return out;
    }

}
