
import { useState } from "react";

function Checkout({
  cart,
  totalItems,
  totalPrice,
  onBack,
  onPlaceOrder,
}) {
  const [customer, setCustomer] = useState({
    fullName: "",
    phone: "",
    address: "",
    city: "",
    pincode: "",
    instructions: "",
  });

  const deliveryFee = totalPrice > 250 ? 0 : 30;
  const grandTotal = totalPrice + deliveryFee;

  const handleChange = (event) => {
    const { name, value } = event.target;

    setCustomer((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    onPlaceOrder(customer);
  };

  return (
    <section className="checkout-page">
      <button className="back-button" onClick={onBack}>
        ← Back to Cart
      </button>

      <div className="checkout-heading">
        <span className="eyebrow">ALMOST THERE!</span>
        <h1>Complete Your Order</h1>
        <p>Just a few details before your delicious food arrives.</p>
      </div>

      <div className="checkout-layout">
        <form className="checkout-form" onSubmit={handleSubmit}>
          <h2>Delivery Details</h2>

          <label>Full Name</label>
          <input
            type="text"
            name="fullName"
            placeholder="Enter your full name"
            value={customer.fullName}
            onChange={handleChange}
            required
          />

          <label>Phone Number</label>
          <input
            type="tel"
            name="phone"
            placeholder="Enter your phone number"
            value={customer.phone}
            onChange={handleChange}
            pattern="[0-9]{10}"
            maxLength="10"
            required
          />

          <label>Delivery Address</label>
          <textarea
            name="address"
            placeholder="House number, street, area..."
            value={customer.address}
            onChange={handleChange}
            required
          />

          <div className="checkout-input-row">
            <div>
              <label>City</label>
              <input
                type="text"
                name="city"
                placeholder="City"
                value={customer.city}
                onChange={handleChange}
                required
              />
            </div>

            <div>
              <label>PIN Code</label>
              <input
                type="text"
                name="pincode"
                placeholder="6-digit PIN"
                value={customer.pincode}
                onChange={handleChange}
                pattern="[0-9]{6}"
                maxLength="6"
                required
              />
            </div>
          </div>

          <label>Delivery Instructions (Optional)</label>
          <textarea
            name="instructions"
            placeholder="Any special delivery instructions?"
            value={customer.instructions}
            onChange={handleChange}
          />

          <button type="submit" className="primary-button place-order-button">
            Place Order · ₹{grandTotal} →
          </button>
        </form>

        <div className="checkout-order-summary">
          <h2>Your Order</h2>

          {cart.map((item) => (
            <div className="checkout-item" key={item.id}>
              <img src={item.image} alt={item.name} />

              <div>
                <h3>{item.name}</h3>
                <p>Quantity: {item.quantity}</p>
              </div>

              <strong>₹{item.price * item.quantity}</strong>
            </div>
          ))}

          <div className="checkout-total-row">
            <span>Total Items</span>
            <strong>{totalItems}</strong>
          </div>

          <div className="checkout-total-row">
            <span>Subtotal</span>
            <strong>₹{totalPrice}</strong>
          </div>

          <div className="checkout-total-row">
            <span>Delivery Fee</span>
            <strong>
              {deliveryFee === 0 ? "FREE" : `₹${deliveryFee}`}
            </strong>
          </div>

          <div className="checkout-grand-total">
            <span>Total Payable</span>
            <strong>₹{grandTotal}</strong>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Checkout;
