package com.vedantu.daos;

import javax.annotation.PreDestroy;
import javax.persistence.Entity;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.vedantu.models.StudentAccountEntity;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.lang.annotation.Annotation;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

@Service
public class MysqlConnection {
	
	private SessionFactory sessionFactory = null;
	
	public MysqlConnection() {
		try {
			Configuration configuration = new Configuration();
            String path = "ENV-LOCAL" + java.io.File.separator
                    + "hibernate.cfg.xml";
			configuration.configure(path); 
			//building SessionFactory from ServiceRegistry (recommended)
			// link is : https://stackoverflow.com/a/22247177
			
			
            // to add annotation classes to identify the table names annotated
			//  (@Entity , @Table( name = table_name) at the top of these classes
			
            final Reflections reflections = new Reflections(StudentAccountEntity.class.getPackage().getName());
            final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
            for (final Class<?> clazz : classes) {
            	System.out.println(clazz);
                configuration.addAnnotatedClass(clazz);
            }
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			System.out.println("ok");
			System.out.println(sessionFactory + " kya re");
		}catch(Exception e) {
			System.out.println(e + " error in mysql connnection");
		}
	}	
	
	public SessionFactory getSessionFactory() {
		//System.out.println(sessionFactory + " from mysql connection");
		System.out.println(sessionFactory + " from mysql connection");
		return sessionFactory;
	}
	
	//cleanup method
    @PreDestroy
    public void cleanUp() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
            Enumeration<Driver> drivers = DriverManager.getDrivers();

            Driver driver = null;

            // clear drivers
            while (drivers.hasMoreElements()) {
                try {
                    driver = drivers.nextElement();
                    System.out.println("deregistering driver " + driver.getMajorVersion());
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    System.out.println("exceoton in driver deregister " + ex.getMessage());
                }
            }
            // MySQL driver leaves around a thread. This static method cleans it up.
            AbandonedConnectionCleanupThread.shutdown();
            System.out.println("cleaning done");
        } catch (Exception e) {
        	System.out.println("Error in closing sql connection "+ e);
        }
    }
}
