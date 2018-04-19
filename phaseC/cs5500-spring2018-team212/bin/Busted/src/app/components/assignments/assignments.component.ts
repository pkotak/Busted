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
import {NgForm} from '@angular/forms';
import { ReportService } from '../../services/report.service.client';

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css']
})
export class AssignmentsComponent implements OnInit {
  @ViewChild('f') createForm: NgForm;
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
  courses = [{}];
  currentcourse: any;
  courseIds = [];
  strategy: String;
  language: String;

  constructor(private userService: UserService,
              private websiteService: WebsiteService,
              private widgetService: WidgetService,
              private pageService: PageService,
              private cookieService: CookieService,
              private route: ActivatedRoute,
              private router: Router,
              private reportSerivce: ReportService) { }

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

  addCourse(courseId) {
    if (this.courseIds.indexOf(courseId) !== -1) {
      const i = this.courseIds.indexOf(courseId);
      console.log(i);

      this.courseIds.splice(i, 1);
      console.log(this.courseIds);
    } else {
      this.courseIds.push(courseId);
      console.log(this.courseIds);
    }
  }

  checkSemester() {
    this.strategy = this.createForm.value.strategy;
    this.language = this.createForm.value.language;
    console.log(this.strategy);
    console.log(this.language);

    if (this.courseIds.length !== 1 || this.assignments.length < 1) {
      alert('Please select one semester and one submission you want to check.');
    } else {
      const courseId1 = this.courseIds[0];
      // const courseId2 = this.courseIds[1];

    this.reportSerivce.checkSemester(courseId1, this.assignments[0], this.strategy, this.language)
      .subscribe((report) => {
        console.log(report.id);
        this.router.navigate(['user', 'website', this.wid, 'page', 'this.pid', 'report', report.id]);
      });
    }
  }

  checkTwo() {
    this.strategy = this.createForm.value.strategy;
    this.language = this.createForm.value.language;
    console.log(this.strategy);
    console.log(this.language);

    if (this.strategy === null || this.language === null) {
      alert('Please select strategy and language.');
    } else if (this.assignments.length !== 2) {
      alert('Please select 2 submissions you want to recheck.');
    } else {
      const assignmentid1 = this.assignments[0];
      const assignmentid2 = this.assignments[1];

    this.reportSerivce.checkAgain(assignmentid1, assignmentid2, this.strategy, this.language)
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

    // this.websiteService.findAllClasses()
    //   .subscribe((classes) => {
    //     this.courses = classes;
    //     console.log(classes);
    //   });

    this.websiteService.findWebsiteById(this.userId, this.courseid)
      .subscribe((course) => {
        this.currentcourse = course;
        console.log(this.currentcourse.code);
        this.websiteService.findSameCourses(this.currentcourse.code)
          .subscribe((classes) => {
            this.courses = classes;
            console.log(classes);
          });
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
