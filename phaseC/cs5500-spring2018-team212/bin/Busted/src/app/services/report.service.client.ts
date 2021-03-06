import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/Rx';
import { Response } from '@angular/http';
import { environment } from '../../environments/environment';


// make class usable for all components
@Injectable()
export class ReportService {

    constructor( private http: Http ) { }

    baseUrl = environment.baseUrl;
    // adds the page parameter instance to the local pages array. The new
    // page's websiteId is set to the websiteId parameter
    newId() {
        return ( Number( Math.floor(( Math.random() ) * 10000 ) ) ).toString();
    }

  recompare(strategy,  language, courseid, hwName) {
    return this.http.get( this.baseUrl + '/api/report/' + courseid + '/get/' + hwName + '/' + strategy + '/' + language )
      .map(( res: Response ) => {
        return res.json();
      } );
  }

  checkAgain(assignmentid1, assignmentid2,  strategy, language) {
    const url = this.baseUrl + '/api/assignment/individual/' + assignmentid1 + '/' + assignmentid2 + '/' + strategy + '/' + language;
    return this.http.get(url)
      .map((response: Response) => {
        return response.json();
      });
  }


  checkSemester(courseId, assignmentId, strategy, language) {
    const url = this.baseUrl + '/api/assignment/semester/' + courseId + '/' + assignmentId + '/' + strategy + '/' + language;
    return this.http.get(url)
      .map((response: Response) => {
        return response.json();
      });
  }

  getReportsForAssignment( courseid, hwName ) {
        const data = {
                'hwName': hwName,
                'courseid': courseid
        };
        return this.http.get( this.baseUrl + '/api/report/' + courseid + '/get/' + hwName)
            .map(( res: Response ) => {
                return res.json();
            } );
    }

    getReportById(rid) {
        return this.http.get(this.baseUrl + '/api/report/' + rid)
            .map(( res: Response ) => {
                return res.json();
            } );
    }
}
