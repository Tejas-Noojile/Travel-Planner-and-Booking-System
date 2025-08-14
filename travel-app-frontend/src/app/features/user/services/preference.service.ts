import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UserPreference } from '../../../shared/models/preference.model';

@Injectable({
  providedIn: 'root',
})
export class PreferenceService {
  private apiUrl = `${environment.apiUrl}/preferences`;

  constructor(private http: HttpClient) {}

  getPreferences(): Observable<UserPreference> {
    return this.http.get<UserPreference>(this.apiUrl);
  }

  updatePreferences(preferences: UserPreference): Observable<void> {
    return this.http.put<void>(this.apiUrl, preferences);

  }
}
