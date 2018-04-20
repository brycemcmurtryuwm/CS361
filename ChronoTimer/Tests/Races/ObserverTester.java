package Races;

import com.haxorz.ChronoTimer.Races.Race;
import com.haxorz.ChronoTimer.Races.RaceType;

import java.util.Observable;
import java.util.Observer;
import static com.haxorz.ChronoTimer.Races.RaceType.IND;

public class ObserverTester implements Observer {

    public Boolean Observed = false;
    public RaceType RaceType = IND;
    public int Finished = 0;
    public int InQueue = 0;
    public int Running = 0;

    @Override
    public void update(Observable o, Object arg) {
        Observed = true;

        if(o instanceof Race)
        {
            RaceType = ((Race) o).getRaceType();
            Finished = ((Race) o).getCompletedTimes().size();
            Running = ((Race) o).getAthletesRunning().size();
            InQueue = ((Race) o).getAthletesInQueue().size();
        }
    }

}
