import {Component, OnInit, ViewChild} from '@angular/core';
import {Route, Router} from '@angular/router';
import {UserService} from '../../../services/user.service.client';
import {User} from '../../../models/user.model.client';
import {NgForm} from '@angular/forms';
import {Response} from '@angular/http';
import {SharedService} from '../../../services/shared.service.client';
import {environment} from '../../../../environments/environment';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {CookieService} from 'ngx-cookie-service';
import {AuthService} from 'angular4-social-login';
import {SocialUser} from 'angular4-social-login';
import {GoogleLoginProvider} from 'angular4-social-login';

// below is an angular component
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  hello: String = 'Hello from the component';
  cookieValue = 'UNKNOWN';
  // create component attached to username
  @ViewChild('f') loginForm: NgForm;
  username: String;
  password: String;
  title: String;
  disabledFlag: boolean;
  errorFlag: boolean;
  errorMsg = 'Invalid user name or password！';
  baseUrl = environment.baseUrl;
  youtubeUrl: SafeResourceUrl;
  url: String;
  user: SocialUser;
  loggedIn: boolean;


  // privately declared variable
  constructor(private userService: UserService,
              private router: Router,
              private sharedService: SharedService,
              public sanitizer: DomSanitizer,
              private cookieService: CookieService,
              private authService: AuthService) {
  }

  signInWithGoogle(): void {
    const currentScope = this;
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)
      .then(function() {
        currentScope.authService.authState.subscribe((user) => {
          currentScope.user = user;
          console.log(user);
          currentScope.userService.findUserByUsername(user.email)
            .subscribe(
              (dbUser: any) => {
                if (dbUser.username === null) {
                  console.log('Found in Database');
                  currentScope._login(dbUser.email, dbUser.password);
                } else {
                  console.log('Not Found in Database');
                  console.log(user);

                  const [firstName, lastName] = user.name.split(' ');
                  currentScope.userService
                    .register(user.email, user.id, 'STUDENT', firstName, lastName)
                    .subscribe((auser: any) => {
                      currentScope._login(user.email, user.id);
                    });
                }
              });
        });
      });
  }

  signOut(): void {
    this.authService.signOut();
  }

  _login(username, password) {
    this.userService
      .login(username, password)
      .subscribe(
        (data: any) => {
      // store current logged in user in SharedService
      //    console.log(data);
      //    if (data.isApproved !== true) {
      //      alert('Your account needs to be approved by Admin');
      //      this.router.navigate(['/login']);
      //    } else {
          // store current logged in user in SharedService
          this.sharedService.user = data;
          this.cookieService.set('user', String(data.id));
          this.router.navigate(['/profile']);
          console.log(this.cookieService.get('user'));
        },
        (error: any) => {
          console.log(error);
          alert('Invalid username or password');
        }
      );
  }

  // api function for login
  login() {
    this.username = this.loginForm.value.username;
    this.password = this.loginForm.value.password;

    console.log('data', this.username);
    // calling client side userservice to send login information
    this._login(this.username, this.password);

  }

  ngOnInit() {
    this.title = 'This is Login Page';
    this.disabledFlag = true;
  }

}
