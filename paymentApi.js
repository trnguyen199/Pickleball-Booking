import axios from 'axios';
import { getTokenFromStorage } from '../utils/authUtils';

const API_URL = 'http://localhost:8000/api/payments';

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
 * Get all payments (admin/manager only)
 * @returns {Promise} - API response
 */
export const getAllPayments = async () => {
  try {
    const response = await axiosInstance.get('/');
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get payment by ID
 * @param {string} paymentId - Payment ID
 * @returns {Promise} - API response
 */
export const getPaymentById = async (paymentId) => {
  try {
    const response = await axiosInstance.get(`/${paymentId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get payments by user ID
 * @param {string} userId - User ID
 * @returns {Promise} - API response
 */
export const getUserPayments = async (userId) => {
  try {
    const response = await axiosInstance.get(`/user/${userId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get payments by booking ID
 * @param {string} bookingId - Booking ID
 * @returns {Promise} - API response
 */
export const getBookingPayments = async (bookingId) => {
  try {
    const response = await axiosInstance.get(`/booking/${bookingId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get payments by status
 * @param {string} status - Payment status
 * @returns {Promise} - API response
 */
export const getPaymentsByStatus = async (status) => {
  try {
    const response = await axiosInstance.get(`/status/${status}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Create a new payment
 * @param {Object} paymentData - Payment data
 * @returns {Promise} - API response
 */
export const createPayment = async (paymentData) => {
  try {
    const response = await axiosInstance.post('/', paymentData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Update payment status
 * @param {string} paymentId - Payment ID
 * @param {string} status - New status
 * @returns {Promise} - API response
 */
export const updatePaymentStatus = async (paymentId, status) => {
  try {
    const response = await axiosInstance.patch(`/${paymentId}/status?status=${status}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Complete a payment
 * @param {string} paymentId - Payment ID
 * @param {string} transactionId - Transaction ID
 * @returns {Promise} - API response
 */
export const completePayment = async (paymentId, transactionId) => {
  try {
    const response = await axiosInstance.patch(`/${paymentId}/complete?transactionId=${transactionId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Delete a payment (admin only)
 * @param {string} paymentId - Payment ID
 * @returns {Promise} - API response
 */
export const deletePayment = async (paymentId) => {
  try {
    const response = await axiosInstance.delete(`/${paymentId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Get total revenue by date range
 * @param {Date} startDate - Start date
 * @param {Date} endDate - End date
 * @returns {Promise} - API response
 */
export const getTotalRevenueByDateRange = async (startDate, endDate) => {
  try {
    const response = await axiosInstance.get('/revenue', {
      params: { startDate, endDate }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default {
  getAllPayments,
  getPaymentById,
  getUserPayments,
  getBookingPayments,
  getPaymentsByStatus,
  createPayment,
  updatePaymentStatus,
  completePayment,
  deletePayment,
  getTotalRevenueByDateRange,
};
