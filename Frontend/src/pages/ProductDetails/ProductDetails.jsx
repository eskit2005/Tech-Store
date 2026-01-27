import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Header from "../../components/Header.jsx";
import { getGameById } from "../../domain/productService";
import styles from "./ProductDetails.module.css";

export default function ProductDetails() {
  const { id } = useParams();
  const [game, setGame] = useState(null);
  const [error, setError] = useState("");

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
                <button className={styles.cartBtn}>Add to Cart</button>
                <button className={styles.wishlistBtn}>♡ Wishlist</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
