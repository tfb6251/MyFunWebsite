import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { ComputerInfoComponent } from './computer-info/computer-info.component';
import { UserInfoComponent } from './user-info/user-info.component';
import { ComputerBasketComponent } from './computer-basket/computer-basket.component';
import { AuthGuard } from './auth.guard';
import { HomeComponent } from './home/home.component';

const routes: Routes = [ // routes
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard], },
  { path: 'info/:id', component: ComputerInfoComponent, canActivate: [AuthGuard] },
  { path: 'info/new', component: ComputerInfoComponent, canActivate: [AuthGuard], data: { adminOnly: true } },
  { path: 'user/new', component: UserInfoComponent },
  { path: 'user/:id', component: UserInfoComponent, canActivate: [AuthGuard] },
  { path: 'user/:id/basket', component: ComputerBasketComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
