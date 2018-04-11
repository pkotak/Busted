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

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  @ViewChild('f') updateForm: NgForm;
  baseUrl = environment.baseUrl;
  user: any;
  userId: String;
  username: String;
  email: String;
  firstName: String;
  lastName: String;
  class: String;
  isApproved: Number;
  phone: String;
  users: [{}];
  // filter: User ;

  constructor(private userService: UserService,
              private activatedRoute: ActivatedRoute,
              private sharedService: SharedService,
              private router: Router,
              public sanitizer: DomSanitizer) { }

  getUser() {
    this.user = this.sharedService.user;
    this.username = this.user['username'];
    this.firstName = this.user['firstName'];
    this.lastName = this.user['lastName'];
    this.email = this.user['email'];
    this.userId = this.user['id'];
    this.isApproved = this.user['isApproved'];
    this.phone = this.user['phone'];
  }

  approve(userId) {
    this.userService.approveUser(userId).subscribe((status) => {
      console.log(status);
      alert('User ' + userId + ' approved.');
      window.location.reload(false); // reload page
    });
  }

  updateUserByAdmin(id, email, firstName, lastName, type, phone, password, isApproved) {
    const newUser = {
      email: email,
      firstName: firstName,
      lastName: lastName,
      type: type,
      phone: phone,
      password: password,
      isApproved: isApproved
    };
    console.log(newUser);

    this.userService.updateUser(id, newUser).
    subscribe((newuser) => {
      // console.log(status);
      this.user = newuser;
      console.log(this.user);
      window.location.reload(false); // reload page
    });
  }


  deleteUser(userId) {
    this.userService.deleteUser(userId).subscribe((status) => {
      console.log(status);
      alert('User deleted!');
      window.location.reload(false); // reload page
    });
  }

  ngOnInit() {
    this.user = this.sharedService.user;

    this.getUser();

    // invoke a function that can pass the value of the parameters
    // this.activatedRoute.params.subscribe((params) => {
    //   this.userId = params['userId'];
    // });

    // this.user = this.userService.findUserById(this.userId);
    this.userService.findUserById(this.userId).subscribe((user: User) => {
      this.user = user;
      // console.log(this.user);
    });

    this.userService.findAllUsers().subscribe((users) => {
      this.users = users;
    });
  }

}
