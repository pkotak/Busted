package edu.northeastern.cs5500;


import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	public void testAppMain() {
		AppMain app = new AppMain();
		app.runLibrary(1);
		app.runLibrary(2);
		app.runLibrary(3);
	}
}
