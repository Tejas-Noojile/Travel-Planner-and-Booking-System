// src/app/components/day-plan/day-plan.component.ts

import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ItineraryService } from '../../../services/itinerary.service';
import { DayPlan, Event, EventCategory, CreateEventRequest } from '../../../models/itinerary.model';

@Component({
  selector: 'app-day-plan',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './day-plan.component.html',
  styleUrls: ['./day-plan.component.scss']
})
export class DayPlanComponent implements OnInit {
  @Input() dayPlan!: DayPlan;
  @Output() dayPlanUpdated = new EventEmitter<DayPlan>();
  @Output() close = new EventEmitter<void>();

  events: Event[] = [];
  isAddingEvent = false;
  editingEvent: Event | null = null;
  
  eventCategories = Object.values(EventCategory);
  
  newEvent: CreateEventRequest = {
    title: '',
    description: '',
    startTime: '09:00',
    endTime: '10:00',
    location: '',
    category: EventCategory.SIGHTSEEING,
    cost: 0,
    notes: ''
  };

  constructor(private itineraryService: ItineraryService) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    if (this.dayPlan.id) {
      this.itineraryService.getEventsByDayPlanId(this.dayPlan.id).subscribe({
        next: (events) => {
          this.events = events.sort((a, b) => a.startTime.localeCompare(b.startTime));
          this.dayPlan.events = this.events;
        },
        error: (error) => {
          console.error('Error loading events:', error);
        }
      });
    }
  }

  addEvent(): void {
    if (!this.dayPlan.id) return;
    
    this.itineraryService.createEvent(this.dayPlan.id, this.newEvent).subscribe({
      next: (createdEvent) => {
        this.events.push(createdEvent);
        this.events.sort((a, b) => a.startTime.localeCompare(b.startTime));
        this.dayPlan.events = this.events;
        this.resetNewEvent();
        this.isAddingEvent = false;
        this.dayPlanUpdated.emit(this.dayPlan);
      },
      error: (error) => {
        console.error('Error creating event:', error);
      }
    });
  }

  editEvent(event: Event): void {
    this.editingEvent = { ...event };
  }

  updateEvent(): void {
    if (!this.editingEvent || !this.editingEvent.id) return;

    const updateRequest = {
      id: this.editingEvent.id,
      title: this.editingEvent.title,
      description: this.editingEvent.description,
      startTime: this.editingEvent.startTime,
      endTime: this.editingEvent.endTime,
      location: this.editingEvent.location,
      category: this.editingEvent.category,
      cost: this.editingEvent.cost,
      notes: this.editingEvent.notes
    };
    this.itineraryService.updateEvent(this.editingEvent.id, updateRequest).subscribe({
      next: (updatedEvent) => {
        const index = this.events.findIndex(e => e.id === updatedEvent.id);
        if (index !== -1) {
          this.events[index] = updatedEvent;
          this.events.sort((a, b) => a.startTime.localeCompare(b.startTime));
          this.dayPlan.events = this.events;
        }
        this.editingEvent = null;
        this.dayPlanUpdated.emit(this.dayPlan);
      },
      error: (error) => {
        console.error('Error updating event:', error);
      }
    });
  }

  deleteEvent(event: Event): void {
    if (!event.id) return;
    
    if (confirm('Are you sure you want to delete this event?')) {
      this.itineraryService.deleteEvent(event.id).subscribe({
        next: () => {
          this.events = this.events.filter(e => e.id !== event.id);
          this.dayPlan.events = this.events;
          this.dayPlanUpdated.emit(this.dayPlan);
        },
        error: (error) => {
          console.error('Error deleting event:', error);
        }
      });
    }
  }

  cancelAddEvent(): void {
    this.isAddingEvent = false;
    this.resetNewEvent();
  }

  cancelEdit(): void {
    this.editingEvent = null;
  }

  private resetNewEvent(): void {
    this.newEvent = {
      title: '',
      description: '',
      startTime: '09:00',
      endTime: '10:00',
      location: '',
      category: EventCategory.SIGHTSEEING,
      cost: 0,
      notes: ''
    };
  }

  getTotalDayCost(): number {
    return this.events.reduce((total, event) => total + (event.cost || 0), 0);
  }

  getCategoryIcon(category: EventCategory): string {
    const icons = {
      [EventCategory.SIGHTSEEING]: '🏛️',
      [EventCategory.FOOD]: '🍽️',
      [EventCategory.TRAVEL]: '🚗',
      [EventCategory.ACCOMMODATION]: '🏨',
      [EventCategory.SHOPPING]: '🛍️',
      [EventCategory.ENTERTAINMENT]: '🎭',
      [EventCategory.OTHER]: '📌'
    };
    return icons[category] || '📌';
  }

  onClose(): void {
    this.close.emit();
  }
}