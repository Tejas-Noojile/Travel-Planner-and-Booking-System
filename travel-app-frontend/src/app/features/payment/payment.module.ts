import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PaymentRoutingModule } from './payment-routing.module';
import { PaymentComponent } from './payment.component';
import { PaymentSummaryComponent } from './components/payment-summary/payment-summary.component';
import { PaymentMethodComponent } from './components/payment-method/payment-method.component';
import { CardFormComponent } from './components/card-form/card-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { UpiFormComponent } from './components/upi-form/upi-form.component';
import { NetbankingFormComponent } from './components/netbanking-form/netbanking-form.component';
import { PaymentSuccessComponent } from './components/payment-success/payment-success.component';
import { OrderListComponent } from './components/order-list/order-list.component';


@NgModule({
  declarations: [
    PaymentComponent,
    PaymentSummaryComponent,
    PaymentMethodComponent,
    CardFormComponent,
    UpiFormComponent,
    NetbankingFormComponent,
    PaymentSuccessComponent,
    OrderListComponent
  ],
  imports: [
    CommonModule,
    PaymentRoutingModule,
    ReactiveFormsModule,
    SharedModule
  ]
})
export class PaymentModule { }
