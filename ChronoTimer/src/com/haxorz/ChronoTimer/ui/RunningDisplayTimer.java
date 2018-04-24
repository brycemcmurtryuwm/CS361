package com.haxorz.ChronoTimer.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunningDisplayTimer {

    public Timer _updateTimer;
    private JTextPane _screen;

    public RunningDisplayTimer(JTextPane screen){

        _screen = screen;
        _updateTimer = new Timer(100, new UpdateTimerListener());
        _updateTimer.start();
    }


    private class UpdateTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(_screen.getText() == "" || _screen.getText() == "No Data To Display")
                return;

            _screen.setText(ChronoTimerUI.getRaceDisplayText());
        }
    }
}
