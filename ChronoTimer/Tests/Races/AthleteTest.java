package Races;

import com.haxorz.ChronoTimer.Races.Athlete;
import com.haxorz.ChronoTimer.Races.RunRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class AthleteTest {

	@Test
	public void testCreateAthlete() {
		Athlete a = new Athlete(7);

		Assert.assertEquals(7, a.getNumber());
	}

	@Test
	public void getTimeTracker(){
		Athlete a = new Athlete(7);

		Assert.assertEquals(null, a.getTimeTracker(1));

		a.registerForRace(1);
		Assert.assertTrue(RunRepository.AthletesPerRun.get(1).contains(a));
		Assert.assertTrue(a.getTimeTracker(1) != null);

		a.discardRun(1);
		Assert.assertTrue(!RunRepository.AthletesPerRun.get(1).contains(a));
		Assert.assertEquals(null, a.getTimeTracker(1));
	}
}