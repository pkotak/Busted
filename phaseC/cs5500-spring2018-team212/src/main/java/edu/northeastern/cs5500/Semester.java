package edu.northeastern.cs5500;

import java.util.Calendar;

/**
 * Semester class - implementation of the ISemester interface.
 * @author abhiruchi
 *
 */
public class Semester implements ISemester{

	private static Semester instance = null;
	private int month = -1;

	/**
	 * constructor
	 */
	private Semester() {
		//empty
	}

	public void setMonth(int num) {
		this.month = num;
	}
	/**
	 * @return an instance of the Semester class.
	 */
	public static Semester getInstance() {
		if (instance == null)
			return new Semester();
		else
			return instance;
	}

	/**
	 * String representations of the three academic semesters.
	 */
	private static final String SPRING = "Spring";
	private static final String SUMMER = "Summer";
	private static final String FALL = "Fall";	

	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.ISemester#checkSemester()
	 */
	@Override
	public String getSemester() {
		if (month == -1)
			month = Calendar.getInstance().get(Calendar.MONTH);
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		
		if (month >= Calendar.JANUARY && month <= Calendar.APRIL)
			return SPRING + " " + year;
		else if	(month >= Calendar.MAY && month <= Calendar.AUGUST)
			return SUMMER + " "  + year;
		else
			return FALL + " "  + year;
	}

}