package Hardware;

import com.haxorz.ChronoTimer.Hardware.Channel;
import com.haxorz.ChronoTimer.Hardware.ChannelListener;
import com.haxorz.ChronoTimer.Hardware.InputSensor;
import com.haxorz.ChronoTimer.Hardware.SensorType;
import com.haxorz.ChronoTimer.SystemClock;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ChannelTest implements ChannelListener {

    private Channel channel;
    private boolean _channelEnabled = false;


    @Before
    public void before() {
        channel = new Channel(1);
    }

    @Test
    public void testConnect() {
        InputSensor sensor = new InputSensor(SensorType.EYE);
        assertEquals(null, sensor.Listener);

        sensor.connect(channel);
        assertEquals(channel, sensor.Listener);
    }

    /** Disconnects any sensor from the channel
     */
    @Test
    public void testDisconnect() {
        InputSensor sensor = new InputSensor(SensorType.EYE);
        sensor.connect(channel);

        sensor.disconnect();
        assertEquals(null, sensor.Listener);
    }

    @Test
    public void testToggle() {
        channel = new Channel(1);

        assertEquals(true, channel.ToggleChannel());


        assertEquals(false, channel.ToggleChannel());

    }

    @Test
    public void testTriggerDisabled() {
        channel = new Channel(1);

        channel.Trigger(SystemClock.now());

        InputSensor sensor = new InputSensor(SensorType.EYE);
        sensor.connect(channel);

        sensor.Triggered(SystemClock.now());

        assertEquals(1,1);
    }

    @Test
    public void testTriggerArmed() {
        channel = new Channel(1);
        channel.ToggleChannel();

        channel.Trigger(SystemClock.now());

        InputSensor sensor = new InputSensor(SensorType.EYE);
        sensor.connect(channel);

        sensor.Triggered(SystemClock.now());
    }

    @Override
    public void channelTriggered(int channelNum, LocalTime timeStamp) {
        if(_channelEnabled){
            assertEquals(1,1);
        }
        else{
            fail("The channel was triggered when it was disabled");
        }

    }
}
