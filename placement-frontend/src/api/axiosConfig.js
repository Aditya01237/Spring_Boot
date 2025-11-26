import axios from 'axios';

// Create an axios instance pointing to the Spring Boot backend
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Ensure this matches your backend port and prefix
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request Interceptor: Automatically attach the JWT token to every protected request
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            // Spring Security requires the header format: Authorization: Bearer [TOKEN]
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;