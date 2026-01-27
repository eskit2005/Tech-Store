import { useState } from "react";
import { Routes, Route } from "react-router-dom";
import { AuthContext } from "./auth/authContext";
import Homepage from "./pages/Homepage/Homepage";
import ProductDetails from "./pages/ProductDetails/ProductDetails";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import AdminDashboard from "./pages/Admin/AdminDashboard";
import Wishlist from "./pages/Wishlist/Wishlist";

function App() {
  const [accessToken, setAccessToken] = useState(null);
  const [user, setUser] = useState(null);

  return (
    <AuthContext.Provider value={{ accessToken, setAccessToken, user, setUser }}>
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/product/:id" element={<ProductDetails />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/admin" element={<AdminDashboard/>}/>
        <Route path="/wishlist" element={<Wishlist/>}/>
      </Routes>
    </AuthContext.Provider>
  );
}

export default App;

