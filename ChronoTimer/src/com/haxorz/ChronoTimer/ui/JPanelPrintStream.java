package com.haxorz.ChronoTimer.ui;

import javax.swing.*;
import java.io.PrintStream;

public class JPanelPrintStream extends PrintStream
{
	private JTextPane printScreen;

	public JPanelPrintStream(JTextPane printScreen) {
		super(System.out,true);
		this.printScreen = printScreen;
	}
	@Override
	public void print(String s) {
		super.print(s);
		printScreen.setText(printScreen.getText()+s);
	}
	@Override
	public void println(String s) {
		printScreen.setText(printScreen.getText()+ s + "\n");
	}
}
