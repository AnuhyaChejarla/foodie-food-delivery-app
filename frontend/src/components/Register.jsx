
import { useState } from "react";

function Register({ onRegister, onShowLogin }) {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
    address: "",
  });

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    setLoading(true);

    try {
      const response = await fetch("http://localhost:8081/user", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || "Registration failed");
      }

      onRegister(data);
    } catch (err) {
      setError(err.message || "Something went wrong");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h2>Create your account</h2>
        <p>Join Foodie and enjoy delicious food!</p>

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="name"
            placeholder="Full Name"
            value={formData.name}
            onChange={handleChange}
            required
          />

          <input
            type="email"
            name="email"
            placeholder="Email Address"
            value={formData.email}
            onChange={handleChange}
            required
          />

          <input
            type="tel"
            name="phone"
            placeholder="Phone Number"
            value={formData.phone}
            onChange={handleChange}
            pattern="[0-9]{10}"
            maxLength="10"
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Password (minimum 6 characters)"
            value={formData.password}
            onChange={handleChange}
            minLength="6"
            required
          />

          <textarea
            name="address"
            placeholder="Delivery Address"
            value={formData.address}
            onChange={handleChange}
            required
          />

          {error && <p className="auth-error">{error}</p>}

          <button type="submit" disabled={loading}>
            {loading ? "Creating Account..." : "Create Account"}
          </button>
        </form>

        <p className="auth-switch">
          Already have an account?{" "}
          <span onClick={onShowLogin}>Login</span>
        </p>
      </div>
    </div>
  );
}

export default Register;
