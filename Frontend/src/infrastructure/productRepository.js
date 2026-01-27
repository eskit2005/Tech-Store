import { API_BASE_URL } from "../config/apiConfig";

export async function fetchAllProducts() {
  console.log('API_BASE_URL:${API_BASE_URL}');
  const res = await fetch(`${API_BASE_URL}/product`);
  if (!res.ok) throw new Error("Failed to fetch products");
  return await res.json();
}

export async function fetchProductsByName(title) {
  const res = await fetch(
    `${API_BASE_URL}/product/name?product_title=${encodeURIComponent(title)}`
  );
  if (!res.ok) throw new Error("Product not found");
  return await res.json();
}

export async function fetchProductsByCategory(categoryId) {
  const res = await fetch(
    `${API_BASE_URL}/product/category?Category_id=${categoryId}`
  );
  if (!res.ok) throw new Error("Category not found");
  return await res.json();
}

export async function fetchProductById(id) {
  const res = await fetch(`${API_BASE_URL}/product/${id}`);
  if (!res.ok) throw new Error("Product not found");
  return await res.json();
}