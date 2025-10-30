export interface PaymentInitiationRequest {
  tripId: string;
  userId: string;
  amount: number;
  created_at?: Date;
  paymentStatus?: string;
}

export interface PaymentResponse {
  paymentId: number;
  tripId: string;
  userId: string;
  amount: number;
}

export interface CardDetails {
  cardNumber: string;
  cardHolderName: string;
  expiryMonth: string;
  expiryYear: string;
  cvv: string;
}

export interface UpiDetails {
  upiId: string;
}

export interface NetBankingDetails {
  bankName: string;
  accountNumber: string;
  ifscCode: string;
}

export interface Payment {
  paymentId: number;
  tripId: string;
  userId: string;
  amount: number;
  paymentStatus: 'PENDING' | 'SUCCESS' | 'FAILED' | 'REFUNDED';
  paymentTimestamp: string; // Comes as an ISO string
}

export interface InvoiceResponse {
  invoiceId: number;
  invoiceNumber: string;
}