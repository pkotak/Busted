package edu.northeastern.cs5500;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zeroturnaround.zip.ZipUtil;

import edu.northeastern.springbootjdbc.controllers.AssignmentService;
import edu.northeastern.springbootjdbc.daos.AssignmentDao;
import edu.northeastern.springbootjdbc.models.Assignment;

import edu.northeastern.cs5500.S3;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*","org.mockito.*", "org.apache.http.*", "org.apache.http.conn.ssl.*", "javax.net.ssl.*" , "javax.crypto.*"})
public class AssignmentTests {

	@Test
	public void testAssignmentModel() {
		Assignment a = new Assignment("HW1", 4, Date.valueOf("2018-03-22"), Date.valueOf("2018-04-21"), false, false, "", 0, "", 1);
		a.setCourseID(a.getCourseID());
		a.setDuedate(a.getDuedate());
		a.setGithubLink(a.getGithubLink());
		a.setId(a.getId());
		a.getIsChecked();
		a.setIsChecked(1);
		a.setIsChecked(0);
		a.getMetadatamismatch();
		a.setMetadatamismatch(1);
		a.setMetadatamismatch(0);
		a.setPlagiarismCount(a.getPlagiarismCount());
		a.setShaID(a.getShaID());
		a.setName(a.getName());
		a.setStudentID(a.getStudentID());
		a.setUploadDate(a.getUploadDate());
	}


	@Test
	@PrepareForTest({S3.class, ZipUtil.class, GitUtil.class})
	public void testAssignmentService() {
        PowerMockito.mockStatic(S3.class);
        PowerMockito.mockStatic(ZipUtil.class);
        PowerMockito.mockStatic(GitUtil.class);
		AssignmentService svc = new AssignmentService();
		when(S3.putObject(any(String.class), any(String.class), any(String.class), eq(false))).thenReturn("shashashasha");
		when(S3.putObject(any(String.class), any(String.class), any(String.class), eq(true))).thenReturn("http://meh");
		when(S3.uploadDir(any(String.class), any(String.class))).thenReturn("http://meh2");
		JSONObject obj1 = new JSONObject();
		
		try {
			obj1.put("githublink", "https://github.com/team212test/test1");
			obj1.put("studentid", 1);
			obj1.put("courseid", 716);
			obj1.put("hwName", "HW3");
			obj1.put("parentAssignment", 2020);
			obj1.put("language", "python");
			svc.uploadGit(obj1.toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		System.out.println(svc.getAssignmentById(2020));
		System.out.println(svc.getSubmissions(716, "HW3"));
	}
	
	@Test
	public void testAvailableAssignment() {
		AssignmentDao dao = AssignmentDao.getInstance();
		dao.getAvailableAssignments(2, 1);
		System.out.println(dao.getAvailableAssignments(2, 1));
	}

	@Test (expected = Exception.class)
	public void testaddReports() {
		List<PlagiarismResult> results = new ArrayList<PlagiarismResult>();
		PlagiarismResult p1 = new PlagiarismResult(95, 12, 13, "");
		PlagiarismResult p2 = new PlagiarismResult(65, 1122, 913, "");
		results.add(p1);
		results.add(p2);
		AssignmentService.addReports(results, "plagiarismresults", "", 1, 716);
	}

}