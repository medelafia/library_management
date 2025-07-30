package com.jaxrsproject.utils;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;


public class HibernateUtils {
    private static SessionFactory sessionFactory ;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class" ,  "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.url" , "jdbc:mysql://" + System.getenv("DB_HOST") + ":" + System.getenv("DB_PORT")+ "/" + System.getenv("DB_NAME"));
            properties.setProperty("hibernate.connection.username" , System.getenv("DB_USER"));
            properties.setProperty("hibernate.connection.password" , System.getenv("DB_PASSWORD"));

            properties.setProperty("hibernate.hbm2ddl.auto", "update");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.current_session_context_class", "thread");
            configuration.addProperties(properties) ;
            sessionFactory = configuration.buildSessionFactory() ;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
