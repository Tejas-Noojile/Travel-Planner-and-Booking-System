import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import {
  LoginRequest,
  LoginResponse,
  SignupRequest,
} from '../../../shared/models/auth.model';
import { jwtDecode } from 'jwt-decode'; // <-- Import jwt-decode
import { Router } from '@angular/router';

interface DecodedToken {
  role: string;
  userId: string; // Adjust based on your token structure
  sub: string; // Typically the user ID or username
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  public userRole$ = new BehaviorSubject<string | null>(null);
  constructor(private http: HttpClient, private router: Router) {
    this.loadUserRoleFromToken();
  }

  private loadUserRoleFromToken() {
    const token = this.getToken();
    if (token) {
      const decodedToken: DecodedToken = jwtDecode(token);
      this.userRole$.next(decodedToken.role);
    }
  }
  signup(request: SignupRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/signup`, request);
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap((response) => {
        // Store the token upon successful login
        console.log(response);
        localStorage.setItem('authToken', response.token);
        this.loadUserRoleFromToken();
      })
    );
  }

  logout(): void {
    // Remove the token from storage
    localStorage.removeItem('authToken');
    this.userRole$.next(null);
    this.router.navigate(['/auth/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  getRole(): string | null {
    return this.userRole$.getValue();
  }

  getUserId(): string | null {
    const token = this.getToken();
    if (!token) return null;
    const decodedToken: DecodedToken = jwtDecode(token);
    return decodedToken.userId; // Adjust based on your token structure
  }
  isAdmin(): boolean {
    const role = this.getRole();
    return role === 'ADMIN';
  }
}
