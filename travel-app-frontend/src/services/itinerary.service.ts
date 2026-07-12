// src/app/services/itinerary.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../src/environments/environment';
import { 
  Itinerary, 
  DayPlan, 
  Event, 
  CreateEventRequest, 
  UpdateEventRequest,
  CreateDayPlanRequest,
  UpdateDayPlanRequest 
} from '../models/itinerary.model';

@Injectable({
  providedIn: 'root'
})
export class ItineraryService {
  private baseUrl = environment.apiUrl; // Update with your backend URL
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  // Itinerary CRUD Operations
  getItineraryByTripId(tripId: number): Observable<Itinerary> {
    return this.http.get<Itinerary>(`${this.baseUrl}/itineraries/trip/${tripId}`);
  }

  createItinerary(itinerary: Partial<Itinerary>): Observable<Itinerary> {
    return this.http.post<Itinerary>(`${this.baseUrl}/itineraries`, itinerary, this.httpOptions);
  }

  updateItinerary(id: number, itinerary: Partial<Itinerary>): Observable<Itinerary> {
    return this.http.put<Itinerary>(`${this.baseUrl}/itineraries/${id}`, itinerary, this.httpOptions);
  }

  deleteItinerary(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/itineraries/${id}`);
  }

  // Day Plan CRUD Operations
  getDayPlansByItineraryId(itineraryId: number): Observable<DayPlan[]> {
    return this.http.get<DayPlan[]>(`${this.baseUrl}/day-plans/itinerary/${itineraryId}`);
  }

  createDayPlan(dayPlan: CreateDayPlanRequest): Observable<DayPlan> {
    return this.http.post<DayPlan>(`${this.baseUrl}/day-plans`, dayPlan, this.httpOptions);
  }

  updateDayPlan(id: number, dayPlan: UpdateDayPlanRequest): Observable<DayPlan> {
    return this.http.put<DayPlan>(`${this.baseUrl}/day-plans/${id}`, dayPlan, this.httpOptions);
  }

  deleteDayPlan(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/day-plans/${id}`);
  }

  // Event CRUD Operations
  getEventsByDayPlanId(dayPlanId: number): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.baseUrl}/events/day-plan/${dayPlanId}`);
  }

  createEvent(dayPlanId: number, event: CreateEventRequest): Observable<Event> {
    return this.http.post<Event>(`${this.baseUrl}/events/day-plan/${dayPlanId}`, event, this.httpOptions);
  }

  updateEvent(id: number, event: UpdateEventRequest): Observable<Event> {
    return this.http.put<Event>(`${this.baseUrl}/events/${id}`, event, this.httpOptions);
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/events/${id}`);
  }
}