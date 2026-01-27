import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../auth/authContext";
import { useAuthFetch } from "../../infrastructure/useAuthFetch";
import Header from "../../components/Header";
import {
    fetchWishlistWithProducts,
    removeWishlistProduct,
} from "../../domain/wishlistService";
import { useNavigate } from "react-router-dom";
import styles from "./Wishlist.module.css";


export default function Wishlist() {
  const { user } = useContext(AuthContext);
  const authFetch = useAuthFetch();
  const navigate = useNavigate();

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user) {
      setLoading(false);
      return;
    }

    async function loadWishlist() {
      try {
        const data = await fetchWishlistWithProducts(authFetch);
        setProducts(data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    loadWishlist();
  }, [user]);

  async function handleRemove(productId) {
    await removeWishlistProduct(authFetch, productId);
    setProducts(prev => prev.filter(p => p.id !== productId));
  }

  return (
    <div className={styles.page}>
      <Header />

      {!user && (
        <div className={styles.center}>
          <h2>Please sign in to view your wishlist</h2>
          <button onClick={() => navigate("/login")}>Sign In</button>
        </div>
      )}

      {user && loading && (
        <p className={styles.loading}>Loading wishlist…</p>
      )}

      {user && !loading && (
        <div className={styles.container}>
          <h1 className={styles.title}>Your Wishlist</h1>

          {products.length === 0 && (
            <p className={styles.empty}>Your wishlist is empty.</p>
          )}

          <div className={styles.list}>
            {products.map(product => (
              <div key={product.id} className={styles.row}>
                <img
                  src={product.coverUrl}
                  alt={product.title}
                  className={styles.cover}
                />

                <div className={styles.info}>
                  <h3>{product.title}</h3>
                  <span className={styles.price}>
                    ${product.price}
                  </span>
                </div>

                <button
                  className={styles.heart}
                  onClick={() => handleRemove(product.id)}
                  title="Remove from wishlist"
                >
                  ❤️
                </button>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
