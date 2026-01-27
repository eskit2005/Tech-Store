import GameCard from "../../components/GameCard";
import styles from "./GameRow.module.css";

export default function GameRow({ genre, games }) {
  return (
    <section className={styles.section}>
      <h2>{genre}</h2>
      <div className={styles.row}>
        {games.map(game => (
          <GameCard key={game.id} game={game} />
        ))}
      </div>
    </section>
  );
}
