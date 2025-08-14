export interface User {
  userId: string;
  name: string;
  email: string;
  phone: string;
  role: 'TRAVELER' | 'ADMIN';
}
