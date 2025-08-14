import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { SignupRequest } from '../../../../shared/models/auth.model';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastService } from '../../../../shared/services/toast.service';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  signupForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['TRAVELER', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.signupForm.invalid) {
      return;
    }

    const request: SignupRequest = this.signupForm.value;

    this.authService.signup(request).subscribe({
      next: () => {
        this.toastService.show('Signup successful! Please log in.', 'success');
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {
        console.error('Signup failed', err);
         this.toastService.show('Signup failed. Please Try again!', 'error');
      },
    });
  }
}
