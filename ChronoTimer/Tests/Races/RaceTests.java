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

		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 2));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 78));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 79));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 80));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(4, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 81));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(5, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(5, _observer.InQueue);



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(3, _observer.Running);
		assertEquals(2, _observer.InQueue);



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(2, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(1, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(!_observer.Observed);
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 81));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 99));
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 81));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		//currently racing DNF SWAP

		//CANCEL on inQueue, Currently Racing and Finished
		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);

		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(1, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 1));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);


		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_observer.Observed = false;

		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(2, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));



		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 15));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now()));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_observer.Observed = false;
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now()));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new PrintCmd(LocalTime.now()));
		int i = 0;
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 78"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 79"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 77"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 81"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 80"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 15: DNF"));
	}

	@Test
	public void testPARINDRace() {
		_chronoTimer.executeCmd(new EventCmd(LocalTime.now(), RaceType.PARIND));

		_chronoTimer.executeCmd(new GenericCmd(CmdType.ENDRUN,LocalTime.now()));
		assertTrue(_observer.Observed);
		assertEquals(RaceType.PARIND, _observer.RaceType);

		_observer.Observed = false;

		int raceNumber = Race.RunNumber;
		_chronoTimer.executeCmd(new GenericCmd(CmdType.NEWRUN, LocalTime.now()));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(raceNumber +1 , Race.RunNumber);

		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 3));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 4));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 78));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 79));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 80));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(4, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 81));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(5, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(5, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		assertTrue(!_observer.Observed);
		assertEquals(0, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(5, _observer.InQueue);



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(0, _observer.Finished);
		assertEquals(3, _observer.Running);
		assertEquals(2, _observer.InQueue);



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(2, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(1, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		assertTrue(!_observer.Observed);
		assertEquals(2, _observer.Finished);
		assertEquals(1, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		assertTrue(!_observer.Observed);
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 81));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 99));
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(1, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 81));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(3, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		//currently racing DNF SWAP

		//CANCEL on inQueue, Currently Racing and Finished
		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);

		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(1, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 77));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(3, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 101));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(4, _observer.InQueue);

		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 3));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));

		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 3));

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(4, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now(), 3));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(2, _observer.Finished);
		assertEquals(4, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));


		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 105));



		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 15));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 17));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(6, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(2, _observer.InQueue);

		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));
		_observer.Observed = false;
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(),1));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(),2));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(8, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new PrintCmd(LocalTime.now()));
		int i = 0;
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 79"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 78"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 81"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 77"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 101"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 80"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 15: DNF"));
		assertTrue(_observer.FinishedTimes.get(i++).contains("Athlete 17: DNF"));
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

	@Test
	public void testPARGrpRace() {
		_chronoTimer.executeCmd(new EventCmd(LocalTime.now(), RaceType.PARGRP));

		_chronoTimer.executeCmd(new GenericCmd(CmdType.ENDRUN,LocalTime.now()));
		assertTrue(_observer.Observed);
		assertEquals(RaceType.PARGRP, _observer.RaceType);

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
		assertEquals(1, _observer.InQueue);

		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 3));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 4));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 5));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 6));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 7));
		_chronoTimer.executeCmd(new ToggleCmd(LocalTime.now(), 8));

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




		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 77));
		assertTrue(!_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 78));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 79));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 770));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 779));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 778));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 777));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 776));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 475));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 477));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 476));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 475));
		//assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(8, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(1, _observer.Finished);
		assertEquals(8, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(4, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 3));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 4));
		assertTrue(!_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(4, _observer.Running);
		assertEquals(0, _observer.InQueue);


		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 178));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 179));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1770));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1779));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1778));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1777));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1776));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1475));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1477));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1476));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1475));
		assertTrue(!_observer.Observed);
		_observer.Observed = false;
		assertEquals(5, _observer.Finished);
		assertEquals(4, _observer.Running);
		assertEquals(0, _observer.InQueue);



		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 5));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 6));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 7));
		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 8));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(9, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);



		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 178));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 179));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1770));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1779));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1778));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1777));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1776));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1475));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1477));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1476));
		_chronoTimer.executeCmd(new NumCmd(LocalTime.now(), 1475));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(9, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(8, _observer.InQueue);


		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 1));
		assertTrue(!_observer.Observed);
		_observer.Observed = false;
		assertEquals(9, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(8, _observer.InQueue);


		_chronoTimer.executeCmd(new CancelCmd(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new SwapCmd(LocalTime.now()));
		_chronoTimer.executeCmd(new ClearCmd(LocalTime.now(), 1));
		assertEquals(9, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(8, _observer.InQueue);




		_chronoTimer.executeCmd(new TriggerCmd(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(9, _observer.Finished);
		assertEquals(8, _observer.Running);
		assertEquals(0, _observer.InQueue);




		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 1));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 2));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 3));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 4));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 5));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 6));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 7));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 8));
		_chronoTimer.executeCmd(new DNFCommand(LocalTime.now(), 1));
		assertTrue(_observer.Observed);
		_observer.Observed = false;
		assertEquals(17, _observer.Finished);
		assertEquals(0, _observer.Running);
		assertEquals(0, _observer.InQueue);

	}
}