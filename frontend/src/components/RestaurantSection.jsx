import { useState } from "react";

function RestaurantSection({
  restaurants,
  loadingRestaurants,
  restaurantError,
  onSelectRestaurant,
}) {
  const [searchTerm, setSearchTerm] = useState("");

  // Filter restaurants based on search
  const filteredRestaurants = restaurants.filter((restaurant) => {
    const search = searchTerm.toLowerCase().trim();

    return (
      restaurant.name?.toLowerCase().includes(search) ||
      restaurant.cusineType?.toLowerCase().includes(search) ||
      restaurant.address?.toLowerCase().includes(search)
    );
  });

  return (
    <section className="restaurant-section" id="restaurants">

      {/* Search heading */}
      <div className="restaurant-search-heading">
        <span className="eyebrow">FIND YOUR FAVOURITE</span>

        <h2>Where would you like to eat?</h2>

        <p>Discover restaurants and explore delicious food.</p>
      </div>

      {/* Restaurant Search Bar */}
      <div className="restaurant-search-box">

        <span className="search-icon">⌕</span>

        <input
          type="text"
          placeholder="Search restaurants, cuisines or locations..."
          value={searchTerm}
          onChange={(event) => setSearchTerm(event.target.value)}
        />

        {searchTerm && (
          <button
            className="clear-search"
            onClick={() => setSearchTerm("")}
            aria-label="Clear search"
          >
            ×
          </button>
        )}

      </div>

      {/* Restaurant count */}
      <div className="dish-heading">
        <div>
          <span className="eyebrow">OUR RESTAURANTS</span>
          <h2>Discover amazing restaurants</h2>
        </div>

        <span>
          {filteredRestaurants.length} Restaurants
        </span>
      </div>

      {/* Loading */}
      {loadingRestaurants && <p>Loading restaurants...</p>}

      {/* Error */}
      {restaurantError && <p>{restaurantError}</p>}

      {/* No restaurants */}
      {!loadingRestaurants &&
        !restaurantError &&
        restaurants.length === 0 && (
          <p>No restaurants available right now.</p>
        )}

      {/* No search results */}
      {!loadingRestaurants &&
        !restaurantError &&
        restaurants.length > 0 &&
        filteredRestaurants.length === 0 && (
          <div className="no-search-results">
            <span>🔍</span>
            <h3>No restaurants found</h3>
            <p>Try searching with another restaurant name or cuisine.</p>
          </div>
        )}

      {/* Restaurant Cards */}
      {!loadingRestaurants &&
        !restaurantError &&
        filteredRestaurants.length > 0 && (

          <div className="restaurant-grid">

            {filteredRestaurants.map((restaurant) => (

              <div
                className="restaurant-card"
                key={restaurant.restaurantId}
                role="button"
                tabIndex={0}
                onClick={() => onSelectRestaurant(restaurant)}
                onKeyDown={(event) => {
                  if (event.key === "Enter" || event.key === " ") {
                    event.preventDefault();
                    onSelectRestaurant(restaurant);
                  }
                }}
              >

                <img
                  src={
                    restaurant.imagePath
                      ? `http://localhost:8081${restaurant.imagePath}`
                      : "https://placehold.co/400x250?text=Restaurant"
                  }
                  alt={restaurant.name}
                />

                <div className="restaurant-info">

                  <h3>{restaurant.name}</h3>

                  <p>{restaurant.cusineType}</p>

                  <p>
                    ⭐ {restaurant.rating} ·{" "}
                    {restaurant.deliveryTime} mins
                  </p>

                  <p>{restaurant.address}</p>

                  <span>View Menu →</span>

                </div>

              </div>

            ))}

          </div>
        )}

    </section>
  );
}

export default RestaurantSection;