// src/app/app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItineraryComponent } from './components/itinerary/itinerary.component';

const routes: Routes = [
  { path: '', redirectTo: '/itinerary', pathMatch: 'full' },
  { path: 'itinerary', component: ItineraryComponent },
  { path: 'itinerary/:tripId', component: ItineraryComponent },
  { path: '**', redirectTo: '/itinerary' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }