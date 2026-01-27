import { useState, useContext, useRef, useEffect } from "react";
import { AuthContext } from "../auth/authContext";
import { useNavigate } from "react-router-dom";
import { useAuthFetch } from "../infrastructure/useAuthFetch";
import { logout } from "../domain/authService";
import styles from "./UserMenu.module.css";

export default function UserMenu() {
  const { user, setUser, setAccessToken } = useContext(AuthContext);
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const menuRef = useRef(null);
  const authFetch = useAuthFetch();

  // Close dropdown on outside click
  useEffect(() => {
    function handleClickOutside(event) {
      if (menuRef.current && !menuRef.current.contains(event.target)) {
        setOpen(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () =>
      document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  async function handleLogout() {
    try {
      await logout(authFetch); // backend logout (refresh token revoke)
    } catch (err) {
      console.error("Logout failed:", err);
    } finally {
      setAccessToken(null);
      setUser(null);
      navigate("/login");
    }
  }

  return (
    <div className={styles.userMenu} ref={menuRef}>
      <button
        className={styles.username}
        onClick={() => setOpen(prev => !prev)}
      >
        {user?.name} ▼
      </button>

      {open && (
        <div className={styles.dropdown}>
          {/* ADMIN OPTION */}
          {user?.role === "ADMIN" && (
            <button
              className={styles.admin}
              onClick={() => {
                navigate("/admin");
                setOpen(false);
              }}
            >
              Admin Dashboard
            </button>
          )}

          <button
            className={styles.logout}
            onClick={handleLogout}
          >
            Logout
          </button>
        </div>
      )}
    </div>
  );
}
