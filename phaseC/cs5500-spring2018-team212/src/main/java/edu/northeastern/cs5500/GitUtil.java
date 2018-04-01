package edu.northeastern.cs5500;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;

public class GitUtil {

	private GitUtil() {
		
	}

	static final Logger LOGGER = Logger.getLogger(GitUtil.class.getName());
	/**
	 * Clone a Github Repo.
	 * 
	 * @param githublink
	 *            Link for Github repo.
	 * @param tempDir
	 *            Link of directory to clone to.
	 */
	public static void cloneRepo(String githublink, File tempDir) {
		
		try {
			Git.cloneRepository().setURI(githublink).setDirectory(tempDir).call();
		} catch (GitAPIException e) {
			LOGGER.info(e.toString());
		} catch (JGitInternalException e) {
			LOGGER.info(e.getMessage());
		}
	}
}
