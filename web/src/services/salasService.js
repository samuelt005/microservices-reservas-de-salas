import axios from 'axios';

const collection = "/salas/";

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getSalas = async () => {
  const res = await api.get(collection);
  return res.data;
};

export const getSala = async (id) => {
  const res = await api.get(`${collection}/${id}`);
  return res.data;
};

export const createSala = async (data) => {
 const res = await api.post(collection, {
    nome: data.nome,
    capacidade: parseInt(data.capacidade),
  });
  return res.data;
};

export const updateSala = async (id, data) => {
  const res = await api.put(`${collection}/${id}`, data);
  return res.status;
};

export default api;
