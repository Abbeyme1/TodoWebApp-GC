package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.datastore.*;

import classes.User;

@WebServlet("/test")
public class Test extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.getWriter().print("working");
		
		
		User u = new User("abhay","abc5@gmail.com","1245");
		
		Entity entity = new Entity("User", "userr");
		entity.setProperty("user", u);
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		ds.put(entity);
		
		
		
		/// 
		
		
	}
}
