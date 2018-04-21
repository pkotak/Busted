import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../../services/website.service.client';
import { Website } from '../../../models/website.model.client';
import { NgForm } from '@angular/forms';
import {Page} from '../../../models/page.model.client';
import {PageService} from '../../../services/page.service.client';
import {Widget} from '../../../models/widget.model.client';
import {WidgetService} from '../../../services/widget.service.client';
import {WidgetEditComponent} from '../widget-edit/widget-edit.component';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {environment} from '../../../../environments/environment';
import {OrderByPipe} from './order-by-pipe.pipe';
import { SortableDirective} from './sortable.directive';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-widget-list',
  templateUrl: './widget-list.component.html',
  styleUrls: ['./widget-list.component.css']
})
export class WidgetListComponent implements OnInit {
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
  gitUrl: String;
  courseid: String;
  assignmentid: String;
  hwName: String;
  submissions: [{}];
  language: String;

  // inject route info in constructor
  constructor(
    private userService: UserService,
    private websiteService: WebsiteService,
    private widgetService: WidgetService,
    private pageService: PageService,
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private router: Router,
    public sanitizer: DomSanitizer) { }


  updateImageUrl(string) {
    let newurl = '';
    if (string.substring(1, 4) === 'ass') {
       newurl = this.baseUrl + string;
    } else {
      newurl = string;
    }
    return newurl;
  }

  uploadGit() {
    this.language = this.createForm.value.language;
    console.log(this.language);
    if (this.gitUrl === null) {
      alert ('Please input your github link or upload file.');
    } else {
      alert('You have submitted you homework!');
      this.pageService.uploadGit(this.gitUrl, this.courseid, this.userId, this.hwName, this.assignmentid, this.language)
        .subscribe(
          (data) => {
            console.log(data);
            alert('You have submitted you homework!');
            this.router.navigate(['user', 'website', this.courseid, 'page']);
          }
        );
    }
  }

  reorderWidgets(indexes) {
    this.widgetService.reorderWidgets(indexes.startIndex, indexes.endIndex, this.pid)
      .subscribe(
        (data) => console.log(data)
        // console.log(this.widgets);
      );
    console.log(this.widgets);
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
              console.log(assignment);
          }
        this.pageService.findSubmissionsForOneStudent(this.hwName, this.courseid, this.userId)
          .subscribe((submissions) => {
            this.submissions = submissions;
            console.log(submissions);
          });
      });
    });

    console.log(this);
    this.userId = this.cookieService.get('user');
  }

}
