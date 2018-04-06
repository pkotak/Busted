import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../../services/website.service.client';
import { ReportService} from '../../../services/report.service.client';
import { Website } from '../../../models/website.model.client';
import { Report } from '../../../models/report.model.client';
import { NgForm } from '@angular/forms';
import {PageService} from '../../../services/page.service.client';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {environment} from '../../../../environments/environment';

import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-report-detail',
  templateUrl: './report-detail.component.html',
  styleUrls: ['./report-detail.component.css']
})
export class ReportDetailComponent implements OnInit {

  wid: String;
  userId: String;
  user: User;
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
  url: SafeResourceUrl;
  page: any;
  imageFileStream: String;
  gitUrl: String;
  courseid: String;
  assignmentid: String;
  hwName: String;
  report: Report;
  reportid: String;
  
  

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

  //transform
  transform(url) {
      return this.sanitizer.bypassSecurityTrustResourceUrl(url);
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
      this.reportid = params['rid'];
      this.pageService.findAssignmentById(this.assignmentid).subscribe((assignment) => {
          if (assignment != null) {
              this.hwName = assignment.name;
          }
      } );

      this.reportService.getReportById(this.reportid).subscribe((report : Report) => {
          if (report != null) {
              this.report = report;
              var s3url = this.report.downloadLink.replace(".zip", "/match0.html");
              this.url = this.sanitizer.bypassSecurityTrustResourceUrl(s3url);
              console.log(this.url);
          }
      })
    });

    console.log(this);
    this.userId = this.cookieService.get('user');
  }

}
