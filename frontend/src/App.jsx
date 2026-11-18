
import { useState, useEffect } from "react";

import "./App.css";

import Checkout from "./components/Checkout";
import Navbar from "./components/Navbar";
import RestaurantSection from "./components/RestaurantSection";
import CartDrawer from "./components/CartDrawer";
import RestaurantMenu from "./components/RestaurantMenu";
import Register from "./components/Register";
import Login from "./components/Login";
import MyOrders from "./components/MyOrders";

function App() {
  const [cart, setCart] = useState([]);
  const [showCart, setShowCart] = useState(false);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [showCheckout, setShowCheckout] = useState(false);
  const [showMyOrders, setShowMyOrders] = useState(false);
  const [showCelebration, setShowCelebration] = useState(false);
  const [celebrationOrderId, setCelebrationOrderId] = useState("");

  const [authPage, setAuthPage] = useState("home");
  const [currentUser, setCurrentUser] = useState(() => {
    try {
      const savedUser = localStorage.getItem("foodieUser");
      return savedUser ? JSON.parse(savedUser) : null;
    } catch {
      return null;
    }
  });

  const [restaurants, setRestaurants] = useState([]);
  const [loadingRestaurants, setLoadingRestaurants] = useState(true);
  const [restaurantError, setRestaurantError] = useState("");

  const [apiDishes, setApiDishes] = useState([]);

  // Fetch menu data
  useEffect(() => {
    fetch("http://localhost:8081/menu")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch menu");
        }
        return response.json();
      })
      .then((data) => {
        setApiDishes(data);
      })
      .catch((error) => {
        console.error("Menu API error:", error);
      });
  }, []);

  // Fetch restaurants
  useEffect(() => {
    fetch("http://localhost:8081/restaurants")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch restaurants");
        }
        return response.json();
      })
      .then((data) => {
        setRestaurants(data);
        setRestaurantError("");
      })
      .catch((error) => {
        console.error("Restaurant error:", error);
        setRestaurantError("Unable to load restaurants.");
      })
      .finally(() => {
        setLoadingRestaurants(false);
      });
  }, []);

  // Convert backend menu data
  const dishes = apiDishes
    .filter((item) => item.available)
    .map((item) => ({
      id: item.menuId,
      restaurantId: item.restaurantId,
      name: item.itemName,
      description: item.description,
      category: item.category,
      price: Number(item.price),
      rating: 4.5,
      image: item.imagePath
        ? `http://localhost:8081${item.imagePath}`
        : "https://placehold.co/400x300?text=Food",
      chef: "Our Chef",
      restaurant:
        restaurants.find(
          (r) => r.restaurantId === item.restaurantId
        )?.name || "Restaurant",
      details: item.description,
    }));

  // Add to cart
  const addToCart = (dish) => {
    setCart((previous) => {
      const existing = previous.find(
        (item) => item.id === dish.id
      );

      if (existing) {
        return previous.map((item) =>
          item.id === dish.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }

      return [...previous, { ...dish, quantity: 1 }];
    });
  };

  // Update cart quantity
  const updateQuantity = (id, change) => {
    setCart((previous) =>
      previous
        .map((item) =>
          item.id === id
            ? { ...item, quantity: item.quantity + change }
            : item
        )
        .filter((item) => item.quantity > 0)
    );
  };

  // Calculate total items
  const totalItems = cart.reduce(
    (total, item) => total + item.quantity,
    0
  );

  // Calculate total price
  const totalPrice = cart.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );
  

  // Place order through backend
  const handlePlaceOrder = async (customer) => {
    if (!currentUser) {
      alert("Please login before placing an order.");
      return;
    }

    try {
		const orderData = {
		  userId: currentUser.userId,

		  deliveryName: customer.fullName,
		  deliveryPhone: customer.phone,
		  deliveryAddress: customer.address,
		  deliveryCity: customer.city,
		  deliveryPincode: customer.pincode,

		  items: cart.map((item) => ({
		    menuId: item.id,
		    quantity: item.quantity,
		  })),
		};

      const response = await fetch(
        "http://localhost:8081/order/checkout",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(orderData),
        }
      );

      if (!response.ok) {
        const errorData = await response.text();
        throw new Error(errorData || "Order placement failed");
      }

	  const savedOrder = await response.json();

	  setCelebrationOrderId(savedOrder.orderId);
	  setCart([]);
	  setShowCheckout(false);
	  setShowMyOrders(true);
	  setShowCelebration(true);

    } catch (error) {
      console.error("Checkout error:", error);
      alert(error.message || "Unable to place your order. Please try again.");
    }
  };


  // Get dishes belonging to selected restaurant
  const restaurantDishes = selectedRestaurant
    ? dishes.filter(
        (dish) =>
          dish.restaurantId === selectedRestaurant.restaurantId
      )
    : [];

  return (
    <div className="foodie-app">

	<Navbar
		
	onGoHome={() => {
	  setSelectedRestaurant(null);
	  setShowCart(false);
	  setShowCheckout(false);
	  setShowMyOrders(false);
	  setAuthPage("home");
	  window.scrollTo({ top: 0, behavior: "smooth" });
	}}
	  totalItems={totalItems}
	  onOpenCart={() => setShowCart(true)}
	  onShowLogin={() => setAuthPage("login")}
	  onShowRegister={() => setAuthPage("register")}
	  currentUser={currentUser}
	  onShowOrders={() => {
	    setShowMyOrders(true);
	  }}
	  onLogout={() => {
	    localStorage.removeItem("foodieUser");
	    setCurrentUser(null);
	    setAuthPage("home");
	    setShowMyOrders(false);
	  }}
	/>

      <main>
        {authPage === "register" ? (
          <Register
            onRegister={() => {
              setAuthPage("login");
              alert("Account created successfully! Please login.");
            }}
            onShowLogin={() => setAuthPage("login")}
          />
        ) : authPage === "login" ? (
          <Login
		  onLogin={(user) => {
		    const loggedInUser = {
		      userId: user.userId,
		      name: user.name,
		      email: user.email
		    };

		    localStorage.setItem("foodieUser", JSON.stringify(loggedInUser));

		    setCurrentUser(loggedInUser);
		    setAuthPage("home");
		  }}
            onShowRegister={() => setAuthPage("register")}
          />
        ) : showCheckout ? (
          <Checkout
            cart={cart}
            totalItems={totalItems}
            totalPrice={totalPrice}
            onBack={() => {
              setShowCheckout(false);
              setShowCart(true);
            }}
            onPlaceOrder={handlePlaceOrder}
          />
		  ) : showMyOrders ? (
		    <MyOrders
		      userId={currentUser.userId}
		      onBack={() => setShowMyOrders(false)}
		    />
		  ) : selectedRestaurant ? ( 
          <RestaurantMenu
            restaurant={selectedRestaurant}
            dishes={restaurantDishes}
            onBack={() => setSelectedRestaurant(null)}
            onAddToCart={addToCart}
          />
        ) : (
          <>
            <div className="welcome-banner">
              <div className="welcome-content">
                <span className="eyebrow">WELCOME TO FOODIE</span>
                <h1>Good food, great mood.</h1>
                <p>
                  Discover your favourite restaurants and order
                  something delicious today.
                </p>
              </div>

              <img
                src="/Smiling%20Foodie%20Delivery%20Hero.png"
                alt="Foodie delivery partner"
                className="delivery-guy"
              />
            </div>

            <div id="restaurants">
              <RestaurantSection
                restaurants={restaurants}
                loadingRestaurants={loadingRestaurants}
                restaurantError={restaurantError}
                onSelectRestaurant={setSelectedRestaurant}
              />
            </div>
			

			{/* Offers Section */}
			<section id="offers" className="info-section offers-section">
			  <span className="eyebrow">SPECIAL DEALS</span>
			  <h2>Offers You'll Love</h2>
			  <p>Enjoy delicious food and save more with Foodie.</p>

			  <div className="info-card">
			    <h3>Free Delivery!</h3>
			    <p>
			      Enjoy free delivery when your order subtotal is above ₹250.
			    </p>
			  </div>
			</section>

			{/* Services Section */}
			<section id="services" className="info-section services-section">
			  <span className="eyebrow">WHY CHOOSE US</span>
			  <h2>Our Services</h2>
			  <p>Everything you need for a convenient food ordering experience.</p>

			  <div className="service-cards">
			    <div className="info-card">
			      <h3>🍽️ Restaurant Choices</h3>
			      <p>Explore different restaurants and discover delicious food.</p>
			    </div>

			    <div className="info-card">
			      <h3>🛒 Easy Ordering</h3>
			      <p>Add your favourite dishes to your cart and place orders easily.</p>
			    </div>

			    <div className="info-card">
			      <h3>📦 Order History</h3>
			      <p>View your previously placed orders from your account.</p>
			    </div>
			  </div>
			</section>

          </>
        )}
      </main>

      <footer className="footer">
        <div className="brand">
          <span className="brand-icon">♨</span>
          FOODIE.
        </div>
        <p>Discover. Taste. Enjoy.</p>
        <span>© 2026 Foodie. All rights reserved.</span>
      </footer>
	  
	  {showCelebration && (
	    <div className="celebration-overlay">
	      <div className="confetti">
	        {Array.from({ length: 30 }).map((_, index) => (
	          <span key={index}></span>
	        ))}
	      </div>

	      <div className="celebration-card">
	        <div className="celebration-emoji">🎉</div>

	        <h2>Order Placed Successfully!</h2>

	        <p>Your delicious food is on its way!</p>

	        <h3>Order ID: #{celebrationOrderId}</h3>

	        <button onClick={() => setShowCelebration(false)}>
	          Continue to My Orders
	        </button>
	      </div>
	    </div>
	  )}

	  <CartDrawer
	    showCart={showCart}
	    setShowCart={setShowCart}
	    cart={cart}
	    totalItems={totalItems}
	    totalPrice={totalPrice}
	    updateQuantity={updateQuantity}
	    onCheckout={() => {
	      if (!currentUser) {
	        setShowCart(false);
	        setAuthPage("login");
	        alert("Please login before proceeding to checkout.");
	        return;
	      }

	      setShowCart(false);
	      setShowCheckout(true);
	    }}
	  />
      

    </div>
  );
}

export default App;
