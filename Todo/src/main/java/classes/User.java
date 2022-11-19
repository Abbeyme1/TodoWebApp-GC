package classes;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener, Serializable{

	private String name;
	private String email;
	private String password;
	private boolean inUse;
	
	
	public User(String name, String email, String password) {
		
		setName(name);
		setEmail(email);
		setPassword(password);
		
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		
		// encrypt password
		this.password = password;
	}
	
	public boolean checkPassword(String password)
	{
		// decrypt password
		
		String pass = this.password;
		
		//check if equal
		return pass.equals(password);
	}
	
	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent e) {
		
		System.out.println("Object bounded by key:"+ e.getName());
		setInUse(true);
		
	}
	
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent e) {
		
		System.out.println("Object unbounded");
		setInUse(false);
	}

	

	
}
