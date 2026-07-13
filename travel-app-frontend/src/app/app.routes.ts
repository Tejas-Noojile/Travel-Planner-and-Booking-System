import { Routes } from '@angular/router';
import { ItineraryComponent } from './components/itinerary/itinerary.component';

export const routes: Routes = [
  { path: '', redirectTo: '/itinerary', pathMatch: 'full' },
  { path: 'itinerary', component: ItineraryComponent },
  { path: 'itinerary/:tripId', component: ItineraryComponent },
  { path: '**', redirectTo: '/itinerary' }
];
