import { useNavigate } from "react-router-dom";
import styles from "./GameCard.module.css";

export default function GameCard({ game }) {
  const navigate = useNavigate();

  return (
    <div
      className={styles.card}
      onClick={() => navigate(`/product/${game.id}`)}
    >
      <img src={game.coverUrl} alt={game.title} />
      <span>{game.title}</span>
      <small>${game.price}</small>
    </div>
  );
}
