package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


@WebFilter("/user")
public class Validate implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("In filter");
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if(!checkName(name) || !checkEmail(email) || !checkPassword(password))
		{
			
			// throw error and get back to login/register
			response.getWriter().println("Invalid Input");
		}
		else
		{
			chain.doFilter(request, response);
		}	
	}
	
	private static boolean checkName(String str)
	{
		if(str == null) return true;
		
		System.out.println("name str ="+ str);
		
		str = str.trim();
		
		System.out.print(str);
		if(str.length() < 3) return false;
		String regex = "^[A-Za-z\\s]+$";
		if(!str.matches(regex)) return false;
		
		return true;
	}
	
	private boolean checkEmail(String str)
	{
		if(str == null || str.length() == 0 || str.trim().length() == 0) return false;
		return true;
	}
	
	private boolean checkPassword(String str)
	{
		if(str == null || str.length() == 0 || str.trim().length() == 0) return false;
		return true;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
