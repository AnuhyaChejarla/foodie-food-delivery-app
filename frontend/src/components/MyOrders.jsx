
import { useEffect, useState } from "react";
import axios from "axios";

function MyOrders({ userId, onBack }) {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8081/order/user/${userId}`
        );
        setOrders(response.data);
      } catch (err) {
        setError("Unable to load your orders. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [userId]);

  return (
    <section className="my-orders-page">

      <div className="orders-top">
        <button className="orders-back-button" onClick={onBack}>
          ← Back to Foodie
        </button>

        <span className="orders-top-label">
          FOODIE • YOUR PERSONAL FOOD SPACE
        </span>
      </div>

      <div className="orders-heading">
        <span className="orders-eyebrow">
          YOUR FOOD JOURNEY
        </span>

        <h1>
          Every order has a <span>delicious story.</span>
        </h1>

        <p>
          Your favourite flavours, all in one place.
          Let's make every meal memorable!
        </p>
      </div>

      {loading && (
        <div className="orders-message">
          <div className="orders-loader"></div>
          <p>Gathering your delicious moments...</p>
        </div>
      )}

      {error && (
        <div className="orders-message">
          <p>{error}</p>
        </div>
      )}

      {!loading && !error && orders.length === 0 && (
        <div className="empty-orders-card">

          <div className="empty-orders-art">
            <div className="food-circle">
              🍱
            </div>
            <span className="food-spark spark-one">✦</span>
            <span className="food-spark spark-two">✧</span>
            <span className="food-spark spark-three">♡</span>
          </div>

          <span className="empty-orders-tag">
            A LITTLE HUNGRY?
          </span>

          <h2>Your story starts with a <span>first bite!</span></h2>

          <p>
            Looks like you haven't ordered anything yet.
            Explore our restaurants and discover something
            delicious today.
          </p>

          <button
            className="explore-orders-button"
            onClick={onBack}
          >
            Explore Restaurants <span>→</span>
          </button>

          <div className="empty-orders-footer">
            Made with ♡ by Foodie
          </div>

        </div>
      )}

      {!loading && !error && orders.length > 0 && (
        <div className="orders-list">

          <div className="orders-list-heading">
            <h2>Your Orders</h2>
            <span>{orders.length} orders</span>
          </div>

          {orders.map((order) => (
            <div className="order-card" key={order.orderId}>

              <div className="order-card-icon">🍽️</div>

              <div className="order-card-details">
                <h3>Order #{order.orderId}</h3>
                <p>
                  {order.orderDate
                    ? new Date(order.orderDate).toLocaleString()
                    : "Date unavailable"}
                </p>
              </div>

              <div className="order-card-right">
                <strong>₹{order.totalAmount}</strong>
                <span className="order-status">
                  {order.status}
                </span>
              </div>

            </div>
          ))}

        </div>
      )}

    </section>
  );
}

export default MyOrders;
