package edu.northeastern.cs5500;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {

	private static final Logger LOGGER = Logger.getLogger(HtmlParser.class.getName());

	public HtmlParser() {
		// Empty constructor
	}

	public String parse() {
		File in = new File("Results/match0-link.html");
		try {
			Document doc = Jsoup.parse(in, null);
			String documentText = doc.body().text();
			String[] documentTextArray = documentText.split(" ");
			for (String s : documentTextArray) {
				if (s.endsWith("%"))
					return s;
			}
		} catch (IOException e) {
			LOGGER.info(e.toString());
		}
		return null;
	}

}
