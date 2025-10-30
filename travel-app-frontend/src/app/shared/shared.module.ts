import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ToastComponent } from './components/toast/toast.component';
import { AdminRoutingModule } from "../features/admin/admin-routing.module";
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
    ToastComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    RouterModule
],
  exports: [
    NavbarComponent,
    FooterComponent,
    ToastComponent
  ]
})
export class SharedModule { }
