package Testing;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import Controllers.*;


public class ControllerTesting
{
	
	@BeforeClass
	public static void initialize()
	{
		Hub.start();
		
		Hub.logger.info("Begin Controller Unit Tests... \n");
		try
		{
			DeleteController.run("500");
			DeleteController.run("1000");
			String[] fields = {"id", "blade_size", "start_date", "hours_used"};
			String[] values = {"1000", "11", "2012-08-06 08:11:12", "30"};
			AddController.run(fields, values);
			
		}
		catch(SQLException e)
		{
			
		}
	}
	/*
	 * This test makes sure a normal blade can be added with expected fields
	 */
	@Test
	public void testAddContoller()
	{
		Hub.logger.info("testAddController start... \n");
		
		String[] fields = {"id", "blade_size", "start_date", "hours_used"};
		String[] values = {"500", "11", "2012-08-06 08:11:12", "30"};
		
		Hub.start();
		
		try
		{
			AddController.run(fields, values);
		}
		catch(SQLException e)
		{
			Hub.logger.severe("testAddController has failed: \n" + e.getMessage() + "\n");	
			assertNull(1);
		}
		Hub.logger.info("testAddController has passed! \n");
		
	}
	
	/*
	 * This test makes sure add controller fails when a field name does not exits
	 */
	
	@Test
	public void testAddMisnamedField()
	{
		Hub.logger.info("testAddMisnamedField start... \n");
		
		String[] fields = {"id", "blade_size", "start_date", "hrs_used"};
		String[] values = {"500", "11", "2012-08-06 08:11:12", "30"};
		
		Hub.start();
		
		try
		{
			AddController.run(fields, values);
		}
		catch(SQLException e)
		{
			Hub.logger.info("testAddMisnamedField has passed! \n");
			return;
		}

		
		Hub.logger.severe("testAddMisnamedField has failed: \n" +"the db added a blade with unwanted attribute \n");	
		assertNull(1);
	}
	
	
	/*
	 * This test ensures a blade isn't added with a required field missing
	 */
	@Test
	public void testAddMissingField()
	{
		Hub.logger.info("testAddMissingField start... \n");
		
		String[] fields = {"id", "blade_size", "hours_used"};
		String[] values = {"600", "11", "30"};
		
		Hub.start();
		
		try
		{
			AddController.run(fields, values);
		}
		catch(SQLException e)
		{
			Hub.logger.info("testAddMissingField has passed! \n");
			return;
		}

		
		Hub.logger.severe("testAddMissingField has failed: \n" +"the db added a blade with unwanted attribute \n");	
		assertNull(1);
	}
	
	/*
	 * "This test ensures that a blade is no longer in the database after being deleted"
	 */
	@Test
	public void deleteController()
	{
		Hub.logger.info("testAddMissingField start... \n");
		
		String[] fields = {"id", "blade_size", "hours_used"};
		String[] values = {"600", "11", "30"};
		
		Hub.start();
		
		try
		{
			AddController.run(fields, values);
			DeleteController.run("600");
			AddController.run(fields, values);
		}
		catch(SQLException e)
		{
			
		}
	}
	
	@Test
	public void testQueryController()
	{
		Hub.logger.info("testQueryController start... \n");
		
		try
		{
			QueryController.run("500");
		}
		catch(SQLException e)
		{
			Hub.logger.severe("testQueryContoller has failed: \n" + e.getMessage() + "\n");	
			assertNull(1);
		}
		Hub.logger.info("testQueryController has passed!");
		
	}
	
	@Test
	public void testQueryNonExistent()
	{
		Hub.logger.info("testQueryNonExistent start.. \n");
		
		try
		{
			QueryController.run("900");
		}
		catch(SQLException e)
		{
			Hub.logger.info("testQueryNonExistent has passed!");
			return;
		}
		Hub.logger.severe("testQueryContoller has failed: \n" +"Blade was found when it shouldn't be there. \n");
	}
	
	@Test
	public void testUpdateController()
	{
		Hub.logger.info("testUpdateContorller start.. \n");
		
		String id = "1000";
		String[] fields = {"id", "blade_size", "start_date", "hours_used"};
		String[] values = {"1000", "37", "2013-11-16 12:21:34", "8"};
		
		String[] updateFields = {"blade_size", "start_date", "hours_used"};
		String[] updateValues = {"45", "2009-03-16 12:21:34", "19"};
		try
		{
			AddController.run(fields, values);
			UpdateController.run(id, updateValues, updateFields);
		}
		catch(SQLException e)
		{
			Hub.logger.severe("testUpdateController has failed: \n" + e.getMessage() + "\n");
			assertNull(1);
		}
		
		Hub.logger.info("testUpdateController has passed!");
	}
}
