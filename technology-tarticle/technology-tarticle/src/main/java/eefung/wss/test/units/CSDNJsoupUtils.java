package eefung.wss.test.units;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import eefung.wss.test.entity.Tnew;

public class CSDNJsoupUtils {
    private static List<String> href=new ArrayList<>();
    public static List<String> csdnArticleLink(String url) throws Exception {
        List<String> href = new ArrayList<>();
        Connection conn = Jsoup.connect(url).timeout(30000);
        Document doc = conn.get();
        // System.out.println(doc.html());
        Elements ele = doc.select(".feedlist_mod").select(".home").select("a");
        for (Element element : ele) {
            String a = element.attr("href");
            // 拼接完整的连接
            String full =a;
            href.add(full);
        }
        return href;
    }
    public static List<Tnew> getCSDNTnews() throws Exception{
        List<Tnew> tnews = new ArrayList<>();
        List<String> urls=getCSDNContentUrl();
        for (String url : urls) {
            tnews.add(CSDNJsoupUtils.csdnArticle(url));
        }
        return tnews;
    }
    public static List<String> getCSDNContentUrl() throws Exception{
        String url="https://www.csdn.net/";
        //获取某一页的所有电影详情页下的连接
        for (String link : CSDNJsoupUtils.csdnArticleLink(url)) {
            Pattern pattern =Pattern.compile("https{0,1}://blog.csdn.net/.*?/article/details/\\d+");
            Matcher matcher = pattern.matcher(link);
            if(matcher.matches()) {
                if(!href.contains(link)) {
                    href.add(link);
                }
            }
        }
        return href;
    }
    public static Tnew csdnArticle(String url) throws IOException{
        Tnew tnew =new Tnew();
        Connection conn=Jsoup.connect(url).timeout(30000);
        Document doc = conn.get();
        String date = doc.select(".article-bar-top").select(".d-flex").select("span").get(0).text();
        String content = "";
        for (Element element : doc.select(".article_content").select("p")) {
            content +=element.text()+"\n";
        }
        String title =doc.select("h1[class=title-article]").text();
        tnew.setDate(date);
        tnew.setContent(content);
        tnew.setTitle(title);
        return tnew;
    }
    public static void main(String[] args) throws IOException {
        csdnArticle("https://blog.csdn.net/B9Q8e64lO6mm/article/details/81267382");
    }
    
}
