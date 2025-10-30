import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TripRoutingModule } from './trip-routing.module';
import { TripListComponent } from './components/trip-list/trip-list.component';
import { TripFormComponent } from './components/trip-form/trip-form.component';
import { SharedModule } from '../../shared/shared.module';
import { TripDetailsComponent } from './components/trip-details/trip-details.component';
import { ItineraryModule } from '../itinerary/itinerary.module';

@NgModule({
  declarations: [
    TripListComponent,
    TripFormComponent,
    TripDetailsComponent
  ],
  imports: [
    CommonModule,
    TripRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    ItineraryModule
  ]
})
export class TripModule { }
