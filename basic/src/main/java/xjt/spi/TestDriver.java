package xjt.spi;

import java.io.PrintWriter;
import java.sql.*;
import java.util.Iterator;
import java.util.ServiceLoader;

public class TestDriver{
    public static void main(String[] args) {
        //serviceloader();
        driver();
    }
    public static void driver(){
        try {
            //Class.forName("xjt.spi.TestDriver");
            /**
             *仅是初始化类，给静态资源赋值以及执行静态代码块。jdk1.5后可以不用调用。https://www.cnblogs.com/xrq730/p/4851944.html
             * */
            //Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLogWriter(new PrintWriter(System.out));
            String mysqlUserName = "zy_developer";
            String mysqlUserPwd = "smLfq9PM2mA69kT4";
            String mysqlUrl = "jdbc:mysql://172.17.38.185:3307/zhiya_monitor?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
            Connection connection = DriverManager.getConnection(mysqlUrl,mysqlUserName,mysqlUserPwd);
            System.out.println("connection="+connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("人生呐");
            if(resultSet.next()){
                System.out.println(resultSet.getString("了"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void serviceloader(){
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            Driver driver = iterator.next();
            System.out.println("driver="+driver.getClass()+",loader="+driver.getClass().getClassLoader());
        }
        System.out.println("current thread contextloader="+Thread.currentThread().getContextClassLoader());
        System.out.println("serviceloader loader="+serviceLoader.getClass().getClassLoader());
    }
}
