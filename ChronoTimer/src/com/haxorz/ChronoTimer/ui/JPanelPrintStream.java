package com.haxorz.ChronoTimer.ui;

import javax.swing.*;
import java.io.PrintStream;

/**
 * A handy dandy tool for turning your Text Panes
 * into print Streams
 */
public class JPanelPrintStream extends PrintStream
{
	private JTextPane printScreen;

	/**
	 * @param printScreen to be used as a print stream
	 */
	public JPanelPrintStream(JTextPane printScreen) {
		super(System.out,true);
		this.printScreen = printScreen;
	}
	@Override
	public void print(String s) {
		super.print(s);
		printScreen.setText(printScreen.getText()+s);
		System.out.print(s);
	}
	@Override
	public void println(String s) {
		printScreen.setText(printScreen.getText()+ s + "\n");
		System.out.println(s);
	}
}
