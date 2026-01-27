import { API_BASE_URL } from "../config/apiConfig";

export async function getWishlist(authFetch) {
  const res = await authFetch(`${API_BASE_URL}/wishlist`);
  if (!res.ok) throw new Error("Failed to fetch wishlist");
  return res.json(); // [{ product_id, user_id }]
}

export async function addToWishlist(authFetch, productId) {
  const res = await authFetch(`${API_BASE_URL}/wishlist/add`, {
    method: "POST",
    body: JSON.stringify({ productId }),
  });
  if (!res.ok) throw new Error("Failed to add to wishlist");
}

export async function removeFromWishlist(authFetch, productId) {
  const res = await authFetch(`${API_BASE_URL}/wishlist/remove`, {
    method: "DELETE",
    body: JSON.stringify({ productId }),
  });
  if (!res.ok) throw new Error("Failed to remove from wishlist");
}

export async function checkingishlist(authFetch,productId,userId){
    const res = await authFetch(`${API_BASE_URL}/wishlist/checking`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      product_id: productId,
      user_id: userId,
    }),
  });

  return res.ok;
}