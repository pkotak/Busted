import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../../services/website.service.client';
import { NgForm } from '@angular/forms';
import {PageService} from '../../../services/page.service.client';
import { SharedService } from '../../../services/shared.service.client';
import {CookieService} from 'ngx-cookie-service';


@Component({
  selector: 'app-page-list',
  templateUrl: './page-list.component.html',
  styleUrls: ['./page-list.component.css']
})
export class PageListComponent implements OnInit {

  @ViewChild('f') updateForm: NgForm;

  wid: String;
  userId: String;
  user: any;
  developerId: String;
  courses: [{}];
  assignments: [{}];
  pid: String;
  description: String;
  course: any;

  // inject route info in constructor
  constructor(
    private userService: UserService,
    private websiteService: WebsiteService,
    private pageService: PageService,
    private route: ActivatedRoute,
    private sharedService: SharedService,
    private router: Router,
    private cookieService: CookieService) { }


  // goEditAss() {
  //   if (this.user.role === 'STUDENT') {
  //     alert('Student cannot create classes');
  //   } else if (this.user.role === 'TA') {
  //     alert('TA cannot create classes');
  //   } else {
  //     this.router.navigate(['user', 'website', this.wid, 'page', page._id ]);
  //   }
  // }

  gotoReport() {
    window.open("http://localhost:63342/Busted/Results/index.html");
  }

  createAssignment() {
    if (this.user.role === 'STUDENT') {
      alert('Student cannot create assignments.');
    } else if (this.user.role === 'TA') {
      alert('TA cannot create assignments.');
    } else {
      this.router.navigate(['user', 'website', this.wid, 'page', 'new' ]);
    }
  }

  // getUser() {
  //   // this.user = JSON.parse(localStorage.getItem("user"));
  //   this.user = this.sharedService.user;
  //   this.userId = this.user['_id'];
  // }

  // notify the changes of the route
  ngOnInit() {
    // invoke a function that can pass the value of the parameters
    this.route.params.subscribe((params: any) => {
      this.wid = params['wid'];
    });

    // this.getUser();

    this.userId = this.cookieService.get('user');

    console.log(this.userId);

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });


    this.websiteService.findWebsitesByUser(this.userId)
      .subscribe((courses) => {
        this.courses = courses;
        console.log(courses);
      });

    this.websiteService.findWebsiteById(this.userId, this.wid)
      .subscribe((course) => {
        this.course = course;
      });

    // this.pageService.findPagesByWebsiteId(this.wid)
    //   .subscribe((data: any) => {
    //     this.assignments = data;
    //     console.log(data);
    //   });

    this.pageService.findAssignmentsByCourseId(this.wid)
      .subscribe((data: any) => {
        this.assignments = data;
        console.log(data);
      });

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });
         // this.pages = this.pageService.findPageByWebsiteId(this.wid);
  }
}
