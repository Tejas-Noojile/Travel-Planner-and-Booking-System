import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TripListComponent } from './components/trip-list/trip-list.component';
import { TripFormComponent } from './components/trip-form/trip-form.component';
import { TripDetailsComponent } from './components/trip-details/trip-details.component';

const routes: Routes = [
  { path: '', component: TripListComponent },
  { path: 'new', component: TripFormComponent },
  { path: 'edit/:id', component: TripFormComponent },
  { path: 'view/:id', component: TripDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TripRoutingModule {}
