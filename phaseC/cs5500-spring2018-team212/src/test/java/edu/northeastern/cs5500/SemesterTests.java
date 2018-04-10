package edu.northeastern.cs5500;

import org.junit.Test;

public class SemesterTests {

	@Test
	public void test() {
		Semester s = Semester.getInstance();
		s.getSemester();
	}

	@Test
	public void test1() {
		Semester s = Semester.getInstance();
		s.setMonth(11);
		s.getSemester();
	}

	@Test
	public void test2() {
		Semester s = Semester.getInstance();
		s.setMonth(7);
		s.getSemester();
	}

}
