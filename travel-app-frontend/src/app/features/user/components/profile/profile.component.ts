import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { UserService } from '../../services/user.service';
import { PreferenceService } from '../../services/preference.service';
import { User } from '../../../../shared/models/user.model';
import { UserPreference } from '../../../../shared/models/preference.model';
import { ToastService } from '../../../../shared/services/toast.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  preferences: UserPreference | null = null;

  profileForm!: FormGroup;
  preferencesForm!: FormGroup;

  editingSection: 'personal' | 'preferences' | null = null;

  allInterests = [
    'Adventure',
    'Relaxation',
    'Culture',
    'Nightlife',
    'Foodie',
    'History',
    'Nature',
  ];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private preferenceService: PreferenceService,
    private toastService: ToastService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['', Validators.required],
    });

    this.preferencesForm = this.fb.group({
      travelInterests: [[]],
      budgetRange: ['Mid-Range'],
    });

    this.loadInitialData();
  }

  loadInitialData(): void {
    forkJoin({
      user: this.userService.getProfile(),
      preferences: this.preferenceService.getPreferences(),
    }).subscribe(({ user, preferences }) => {
      this.user = user;
      this.profileForm.patchValue(user);

      this.preferences = preferences;
      const interestsArray = preferences.travelInterests
        ? preferences.travelInterests.split(',')
        : [];
      this.preferencesForm.patchValue({
        ...preferences,
        travelInterests: interestsArray,
      });
    });
  }

  edit(section: 'personal' | 'preferences'): void {
    this.editingSection = section;
  }

  cancelEdit(): void {
    this.editingSection = null;
    if (this.user) this.profileForm.patchValue(this.user);
    if (this.preferences) {
      const interestsArray = this.preferences.travelInterests
        ? this.preferences.travelInterests.split(',')
        : [];
      this.preferencesForm.patchValue({
        ...this.preferences,
        travelInterests: interestsArray,
      });
    }
  }

  savePersonalDetails(): void {
    if (this.profileForm.invalid || !this.user) return;

    const updatedUser = { ...this.user, ...this.profileForm.getRawValue() };
    this.userService.updateProfile(updatedUser).subscribe(() => {
      this.user = updatedUser;
      this.toastService.show('Profile updated successfully!', 'success');
      this.editingSection = null;
    });
  }

  savePreferences(): void {
    if (this.preferencesForm.invalid) return;

    const interestsString =
      this.preferencesForm.value.travelInterests.join(',');
    const updatedPreferences = {
      ...this.preferencesForm.value,
      travelInterests: interestsString,
    };

    this.preferenceService
      .updatePreferences(updatedPreferences)
      .subscribe(() => {
        this.preferences = { ...this.preferences, ...updatedPreferences };
        this.toastService.show('Preferences updated successfully!', 'success');
        this.editingSection = null;
      });
  }

  // --- Preference Chip Logic ---
  toggleInterest(interest: string): void {
    const interestsControl = this.preferencesForm.get('travelInterests');
    if (interestsControl) {
      const currentInterests = interestsControl.value as string[];
      const index = currentInterests.indexOf(interest);

      if (index > -1) {
        currentInterests.splice(index, 1);
      } else {
        currentInterests.push(interest);
      }
      interestsControl.setValue(currentInterests);
    }
  }

  isInterestSelected(interest: string): boolean {
    const interestsControl = this.preferencesForm.get('travelInterests');
    return interestsControl
      ? (interestsControl.value as string[]).includes(interest)
      : false;
  }

  logout(): void {
    this.authService.logout();
  }
}
