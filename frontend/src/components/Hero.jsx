
import { useState } from "react";

function Hero({ onExploreRestaurants }) {
  const [location, setLocation] = useState("");

  const handleSearch = (event) => {
    event.preventDefault();
    onExploreRestaurants();
  };

  return (
    <>
      <section className="hero">
        <div className="hero-content">
          <div className="trust-badge">
            <span>♥</span> People trust us
          </div>

          <h1>
            Your Favourite
            <br />
            Food,
            <strong>Delivered Fast.</strong>
          </h1>

          <p>
            Find restaurants near you and enjoy
            convenient delivery.
          </p>

          <form className="location-search" onSubmit={handleSearch}>
            <span className="location-icon">📍</span>

            <input
              type="text"
              placeholder="Enter your location"
              value={location}
              onChange={(event) => setLocation(event.target.value)}
            />

            <button type="submit" aria-label="Find restaurants">
              →
            </button>
          </form>

          <div className="hero-small-text">
            Delivering happiness, right to your doorstep.
          </div>
        </div>

        <div className="hero-visual">
          <div className="orange-circle"></div>

          <img
            className="delivery-person"
            src="/foodie-delivery-person.png"
            alt="Foodie delivery person"
          />

          <div className="floating-label quality-label">
            <span>🛡️</span> Quality Service
          </div>

          <div className="floating-label fast-label">
            <span>⚡</span> Fast Delivery
          </div>

          <div className="floating-label time-label">
            <span>🛵</span> On Time
          </div>

          <span className="hero-spark spark-one">✦</span>
          <span className="hero-spark spark-two">✧</span>
        </div>
      </section>

      <section className="why-foodie" id="why-foodie">
        <div className="why-heading">
          <span className="orange-line"></span>
          <h2>
            Why choose <strong>Foodie?</strong>
          </h2>
          <p>
            Because good service
            <br />
            should always be easy!
          </p>
          <button onClick={onExploreRestaurants}>
            Learn More →
          </button>
        </div>

        <div className="feature-card">
          <div className="feature-icon pink-icon">♨</div>
          <div>
            <span>01</span>
            <h3>Quality Service</h3>
            <p>Service you can trust.</p>
          </div>
        </div>

        <div className="feature-card">
          <div className="feature-icon purple-icon">⚡</div>
          <div>
            <span>02</span>
            <h3>Easy Ordering</h3>
            <p>Just a few clicks.</p>
          </div>
        </div>

        <div className="feature-card">
          <div className="feature-icon pink-icon">🛵</div>
          <div>
            <span>03</span>
            <h3>Fast Delivery</h3>
            <p>Right to your door.</p>
          </div>
        </div>
      </section>
    </>
  );
}

export default Hero;
