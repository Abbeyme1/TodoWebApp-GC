package servlets;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

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

import javax.servlet.ServletException;
import javax.servlet.annotation.*;

import classes.Todo;
import classes.User;


//@WebServlet(name = "Add", urlPatterns = "/add", initParams = {
//		@WebInitParam(name="Descripion", value = "Not Provided"),
//		@WebInitParam(name="done", value = "Not Provided")
//})
public class AddTask extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println("AddTask : doPost");
//		String description = req.getParameter("description").strip();
		String description = req.getParameter("description");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		HttpSession session = req.getSession();
		if(session == null) res.sendRedirect("index");
		
		User user = (User) session.getAttribute("user");
		
		
		if(description.length() == 0)
		{
			req.setAttribute("error", "Add Description");
			req.getRequestDispatcher("index").forward(req, res);
//			res.sendRedirect("index.jsp");
		}
		else
		{
			req.removeAttribute("error");
			
			Todo todo = new Todo(description);
			
			// add todos in db
			try {
				
				// add task
				Key key = KeyFactory.createKey("Task", todo.getId());
				
				// add it in db
				Entity entity = new Entity(key);
				entity.setProperty("todo", new Gson().toJson(todo));
				
				datastore.put(entity);
				

				// add task in TODO
				key = KeyFactory.createKey("Todo", user.getEmail());
				entity = datastore.get(key);
				String s = (String)entity.getProperty("todos");
				
				JsonParser parser = new JsonParser();
				JsonArray arr = parser.parse(s).getAsJsonArray();
				List<String> todos = new ArrayList<>();
				
				for(final JsonElement e : arr)
				{
					Gson g = new Gson();
					String t = g.fromJson(e, String.class);
					todos.add(t);
				}
				todos.add(todo.getId());
				entity.setProperty("todos", new Gson().toJson(todos));
				datastore.put(entity);
				
				
				
				
				// fill sessions
				List<Todo> l = new ArrayList<>();
				
				for(String str : todos)
				{
					key = KeyFactory.createKey("Task", str);
					entity = datastore.get(key);
					s = (String)entity.getProperty("todo");
					Gson g = new Gson();
					Todo t = g.fromJson(s, Todo.class);
					l.add(t);
				}
				
				
				session.setAttribute("todo", l);
				res.sendRedirect("index");
			}
			catch(EntityNotFoundException e)
			{
				
				e.printStackTrace();
			}
			
			
		}
		
		

	}
	
	
	
	
}
