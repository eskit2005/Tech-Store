import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../../domain/authService";
import styles from "./Register.module.css";

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: "", email: "", password: "" });
  const [error, setError] = useState("");

  async function submit(e) {
    e.preventDefault();
    setError("");

    try {
      await registerUser(form);
      navigate("/login");
    } catch(error) {
      setError(error.message);
    }
  }

  return (
    <div className={styles.container}>
      <form className={styles.card + " " + styles.form} onSubmit={submit}>
        <h1 className={styles.title}>Create Account</h1>
        <p className={styles.subtitle}>Join Molla Games</p>

        {error && <div className={styles.error}>{error}</div>}

        <input
          className={styles.input}
          placeholder="Name"
          required
          onChange={(e) => setForm({ ...form, name: e.target.value })}
        />

        <input
          className={styles.input}
          placeholder="Email"
          required
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />

        <input
          className={styles.input}
          type="password"
          placeholder="Password"
          required
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <button className={styles.button}>Sign Up</button>

        <div className={styles.footer}>
          Already have an account? <a href="/login">Sign In</a>
        </div>
      </form>
    </div>
  );
}
