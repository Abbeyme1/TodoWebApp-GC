package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import classes.Todo;
import classes.User;


@WebServlet(name = "UserHandler", urlPatterns = "/user")
public class UserHandler extends HttpServlet {
	
	
	public static DatastoreService datastore;
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		super.service(req, res);
	}
	
	
	// login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("User Handler: Get");
		String email = getParameter(req,"email");
		String password = getParameter(req,"password");
		HttpSession session = req.getSession();
		
		try {
			
			Key key = KeyFactory.createKey("User", email);
			
			Entity entity = datastore.get(key);
			
			String s = (String) entity.getProperty("json");
			
			User user = new Gson().fromJson(s, User.class);
			
			if(user.checkPassword(password))
			{
				// success
				
				// fetch todos
				
				
				key = KeyFactory.createKey("Todo", user.getEmail());
				entity = datastore.get(key);
				s = (String)entity.getProperty("todos");
				
				JsonParser parser = new JsonParser();
				JsonArray arr = parser.parse(s).getAsJsonArray();
				List<Todo> todos = new ArrayList<>();
				
				for(final JsonElement e : arr)
				{
					Gson g = new Gson();
					String taskId = g.fromJson(e, String.class); // id of tasks [t1,t2..]

					Key k = KeyFactory.createKey("Task", taskId);
					entity = datastore.get(k);
					String todo = (String)entity.getProperty("todo");
					Todo t = g.fromJson(todo, Todo.class);
					todos.add(t);
				}
				session.setAttribute("todo", todos);
				session.setAttribute("user", user);
				res.sendRedirect("index");
			}
			else
			{
				// send error : email/password is wrong
				
				res.sendRedirect("login");
			}
		}
		catch(EntityNotFoundException e)
		{
			// send error No user exist : login
			res.sendRedirect("login");
		}
		
	}
	
	// SIGNUP
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
		System.out.println("User Handler: Post");
		
		HttpSession session = req.getSession();
		String username = getParameter(req,"name");
		String email = getParameter(req,"email");
		String password = getParameter(req,"password");
		
		
		
		try {
			// user exists
			Key key = KeyFactory.createKey("User", email);
			
			Entity e = datastore.get(key);
			
			// send error that 'please log in : Email already in use'
			
			res.sendRedirect("login");
		}
		catch(EntityNotFoundException e)
		{
			User user = new User(username, email, password);
			try {
				
				//user
				Entity entity = new Entity("User", email);
				entity.setProperty("json", new Gson().toJson(user));
				entity.setProperty("byteFile", stringify(user));
				
				datastore.put(entity);
				
				//todos
				entity = new Entity("Todo", email);
				entity.setProperty("todos", new Gson().toJson(new ArrayList<Todo>()));
				
				datastore.put(entity);
				
				// success
				session.setAttribute("todo", new ArrayList<Todo>());
				session.setAttribute("user", user);
				res.sendRedirect("index");
			}
			catch(Exception ex)
			{
				e.printStackTrace();
				
				// send error to index via req.
			}
		}
		
		
		
	}
	
	public <T> String stringify (T obj)
	  {
		  
		  String str= null;
		  try {
			  
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      ObjectOutputStream os = new ObjectOutputStream(bos);
		      os.writeObject(obj);
		      str= bos.toString();
		      os.close();
			  
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		
		  return str;
		  
	  }

	
	public String getParameter(HttpServletRequest req, String attr)
	{
		String param = req.getParameter(attr);
		
		// handle null/empty
		
		return param;
	}

}
