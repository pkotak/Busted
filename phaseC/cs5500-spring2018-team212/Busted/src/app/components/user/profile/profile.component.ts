import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user.service.client';
import { User } from '../../../models/user.model.client';
import { Router } from '@angular/router';
import { WebsiteService } from '../../../services/website.service.client';
import { NgForm } from '@angular/forms';
import {environment} from '../../../../environments/environment';
import { SharedService } from '../../../services/shared.service.client';
import {CanActivate} from '@angular/router';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {Website} from '../../../models/website.model.client';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  @ViewChild('f') updateForm: NgForm;
  cookieValue = 'unknown';
  baseUrl = environment.baseUrl;
  type: String;
  user: any;
  phone: String;
  userId: String;
  username: String;
  email: String;
  firstName: String;
  lastName: String;
  class: String;
  password: String;
  youtubeUrl: SafeResourceUrl;
  url: String;
  courses: [{}];
  // inject route info in constructor
  constructor(
          private userService: UserService,
          private activatedRoute: ActivatedRoute,
          private sharedService: SharedService,
          private websiteService: WebsiteService,
          private router: Router,
          public sanitizer: DomSanitizer,
          private cookieService: CookieService) { }

  updateVideoUrl() {
    // const aurl = 'https://www.youtube.com/embed/qdA32j7_U6U';
    return this.youtubeUrl = this.sanitizer
      .bypassSecurityTrustResourceUrl('https://www.youtube.com/embed/Ga3maNZ0x0w?autoplay=1&cc_load_policy=1&controls=0');
  }

  // updateImageUrl(string) {
  //   let newurl = '';
  //   if (string.substring(1, 4) === 'ass') {
  //     newurl = this.baseUrl + string;
  //   } else {
  //     newurl = this.baseUrl + '/assets/images/placeholder-user-1-400x400.png';
  //   }
  //   return newurl;
  // }

  // issue a logout request to the server. On successful logout, set the currentUser to null.
  // Use the code below as an example.
  logout() {
    this.userService.logout()
      .subscribe((status) => {
        this.router.navigate(['']);
      });
  }

  update() {
    // console.log(user);
    // this.username = this.updateForm.value.username;
    this.firstName = this.updateForm.value.firstName;
    this.lastName = this.updateForm.value.lastName;
    this.email = this.updateForm.value.email;
    this.password = this.updateForm.value.password;
    this.phone = this.updateForm.value.phone;
    this.type = this.updateForm.value.type;



    const updatedUser = {
      password: this.user.password,
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      phone: this.phone,
      type: this.type
    };

    console.log(updatedUser);

    this.userService.updateUser(this.userId, updatedUser).
    subscribe((newuser) => {
      // console.log(status);
      this.user = newuser;
      console.log(this.user);
      // window.location.reload(false); // reload page
    });
  }

  deleteUser() {
    this.userService.deleteUser(this.userId).subscribe((status) => {
      console.log(status);
    });
  }

  gotoAdmin() {
    if (this.user.type !== 'ADMIN') {
      alert('Access forbidden');
    } else {
      this.router.navigate(['user', 'admin']);
    }
  }

  addclass() {
    if (this.user.role === 'STUDENT') {
      alert('Student cannot create classes');
    } else if (this.user.role === 'TA') {
      alert('TA cannot create classes');
    } else {
      this.router.navigate(['user', 'website', 'new']);
    }
  }

  // gotoAllclass() {
  //   if (this.user.role !== 'ADMIN') {
  //     alert('Access forbidden');
  //   } else {
  //     this.router.navigate(['user', 'website']);
  //   }
  // }

  // notify the changes of the route
  ngOnInit() {

    // this.cookieService.set( 'user', String(this.userId) );

    this.userId = this.cookieService.get('user');

    console.log(this.userId);

    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      console.log(this.user);
    });


    // console.log(this.sharedService.user);
    // this.getUser();
    // this.user = this.sharedService.user;

    // this.websiteService.findWebsitesByUserAndRole(this.userId, this.user.role)
    //   .subscribe((classes) => {
    //     this.websites = classes;
    //     console.log(classes);
    //   });

    this.websiteService.findWebsitesByUser(this.userId)
      .subscribe((courses) => {
        this.courses = courses;
        console.log(courses);
      });


    // invoke a function that can pass the value of the parameters
    // this.activatedRoute.params.subscribe((params) => {
    //   this.userId = params['userId'];
    // });

    // this.user = this.userService.findUserById(this.userId);
    // this.userService.findUserById(this.userId).subscribe((user: User) => {
    //     this.user = user;
    //     console.log(this.user);
    // });
    // alert('userId: ' + this.userId);
  }


}
