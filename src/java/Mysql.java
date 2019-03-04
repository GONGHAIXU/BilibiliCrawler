import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
//数据库操作类
public class Mysql {
    //得到SqlSession对象
    public SqlSession getSqlSession()throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
    //插入视频
    synchronized public void insertVideo(SqlSession sqlSession,Video video){
        sqlSession.insert("VideoMapper.insertVideo",video);
        sqlSession.commit();
    }
}
