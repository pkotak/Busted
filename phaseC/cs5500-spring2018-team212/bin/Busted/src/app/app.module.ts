import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/user/login/login.component';
import { RegisterComponent } from './components/user/register/register.component';
import { FormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';

// Social module
import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider } from 'angular4-social-login';

const config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider('596677888081-jm3cm0csfhpht9jjm0iv4qiocojj7c3g.apps.googleusercontent.com')
  },
]);

export function provideConfig() {
  return config;
}


// import routing in appmodule.
import {routing} from './app.routing';
import { ProfileComponent } from './components/user/profile/profile.component';
import { WebsiteNewComponent } from './components/website/website-new/website-new.component';
import { WebsiteEditComponent } from './components/website/website-edit/website-edit.component';
import { WebsiteListComponent } from './components/website/website-list/website-list.component';
import { PageNewComponent } from './components/page/page-new/page-new.component';
import { PageListComponent } from './components/page/page-list/page-list.component';
import { PageEditComponent } from './components/page/page-edit/page-edit.component';
import { WidgetChooserComponent } from './components/widget/widget-chooser/widget-chooser.component';
import { WidgetListComponent } from './components/widget/widget-list/widget-list.component';
import { WidgetEditComponent } from './components/widget/widget-edit/widget-edit.component';
import { WidgetHeaderComponent } from './components/widget/widget-edit/widget-header/widget-header.component';
import { WidgetImageComponent } from './components/widget/widget-edit/widget-image/widget-image.component';
import { WidgetYoutubeComponent } from './components/widget/widget-edit/widget-youtube/widget-youtube.component';
import { ReportListComponent } from './components/report/report-list.component';
import { ReportDetailComponent } from './components/report/report-detail/report-detail.component';
import { WebsiteService} from './services/website.service.client';
import { UserService } from './services/user.service.client';
import { PageService } from './services/page.service.client';
import { WidgetService } from './services/widget.service.client';
import { AssignmentService } from './services/assignment.service.client';
import { ReportService } from './services/report.service.client';
import { HomeComponent} from './components/home/home.component';
import { HttpModule} from '@angular/http';
import { FlickrImageSearchComponent } from './components/widget/widget-edit/widget-image/flickr-image-search/flickr-image-search.component';
import { FlickrService} from './services/flickr.service.clients';
import { WidgetHtmlComponent } from './components/widget/widget-edit/widget-html/widget-html.component';
import { WidgetTextComponent } from './components/widget/widget-edit/widget-text/widget-text.component';
import { QuillEditorModule } from 'ngx-quill-editor';
import { SharedService} from './services/shared.service.client';
import { AuthenticationService} from './services/authentication.service.client';
import { SortableDirective} from './components/widget/widget-list/sortable.directive';
import { OrderByPipe } from './components/widget/widget-list/order-by-pipe.pipe';
import { AdminComponent } from './components/user/admin/admin.component';
import { AssignmentsComponent } from './components/assignments/assignments.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    WebsiteNewComponent,
    WebsiteEditComponent,
    WebsiteListComponent,
    PageNewComponent,
    PageListComponent,
    PageEditComponent,
    WidgetChooserComponent,
    WidgetListComponent,
    WidgetEditComponent,
    WidgetHeaderComponent,
    WidgetImageComponent,
    WidgetYoutubeComponent,
    ReportListComponent,
    ReportDetailComponent,
    HomeComponent,
    FlickrImageSearchComponent,
    WidgetHtmlComponent,
    WidgetTextComponent,
    SortableDirective,
    OrderByPipe,
    AdminComponent,
    AssignmentsComponent
  ],
  imports: [
    BrowserModule, routing, FormsModule, HttpModule, QuillEditorModule, SocialLoginModule
  ],

  // inject it into any constructors
  providers: [
    UserService,
    WebsiteService,
    PageService,
    WidgetService,
    FlickrService,
    SharedService,
    ReportService,
    AuthenticationService,
    CookieService,
    {
      provide: AuthServiceConfig,
      useFactory: provideConfig
    },
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
