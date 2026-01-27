import { API_BASE_URL } from "../config/apiConfig";
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