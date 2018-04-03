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

  // retrieves the assignments in a course
  findPagesByWebsiteId(courseId) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment';
    return this.http.get(url).map((response: Response) => {
      return response.json();
    });
  }

  findAssignmentsByCourseId(courseId) {
    const url = this.baseUrl + '/api/course/' + courseId + '/assignment';
    return this.http.get(url).map((response: Response) => {
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

  // removes the page from local pages array whose _id matches the pageId parameter
  deletePage(websiteId, pageId) {
    const url = this.baseUrl + '/api/website/' + websiteId + '/page/' + pageId;
    return this.http.delete(url).map((response: Response) => {
      return response.json();
    });

  }


}
