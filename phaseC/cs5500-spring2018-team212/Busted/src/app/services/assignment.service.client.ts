import {Injectable} from '@angular/core';
import { Http, Response} from '@angular/http';
import 'rxjs/Rx';
import {environment} from '../../environments/environment';

// make class usable for all components
@Injectable()
export class AssignmentService {

  baseUrl = environment.baseUrl;
  // inject http service
  constructor(private http: Http) {}

  newId() {
    return (Number( Math.floor((Math.random()) * 10000))).toString();
  }


  
  
}
