import { useEffect, useState } from "react";
import { getAllGames, searchGamesByName, getGamesByCategory } from "../../domain/productService";
import Header from "../../components/Header.jsx";
import GameRow from "./GameRow.jsx";
import styles from "./HomePage.module.css";


export default function HomePage() {
  const [games, setGames] = useState([]);
  const [inputText, setInputText] = useState("");
  const [selectedCategory, setSelectedCategory] = useState(""); // NEW
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // LOAD ALL PRODUCTS INITIALLY
  useEffect(() => {
    async function loadAll() {
      try {
        setLoading(true);
        const data = await getAllGames();
        setGames(data);
        setError("");
      } catch (err) {
        setError("Failed to load products");
      } finally {
        setLoading(false);
      }
    }
    loadAll();
  }, []);

  // SERVER-SIDE SEARCH
  useEffect(() => {
    async function searchProducts() {
      const query = inputText.trim();

      // 🔴 skip empty input
      if (!query) {
        setError("");
        const data = await getAllGames();
        setGames(data);
        return;
      }

      try {
        setLoading(true);
        const data = await searchGamesByName(query);

        if (!data || data.length === 0) {
          setError("No products were found");
          setGames([]);
        } else {
          setGames(data);
          setError("");
        }
      } catch (err) {
        console.error(err);
        setGames([]);
        setError("No products were found");
      } finally {
        setLoading(false);
      }
    }

    searchProducts();
  }, [inputText]);

  // SERVER-SIDE CATEGORY FILTER
  useEffect(() => {
    async function filterByCategory() {
      try {
        setLoading(true);
        setError("");

        if (!selectedCategory) {
          // If "All Categories" selected, load all games
          const data = await getAllGames();
          setGames(data);
          return;
        }

        const data = await getGamesByCategory(Number(selectedCategory));
        if (!data || data.length === 0) {
          setError("No products in this category");
          setGames([]);
        } else {
          setGames(data);
          setError("");
        }
      } catch (err) {
        console.error(err);
        setGames([]);
        setError("Failed to load category products");
      } finally {
        setLoading(false);
      }
    }

    filterByCategory();
  }, [selectedCategory]);

  // GROUP BY CATEGORY
  const grouped = games.reduce((acc, game) => {
    acc[game.category] = acc[game.category] || [];
    acc[game.category].push(game);
    return acc;
  }, {});

  return (
    <div className={styles.container}>
      
      <Header/>

      {/* HERO */}
      <section className={styles.hero}>
        <h1>Physical Games at Reasonable Prices</h1>
        <p>
          Find authentic physical game copies with fair pricing,
          verified sellers, and original discs.
        </p>
      </section>

      {/* SEARCH + CATEGORY FILTER */}
      <div className={styles.filters}>
        <input
          className={styles.search}
          placeholder="Search games..."
          value={inputText}
          onChange={(e) => setInputText(e.target.value)}
        />

        <select
          className={styles.categorySelect}
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
        >
          <option value="">All Categories</option>
          <option value="1">Shooter</option>
          <option value="2">RPG</option>
          <option value="3">Stategy</option>
          <option value="4">Racing</option>
          <option value="5">Adventure</option>
        </select>
      </div>

      {/* ERROR */}
      {error && <div className={styles.error}>{error}</div>}

      {/* LOADING */}
      {loading && <div className={styles.loading}>Loading...</div>}

      {/* RESULTS */}
      {!loading && !error &&
        Object.entries(grouped).map(([genre, games]) => (
          <GameRow key={genre} genre={genre} games={games} />
        ))}
    </div>
  );
}