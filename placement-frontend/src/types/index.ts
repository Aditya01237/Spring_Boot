export interface Student {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  rollNumber?: string;
  cgpa?: number;
  graduationYear?: number;
  role?: string;
  program?: string;
  specialisationCode?: string;
  specialisationName?: string;
}

export interface Placement {
  id: number;
  organisation: string;
  profile: string;
  description: string;
  intake: number;
  minimumGrade: number;
  filters?: PlacementFilter[];
}

export interface PlacementFilter {
  id?: number;
  domain?: Domain;
  specialisation?: Specialisation;
}

export interface Domain {
  id?: number;
  program: string;
  batch?: string;
  capacity?: number;
  qualification?: string;
}

export interface Specialisation {
  id?: number;
  code: string;
  name?: string;
  description?: string;
  creditsRequired?: number;
  year?: number;
}

export interface PlacementApplication {
  id: number;
  placement: Placement;
  date: string;
  about: string;
  acceptance: boolean;
  cvFilePath?: string;
}

export interface LoginResponse {
  token: string;
  user: Student;
}

export interface AuthError {
  error: string;
  message?: string;
}

