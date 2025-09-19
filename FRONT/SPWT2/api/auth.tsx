
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import config from '../config';

interface User {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    userStatus: number;
}

interface RegisterData {
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    phone: string;
}

const handleApiError = (error: any): Error => {
    if (axios.isAxiosError(error)) {
        const message = error.response?.data?.message || error.message || 'API Error';
        return new Error(message);
    } else if (error instanceof Error) {
        return error;
    }
    return new Error('Unknown error occurred');
};

export const registerUser = async (userData: RegisterData): Promise<User> => {
    try {
        const response = await axios.post('http://localhost:8088/user', {
            id: 0,
            username: userData.username,
            firstName: userData.firstName,
            lastName: userData.lastName,
            email: userData.email,
            password: userData.password,
            phone: userData.phone,
            userStatus: 0
        }, {
            timeout: 10000, // 10 секунд таймаут
            headers: {
                'Content-Type': 'application/json'
            }
        });

        return response.data;
    } catch (error: any) {
        if (error.code === 'ECONNABORTED') {
            throw new Error('Таймаут подключения к серверу');
        } else if (error.code === 'ERR_NETWORK') {
            throw new Error('Сервер не доступен. Запустите бэкенд на localhost:8088');
        }
        throw handleApiError(error);
    }
};

export const loginUser = async (username: string, password: string): Promise<User> => {
    try {
        console.log('Logging in user:', username);
        const response = await axios.get(`${config.API_BASE_URL}/user/login`, {
            params: { username, password }
        });

        console.log('Login response:', response.data);

        // Сохраняем пользователя в AsyncStorage
        if (response.data) {
            await AsyncStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;

    } catch (error: any) {
        console.error('Login error:', error);
        throw handleApiError(error);
    }
};

export const logoutUser = async (): Promise<void> => {
    try {
        await axios.get(`${config.API_BASE_URL}/user/logout`);
    } catch (error: any) {
        console.warn('Logout API error:', error);
    } finally {
        await AsyncStorage.removeItem('user');
        await AsyncStorage.removeItem('authToken');
        await AsyncStorage.removeItem('refreshToken');
    }
};

export const deleteUser = async (username: string): Promise<void> => {
    try {
        await axios.delete(`${config.API_BASE_URL}/user/${username}`);
    } catch (error: any) {
        throw handleApiError(error);
    } finally {
        await AsyncStorage.removeItem('user');
        await AsyncStorage.removeItem('authToken');
        await AsyncStorage.removeItem('refreshToken');
    }
};

export const checkServerStatus = async (): Promise<boolean> => {
    try {
        const response = await axios.get('http://localhost:8088/actuator/health', {
            timeout: 5000
        });
        console.log('✅ Server is alive:', response.data);
        return true;
    } catch (error: any) {
        console.error('❌ Server is not responding:', error.message);
        return false;
    }
};