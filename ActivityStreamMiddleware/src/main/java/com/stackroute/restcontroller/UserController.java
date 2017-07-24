package com.stackroute.restcontroller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.dao.UserDao;
import com.stackroute.model.User;


@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	 UserDao userDao;

	@Autowired
	 User user;
	
	@RequestMapping
	public List getUser() {
		
		return userDao.list();
	}

	@RequestMapping(value="/search/{id}",method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		
		User user=userDao.get(id);
		if (user == null) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(user, HttpStatus.OK);
	}

	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public ResponseEntity createuser(@RequestBody User user) {
		userDao.save(user);
		return new ResponseEntity(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<User> authenticateUser(@RequestBody User user)
	{
		user=userDao.validate(user.getUsername(), user.getPassword());
		if(user==null)
		{
			return new ResponseEntity("Invalid Username and Password", HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity logout(HttpSession session)
	{
		if(session!=null)
		{
			session.invalidate();
			return new ResponseEntity("Logout Successfull!!",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Already Logged Out!! Please Login to continue..",HttpStatus.OK);
		}
	}
}
