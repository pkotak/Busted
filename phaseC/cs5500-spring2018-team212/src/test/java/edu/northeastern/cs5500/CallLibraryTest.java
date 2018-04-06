package edu.northeastern.cs5500;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class CallLibraryTest extends TestCase {
	
	public void testcompareFiles() {
		CallLibrary cl = new CallLibrary();
		cl.getIndividualReport("", "", 1);
		cl.getReports("", "", 1);
	}
	
}
