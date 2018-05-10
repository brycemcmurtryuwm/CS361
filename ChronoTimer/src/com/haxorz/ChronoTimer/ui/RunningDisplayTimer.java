package com.haxorz.ChronoTimer.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the part of the display that shows
 * all of the athletes currently racing
 *
 * Gets linked to a swing JTextPane Object
 */
public class RunningDisplayTimer {

    public Timer _updateTimer;
    private JTextPane _screen;

    /**
     * Sets it up so the screen gets updated ever .1 second
     *
     * @param screen the JTextPane object it is displayed to
     */
    public RunningDisplayTimer(JTextPane screen){

        _screen = screen;
        _updateTimer = new Timer(100, new UpdateTimerListener());
        _updateTimer.start();
    }


    /**
     * When the screen is told to refresh, it will go to the UI
     * and get the most up to date version.
     *
     * Probably shouldn't be done more than 60 times a second because
     * most screens couldn't process it any faster
     */
    private class UpdateTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = _screen.getText();
            if(text.equals("") || text.equals("No Data To Display") || text.startsWith("Use the numpad to"))
                return;

            _screen.setText(ChronoTimerUI.getRaceDisplayText());
        }
    }
}
