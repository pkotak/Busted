package edu.northeastern.cs5500;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author abhiruchi
 *
 */
public class MetaDataTests {

	/**
	 * tests MetadataAnalyser
	 */
	@Test
	public void test1() {
		MetadataAnalyser mda = new MetadataAnalyser();
		mda.getMetaData("C:\\Users\\abhir\\Documents\\team-212\\phaseC\\cs5500-spring2018-team212\\Python Code Sample\\sample.py");
	}

	/**
	 * tests MetadataAnalyser
	 */
	@Test
	public void test2() {
		MetadataAnalyser mda = new MetadataAnalyser();
		mda.getMetaData("C:\\Users\\abhir\\Documents\\team-212\\phaseC\\cs5500-spring2018-team212\\Python Code Sample\\sam.py");
	}
	/**
	 * tests MetadataAnalyser compare files 
	 */
	@Test
	public void testCompare() {
		String f1path = "C:\\Users\\abhir\\Documents\\team-212\\phaseC\\cs5500-spring2018-team212\\Python Code Sample\\sample.py";
		String f2path = "C:\\Users\\abhir\\Documents\\team-212\\phaseC\\cs5500-spring2018-team212\\Python Code Sample\\hello.py";
		MetadataAnalyser mda = new MetadataAnalyser();
		assertEquals(0, mda.checkifSame(f1path, f2path));
	}

}