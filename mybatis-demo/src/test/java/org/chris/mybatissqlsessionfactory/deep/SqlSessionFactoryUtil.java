package org.chris.mybatissqlsessionfactory.deep;

import lombok.NoArgsConstructor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
/**
 * @Auther: chris
 * @Date: 2019/4/13 12:07
 * @Description: 自己创建sqlsessionfactory sqlsession
 */
@NoArgsConstructor
public class SqlSessionFactoryUtil {
    //类线程锁
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;
    //SqlSessionFactory对象
    private static SqlSessionFactory sqlSessionFactory = null;

    private static SqlSessionFactory getInstance() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(SqlSessionFactoryUtil
                    .class.getName()).log(Level.SEVERE, null, e);
        }
        synchronized (CLASS_LOCK) {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSqlSession() {
        if (sqlSessionFactory == null) {
            getInstance();
        }
        /**
         * sqlSessionFactory.openSession(); 已SIMPLE 执行器形式创建sqlSession
         * sqlSessionFactory.openSession(ExecutorType.BATCH); 以BATCH 执行器形式创建sqlSession （用于批量更新）
         *
         * */
        return sqlSessionFactory.openSession();
    }
}