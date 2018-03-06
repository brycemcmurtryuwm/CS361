package Commands;
import com.haxorz.ChronoTimer.Commands.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Parsing {


	@Before
	public void before() {
	}

	@Test
	public void testPower() {
		CTCommand c =CTCommand.ParseFromString("10:00:00 Start");
		Assert.assertTrue(c instanceof GenericCmd);
	}
	@Test
	public void testBadCommand() {
		CTCommand c =CTCommand.ParseFromString("fghjkl");
		Assert.assertTrue(c == null);
	}
	@Test
	public void testWithoutStamp(){
		CTCommand c =CTCommand.ParseFromString("cancel");
		Assert.assertTrue(c instanceof CancelCmd);
	}
	@Test
	public void testEvent(){
		CTCommand c =CTCommand.ParseFromString("21:10:10 Event IND");
		Assert.assertTrue(c instanceof EventCmd);
	}
	@Test
	public void testBadTime(){
		try{
			CTCommand c =CTCommand.ParseFromString("25:10:10 Event IND");
			Assert.assertTrue(true);
		}
		catch(Exception e){
			Assert.fail();
		}
	}
	@Test
	public void testPrint(){
		CTCommand c =CTCommand.ParseFromString("21:10:10 Print hello");
		Assert.assertTrue(c instanceof PrintCmd);
	}
}
