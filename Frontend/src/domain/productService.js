import {
  fetchAllProducts,
  fetchProductsByName,
  fetchProductsByCategory,
  fetchProductById
} from "../infrastructure/productRepository";
import { API_BASE_URL } from "../config/apiConfig";

function mapDto(dto) {
  return {
    id: dto.id,
    title: dto.title,
    description: dto.description,
    category: dto.category_name,
    price: dto.price,
    stock: dto.stock,
    coverUrl: dto.coverUrl
  };
}

export async function getAllGames() {
  return (await fetchAllProducts()).map(mapDto);
}

export async function searchGamesByName(title) {
  if (!title) return getAllGames();
  return (await fetchProductsByName(title)).map(mapDto);
}

export async function getGamesByCategory(categoryId) {
  return (await fetchProductsByCategory(categoryId)).map(mapDto);
}

export async function getGameById(id) {
  return mapDto(await fetchProductById(id));
}

/**
 * ADMIN: Add product
 */
export async function addProduct(authFetch, payload) {
  const res = await authFetch(`${API_BASE_URL}/product/add`, {
    method: "POST",
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    throw new Error("Failed to add product");
  }

  return res.json();
}

/**
 * ADMIN: Remove product
 */
export async function removeProduct(authFetch, payload) {
  const res = await authFetch(`${API_BASE_URL}/product/remove`, {
    method: "DELETE",
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    throw new Error("Failed to remove product");
  }
}
