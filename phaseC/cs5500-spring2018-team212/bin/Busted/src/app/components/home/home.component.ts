import { Component, OnInit } from '@angular/core';
import {NgModel} from '@angular/forms';
import {Router} from '@angular/router';
import {NgForm} from '@angular/forms';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  baseUrl = environment.baseUrl;

  constructor(
    private router: Router
  ) { }

  goLogin() {
    this.router.navigate(['login']);
  }

  ngOnInit() {
  }

}
