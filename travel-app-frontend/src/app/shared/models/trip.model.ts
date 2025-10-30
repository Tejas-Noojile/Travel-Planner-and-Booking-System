export interface Trip {
  tripId?: string;
  title: string;
  destination: string;
  startDate: string;
  endDate: string;
  numPeople: number;
  status?: 'DRAFT' | 'BOOKED' | 'CANCELLED';
}
