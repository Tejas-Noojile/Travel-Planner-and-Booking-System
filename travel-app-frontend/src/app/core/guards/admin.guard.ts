import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../../features/auth/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const role = this.authService.getRole();
    if (this.authService.isLoggedIn() && role === 'ADMIN') {
      return true;
    } else {
      this.router.navigate(['/user/profile']);
      return false;
    }
  }
}
