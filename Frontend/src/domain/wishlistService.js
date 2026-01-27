import {
  getWishlist,
  addToWishlist,
  removeFromWishlist,
  checkingishlist,
} from "../infrastructure/wishlistRepository";
import { getGameById } from "./productService";

export async function fetchWishlistWithProducts(authFetch) {
  const wishlist = await getWishlist(authFetch);

  // Fetch product details for each wishlist item
  const products = await Promise.all(
    wishlist.map(item => getGameById(item.product_id))
  );

  return products;
}

export function addWishlistProduct(authFetch, productId) {
  return addToWishlist(authFetch, productId);
}

export function removeWishlistProduct(authFetch, productId) {
  return removeFromWishlist(authFetch, productId);
}

export async function checkWishlist(authFetch, productId, userId) {
  return checkingishlist(authFetch,productId,userId);
}