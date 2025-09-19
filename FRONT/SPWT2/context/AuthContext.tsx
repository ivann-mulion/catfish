
import React, { createContext, useContext, useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

interface User {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    userStatus: number;
}

interface AuthContextType {
    user: User | null;
    setUser: (user: User | null) => void;
    isAuthenticated: boolean;
    logout: () => void;
    loading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUserState] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        loadUserFromStorage();
    }, []);

    const loadUserFromStorage = async () => {
        try {
            const userData = await AsyncStorage.getItem('user');
            if (userData) {
                const parsedUser = JSON.parse(userData);
                setUserState(parsedUser);
                setIsAuthenticated(true);
            }
        } catch (error: any) {
            console.error('Error loading user:', error);
        } finally {
            setLoading(false);
        }
    };

    const setUser = (userData: User | null) => {
        setUserState(userData);
        setIsAuthenticated(!!userData);
        if (userData) {
            AsyncStorage.setItem('user', JSON.stringify(userData));
        } else {
            AsyncStorage.removeItem('user');
        }
    };

    const logout = async () => {
        setUserState(null);
        setIsAuthenticated(false);
        try {
            await AsyncStorage.removeItem('user');
            await AsyncStorage.removeItem('authToken');
            await AsyncStorage.removeItem('refreshToken');
        } catch (error: any) {
            console.error('Error during logout:', error);
        }
    };

    const value: AuthContextType = {
        user,
        setUser,
        isAuthenticated,
        logout,
        loading
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};