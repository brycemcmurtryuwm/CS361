import Commands.Parsing;
import Hardware.ChannelTest;
import Races.AthleteTest;
import Races.RaceTests;
import Races.TestRaceTime;
import org.junit.runner.RunWith;

import org.junit.runners.Suite;



@RunWith(Suite.class)

@Suite.SuiteClasses({

        Parsing.class,

        ChannelTest.class,

        AthleteTest.class,

        RaceTests.class,

        TestRaceTime.class

})



public class TestSuite {

}