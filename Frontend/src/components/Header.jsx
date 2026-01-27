import { NavLink } from "react-router-dom";
import styles from "./Header.module.css";
import { useContext } from "react";
import { AuthContext } from "../auth/authContext";
import UserMenu from "./UserMenu";

export default function Header() {
  const { accessToken } = useContext(AuthContext);

  return (
    <header className={styles.header}>
      <div className={styles.logo}>Molla Games</div>

      <nav className={styles.nav}>
        <NavLink
          to="/"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          Home
        </NavLink>

        <NavLink
          to="/wishlist"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          Wishlist
        </NavLink>

        <NavLink
          to="/orders"
          className={({ isActive }) => (isActive ? styles.active : styles.link)}
        >
          Orders
        </NavLink>

        {!accessToken && (
          <NavLink
            to="/login"
            className={({ isActive }) => (isActive ? styles.active : styles.link)}
          >
            Sign In
          </NavLink>
        )}

        {accessToken && <UserMenu />}
      </nav>
    </header>
  );
}
