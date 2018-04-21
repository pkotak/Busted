package edu.northeastern.cs5500;

import static org.junit.Assert.*;

import org.junit.Test;

public class LanguageTests {

	@Test
	public void test() {
		assertEquals("python3", Language.setLanguage("python"));
		assertEquals("java17", Language.setLanguage("java"));
		assertEquals("scheme", Language.setLanguage("scheme"));
		assertEquals("c/c++", Language.setLanguage("c"));
		assertEquals("text", Language.setLanguage("default"));
	}
}
