import { useContext, useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Header from "../../components/Header.jsx";
import { getGameById } from "../../domain/productService";
import {
  checkWishlist,addWishlistProduct,removeWishlistProduct
} from "../../domain/wishlistService";
import { AuthContext } from "../../auth/authContext";
import { useAuthFetch } from "../../infrastructure/useAuthFetch";
import styles from "./ProductDetails.module.css";

export default function ProductDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const authFetch = useAuthFetch();

  const [game, setGame] = useState(null);
  const [error, setError] = useState("");
  const [wishlisted, setWishlisted] = useState(false);
  const [checking, setChecking] = useState(true);

  useEffect(() => {
    async function load() {
      try {
        const data = await getGameById(id);
        setGame(data);
      } catch {
        setError("Game not found");
      }
    }
    load();
  }, [id]);

  useEffect(() => {
    if (!user || !game) {
      setChecking(false);
      return;
    }

    async function check() {
      try {
        const exists = await checkWishlist(
          authFetch,
          game.id,
          user.id
        );
        setWishlisted(exists);
      } finally {
        setChecking(false);
      }
    }

    check();
  }, [user, game]);

  async function toggleWishlist() {
    if (!user) {
      navigate("/login");
      return;
    }

    if (wishlisted) {
      await removeWishlistProduct(authFetch, game.id);
      setWishlisted(false);
    } else {
      await addWishlistProduct(authFetch, game.id);
      setWishlisted(true);
    }
  }

  if (error) return <div className={styles.error}>{error}</div>;
  if (!game) return <div className={styles.loading}>Loading...</div>;

  return (
    <>
      <Header />

      <div className={styles.page}>
        <div className={styles.card}>
          {/* IMAGE */}
          <div className={styles.imageWrapper}>
            <img src={game.coverUrl} alt={game.title} />
          </div>

          {/* INFO */}
          <div className={styles.info}>
            <h1>{game.title}</h1>

            <p className={styles.description}>{game.description}</p>

            <div className={styles.meta}>
              <span><b>Category:</b> {game.category}</span>
              <span><b>Stock:</b> {game.stock}</span>
            </div>

            {/* PRICE + ACTIONS */}
            <div className={styles.actions}>
              <span className={styles.price}>${game.price}</span>

              <div className={styles.buttons}>
                <button className={styles.cartBtn}>
                  Add to Cart
                </button>

                <button
                  className={`${styles.wishlistBtn} ${
                    wishlisted ? styles.activeWishlist : ""
                  }`}
                  onClick={toggleWishlist}
                  disabled={checking}
                >
                  {wishlisted ? "❤️ Wishlisted" : "♡ Wishlist"}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
