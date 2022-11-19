package listeners;

import java.util.logging.Logger;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletRequestAttributeListener implements ServletRequestAttributeListener{

	private static final Logger logger = Logger.getLogger(MyHttpSessionListener.class.getName());
	
	@Override
	public void attributeAdded(ServletRequestAttributeEvent e) {
//		System.out.println("----ATTRIBUTE ADDED----"+ e.getName());
		logger.info("----ATTRIBUTE ADDED----"+ e.getName());
	}
	
	
	@Override
	public void attributeReplaced(ServletRequestAttributeEvent e) {
		
//		System.out.println("----ATTRIBUTE REPLACED----"+ ", name = " + e.getName() + " , value = " +e.getValue().toString());
		logger.info("----ATTRIBUTE REPLACED----"+ ", name = " + e.getName() + " , value = " +e.getValue().toString());

	}
	
	@Override
	public void attributeRemoved(ServletRequestAttributeEvent e) {
//		System.out.println("----ATTRIBUTE REMOVED----"+ e.getName());
		logger.info("----ATTRIBUTE REMOVED----"+ e.getName());

	}
	
	
}
