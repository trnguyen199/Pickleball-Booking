import axios from 'axios';
import { getTokenFromStorage } from '../utils/authUtils';

const API_URL = 'http://localhost:8000/api/bookings';

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
 * Get all bookings (admin/manager only)
 * @returns {Promise} - API response
 */
export const getAllBookings = async () => {
  try {
    const response = await axiosInstance.get('/');
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get booking by ID
 * @param {string} bookingId - Booking ID
 * @returns {Promise} - API response
 */
export const getBookingById = async (bookingId) => {
  try {
    const response = await axiosInstance.get(`/${bookingId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get bookings by user ID
 * @param {string} userId - User ID
 * @returns {Promise} - API response
 */
export const getUserBookings = async (userId) => {
  try {
    const response = await axiosInstance.get(`/user/${userId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get bookings by court ID
 * @param {string} courtId - Court ID
 * @returns {Promise} - API response
 */
export const getCourtBookings = async (courtId) => {
  try {
    const response = await axiosInstance.get(`/court/${courtId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get bookings by court ID and date range
 * @param {string} courtId - Court ID
 * @param {Date} startDate - Start date
 * @param {Date} endDate - End date
 * @returns {Promise} - API response
 */
export const getCourtBookingsByDateRange = async (courtId, startDate, endDate) => {
  try {
    const response = await axiosInstance.get(`/court/${courtId}/date-range`, {
      params: { startDate, endDate }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get bookings by status
 * @param {string} status - Booking status
 * @returns {Promise} - API response
 */
export const getBookingsByStatus = async (status) => {
  try {
    const response = await axiosInstance.get(`/status/${status}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Create a new booking
 * @param {Object} bookingData - Booking data
 * @returns {Promise} - API response
 */
export const createBooking = async (bookingData) => {
  try {
    const response = await axiosInstance.post('/', bookingData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Update an existing booking
 * @param {string} bookingId - Booking ID
 * @param {Object} bookingData - Booking data
 * @returns {Promise} - API response
 */
export const updateBooking = async (bookingId, bookingData) => {
  try {
    const response = await axiosInstance.put(`/${bookingId}`, bookingData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Update booking status
 * @param {string} bookingId - Booking ID
 * @param {string} status - New status
 * @returns {Promise} - API response
 */
export const updateBookingStatus = async (bookingId, status) => {
  try {
    const response = await axiosInstance.patch(`/${bookingId}/status?status=${status}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Delete a booking
 * @param {string} bookingId - Booking ID
 * @returns {Promise} - API response
 */
export const deleteBooking = async (bookingId) => {
  try {
    const response = await axiosInstance.delete(`/${bookingId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Check time slot availability
 * @param {string} courtId - Court ID
 * @param {number} courtNumber - Court number
 * @param {Date} startTime - Start time
 * @param {Date} endTime - End time
 * @returns {Promise} - API response
 */
export const checkTimeSlotAvailability = async (courtId, courtNumber, startTime, endTime) => {
  try {
    const response = await axiosInstance.get('/availability', {
      params: { courtId, courtNumber, startTime, endTime }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default {
  getAllBookings,
  getBookingById,
  getUserBookings,
  getCourtBookings,
  getCourtBookingsByDateRange,
  getBookingsByStatus,
  createBooking,
  updateBooking,
  updateBookingStatus,
  deleteBooking,
  checkTimeSlotAvailability,
};
