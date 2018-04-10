package edu.northeastern.cs5500;

import java.io.File;
import org.junit.Test;

public class GitUtilTests {

	@Test
	public void testGitClone() {
		GitUtil.cloneRepo("https://github.com/team212test/empty", new File("./empty"));
	}

	
	@Test
	public void testGitClone2() {
		GitUtil.cloneRepo("", new File(""));
	}
}
