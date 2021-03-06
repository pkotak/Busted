import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../../services/website.service.client';
import {SharedService} from '../../../services/shared.service.client';
import { CookieService} from 'ngx-cookie-service';


@Component({
  selector: 'app-website-list',
  templateUrl: './website-list.component.html',
  styleUrls: ['./website-list.component.css']
})

export class WebsiteListComponent implements OnInit {
  // @ViewChild('f') updateForm: NgForm;
  wid: String;
  userId: String;
  user: any;
  developerId: String;
  courses: [{}];
  description: String;
  pid: String;
  mywebsite: String;
  role: String;

  // inject route info in constructor
  constructor(
    private websiteService: WebsiteService,
    private route: ActivatedRoute,
    private userService: UserService,
    private sharedService: SharedService,
    private router: Router,
    private cookieService: CookieService) { }

  addclass() {
    if (this.user.type === 'STUDENT') {
      alert('Student cannot create courses');
    } else if (this.user.type === 'TA') {
      alert('TA cannot create courses');
    } else {
      this.router.navigate(['user', 'website', 'new']);
    }
  }



  getUser() {
    // this.user = JSON.parse(localStorage.getItem("user"));
    this.user = this.sharedService.user;
    this.userId = this.user['_id'];
  }

  // notify the changes of the route
  ngOnInit() {

    this.userId = this.cookieService.get('user');

    console.log(this.userId);

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });

      this.websiteService.findAllClasses()
        .subscribe((classes) => {
          this.courses = classes;
          console.log(classes);
        });

      this.userService.findUserById(this.userId).subscribe((user: User) => {
        this.user = user;
        console.log(this.user);
      });

      console.log(this.courses);
  }


}
