import org.apache.ibatis.session.SqlSession;
import java.io.IOException;
//线程类
public class MyThread implements Runnable{
    //av号，每个线程共享
    private int av = 2;
    Mysql mysql = new Mysql();
    String url = "https://www.bilibili.com/video/av";
        synchronized public void run(){
            try {
                //得到SqlSession对象
                SqlSession sqlSession = mysql.getSqlSession();
                Video video;
                //用一个死循环不断改变av号来爬取视频
                while(true){
                    //判断该av号对应的视频是否存在
                    if((video = Until.getVideo(url + av)) != null){
                            //得到Video对象
                            video = Until.getVideo(url + av);
                            //将Video对象插入数据库
                            mysql.insertVideo(sqlSession,video);
                        }
                    av++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
