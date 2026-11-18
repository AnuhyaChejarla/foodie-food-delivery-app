
import { useEffect, useState } from "react";

function AdminPanel({ onBack, authHeader }) {
  const [restaurants, setRestaurants] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const [restaurantForm, setRestaurantForm] = useState({
    name: "",
    cusineType: "",
    deliveryTime: "",
    address: "",
    rating: "4.0",
    imagePath: "",
  });

  const [menuForm, setMenuForm] = useState({
    itemName: "",
    price: "",
    description: "",
    category: "",
    imagePath: "",
    restaurantId: "",
  });

  // Fetch restaurants
  const fetchRestaurants = async () => {
    try {
      const response = await fetch(
        "http://localhost:8081/restaurants",
        {
          headers: {
            Authorization: authHeader,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Unable to load restaurants.");
      }

      const data = await response.json();
      setRestaurants(data);
    } catch (err) {
      setError(err.message);
    }
  };

  useEffect(() => {
    fetchRestaurants();
  }, []);

  // Handle restaurant form changes
  const handleRestaurantChange = (e) => {
    const { name, value } = e.target;

    setRestaurantForm((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  // Handle menu form changes
  const handleMenuChange = (e) => {
    const { name, value } = e.target;

    setMenuForm((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  // Add restaurant
  const handleAddRestaurant = async (e) => {
    e.preventDefault();

    setMessage("");
    setError("");
    setLoading(true);

    try {
      const restaurantData = {
        name: restaurantForm.name,
        cusineType: restaurantForm.cusineType,
        deliveryTime: Number(restaurantForm.deliveryTime),
        address: restaurantForm.address,
        rating: Number(restaurantForm.rating),
        imagePath: restaurantForm.imagePath,
        active: true,
      };

      const response = await fetch(
        "http://localhost:8081/restaurants",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: authHeader,
          },
          body: JSON.stringify(restaurantData),
        }
      );

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(
          errorText || "Failed to add restaurant."
        );
      }

      setMessage("Restaurant added successfully!");

      setRestaurantForm({
        name: "",
        cusineType: "",
        deliveryTime: "",
        address: "",
        rating: "4.0",
        imagePath: "",
      });

      await fetchRestaurants();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Add menu item
  const handleAddMenuItem = async (e) => {
    e.preventDefault();

    setMessage("");
    setError("");
    setLoading(true);

    try {
      const menuData = {
        itemName: menuForm.itemName,
        price: Number(menuForm.price),
        description: menuForm.description,
        category: menuForm.category,
        imagePath: menuForm.imagePath,
        available: true,
        restaurantId: Number(menuForm.restaurantId),
      };

      const response = await fetch(
        "http://localhost:8081/menu",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: authHeader,
          },
          body: JSON.stringify(menuData),
        }
      );

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(
          errorText || "Failed to add menu item."
        );
      }

      setMessage("Menu item added successfully!");

      setMenuForm({
        itemName: "",
        price: "",
        description: "",
        category: "",
        imagePath: "",
        restaurantId: "",
      });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="admin-panel">
      <div className="admin-header">
        <div>
          <h1>Foodie Admin Panel</h1>
          <p>Manage restaurants and delicious menu items.</p>
        </div>

        <button onClick={onBack}>
          ← Back to Home
        </button>
      </div>

      {message && (
        <p className="admin-success">{message}</p>
      )}

      {error && (
        <p className="admin-error">{error}</p>
      )}

      <div className="admin-forms">

        {/* Add Restaurant */}
        <section className="admin-card">
          <h2>🍽️ Add Restaurant</h2>

          <form onSubmit={handleAddRestaurant}>
            <label>Restaurant Name</label>
            <input
              type="text"
              name="name"
              value={restaurantForm.name}
              onChange={handleRestaurantChange}
              placeholder="Enter restaurant name"
              required
            />

            <label>Cuisine Type</label>
            <input
              type="text"
              name="cusineType"
              value={restaurantForm.cusineType}
              onChange={handleRestaurantChange}
              placeholder="Example: Indian, Chinese"
              required
            />

            <label>Delivery Time (minutes)</label>
            <input
              type="number"
              name="deliveryTime"
              value={restaurantForm.deliveryTime}
              onChange={handleRestaurantChange}
              placeholder="Example: 30"
              min="1"
              required
            />

            <label>Restaurant Address</label>
            <input
              type="text"
              name="address"
              value={restaurantForm.address}
              onChange={handleRestaurantChange}
              placeholder="Enter restaurant address"
              required
            />

            <label>Rating</label>
            <input
              type="number"
              name="rating"
              value={restaurantForm.rating}
              onChange={handleRestaurantChange}
              min="0"
              max="5"
              step="0.1"
              required
            />

            <label>Image Path</label>
            <input
              type="text"
              name="imagePath"
              value={restaurantForm.imagePath}
              onChange={handleRestaurantChange}
              placeholder="/images/restaurant.jpg"
            />

            <button type="submit" disabled={loading}>
              {loading ? "Please wait..." : "Add Restaurant"}
            </button>
          </form>
        </section>

        {/* Add Menu Item */}
        <section className="admin-card">
          <h2>🥘 Add Menu Item</h2>

          <form onSubmit={handleAddMenuItem}>
            <label>Select Restaurant</label>

            <select
              name="restaurantId"
              value={menuForm.restaurantId}
              onChange={handleMenuChange}
              required
            >
              <option value="">Choose Restaurant</option>

              {restaurants.map((restaurant) => (
                <option
                  key={restaurant.restaurantId}
                  value={restaurant.restaurantId}
                >
                  {restaurant.name}
                </option>
              ))}
            </select>

            <label>Food Item Name</label>
            <input
              type="text"
              name="itemName"
              value={menuForm.itemName}
              onChange={handleMenuChange}
              placeholder="Example: Chicken Biryani"
              required
            />

            <label>Price (₹)</label>
            <input
              type="number"
              name="price"
              value={menuForm.price}
              onChange={handleMenuChange}
              placeholder="Enter price"
              min="0.01"
              step="0.01"
              required
            />

            <label>Description</label>
            <textarea
              name="description"
              value={menuForm.description}
              onChange={handleMenuChange}
              placeholder="Describe the food item"
              required
            />

            <label>Category</label>
            <input
              type="text"
              name="category"
              value={menuForm.category}
              onChange={handleMenuChange}
              placeholder="Example: Biryani, Pizza, Dessert"
              required
            />

            <label>Image Path</label>
            <input
              type="text"
              name="imagePath"
              value={menuForm.imagePath}
              onChange={handleMenuChange}
              placeholder="/images/biryani.jpg"
            />

            <button
              type="submit"
              disabled={loading || restaurants.length === 0}
            >
              {loading ? "Please wait..." : "Add Menu Item"}
            </button>

            {restaurants.length === 0 && (
              <p>Add a restaurant first to add menu items.</p>
            )}
          </form>
        </section>

      </div>
    </div>
  );
}

export default AdminPanel;
