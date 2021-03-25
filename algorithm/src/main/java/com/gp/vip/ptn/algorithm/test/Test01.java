package com.gp.vip.ptn.algorithm.test;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: Fred
 * @date: 2020/8/14 8:05 下午
 * @description: (类的描述)
 */
@Slf4j
public class Test01 {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        int  x=5;
        int y= x--+--x;
        int a=5,b=6;
        System.out.println();
        System.out.println(a--);
        System.out.println(--b);
        System.out.println(y);

        Boolean  jjj= null;
        System.out.println(jjj=true);

        Class c= Students.class;
        Object obj = c.newInstance();
        Method[] ms= c.getMethods();
        Method  m = c.getDeclaredMethod("getName",null);
        System.out.println(m.invoke(obj,null));
        System.out.println((12+3/8)/4%3);
    }

    private Connection connection=null;

    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb" +
                "?useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=false&rewriteBatchedStatements=true","root","root");

            connection.setAutoCommit(false); //设置手动提交
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //批量保存人员信息
    public void saveBatchSql(int stuNum){
        try {

            //预编译sql对象,只编译一回
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO person(person_name, dept_id) VALUES (?, ?)");
            for (int i = 0; i < stuNum; i++) {
                ps.setString(1, "jack"+i);
                ps.setInt(2, i);
                ps.addBatch();//添加到批次
            }
            ps.executeBatch();//提交批处理
            connection.commit();//执行
            connection.close();
        } catch (SQLException e) {
            log.info("连接数据库失败");
            e.printStackTrace();
        }

    }

    //保存人员信息
    public void savePerson(Person p) throws SQLException {
        String sql = "INSERT INTO person(person_name, dept_id) VALUES (?, ?)";		//插入sql语句
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, p.getPersonName());
            ps.setInt(2, p.getDeptId());

            ps.executeUpdate();			//执行sql语句

            System.out.println("插入成功(*￣︶￣)");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection.close();
        }

    }

    //查询部门是否存在若不存在则创建部门
    public void createDept(){
        //查询部门是否存在
        String exist = "select dept_id  from dept d where d.dept_id=?";
        //创建部门
        String createDeptSql= "insert into dept( dept_id, dept_name )values (?, ?)";

    }

    public List<Person> queryPersons(){
        String qrySql="select id, person_name, dept_id  from person p " +
                "left join dept d " +
                "on p.dept_id = d.dept_id " +
                "where p.dept_id in " +
                "( select dept_id from person pp where pp.person_name=?) ";
        List<Person> people= Lists.newArrayList();
        //执行jdbc 查询获取people集合
        //people=...
        return people;
    }


}
@Data
class Person{
    int id;
    int deptId;
    String personName;
}
class Students {
    public String getName(){
        return "lee";
    }
}
