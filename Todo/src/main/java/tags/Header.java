package tags;

import javax.servlet.jsp.tagext.TagSupport;

public class Header extends TagSupport{
	
	
	private boolean loggedIn;
	private String name;
	
	@Override
	public int doStartTag() {
		
		try {
			
			if(loggedIn)
			{
				
			}
			else
			{
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return SKIP_BODY;
		
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
