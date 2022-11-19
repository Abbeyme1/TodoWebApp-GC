package listeners;

import java.util.logging.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;


public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener{

	private static final Logger logger = Logger.getLogger(MyHttpSessionAttributeListener.class.getName());

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
		String str = "----SESSION ATTRIBUTE ADDED----"+ event.getName();
//		System.out.println(str);
		logger.info(str);
	}
	
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
		String str = "----SESSION ATTRIBUTE REMOVED----"+ event.getName();
//		System.out.println(str);
		logger.info(str);
	}
	
	
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
		String str = "----SESSION ATTRIBUTE REPLACED----"+ event.getName();
//		System.out.println(str);
		logger.info(str);
	}
}
