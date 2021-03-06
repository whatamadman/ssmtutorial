package com.ssm.service;

import com.ssm.mapper.TeacherMapper;
import com.ssm.model.Teacher;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yang on 06/04/2017.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherService {

    private static SqlSessionFactory sessionFactory;
    private static ApplicationContext ctx;
    private static TeacherMapper teacherMapper;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Teacher getTeacher(@PathVariable int id) {

//        try {
//            ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
//            teacherMapper = (TeacherMapper) ctx.getBean("teacherMapper");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        String mybatisResource = "mybatis.xml";

        /* 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件） */
        try{
            InputStream ins = Resources.getResourceAsStream(mybatisResource);
            //构建sqlSession的工厂
            sessionFactory = new SqlSessionFactoryBuilder().build(ins);
        }catch(Exception e){
            e.printStackTrace();
        }

        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();

        /**
         * 映射sql的标识字符串
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
//        String statement = "com.vstudio.mapping.Teacher.selectTeacherByID";//映射sql的标识字符串
//        Teacher teacher = session.selectOne(statement, 8000);

        TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);
        Teacher teacher = teacherMapper.selectTeacherByID(id);

        return teacher;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Teacher> getAllTeacher() {

//        try {
//            ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
//            teacherMapper = (TeacherMapper) ctx.getBean("teacherMapper");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        String mybatisResource = "mybatis.xml";

        /* 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件） */
        try{
            InputStream ins = Resources.getResourceAsStream(mybatisResource);
            //构建sqlSession的工厂
            sessionFactory = new SqlSessionFactoryBuilder().build(ins);
        }catch(Exception e){
            e.printStackTrace();
        }

        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        TeacherMapper teacherMapper = session.getMapper(TeacherMapper.class);

        List<Teacher> teachers = teacherMapper.selectTeachers();

        return teachers;
    }
}
