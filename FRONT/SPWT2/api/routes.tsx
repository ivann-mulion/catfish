
import axios from 'axios';
import config from '../config';

export interface RoutePhoto {
    photoId: number;
    photoLink: string;
    route: string;
}

export interface Route {
    routeId: number;
    routePhotos: RoutePhoto[];
    name: string;
    description: string;
}

// Функция для обработки ошибок
const handleApiError = (error: any): Error => {
    if (axios.isAxiosError(error)) {
        const message = error.response?.data?.message || error.message || 'API Error';
        return new Error(message);
    } else if (error instanceof Error) {
        return error;
    }
    return new Error('Unknown error occurred');
};

export const fetchRoutes = async (): Promise<Route[]> => {
    try {
        console.log('Fetching routes from:', `${config.ROUTE_API_URL}/api/routes`);

        const response = await axios.get(`${config.ROUTE_API_URL}/api/routes`, {
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log('Routes response:', response.data);
        return response.data;

    } catch (error: any) {
        console.error('Error fetching routes:', {
            message: error.message,
            status: error.response?.status,
            data: error.response?.data,
            url: error.config?.url
        });
        throw handleApiError(error);
    }
};

export const fetchRoutePhotos = async (routeId: number): Promise<RoutePhoto[]> => {
    try {
        const response = await axios.get(`${config.ROUTE_API_URL}/api/routes/${routeId}/photos`, {
            timeout: 10000
        });
        return response.data;
    } catch (error: any) {
        console.error('Error fetching route photos:', error);
        throw handleApiError(error);
    }
};

export const createRoute = async (routeData: Omit<Route, 'routeId'>): Promise<Route> => {
    try {
        const response = await axios.post(`${config.ROUTE_API_URL}/api/routes`, routeData, {
            timeout: 10000
        });
        return response.data;
    } catch (error: any) {
        console.error('Error creating route:', error);
        throw handleApiError(error);
    }
};

export const updateRoute = async (routeId: number, routeData: Partial<Route>): Promise<Route> => {
    try {
        const response = await axios.put(`${config.ROUTE_API_URL}/api/routes/${routeId}`, routeData, {
            timeout: 10000
        });
        return response.data;
    } catch (error: any) {
        console.error('Error updating route:', error);
        throw handleApiError(error);
    }
};

export const deleteRoute = async (routeId: number): Promise<void> => {
    try {
        await axios.delete(`${config.ROUTE_API_URL}/api/routes/${routeId}`, {
            timeout: 10000
        });
    } catch (error: any) {
        console.error('Error deleting route:', error);
        throw handleApiError(error);
    }
};