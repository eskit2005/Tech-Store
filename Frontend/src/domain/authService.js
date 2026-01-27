import { useContext } from "react";
import { API_BASE_URL } from "../config/apiConfig";

import { AuthContext } from "../auth/authContext";

export async function login(email, password) {
    console.log(`API_BASE_URL:${API_BASE_URL}`);
    const res = await fetch(`${API_BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ email, password }),
    });

    if (!res.ok) throw new Error("Invalid credentials");
    return res.json();
}

export async function fetchMe(accessToken) {
    const res = await fetch(`${API_BASE_URL}/auth/me`, {
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
    });

    if (!res.ok) throw new Error("Unauthorized");
    return res.json();
}

export async function registerUser(payload) {
    const res = await fetch(`${API_BASE_URL}/User/add`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    });

    if(res.status===422) throw new Error("User already exists")
    if (!res.ok) throw new Error("Registration failed");
}

export async function logout(authfetch) {
  const res=await authfetch(`${API_BASE_URL}/auth/logout`,{
    method: "DELETE",
  })
  if (!res.ok) {
    throw new Error("Logout failed");
  }
}

export async function persistReload(setAccessToken, setUser) {
  const res = await fetch(`${API_BASE_URL}/auth/refresh`, {
    credentials: "include",
  });

  if (!res.ok) throw new Error("Cookie expired or user not signed in");

  const { accessToken } = await res.json();
  setAccessToken(accessToken);

  const userRes = await fetch(`${API_BASE_URL}/auth/me`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

  if (!userRes.ok) throw new Error("Couldn't verify user");

  const user = await userRes.json();
  setUser(user);
}
