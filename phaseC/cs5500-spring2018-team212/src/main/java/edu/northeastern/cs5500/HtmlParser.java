package edu.northeastern.cs5500;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * class to extract the data from the HTML report
 * @author abhiruchi
 *
 */
public class HtmlParser implements IHTMLParser{

	private static final Logger LOGGER = Logger.getLogger(HtmlParser.class.getName());
	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.IHTMLParser#parse()
	 */
	@Override
	public List<PlagiarismResult> getSimilarityScore(String outputDir) {
		List<PlagiarismResult> plagiarism = new ArrayList<PlagiarismResult>();

		List<File> alldirList = new ArrayList<File>(getAllDir(outputDir));
		List<File> opdirList = new ArrayList<File>();

		for (File fl : alldirList) {
			opdirList.addAll(getRelevantFiles(fl.getAbsolutePath()));
		}
		for(int i=0; i<opdirList.size(); i++) {			
			File in = opdirList.get(i);
			try {
				Document doc = Jsoup.parse(in, null);
				String documentText = doc.body().text();
				String[] documentTextArray = documentText.split(" ");
				int similarityScore = Math.round(Float.parseFloat(documentTextArray[5].replaceAll("%", "")));
				int aid1 = Integer.parseInt(documentTextArray[2]);
				int aid2 = Integer.parseInt(documentTextArray[4]);
				String path = alldirList.get(i).getName();
				PlagiarismResult res = new PlagiarismResult(similarityScore, aid1, aid2, path);
				plagiarism.add(res);
			} catch (IOException e) {
				LOGGER.info(e.toString());
			} 
		}
		return plagiarism;
	}

	/**
	 * method to return the list of all Directories present in this directory.s
	 * @param opdir
	 * @return
	 */
	public List<File> getAllDir(String opdir) {
		File folder = new File(opdir);
		List<File> fileList = new ArrayList<File>();

		try {	
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isDirectory()) 
					fileList.add(listOfFiles[i]);
			}
		} catch (NullPointerException e) {
			LOGGER.info(e.toString());
		}

		return fileList;
	}

	/**
	 * method to get all the files that are relevant to find the plagiarism score
	 * in this directory.
	 * @param opdir
	 * @return
	 */
	public List<File> getRelevantFiles(String opdir) {
		File folder = new File(opdir);
		List<File> fileList = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File x = listOfFiles[i];
				if (x.getName().endsWith("link.html"))
					fileList.add(x);
			}
		}
		return fileList;
	}
}