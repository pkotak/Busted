import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService} from '../../../services/website.service.client';
import { NgForm } from '@angular/forms';
import { ViewChild } from '@angular/core';
import {SharedService} from '../../../services/shared.service.client';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-website-edit',
  templateUrl: './website-edit.component.html',
  styleUrls: ['./website-edit.component.css']
})
export class WebsiteEditComponent implements OnInit {

  @ViewChild('f') updateForm: NgForm;

  wid: String;
  userId: String;
  user: any;
  name: String;
  developerId: String;
  courses: [{}];
  description: String;
  website: any;
  semester: String;
  code: String;
  course: any;

  // inject route info in constructor
  constructor(
    private websiteService: WebsiteService,
    private route: ActivatedRoute,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sharedService: SharedService,
    private userService: UserService,
    private cookieService: CookieService) { }

    update() {
      if (this.user.type === 'STUDENT') {
        alert ('Students cannot modify course information.');
      } else if (this.user.type === 'TA') {
        alert ('TA cannot modify course information.');
      } else {
        console.log();
        if (!this.course.semester) {
          alert('Please input course semester');
        } else if (!this.course.name) {
          alert('Please input course name');
        } else if (!this.course.code) {
          alert('Please input course code');
        } else {
          const newCourse = {
            id: this.wid,
            name: this.course.name,
            code: this.course.code,
            semester: this.course.semester
          };
          this.websiteService.updateWebsite(this.wid, newCourse)
            .subscribe((status) => {
              console.log(status);
              this.router.navigate(['profile']);
            });
        }
      }
    }

    joinClass() {
      this.userService.findUserInCourse(this.user.id, this.course.id, this.user.type)
        .subscribe((user) => {
          if (user.id === this.user.id) {
            alert('You are enrolled in this course already!');
          } else {
            const newCourseRole = {
              userId: this.user.id,
              type: this.user.type,
              courseId: this.course.id
            };
            console.log(newCourseRole);
            this.userService.joinCourse(newCourseRole)
              .subscribe((newuser) => {
                // console.log(status);
                // this.user = newuser;
                console.log(this.user);
                alert('Welcome to class "' + this.course.code + ' ' + this.course.name + '"');
                window.location.reload(false); // reload page
              });
          }
        });
    }

    dropClass() {
      this.userService.findUserInCourse(this.user.id, this.course.id, this.user.type)
        .subscribe((user) => {
          if (user.id !== this.user.id) {
            alert('You are not enrolled in this course!');
          } else {
            const courseRole = {
              userId: this.user.id,
              type: this.user.type,
              courseId: this.course.id
            };
            console.log(courseRole);
            this.userService.dropCourse(courseRole)
              .subscribe((newuser) => {
                // console.log(status);
                // this.user = newuser;
                console.log(this.user);
                alert('You have dropped "' + this.course.code + ' ' + this.course.name + '"');
                window.location.reload(false); // reload page
              });

          }
        });
    }


    goToAssignment() {

      this.userService.findUserInCourse(this.user.id, this.course.id, this.user.type)
        .subscribe((user) => {
          if (user.id !== this.user.id) {
            alert('You are currently not enrolled in this course.');
          } else {
            this.router.navigate(['user', 'website', this.wid, 'page']);
          }
        });
    }

    deleteWebsite() {
      if (this.user.type === 'STUDENT') {
        alert ('Student cannot delete courses!');
      } else if (this.user.type === 'TA') {
        alert ('TA cannot delete courses!');
      } else {
        this.websiteService.deleteWebsite(this.userId, this.wid)
          .subscribe((courses: any) => {
            alert('You have deleted "' + this.course.code + ' ' + this.course.name + '"');
            this.router.navigate(['profile']);
          });
      }
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

    this.websiteService.findWebsitesByUser(this.userId)
      .subscribe((courses) => {
        this.courses = courses;
        console.log(courses);
      });


    this.websiteService.findWebsiteById(this.userId, this.wid)
      .subscribe((course) => {
        this.course = course;
      });
  }
}
