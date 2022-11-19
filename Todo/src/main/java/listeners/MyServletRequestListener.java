package listeners;

import java.util.logging.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletRequestListener implements ServletRequestListener{

	public static int count;
	private static final Logger logger = Logger.getLogger(MyHttpSessionAttributeListener.class.getName());
	
	@Override
	public void requestInitialized(ServletRequestEvent e) {
		
		count++;
		System.out.println("----REQUEST INIT.----" + "count = " + count + " " + e.getServletRequest());
//		logger.info("----REQUEST INIT.----" + "count = " + count);
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent e) {
		
		System.out.println("----REQUEST DESTROYED----" + e.getSource().toString());

	}
}
