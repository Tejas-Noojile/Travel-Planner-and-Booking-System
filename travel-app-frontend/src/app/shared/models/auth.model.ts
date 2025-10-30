export interface LoginRequest {
  emailOrPhone: string;
  password?: string;
}

export interface LoginResponse {
  token: string;
  // You might want to add user details here in the future
}

export interface SignupRequest {
  name: string;
  email: string;
  phone: string;
  password: string;
  role: 'TRAVELER' | 'ADMIN';
}
