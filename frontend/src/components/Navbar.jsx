
import { useState } from "react";

function Navbar({
  totalItems,
  onOpenCart,
  onShowLogin,
  onShowRegister,
  currentUser,
  onLogout,
  onShowOrders,
  onGoHome,
}) {
  const [showProfile, setShowProfile] = useState(false);
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);

  const handleHome = (e) => {
    e.preventDefault();
    setShowProfile(false);

    if (onGoHome) {
      onGoHome();
    }
  };

  const handleSectionNavigation = (e, sectionId) => {
    e.preventDefault();
    setShowProfile(false);

    // If we are already on the homepage, scroll directly.
    const section = document.getElementById(sectionId);

    if (section) {
      section.scrollIntoView({
        behavior: "smooth",
        block: "start",
      });
    } else {
      // Return to homepage first if the section is currently hidden.
      if (onGoHome) {
        onGoHome();

        // Wait for the homepage to render before scrolling.
        setTimeout(() => {
          document.getElementById(sectionId)?.scrollIntoView({
            behavior: "smooth",
            block: "start",
          });
        }, 300);
      }
    }
  };

  return (
    <nav className="navbar" id="home">

      {/* BRAND */}
      <a href="#home" className="brand" onClick={handleHome}>
        <span className="brand-icon">♨</span>
        <span>
          Foodie<span className="brand-dot">.</span>
        </span>
      </a>

      {/* NAVIGATION LINKS */}
      <div className="nav-links">
        <a href="#home" onClick={handleHome}>
          Home
        </a>

        <a
          href="#restaurants"
          onClick={(e) => handleSectionNavigation(e, "restaurants")}
        >
          Restaurants
        </a>

        <a
          href="#offers"
          onClick={(e) => handleSectionNavigation(e, "offers")}
        >
          Offers
        </a>

        <a
          href="#services"
          onClick={(e) => handleSectionNavigation(e, "services")}
        >
          Service
        </a>
      </div>

      {/* NAVIGATION ACTIONS */}
      <div className="nav-actions">

        <button
          className="nav-cart"
          onClick={onOpenCart}
          aria-label="Open cart"
        >
          🛒
          <span className="cart-count">{totalItems}</span>
        </button>

        {currentUser ? (
          <div className="profile-container">

            <button
              className="profile-button"
              onClick={() => setShowProfile(!showProfile)}
            >
              <span className="profile-icon">👤</span>
              <span>{currentUser.name}</span>
              <span>⌄</span>
            </button>

            {showProfile && (
              <div className="profile-dropdown">

                <p>{currentUser.email}</p>

                <button
                  onClick={() => {
                    setShowProfile(false);
                    onShowOrders();
                  }}
                >
                  <span>📦</span> My Orders
                </button>

                <button
                  onClick={() => {
                    setShowProfile(false);
                    setShowLogoutConfirm(true);
                  }}
                >
                  <span>↪</span> Logout
                </button>

              </div>
            )}

          </div>
        ) : (
          <>
            <button
              className="register-button"
              onClick={onShowLogin}
            >
              Login
            </button>

            <button
              className="register-button"
              onClick={onShowRegister}
            >
              Register Now <span>→</span>
            </button>
          </>
        )}

      </div>

      {/* LOGOUT CONFIRMATION */}
      {showLogoutConfirm && (
        <div className="logout-overlay">

          <div className="logout-modal">

            <div className="logout-icon">👋</div>

            <h2>Leaving so soon?</h2>

            <p>
              Are you sure you want to log out of your Foodie account?
            </p>

            <div className="logout-actions">

              <button
                className="logout-cancel"
                onClick={() => setShowLogoutConfirm(false)}
              >
                Stay Logged In
              </button>

              <button
                className="logout-confirm"
                onClick={() => {
                  setShowLogoutConfirm(false);
                  onLogout();
                }}
              >
                Yes, Logout
              </button>

            </div>

          </div>

        </div>
      )}

    </nav>
  );
}

export default Navbar;
