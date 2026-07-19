import { Routes } from '@angular/router';
import { ItineraryComponent } from './components/itinerary/itinerary.component';
import { AuthComponent } from './features/auth/auth.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { SignupComponent } from './features/auth/components/signup/signup.component';
import { HomeComponent } from './features/home/home.component';
import { LandingComponent } from './features/home/components/landing/landing.component';
import { UserComponent } from './features/user/user.component';
import { ProfileComponent } from './features/user/components/profile/profile.component';
import { AdminComponent } from './features/admin/admin.component';
import { DashboardComponent } from './features/admin/components/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'itinerary', component: ItineraryComponent },
  { path: 'itinerary/:tripId', component: ItineraryComponent },
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'signup', component: SignupComponent },
    ],
  },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: '', component: LandingComponent }
    ],
  },
  {
    path: 'user',
    component: UserComponent,
    children: [
      { path: '', redirectTo: 'profile', pathMatch: 'full' },
      { path: 'profile', component: ProfileComponent }
    ],
  },
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
    ],
  },
  { path: '**', redirectTo: '/home' }
];
