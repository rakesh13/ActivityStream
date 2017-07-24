package com.stackroute.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.stackroute.dao.UserDao;
import com.stackroute.model.User;

public class UserTest {
	
	AnnotationConfigApplicationContext context;
	UserDao userDAO;
	User user;
	
	@Before
	public void init()
	{
		context = new AnnotationConfigApplicationContext();
		context.scan("com.stackroute");
		context.refresh();
		userDAO = (UserDao) context.getBean("userDao");
		user = (User) context.getBean("user");
	}
	
	
	@Test
	public void insertUser()
	{
		user.setUsername("ram");
		user.setName("ram");
		user.setEmail("ram@gmail.com");
		user.setActive(true);
		user.setPassword("ram");
		boolean status=userDAO.save(user);
		assertEquals(true,status);
		System.out.println("Records Inserted");
	}
	@Ignore
	@Test
	public void checkAuthentication()
	{
		String username="rakesh";
		String password="india";
		user=userDAO.validate(username, password);
		String expectedName="rakesh";
		String actualValue=user.getName();
		assertEquals("Login Successfull",expectedName,actualValue);
		System.out.println("Login Successfull");
	}

}
