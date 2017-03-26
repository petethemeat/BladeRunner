package Logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;



public class BladeLogger {
	
	public final static Logger logger = Logger.getLogger(BladeLogger.class.getName());	
	
	
	public void info(String message)
	{
		logger.info(message);
	}
	
	public void warning(String message)
	{
		logger.warning(message);
	}
	
	public void severe(String message)
	{
		logger.severe(message);
	}

}
