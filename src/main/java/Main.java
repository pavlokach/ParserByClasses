import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        parse_category("https://rozetka.com.ua/ua/mobile_charger/c146341/");
    }

    public static void parse_category(String url) throws Exception{

        Document doc = Jsoup.connect(url).get();
        int num = Integer.parseInt(doc.getElementsByClass("novisited paginator-catalog-l-link").last().text());
        System.out.println("num = " + num);
        for (int i = 0; i < num; i++){
            System.out.println(i);
            parse_category_page(url + "page=" + i + "/");
        }
    }

    public static void parse_category_page(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("div.g-i-tile-i-title").nextAll();
        for (Element link:links) {
            if (!(link.select("a").attr("href").isEmpty()))
                parse_reviews(link.select("a").attr("href"));
            //parse_reviews(link.select("a").attr("href"));
        }
    }
    public static void parse_reviews(String url) throws IOException {
        System.out.println("url = " + url);
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("div.g-i-tile-i-title").nextAll();
        int num = 0;
        if (!(doc.getElementsByClass("paginator-catalog-l-link").last() == null))
            num = Integer.parseInt(doc.getElementsByClass("paginator-catalog-l-link").last().text());
        List out = new ArrayList();
        for (int i = 0; i < num; i++){
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