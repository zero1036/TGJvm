package com.tg.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * MybatisBase
 * Created by linzc on 2017/7/13.
 */
public class MybatisBase {

    @Test
    public void testSelect() {
        //mybatis的配置文件
        String resource = "conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = MybatisBase.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();

        /**
         * 映射sql的标识字符串，
         * com.tg.mybatis.mapping.lotteryMapper是lotteryMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "com.tg.mybatis.mapping.lotteryMapper.getLotteryActivities";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        List<LotteryActivity> list = session.selectList(statement);

        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        LotteryActivity activity = new LotteryActivity();
        activity.setTitle("abc2");
        activity.setGameType(2);
        activity.setDel(true);
        int target = sqlSession.insert("com.tg.mybatis.mapping.lotteryMapper.addActivity", activity);

        //使用SqlSession执行完SQL之后需要关闭SqlSession
        sqlSession.close();
        System.out.println(target);
    }

    @Test
    public void testSelectByAutoMapper() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        //得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
        LotteryMapper mapper = sqlSession.getMapper(LotteryMapper.class);
//        LotteryActivity activity = new LotteryActivity();
//        activity.setTitle("abc");
//        activity.setGameType(2);
//        activity.setDel(false);

        List<LotteryActivity> list = mapper.selectAll();
        //使用SqlSession执行完SQL之后需要关闭SqlSession
        sqlSession.close();
        System.out.println(list);
    }


}
