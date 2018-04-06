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
  selector: 'app-page-edit',
  templateUrl: './page-edit.component.html',
  styleUrls: ['./page-edit.component.css']
})
export class PageEditComponent implements OnInit {

  @ViewChild('f') updateForm: NgForm;

  wid: String;
  pid: String;
  name: String;
  userId: String;
  title: String;
  user: any;
  pages: [{}];
  page: any;
  developerId: String;
  websites: Website[];
  description: String;
  course: any;
  assignment: any;

  // inject route info in constructor
  constructor(
    private userService: UserService,
    private websiteService: WebsiteService,
    private pageService: PageService,
    private router: Router,
    private route: ActivatedRoute,
    private sharedService: SharedService,
    private cookieService: CookieService) { }

  getUser() {
    // this.user = JSON.parse(localStorage.getItem("user"));
    this.user = this.sharedService.user;
    this.userId = this.user['_id'];
  }



  update() {
      const newAssignment = {
        studentId: this.userId,
        name: this.assignment.name,
        duedate: this.assignment.duedate,
        courseId: this.course.id,
        githublink: '',
      };
      console.log(newAssignment);

      this.pageService.updateAssignment(this.wid, this.pid, newAssignment)
        .subscribe((pages) => {
          // this.pages = pages;
          this.router.navigate(['user', 'website', this.wid, 'page']);
        });
  }

  // deletePage(pageId) {
  //   this.pageService.deletePage(pageId);
  // }

  deletePage(websiteId, pageId) {
    if (!((this.user._id === this.page.owner) || (this.user.role === 'ADMIN'))) {
      alert('Only admin can modify other\'s portfolio.');
    } else {
      this.pageService.deletePage(websiteId, pageId)
        .subscribe((pages) => {
          this.pages = pages;
          this.router.navigate(['user', 'website', this.wid, 'page']);
          // window.location.reload(false); // reload page
        });
    }
  }


  deleteAssignment(courseId, assignmentId) {

    this.pageService.deleteAssignment(courseId, assignmentId)
      .subscribe((pages) => {
        this.pages = pages;
        this.router.navigate(['user', 'website', this.wid, 'page']);
      });
  }

  // notify the changes of the route
  ngOnInit() {
    // invoke a function that can pass the value of the parameters
    this.route.params.subscribe((params: any) => {
      // this.user = this.userService.findUserById(this.userId);
      this.wid = params['wid'];
      this.pid = params['pid'];
      console.log(this.pid);
    });

    this.websiteService.findWebsiteById(this.userId, this.wid)
      .subscribe((course) => {
        this.course = course;
      });

    this.pageService.findAssignmentById(this.pid)
      .subscribe((assignment) => {
        this.assignment = assignment;
      });


    this.userId = this.cookieService.get('user');

    console.log(this.userId);

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });


  }
}

