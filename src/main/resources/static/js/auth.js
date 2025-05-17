// Authentication related functions
const API_BASE_URL = '/api';

async function login(username, password) {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error('Login failed');
        }

        const data = await response.json();
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(data.user));

        // Redirect based on user role
        if (data.user.role === 'ADMIN') {
            window.location.href = '/admin/dashboard.html';
        } else {
            window.location.href = '/student/dashboard.html';
        }
    } catch (error) {
        showError('Login failed. Please check your credentials.');
        console.error('Login error:', error);
    }
}

async function register(userData) {
    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            throw new Error('Registration failed');
        }

        showSuccess('Registration successful! Please login.');
        setTimeout(() => {
            window.location.href = '/login.html';
        }, 2000);
    } catch (error) {
        showError('Registration failed. Please try again.');
        console.error('Registration error:', error);
    }
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login.html';
}

function isAuthenticated() {
    return localStorage.getItem('token') !== null;
}

function getCurrentUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
}

function showError(message) {
    const errorDiv = document.getElementById('error-message');
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.display = 'none';
        }, 5000);
    }
}

function showSuccess(message) {
    const successDiv = document.getElementById('success-message');
    if (successDiv) {
        successDiv.textContent = message;
        successDiv.style.display = 'block';
        setTimeout(() => {
            successDiv.style.display = 'none';
        }, 5000);
    }
}

// Event listeners for login form
document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            login(username, password);
        });
    }

    // Check if user is already logged in
    if (isAuthenticated()) {
        const user = getCurrentUser();
        if (user.role === 'ADMIN') {
            window.location.href = '/admin/dashboard.html';
        } else {
            window.location.href = '/student/dashboard.html';
        }
    }
}); 