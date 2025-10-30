import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { LandingComponent } from './components/landing/landing.component';
import { SharedModule } from '../../shared/shared.module'; // Import SharedModule

@NgModule({
  declarations: [LandingComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule, // Add SharedModule to use the navbar
  ],
})
export class HomeModule {}
