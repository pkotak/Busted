package edu.northeastern.cs5500;

import java.io.File;

import org.junit.Test;

public class DirectoryTests {

	@Test
	public void test() {
		File f1 = new File("1");
		f1.mkdir();
		File f2 = new File("2");
		f2.mkdir();
		File root = new File("testdir");
		root.mkdir();
		IDirectory dir = new Directory();
		dir.mergeDir(f1.getName(), f2.getName(), root.getName());
		dir.getOtherDirs(f1.getName(), f2.getName());
		dir.deleteDir(root.getName());
		dir.deleteDir(f1.getName());
		dir.deleteDir(f2.getName());
		
	}

}
