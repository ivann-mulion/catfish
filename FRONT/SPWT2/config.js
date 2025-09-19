// config.js
const config = {
    // Базовые URL API
    USER_API_URL: 'http://localhost:8088', // пользовательский сервис
    ROUTE_API_URL: 'http://localhost:8089', // сервис маршрутов

    // JWT настройки
    JWT_SECRET: 'WX5pJ7vQ2zTbV9yN1cR4fU6iM8kL3oP5hA0qS9dF2gH7jK4lZ1xW8eB5vC6mD3nG',
    JWT_EXPIRATION: 86400,
    JWT_REFRESH_EXPIRATION: 172800,

    // Refresh token URL
    REFRESH_TOKEN_URL: 'http://localhost:8080/api/auth/refresh',

    // Kafka
    KAFKA_CONFIG: {
        bootstrapServers: 'localhost:9092',
        retries: 3,
        batchSize: 16384,
        bufferMemory: '32MB'
    },

    // Eureka
    EUREKA_URI: 'http://localhost:8761/eureka'
};

// Для разработки
const development = {
    ...config,
    USER_API_URL: 'http://localhost:8088',
    ROUTE_API_URL: 'http://localhost:8089'
};

// Для продакшена
const production = {
    ...config,
    USER_API_URL: 'https://your-production-api.com',
    ROUTE_API_URL: 'https://your-route-api.com'
};

export default __DEV__ ? development : production;