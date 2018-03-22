package edu.northeastern.cs5500;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paarth
 *
 */
public class CallLibrary {

	private static final Logger LOGGER = Logger.getLogger(CallLibrary.class.getName());

	/**
	public static void main(String[] args) {
		AppMain app = new AppMain();
		app.runLibrary();
	}
	*/


	/**
	 * Runs the comparison in 3 modes: 
	 * Extreme strict, Moderate Strictness, Easy
	 */
	public void runLibrary(int strictness) {
		String jarCommand = "java -jar ";
		String directorycommand = " -s \"";
		String cmd = "";
		if(strictness == 1) {
			cmd = jarCommand + Constants.LIBRARY_PATH + " -l " + Constants.LANGUAGE + " -r "
					+ Constants.OUTPUT_DIRECTORY +" -t "+Constants.STRICTNESS_EXTREME+directorycommand+ Constants.INPUT_DIRECTORY + "\"";
			LOGGER.info(cmd);
		}else if(strictness == 2) {
			cmd = jarCommand + Constants.LIBRARY_PATH + " -l " + Constants.LANGUAGE + " -r "
					+ Constants.OUTPUT_DIRECTORY +" -t "+Constants.STRICTNESS_MEDIUM+directorycommand+ Constants.INPUT_DIRECTORY + "\"";
		}else {
			cmd = jarCommand + Constants.LIBRARY_PATH + " -l " + Constants.LANGUAGE + " -r "
					+ Constants.OUTPUT_DIRECTORY +" -t "+Constants.STRICTNESS_EASY+directorycommand+ Constants.INPUT_DIRECTORY + "\"";
		}
			
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.toString());
		}
	}
	
	/**
	 * Runs the comparison in 3 modes: 
	 * Extreme strict, Moderate Strictness, Easy
	 */
	public void compareFiles(String ipdir, String opdir, int strictness) {
		String jarCommand = "java -jar ";
		String directorycommand = " -s \"";
		String cmd = "";
		if(strictness == 1) {
			cmd = jarCommand + Constants.LIBRARY_PATH + " -l " + Constants.LANGUAGE + " -r "
					+ opdir +" -t "+Constants.STRICTNESS_EXTREME+directorycommand+ ipdir + "\"";
			LOGGER.info(cmd);
		}else if(strictness == 2) {
			cmd = jarCommand + Constants.LIBRARY_PATH + " -l " + Constants.LANGUAGE + " -r "
					+ opdir +" -t "+Constants.STRICTNESS_MEDIUM+directorycommand+ ipdir + "\"";
		}else {
			cmd = jarCommand + Constants.LIBRARY_PATH + " -l " + Constants.LANGUAGE + " -r "
					+ opdir +" -t "+Constants.STRICTNESS_EASY+directorycommand+ ipdir + "\"";
		}
			
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.toString());
		}
	}
}
