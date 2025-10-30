import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ItineraryListComponent } from './components/itinerary-list/itinerary-list.component';
import { ItineraryFormComponent } from './components/itinerary-form/itinerary-form.component';

@NgModule({
  declarations: [ItineraryListComponent, ItineraryFormComponent],
  imports: [CommonModule, ReactiveFormsModule],
  exports: [
    // Export the components so TripDetails can use them
    ItineraryListComponent,
    ItineraryFormComponent,
  ],
})
export class ItineraryModule {}
