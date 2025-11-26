import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import MyApplications from "./pages/MyApplications";

function App() {
  return (
    <Router>
      {/* Navbar displays across all pages */}
      <Navbar />
      <Routes>
        
        {/* The root path checks for a token. If found, go to Dashboard; otherwise, go to Login. */}
        <Route path="/" element={<Navigate to={localStorage.getItem('token') ? "/dashboard" : "/login"} />} /> 
        
        {/* Public Routes */}
        <Route path="/login" element={<Login />} />
        
        {/* Protected Route (Implicitly handled by logic inside Dashboard.jsx checking the token) */}
        <Route path="/dashboard" element={<Dashboard />} />
        
        {/* Fallback route for unknown paths */}
        <Route path="*" element={<Navigate to={localStorage.getItem('token') ? "/dashboard" : "/login"} />} />

        {/* New Route for My Applications */}
        <Route path="/my-applications" element={<MyApplications />} />
      </Routes>
    </Router>
  );
}

export default App;