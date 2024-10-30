import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MessagesComponent } from './messages/messages.component';
import { ComputerInfoComponent } from './computer-info/computer-info.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { UserInfoComponent } from './user-info/user-info.component';
import { ComputerBasketComponent } from './computer-basket/computer-basket.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    MessagesComponent,
    LoginComponent,
    ComputerInfoComponent,
    UserInfoComponent,
    ComputerBasketComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
