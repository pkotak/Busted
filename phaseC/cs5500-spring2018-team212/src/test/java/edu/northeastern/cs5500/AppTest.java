package edu.northeastern.cs5500;


import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	public void testAppMain() {
		CallLibrary app = new CallLibrary();
		app.runLibrary(1);
		app.runLibrary(2);
		app.runLibrary(3);
	}
	
	public void testcompareFiles() {
		CallLibrary app = new CallLibrary();
		//first string - input dir
		//second string - output dir
		app.compareFiles("", "", 1);
		app.compareFiles("", "", 2);
		app.compareFiles("", "", 3);
	}
}
