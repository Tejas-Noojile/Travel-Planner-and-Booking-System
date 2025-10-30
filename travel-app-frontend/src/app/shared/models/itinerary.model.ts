export interface ItineraryItem {
  itemId?: string;
  tripId: string;
  dayNumber: number;
  time: string; // HH:mm format
  activityType: string;
  description: string;
  location: string;
}

export enum ActivityType {
  SIGHTSEEING = 'SIGHTSEEING',
  TRAVEL = 'TRAVEL',
  FOOD = 'FOOD',
  ACCOMMODATION = 'ACCOMMODATION',
  SHOPPING = 'SHOPPING',
  ENTERTAINMENT = 'ENTERTAINMENT',
  OTHER = 'OTHER',
}
