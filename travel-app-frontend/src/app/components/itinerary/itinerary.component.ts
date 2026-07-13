// src/app/components/itinerary/itinerary.component.ts

import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ItineraryService } from '../../../services/itinerary.service';
import { Itinerary, DayPlan, Event } from '../../../models/itinerary.model';
import { DayPlanComponent } from '../day-plan/day-plan.component';

@Component({
  selector: 'app-itinerary',
  standalone: true,
  imports: [CommonModule, FormsModule, DayPlanComponent],
  templateUrl: './itinerary.component.html',
  styleUrls: ['./itinerary.component.scss']
})
export class ItineraryComponent implements OnInit {
  @Input() tripId: number = 1; // This should come from route params or parent component
  
  itinerary: Itinerary | null = null;
  dayPlans: DayPlan[] = [];
  selectedDayPlan: DayPlan | null = null;
  isLoading = false;
  errorMessage = '';
  
  // View modes
  viewMode: 'calendar' | 'list' = 'list';

  constructor(private itineraryService: ItineraryService) {}

  ngOnInit(): void {
    this.loadItinerary();
  }

  loadItinerary(): void {
    this.isLoading = true;
    this.errorMessage = '';
    
    this.itineraryService.getItineraryByTripId(this.tripId).subscribe({
      next: (itinerary) => {
        this.itinerary = itinerary;
        this.dayPlans = itinerary.dayPlans || [];
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load itinerary. Please try again.';
        this.isLoading = false;
        console.error('Error loading itinerary:', error);
      }
    });
  }

  addNewDayPlan(): void {
    if (!this.itinerary) return;
    
    const newDayPlan: DayPlan = {
      date: this.getNextAvailableDate(),
      title: `Day ${this.dayPlans.length + 1}`,
      events: [],
      itineraryId: this.itinerary.id!
    };
    
    this.itineraryService.createDayPlan(newDayPlan).subscribe({
      next: (createdDayPlan) => {
        this.dayPlans.push(createdDayPlan);
      },
      error: (error) => {
        console.error('Error creating day plan:', error);
        this.errorMessage = 'Failed to create day plan';
      }
    });
  }

  selectDayPlan(dayPlan: DayPlan): void {
    this.selectedDayPlan = dayPlan;
  }

  deleteDayPlan(dayPlan: DayPlan): void {
    if (!dayPlan.id) return;
    
    if (confirm('Are you sure you want to delete this day plan?')) {
      this.itineraryService.deleteDayPlan(dayPlan.id).subscribe({
        next: () => {
          this.dayPlans = this.dayPlans.filter(dp => dp.id !== dayPlan.id);
          if (this.selectedDayPlan?.id === dayPlan.id) {
            this.selectedDayPlan = null;
          }
        },
        error: (error) => {
          console.error('Error deleting day plan:', error);
          this.errorMessage = 'Failed to delete day plan';
        }
      });
    }
  }

  toggleViewMode(): void {
    this.viewMode = this.viewMode === 'list' ? 'calendar' : 'list';
  }

  onDayPlanUpdated(updatedDayPlan: DayPlan): void {
    const index = this.dayPlans.findIndex(dp => dp.id === updatedDayPlan.id);
    if (index !== -1) {
      this.dayPlans[index] = updatedDayPlan;
    }
  }

  private getNextAvailableDate(): string {
    if (!this.itinerary || this.dayPlans.length === 0) {
      return new Date().toISOString().split('T')[0];
    }
    
    const lastDate = new Date(this.dayPlans[this.dayPlans.length - 1].date);
    lastDate.setDate(lastDate.getDate() + 1);
    return lastDate.toISOString().split('T')[0];
  }

  getTotalBudget(): number {
    return this.dayPlans.reduce((total, dayPlan) => {
      const dayTotal = dayPlan.events?.reduce((daySum, event) => daySum + (event.cost || 0), 0) || 0;
      return total + dayTotal;
    }, 0);
  }
}