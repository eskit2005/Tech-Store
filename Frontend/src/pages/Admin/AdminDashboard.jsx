import { useState } from "react";
import { useAuthFetch } from "../../infrastructure/useAuthFetch";
import { addProduct, removeProduct } from "../../domain/productService";
import { uploadToCloudinary } from "../../infrastructure/uploadToCloudinary";
import styles from "./AdminDashboard.module.css";

export default function AdminDashboard() {
  const authFetch = useAuthFetch();

  const [addForm, setAddForm] = useState({
    title: "",
    description: "",
    price: "",
    stock: "",
    categoryId: "",
  });

  const [file, setFile] = useState(null);
  const [removeForm, setRemoveForm] = useState({
    Id: "",
    name: "",
  });

  async function handleAddProduct() {
    try {
      const coverUrl = await uploadToCloudinary(file);

      await addProduct(authFetch, {
        ...addForm,
        price: Number(addForm.price),
        stock: Number(addForm.stock),
        categoryId: Number(addForm.categoryId),
        cover_url: coverUrl,
      });

      alert("Product added successfully");
    } catch (err) {
      console.error(err);
      alert("Failed to add product");
    }
  }

  async function handleRemoveProduct() {
    try {
      let code=await removeProduct(authFetch, removeForm);
      alert("Product was removed")
      console.log(code)
    } catch (err) {
      console.error(err);
      
    }
  }

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Admin Dashboard</h1>

      <div className={styles.cards}>
        {/* ADD PRODUCT */}
        <div className={styles.card}>
          <h2>Add Product</h2>

          <input placeholder="Title" onChange={e => setAddForm({ ...addForm, title: e.target.value })} />
          <textarea placeholder="Description" onChange={e => setAddForm({ ...addForm, description: e.target.value })} />
          <input type="number" placeholder="Price" onChange={e => setAddForm({ ...addForm, price: e.target.value })} />
          <input type="number" placeholder="Stock" onChange={e => setAddForm({ ...addForm, stock: e.target.value })} />
          <input type="number" placeholder="Category ID" onChange={e => setAddForm({ ...addForm, categoryId: e.target.value })} />
          <input type="file" onChange={e => setFile(e.target.files[0])} />

          <button onClick={handleAddProduct}>Add Product</button>
        </div>

        {/* REMOVE PRODUCT */}
        <div className={styles.card}>
          <h2>Remove Product</h2>

          <input placeholder="Product UUID" onChange={e => setRemoveForm({ ...removeForm, Id: e.target.value })} />
          <input placeholder="Product Name" onChange={e => setRemoveForm({ ...removeForm, name: e.target.value })} />

          <button className={styles.danger} onClick={handleRemoveProduct}>
            Remove Product
          </button>
        </div>
      </div>
    </div>
  );
}

