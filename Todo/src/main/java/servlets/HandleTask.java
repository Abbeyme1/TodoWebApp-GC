package servlets;

import java.io.IOException;
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


@WebServlet(name = "HandleTask", urlPatterns = "/handleTask", initParams = {
		
})
public class HandleTask extends HttpServlet {
	
	
	private static DatastoreService datastore;
	private static User user;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		datastore = DatastoreServiceFactory.getDatastoreService();
		user = (User) req.getSession().getAttribute("user");
		super.service(req, res);
	}
	// STRIKE TASK
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		int index = Integer.parseInt(req.getParameter("index"));
		
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<Todo> todos = (List<Todo>) session.getAttribute("todo");
		String id = todos.get(index).getId();
		
		Key key = KeyFactory.createKey("Task", id);
		
		try {
			Entity entity = datastore.get(key);
			Todo todo = new Gson().fromJson((String) entity.getProperty("todo"), Todo.class);
			todo.changeDone();
			entity.setProperty("todo", new Gson().toJson(todo));
			datastore.put(entity);
			todos.get(index).changeDone();
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.sendRedirect("index");
	}
	
	// REMOVE TASK
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int index = Integer.parseInt(req.getParameter("index"));
		HttpSession session = req.getSession();
		List<Todo> l = (List<Todo>)session.getAttribute("todo");
		String id = l.get(index).getId();
		
		
		if(l.size() == 0) session.removeAttribute("todo");

		// remove from Task and Todo
		
		Key key = KeyFactory.createKey("Task", id);
		datastore.delete(key);
		
		
		key = KeyFactory.createKey("Todo",user.getEmail());
		
		try {
			
			Entity entity = datastore.get(key);
			String s = (String)entity.getProperty("todos");
			
			JsonParser parser = new JsonParser();
			JsonArray arr = parser.parse(s).getAsJsonArray();
			List<String> todos = new ArrayList<>();
			
			for(final JsonElement e : arr)
			{
				Gson g = new Gson();
				String t = g.fromJson(e, String.class);
				
				if(!t.equals(id)) todos.add(t);
			}
			
			entity.setProperty("todos", new Gson().toJson(todos));
			datastore.put(entity);
			l.remove(index);
			
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		res.sendRedirect("index");
	}
}
