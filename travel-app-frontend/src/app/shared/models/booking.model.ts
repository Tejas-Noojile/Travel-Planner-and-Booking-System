export interface Hotel {
  hotelId: number;
  name: string;
  city: string;
  location: string;
  rating: number;
  pricePerNight: number;
  imageUrl: string;
}

export interface Booking {
  bookingId?: string;
  userId: string;
  tripId: string;
  bookingType: 'HOTEL' | 'FLIGHT' | 'CAB';
  details: string;
  status: 'CONFIRMED' | 'CANCELLED' | 'PAID';
  amount: number;
  startDatetime?: string;
  endDatetime?: string;
}
export interface Cab {
  type: 'Economy' | 'Sedan' | 'SUV';
  pricePerDay: number;
  imageUrl: string;
}
