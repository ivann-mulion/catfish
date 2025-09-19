
export interface User {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    userStatus: number;
}

export interface AuthContextType {
    user: User | null;
    setUser: (user: User | null) => void;
    isAuthenticated: boolean;
    loading: boolean;
}

export interface LoginCredentials {
    username: string;
    password: string;
}

export interface RegisterData extends LoginCredentials {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
}