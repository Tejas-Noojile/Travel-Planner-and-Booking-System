import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaymentSummaryComponent } from './components/payment-summary/payment-summary.component';
import { PaymentMethodComponent } from './components/payment-method/payment-method.component';
import { CardFormComponent } from './components/card-form/card-form.component';
import { UpiFormComponent } from './components/upi-form/upi-form.component';
import { PaymentSuccessComponent } from './components/payment-success/payment-success.component';
import { NetbankingFormComponent } from './components/netbanking-form/netbanking-form.component';
import { OrderListComponent } from './components/order-list/order-list.component';

const routes: Routes = [
  { path: 'summary', component: PaymentSummaryComponent },
  { path: 'methods', component: PaymentMethodComponent },
  { path: 'card', component: CardFormComponent },
  { path: 'upi', component: UpiFormComponent }, // Assuming UPI form is similar to card form
  { path: 'netbanking', component: NetbankingFormComponent }, // Assuming NetBanking form is similar to card form
  { path: 'success', component: PaymentSuccessComponent },
  { path: 'orders', component: OrderListComponent },
  // Add routes for success page later
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PaymentRoutingModule {}
