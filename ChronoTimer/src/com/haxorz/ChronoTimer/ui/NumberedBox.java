package com.haxorz.ChronoTimer.ui;

import javax.swing.*;

/**
 * Like a checkbox, but it also has a number associated with it
 */
public class NumberedBox extends JCheckBox {
	public int channel;

	public NumberedBox(int channel){
		super();
		this.channel = channel;
	}

}
