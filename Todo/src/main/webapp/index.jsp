<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="com.google.appengine.api.datastore.EntityNotFoundException"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="classes.User"%>
<%@page import="classes.Todo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo</title>
<link rel="stylesheet" href="style.css">
<%-- <script type="text/javascript" src="script.js" ></script> --%>
</head>
<body>


<%--
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

public List<Todo> getTodos(boolean isLoggedIn,String email)
{
	List<Todo> l = new ArrayList<>();
	if(!isLoggedIn) return l;
	
	try {
		
		Key key = KeyFactory.createKey("Todo",email);
		Entity entity = datastore.get(key);
		String s = (String)entity.getProperty("todos");
		Object obj = new Gson().fromJson(s, List.class);
		List<Todo> l = (List<Todo>) obj;
		
		
		
		
		
	}
	catch(EntityNotFoundException e)
	{
		e.printStackTrace();
		return l;
	}
	
}

--%>


<%
	
	boolean isLoggedIn = false;

	HttpSession s = request.getSession();
	
	User currentUser = (User) s.getAttribute("user");
	
	if(s != null && currentUser != null) isLoggedIn = true;
	
	@SuppressWarnings("unchecked")
	List<Todo> todos = (List<Todo>) s.getAttribute("todo");

%>

<div class="nav" >

<% if(isLoggedIn) { %>
	<%  String userName =  currentUser.getName(); %>
	
	<div>
    	<p style="padding-right: 10px">  <%=userName %> </p>
    </div>
    <div>
		<form action="logout">
			<input type="hidden" name="logout" value="true" />
			<a href="#" onclick="this.parentNode.submit()">Logout</a>
		</form>
     </div>
<% } 

else { %>
    
	<p style="padding-right: 10px"> 
		<a href="login">Login</a>
	</p>
	<p> 
		<a href="signup">SignUp</a>
	</p>

<% } %>

</div>


<div class="todoBox" >
<h2 align="center"> Todoo App</h2>
<%
if(isLoggedIn) {
%>
 	<% if(todos == null || todos.size() == 0) { %>
		<p>  Add todos </p>

	<% } 
else  { 
%>

	<ul>
		<% for(int i=0;i<todos.size();i++) { %>
			
			<div class="todo">
			<p style="text-decoration: <%= todos.get(i).isDone() ? "line-through" : "none"%>"> 
				<a href="handleTask?index=<%=i%>" style="text-decoration: none" > <%=todos.get(i).getDescription() %> </a>
				<span style="padding-left: 5px">
				<form action="handleTask?index=<%=i%>" method="post" style="display: inline;">
 					<button type="submit" >-</button>
 				</form>
 				</span>
			</p>
			
			</div>
	<%	}%>
	</ul>


<% } %>

 
 
 <form action="add" method="post">
 	<span> Task: </span>
 	<input name="description" type="text"/>
 	<button type="submit">+</button>
 </form>
 
 
<% } 


 else { %>

	<div align="center" style="margin: 50px 0">
		<a href="login"><h3> Please Login </h3> </a>
	</div>


<% } %>
</div>

<% String error = (String) request.getAttribute("error");  %>

<% if(error != null) { %>

<p style="color: red"> <%= error %></p>


<% } %>

</body>
</html>