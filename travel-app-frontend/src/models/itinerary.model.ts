// src/app/models/itinerary.model.ts

export interface Event {
  id?: number;
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  location: string;
  category: EventCategory;
  cost?: number;
  notes?: string;
  dayPlanId: number;
}

export interface DayPlan {
  id?: number;
  date: string;
  title: string;
  events: Event[];
  itineraryId: number;
  totalBudget?: number;
}

export interface Itinerary {
  id?: number;
  tripId: number;
  title: string;
  startDate: string;
  endDate: string;
  destination: string;
  totalDays: number;
  dayPlans: DayPlan[];
  createdAt?: Date;
  updatedAt?: Date;
}

export enum EventCategory {
  SIGHTSEEING = 'SIGHTSEEING',
  FOOD = 'FOOD',
  TRAVEL = 'TRAVEL',
  ACCOMMODATION = 'ACCOMMODATION',
  SHOPPING = 'SHOPPING',
  ENTERTAINMENT = 'ENTERTAINMENT',
  OTHER = 'OTHER'
}

export interface CreateEventRequest {
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  location: string;
  category: EventCategory;
  cost?: number;
  notes?: string;
}

export interface UpdateEventRequest extends CreateEventRequest {
  id: number;
}

export interface CreateDayPlanRequest {
  date: string;
  title: string;
  itineraryId: number;
}

export interface UpdateDayPlanRequest extends CreateDayPlanRequest {
  id: number;
}