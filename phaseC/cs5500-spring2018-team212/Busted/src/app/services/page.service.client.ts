import {Page} from '../models/page.model.client';
import {Injectable} from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/Rx';
import {Response} from '@angular/http';
import { environment} from '../../environments/environment';


// make class usable for all components
@Injectable()
export class PageService {

  constructor(private http: Http) {}

  baseUrl = environment.baseUrl;
  // adds the page parameter instance to the local pages array. The new
  // page's websiteId is set to the websiteId parameter
  newId() {
    return (Number( Math.floor((Math.random()) * 10000))).toString();
  }

  // create assignment for a course
  createPage(courseId, assignment) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment';
    return this.http.post(url, assignment)
      .map((response: Response) => {
        return response.json();
      });
  }

  createAssignment(assignment) {
    const url = this.baseUrl + '/api/assignment/new';
    console.log(assignment);
    return this.http.post(url, assignment)
      .map((response: Response) => {
        return response.json();
      });
  }

  checkAgain(assignments) {
    const url = this.baseUrl + '/api/course/assignment/checkAgain';
    return this.http.post(url, assignments)
      .map((response: Response) => {
        return response.json();
      });
  }

  getAssignmentProgress(courseId, assignmentName) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment/' + assignmentName;
    return this.http.get(url)
      .map((response: Response) => {
        return response.json();
      });
  }

  // retrieves the assignments in a course
  findPagesByWebsiteId(courseId) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment';
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }

  // Find an assignment's info by course Id
  findAssignmentById(assignmentId) {
    const url = this.baseUrl + '/api/course/assignment/' + assignmentId;
    return this.http.get(url).map((response: Response) => {
      console.log(response);
      return response.json();
    });
  }

  findAssignmentsByCourseId(userId , courseId) {
    const url = this.baseUrl + '/api/' + userId + '/course/' + courseId + '/assignment';
    return this.http.get(url).map((response: Response) => {
      console.log(response);
      return response.json();
      });
  }

  findSubmissions(hwName, courseId) {
      const url = this.baseUrl + '/api/course/' + courseId + '/assignment/' + hwName;
      console.log(hwName);
      return this.http.get(url).map((response: Response) => {
        console.log(response);
        return response.json();
        });
  }

  findSubmissionsForOneStudent(hwName, courseId, userId) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment/' + hwName + '/user/' + userId;
    console.log(hwName);
    return this.http.get(url).map((response: Response) => {
      console.log(response);
      return response.json();
    });
  }

  // retrieves the page in local pages array whose _id matches the pageId parameter
  findPageById(websiteId, pageId) {
    const url = this.baseUrl + '/api/website/' + websiteId + '/page/' + pageId;
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }


  // updates the page in local pages array whose _id matches the pageId parameter
  updatePage(websiteId, pageId, page) {
    const url = this.baseUrl + '/api/website/' + websiteId + '/page/' + pageId;
    return this.http.put(url, page).map((response: Response) => {
      return response.json();
    });
  }

  // updates the assignment
  updateAssignment(courseId, assignmentId, assignment) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment/' + assignmentId;
    return this.http.put(url, assignment).map((response: Response) => {
      return response.json();
    });
  }

  // removes the page from local pages array whose _id matches the pageId parameter
  deletePage(websiteId, pageId) {
    const url = this.baseUrl + '/api/website/' + websiteId + '/page/' + pageId;
    return this.http.delete(url).map((response: Response) => {
      return response.json();
    });
  }

  deleteAssignment(courseId, assignmentId) {
    const url = this.baseUrl + '/api/course/assignment/' + assignmentId;
    return this.http.delete(url).map((response: Response) => {
      return response.json();
    });
  }


// uploads git repo
  uploadGit(githublink, courseid, studentid, hwName, parentAssignmentId) {
    console.log(githublink, courseid, studentid);
    const data = {
        'githublink': githublink,
        'courseid': courseid,
        'studentid': studentid,
        'hwName': hwName,
        'parentAssignment': parentAssignmentId
    };
    const url = this.baseUrl + '/api/assignment/uploadGit';
    return this.http.post(url, data).map((response: Response) => {
      return response.json();
    });
  }
}
