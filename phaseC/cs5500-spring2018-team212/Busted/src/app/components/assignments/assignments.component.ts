import { Component, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { UserService } from '../../services/user.service.client';
import {environment} from '../../../environments/environment';
import {WidgetService} from '../../services/widget.service.client';
import {PageService} from '../../services/page.service.client';
import {CookieService} from 'ngx-cookie-service';
import {WebsiteService} from '../../services/website.service.client';
import {User} from '../../models/user.model.client';
import {Website} from '../../models/website.model.client';

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css']
})
export class AssignmentsComponent implements OnInit {
  wid: String;
  userId: String;
  user: User;
  developerId: String;
  websites: Website[];
  pid: String;
  description: String;
  widgets = [];
  type: String;
  widget = {type: '', width: ''};
  width: String;
  baseUrl: String;
  url: String;
  page: any;
  courseid: String;
  assignmentid: String;
  name: String;
  submissions: [{}];
  assignment: any;
  assignments = [];

  constructor(private userService: UserService,
              private websiteService: WebsiteService,
              private widgetService: WidgetService,
              private pageService: PageService,
              private cookieService: CookieService,
              private route: ActivatedRoute,
              private router: Router, ) { }

  add(assignmentId) {
    if (this.assignments.indexOf(assignmentId) !== -1) {
      const i = this.assignments.indexOf(assignmentId);
      console.log(i);

      this.assignments.splice(i, 1);
      console.log(this.assignments);
    } else {
      this.assignments.push(assignmentId);
      console.log(this.assignments);
    }
  }

  checkAgain() {
    if (this.assignments.length !== 2) {
      alert('Please select 2 submissions you want to recheck.');
    } else {
      this.pageService.checkAgain(this.assignments)
        .subscribe((report) => {
          console.log(report.id);
          this.router.navigate(['user', 'website', this.wid, 'page', 'this.pid', 'report', report.id]);
        });
    }

  }

  ngOnInit() {
    this.baseUrl = environment.baseUrl;
    this.userId = this.cookieService.get('user');

    // invoke a function that can pass the value of the parameters
    this.route.params.subscribe((params: any) => {
      // this.user = this.userService.findUserById(this.userId);
      this.courseid = params['wid'];
      this.assignmentid = params['pid'];

    });

    this.pageService.findAssignmentById(this.assignmentid).subscribe(( assignment ) => {
      if (assignment != null) {
        this.assignment = assignment;
        console.log(assignment);
        console.log(this.courseid);
        this.pageService.findSubmissions(this.assignment.name, this.courseid)
          .subscribe((submissions) => {
            this.submissions = submissions;
            console.log(submissions);
          });
      }
    });

  }

}
