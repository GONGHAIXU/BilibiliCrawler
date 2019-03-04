import org.apache.commons.lang.StringUtils;
//实体类
public class Video {
    private String allP;
    private String name;
    private String cover;
    private String author;
    private String introduction;
    private int playNum;
    private int barrageNum;
    private String uploadDate;
    private String url;
    private long avId;
    private String barrage;

    public Video(String url){
        this.url = url;
        //根据url获得av号
        this.avId = Long.parseLong(StringUtils.substringAfterLast(url,"v"));
    }
    public String getBarrage() {
        return barrage;
    }

    public void setBarrage(String barrage) {
        this.barrage = barrage;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getAvId() {
        return avId;
    }

    public void setAvId(long avId) {
        this.avId = avId;
    }

    public String getAllP() {
        return allP;
    }

    public void setAllP(String allP) {
        this.allP = allP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public int getBarrageNum() {
        return barrageNum;
    }

    public void setBarrageNum(int barrageNum) {
        this.barrageNum = barrageNum;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
