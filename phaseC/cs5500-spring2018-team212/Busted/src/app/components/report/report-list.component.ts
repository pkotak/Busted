import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service.client';
import { User } from '../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../services/website.service.client';
import { ReportService} from '../../services/report.service.client';
import { Website } from '../../models/website.model.client';
import { NgForm } from '@angular/forms';
import {Page} from '../../models/page.model.client';
import {PageService} from '../../services/page.service.client';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {environment} from '../../../environments/environment';

import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html',
  styleUrls: ['./report-list.component.css']
})
export class ReportListComponent implements OnInit {
  @ViewChild('f') createForm: NgForm;

  wid: String;
  userId: String;
  user: any;
  developerId: String;
  websites: Website[];
  pid: String;
  description: String;
  widgets = [];
  youtubeUrl: SafeResourceUrl;
  type: String;
  widget = {type: '', width: ''};
  width: String;
  baseUrl: String;
  url: String;
  page: any;
  imageFileStream: String;
  gitUrl: String;
  courseid: String;
  assignmentid: String;
  hwName: String;
  reports = [];
  strategy: String;
  language: String;
  percentage: number;

  // inject route info in constructor
  constructor(
    private userService: UserService,
    private websiteService: WebsiteService,
    private pageService: PageService,
    private cookieService: CookieService,
    private reportService: ReportService,
    private route: ActivatedRoute,
    private router: Router,
    public sanitizer: DomSanitizer) { }

    recompare() {
      this.strategy = this.createForm.value.strategy;
      this.language = this.createForm.value.language;
      this.percentage = this.createForm.value.percentage;
      console.log(this.strategy);
      console.log(this.language);
      console.log(this.percentage);


      this.reportService.recompare(this.strategy, this.language, this.courseid, this.hwName)
        .subscribe((reports) => {
          this.reports = reports;
          console.log(this.reports);
        });
    }

  // notify the changes of the route
  ngOnInit() {
    // width = str.substring(0, str.length - 1);
    // this.testwidth = '80%';
    this.baseUrl = environment.baseUrl;
    // invoke a function that can pass the value of the parameters
    this.route.params.subscribe((params: any) => {
      // this.user = this.userService.findUserById(this.userId);
      this.courseid = params['wid'];
      this.assignmentid = params['pid'];
      this.pageService.findAssignmentById(this.assignmentid).subscribe(( assignment ) => {
          if (assignment != null) {
              this.hwName = assignment.name;
              this.reportService.getReportsForAssignment(this.courseid, this.hwName)
                  .subscribe((reports) => {this.reports = reports; console.log(this.reports); });
          }
      } );
    });

    console.log(this);
    this.userId = this.cookieService.get('user');
    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });

    if (this.userId === '') {
      alert('Please login first');
      this.router.navigate(['']);
    }
  }

}
