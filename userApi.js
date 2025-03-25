import axios from 'axios';
import { getTokenFromStorage } from '../utils/authUtils';

const API_URL = 'http://localhost:8000/api/users';

// Create axios instance with default config
const axiosInstance = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to request if available
axiosInstance.interceptors.request.use(
  (config) => {
    const token = getTokenFromStorage();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

/**
 * Get all users (admin only)
 * @returns {Promise} - API response
 */
export const getAllUsers = async () => {
  try {
    const response = await axiosInstance.get('/');
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get user by ID
 * @param {string} userId - User ID
 * @returns {Promise} - API response
 */
export const getUserById = async (userId) => {
  try {
    const response = await axiosInstance.get(`/${userId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get users by role
 * @param {string} role - Role name
 * @returns {Promise} - API response
 */
export const getUsersByRole = async (role) => {
  try {
    const response = await axiosInstance.get(`/role/${role}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Update user
 * @param {string} userId - User ID
 * @param {Object} userData - User data
 * @returns {Promise} - API response
 */
export const updateUser = async (userId, userData) => {
  try {
    const response = await axiosInstance.put(`/${userId}`, userData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Delete user (admin only)
 * @param {string} userId - User ID
 * @returns {Promise} - API response
 */
export const deleteUser = async (userId) => {
  try {
    const response = await axiosInstance.delete(`/${userId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get current user
 * @returns {Promise} - API response
 */
export const getCurrentUser = async () => {
  try {
    const response = await axiosInstance.get('/me');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default {
  getAllUsers,
  getUserById,
  getUsersByRole,
  updateUser,
  deleteUser,
  getCurrentUser,
};
