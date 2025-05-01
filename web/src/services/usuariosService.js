import axios from 'axios';

const collection = "/usuarios/";

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getUsuarios = async () => {
  const res = await api.get(collection);
  return res.data;
};

export const getUsuario = async (id) => {
  const res = await api.get(`${collection}/${id}`);
  return res.data;
};

export const createUsuario = async (data) => {
  const res = await api.post(collection, data);
  return res.data;
};

export const updateUsuario = async (id, data) => {
  const res = await api.put(`${collection}/${id}`, data);
  return res.status;
};

export default api;
