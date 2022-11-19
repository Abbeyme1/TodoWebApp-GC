package listeners;


import java.util.logging.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@WebListener
public class MyHttpSessionListener implements HttpSessionListener{

	private static final Logger logger = Logger.getLogger(MyHttpSessionListener.class.getName());

	
	@Override
	public void sessionCreated(HttpSessionEvent e) {
		
//		System.out.println("----SESSION CREATED----");
		logger.info("----SESSION CREATED----");
	}
	
	
	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		
		System.out.println("----SESSION DESTROYED----");
		logger.info("----SESSION DESTROYED----");
	}
}

