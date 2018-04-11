import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../../services/website.service.client';
import { Website} from '../../../models/website.model.client';
import { NgForm } from '@angular/forms';
import {SharedService} from '../../../services/shared.service.client';
import {CookieService} from 'ngx-cookie-service';


@Component({
  selector: 'app-website-new',
  templateUrl: './website-new.component.html',
  styleUrls: ['./website-new.component.css']
})
export class WebsiteNewComponent implements OnInit {
  @ViewChild('f') createForm: NgForm;

  wid: String;
  userId: String;
  user: any;
  name: String;
  developerId: String;
  code: String;
  semester: String;
  courses: [{}];
  description: String;
  newCourse: any;
  section: any;

  // inject route info in constructor
  constructor(
    private websiteService: WebsiteService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private cookieService: CookieService,
    private userService: UserService) { }

  getUser() {
    // this.user = JSON.parse(localStorage.getItem("user"));
    this.user = this.sharedService.user;
    this.userId = this.user['_id'];
  }

  create() {

    this.name = this.createForm.value.name;
    this.code = this.createForm.value.code;
    this.semester = this.createForm.value.semester;
    this.section = this.createForm.value.section;


    if (this.semester === '') {
      alert('Please input semester');
    } else if (this.code === '') {
      alert('Please input course code');
    } else if (this.name === '') {
      alert('Please input course name');
    } else {
      this.newCourse = {
        // _id: this.websiteService.newId(),
        name: this.name,
        code: this.code,
        semester: this.semester,
        section: this.section
      };
    }
    console.log(this.newCourse);


    this.websiteService.createWebsite(this.userId, this.newCourse)
      .subscribe((courses) => {
        // this.websites = websites;
        alert('You have added "' + this.newCourse.code + ' ' + this.newCourse.name + '"');
        this.router.navigate(['profile']);
      });
  }


  // notify the changes of the route
  ngOnInit() {

    // invoke a function that can pass the value of the parameters
    this.route.params.subscribe((params: any) => {
      this.wid = params['wid'];
    });

    this.userId = this.cookieService.get('user');
    console.log(this.userId);

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });

    // this.userId = this.user['id'];

    this.websiteService.findWebsitesByUser(this.userId)
      .subscribe((courses) => {
        this.courses = courses;
        console.log(courses);
      });

  }

}





