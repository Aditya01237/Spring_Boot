import axios from 'axios';

// Create an axios instance pointing to the backend.
// In development, Vite proxies `/api` to `http://localhost:8080`,
// so from the browser's perspective everything is same-origin (no CORS).
const api = axios.create({
    baseURL: '/api',
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