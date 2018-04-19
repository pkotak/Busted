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
import {SharedService} from '../../../services/shared.service.client';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-page-new',
  templateUrl: './page-new.component.html',
  styleUrls: ['./page-new.component.css']
})
export class PageNewComponent implements OnInit {

  @ViewChild('f') createForm: NgForm;

  wid: String;
  userId: String;
  user: any;
  developerId: String;
  websites: Website[];
  description: String;
  pid: String;
  coursename: String;
  title: String;
  pages: Page[];
  duedate: Date;
  course: any;
  courses: [{}];

  // inject route info in constructor
  constructor(
    private userService: UserService,
    private websiteService: WebsiteService,
    private pageService: PageService,
    private route: ActivatedRoute,
    private router: Router,
    private cookieService: CookieService,
    private sharedService: SharedService) { }

  getUser() {
    // this.user = JSON.parse(localStorage.getItem("user"));
    this.user = this.sharedService.user;
    this.userId = this.user['_id'];
  }

  create(name, duedate) {
    if (!name) {
      alert('Please input assignment name');
    } else {
      const newPage = {
        name: name,
        duedate: duedate
      };

      this.pageService.createPage(this.wid, newPage).subscribe((pages) => {
        this.pages = pages;
        this.router.navigate(['user', 'website', this.wid, 'page']);
      });
    }
  }

  createAssignment(name, duedate) {
    if (!name) {
      alert('Please input assignment name');
    } else {
      const newAssignment = {
        studentId: this.user.id,
        name: name,
        duedate: duedate,
        courseId: this.course.id,
        githublink: '',
      };
      console.log(newAssignment);

      this.pageService.createAssignment(newAssignment).subscribe((courses) => {
        this.courses = courses;
        this.router.navigate(['user', 'website', this.wid, 'page']);
      });
    }
  }


  // notify the changes of the route
  ngOnInit() {
    // invoke a function that can pass the value of the parameters
    this.route.params.subscribe((params: any) => {
      // this.user = this.userService.findUserById(this.userId);
      this.wid = params['wid'];
      // alert('userId: ' + this.userId);
      // this.websites = this.websiteService.findWebsitesByUser(this.userId);
      console.log(this.websites);
    });

    this.userId = this.cookieService.get('user');

    console.log(this.userId);

    this.websiteService.findWebsiteById(this.userId, this.wid)
      .subscribe((course) => {
        this.course = course;
        console.log(course);
      });

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });
  }
}
