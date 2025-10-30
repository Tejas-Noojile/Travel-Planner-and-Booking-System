import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms'; // Import FormsModule
import { BookingRoutingModule } from './booking-routing.module';
import { BookingHomeComponent } from './components/booking-home/booking-home.component';
import { HotelSearchComponent } from './components/hotel-search/hotel-search.component';
import { SharedModule } from '../../shared/shared.module';
import { CabSearchComponent } from './components/cab-search/cab-search.component';

@NgModule({
  declarations: [BookingHomeComponent, HotelSearchComponent, CabSearchComponent],
  imports: [
    CommonModule,
    BookingRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
  ],
})
export class BookingModule {}
