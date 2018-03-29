package edu.northeastern.cs5500;

/**
 * interface to check the metadata of the assignments
 * @author abhiruchi
 *
 */
public interface IMetaData {

	/**
	 * method to extract metadata
	 * @param filepath of the given file
	 * @return the metadata as string
	 */
	public String getMetaData(String filepath);
	
	/**
	 * method to compare metadata
	 * @param f1path - path of first file
	 * @param f2path - path of second file
	 * @return int indicating comparison files
	 */
	public int checkifSame(String f1path, String f2path);
	
}
