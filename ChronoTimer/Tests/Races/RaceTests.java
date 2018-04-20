package Races;

import com.haxorz.ChronoTimer.ChronoTimer;
import com.haxorz.ChronoTimer.Commands.*;
import com.haxorz.ChronoTimer.Races.Athlete;
import com.haxorz.ChronoTimer.Races.Race;
import com.haxorz.ChronoTimer.Races.RaceType;
import com.haxorz.ChronoTimer.Races.RunRepository;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

public class RaceTests {

	private ChronoTimer _chronoTimer;
	private ObserverTester _observer = new ObserverTester();
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Before
	public void before() {
		_chronoTimer = new ChronoTimer(new PrintStream(out));

		_chronoTimer.setRaceObserver(_observer);
		if(!_chronoTimer.isPoweredOn())
			_chronoTimer.executeCmd(new GenericCmd(CmdType.POWER, LocalTime.now()));
	}

	@Test
	public void testINDRace() {
		_chronoTimer.executeCmd(new EventCmd(LocalTime.now(), RaceType.IND));

		_chronoTimer.executeCmd(new GenericCmd(CmdType.ENDRUN,LocalTime.now()));
		assertTrue(_observer.Observed);
		assertEquals(RaceType.IND, _observer.RaceType);

		_observer.Observed = false;

		int raceNumber = Race.RunNumber;
		_chronoTimer.executeCmd(new GenericCmd(CmdType.NEWRUN, LocalTime.now()));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(raceNumber +1 , Race.RunNumber);




		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 2));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(4, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);



		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 78));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 79));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new PrintCmd(LocalTime.now()));
		String output = out.toString();
		assertTrue(output.contains("Athlete 77"));
		assertTrue(output.contains("Athlete 78"));
		assertTrue(output.contains("Athlete 79"));
		assertTrue(output.contains("Athlete 4"));
		assertTrue(output.contains("Athlete 5"));
		assertTrue(output.contains("Athlete 6"));
	}

	@Test
	public void testGrpRace() {
		_chronoTimer.executeCmd(new EventCmd(LocalTime.now(), RaceType.GRP));

		_chronoTimer.executeCmd(new GenericCmd(CmdType.ENDRUN,LocalTime.now()));
		assertTrue(_observer.Observed);
		assertEquals(RaceType.GRP, _observer.RaceType);

		_observer.Observed = false;

		int raceNumber = Race.RunNumber;
		_chronoTimer.executeCmd(new GenericCmd(CmdType.NEWRUN, LocalTime.now()));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(raceNumber +1 , Race.RunNumber);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 2));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(4, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);



		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 78));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 79));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new PrintCmd(LocalTime.now()));
		String output = out.toString();
		assertTrue(output.contains("Athlete 77"));
		assertTrue(output.contains("Athlete 78"));
		assertTrue(output.contains("Athlete 79"));
		assertTrue(output.contains("Athlete 4"));
		assertTrue(output.contains("Athlete 5"));
		assertTrue(output.contains("Athlete 6"));


		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now()));
		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now()));
		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 1));
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);
	}
}