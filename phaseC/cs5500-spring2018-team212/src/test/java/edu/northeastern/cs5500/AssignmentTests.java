package edu.northeastern.cs5500;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.HashMap;

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
		Assignment a = new Assignment("HW1", 1, Date.valueOf("2018-03-22"), Date.valueOf("2018-04-21"), false, false, "", 0, "", 1);
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
	@PrepareForTest({S3.class, ZipUtil.class})
	public void testAssignmentService() {
        PowerMockito.mockStatic(S3.class);
        PowerMockito.mockStatic(ZipUtil.class);
		AssignmentService svc = new AssignmentService();
		when(S3.putObject(any(String.class), any(String.class), any(String.class), eq(false))).thenReturn("shaaaa");
		when(S3.putObject(any(String.class), any(String.class), any(String.class), eq(true))).thenReturn("http://meh");
		when(S3.uploadDir(any(String.class), any(String.class))).thenReturn("http://meh2");
		JSONObject obj = new JSONObject();

		try {
			obj.put("name", "HW1");
			obj.put("githublink", "https://github.com/team212test/test1");
			obj.put("studentid", 1);
			obj.put("courseid", 2);
			obj.put("hwName", "HW1");
			obj.put("parentAssignment", 1);
			svc.uploadGit(obj.toString());
			obj.put("githublink", "https://github.com/team212test/test2");
			svc.uploadGit(obj.toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		System.out.println(svc.getAssignmentById(2));
		System.out.println(svc.getSubmissions(4, 2, "HW1"));
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("studentId", "3");
		map.put("courseId", "2");
		map.put("duedate", "2018-08-08");
		map.put("name", "HW10");
		System.out.println(svc.createAssignmentForProfessor(map));
	}
	
	@Test
	public void testAvailableAssignment() {
		AssignmentDao dao = AssignmentDao.getInstance();
		dao.getAvailableAssignments(2, 1);
		System.out.println(dao.getAvailableAssignments(2, 1));
	}

}