package edu.northeastern.cs5500;

public class Language {

	public static String setLanguage(String language) {
		if (language.equalsIgnoreCase("python"))
			return "python3";
		else if (language.equalsIgnoreCase("java")) 
			return "java17";
		else if (language.equalsIgnoreCase("scheme"))
			return "scheme";
		else if (language.equalsIgnoreCase("c"))
			return "c/c++";
		else
			return "text";
	}	
}