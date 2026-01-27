import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../auth/authContext";
import { login, fetchMe } from "../../domain/authService";
import styles from "./Login.module.css";

export default function Login() {
  const { setAccessToken, setUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    try {
      const { accessToken } = await login(email, password);
      setAccessToken(accessToken);

      const user = await fetchMe(accessToken);
      console.log(user);
      setUser(user);

      navigate("/");
    } catch {
      setError("Invalid email or password");
    }
  }

  return (
    <div className={styles.container}>
      <form className={styles.card + " " + styles.form} onSubmit={handleSubmit}>
        <h1 className={styles.title}>Sign In</h1>

        {error && <div className={styles.error}>{error}</div>}

        <input
          className={styles.input}
          type="email"
          placeholder="Email"
          required
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          className={styles.input}
          type="password"
          placeholder="Password"
          required
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className={styles.button}>Sign In</button>

        <div className={styles.footer}>
          Don’t have an account? <a href="/register">Register</a>
        </div>
      </form>
    </div>
  );
}
