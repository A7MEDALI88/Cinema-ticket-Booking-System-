// src/services/hallService.js
import api from "./api";

export const getHalls = () => api.get("/halls");
export const addHall = (data) => api.post("/admin/halls", data);
export const updateHall = (id, data) => api.put(`/admin/halls/${id}`, data);
export const deleteHall = (id) => api.delete(`/admin/halls/${id}`);