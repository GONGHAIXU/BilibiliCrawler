import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
//工具类
public class Until {
    //弹幕api：https://api.bilibili.com/x/v1/dm/list.so?oid= + cid号
    public static final String barrageApi = "https://api.bilibili.com/x/v1/dm/list.so?oid=";
    // cid号，用于获取弹幕
    public static String cid = null;
    //获得Document对象
    public static Document getDoc(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    synchronized public static Video getVideo(String url){
        Document doc = getDoc(url);
        //根据视频url获得一个不完整的Video对象
        Video video = new Video(url);
        //判断该url是否还存有视频，以决定是否跳过该av号
        if (!doc.body().getElementById("app").getElementsByClass("error-body").isEmpty()){
            return null;
        }
        //获得能用Jsoup能解析出的数据
        Element element = doc.body().getElementsByClass("l-con").first();
        video.setName(element.getElementById("viewbox_report").getElementsByTag("h1").first().attr("title"));
        Elements elements = element.getElementsByClass("video-data");
        video.setUploadDate(elements.first().getElementsByTag("time").first().text());
        video.setIntroduction(element.getElementById("v_desc").getElementsByClass("info open").first().text());
        video.setCover(doc.select("meta[property=\"og:image\"]").first().attr("content"));
        element = doc.body().getElementsByClass("r-con").first();
        video.setAuthor(element.getElementsByClass("name").first().getElementsByTag("a").first().text());
        //根据api获得json对象并获得观看数和弹幕数和分p
        JSONObject object = Until.doPost("https://api.bilibili.com/x/web-interface/view?aid=" + video.getAvId());
        object = object.getJSONObject("data");
        video.setPlayNum(object.getJSONObject("stat").getIntValue("view"));
        video.setBarrageNum(object.getJSONObject("stat").getIntValue("danmaku"));
        //获得拥有的总p数量和每p地址，存入一个字符串对象中
        int x = object.getJSONArray("pages").size();
        String  s = "p" + 1 + ":" + video.getUrl() + "/?p=" + 1 + "\n";
        for(int i = 2 ; i <= x ; i ++){
            s += "p" + i + ":" + video.getUrl() + "/?p=" + i + "\n";
        }
        video.setAllP(s);
        //根据弹幕api获得弹幕
        //根据上面获得的json对象获得cid号并得到弹幕api
        cid = barrageApi + object.getJSONArray("pages").getJSONObject(0).getString("cid");
        //根据弹幕api解析出弹幕并存入Video对象
        try {
            Document document = Jsoup.connect(cid).get();
            video.setBarrage(document.getElementsByTag("d").text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return video;
    }
    //根据url获得json对象
    public static JSONObject doPost(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //设置header
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                JSONObject obj = JSONObject.parseObject(EntityUtils.toString(responseEntity,"UTF-8"));
                return obj;
            }
            else{
                System.out.println(state);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (response != null)
                    response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}