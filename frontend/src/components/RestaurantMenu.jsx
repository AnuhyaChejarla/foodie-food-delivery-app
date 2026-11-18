
function RestaurantMenu({
  restaurant,
  dishes,
  onBack,
  onAddToCart,
}) {
  const categories = [
    ...new Set(
      dishes.map((dish) => dish.category || "Other")
    ),
  ];

  return (
    <section className="restaurant-menu">
      <button className="back-button" onClick={onBack}>
        ← Back to Restaurants
      </button>

      <div className="restaurant-menu-heading">
        <span className="eyebrow">RESTAURANT MENU</span>
        <h2>{restaurant.name}</h2>
        <p>
          {restaurant.cusineType} · ⭐ {restaurant.rating} ·
          {" "}{restaurant.deliveryTime} mins
        </p>
        <p>{restaurant.address}</p>
      </div>

      {categories.length === 0 ? (
        <p>No menu items available for this restaurant.</p>
      ) : (
        categories.map((category) => (
          <div className="menu-category" key={category}>
            <h2>{category}</h2>

            <div className="menu-items-grid">
              {dishes
                .filter(
                  (dish) =>
                    (dish.category || "Other") === category
                )
                .map((dish) => (
                  <div className="menu-item-card" key={dish.id}>
                    <img src={dish.image} alt={dish.name} />

                    <div className="menu-item-info">
                      <h3>{dish.name}</h3>
                      <p>{dish.description}</p>
                      <strong>₹{dish.price}</strong>

                      <button
                        onClick={() => onAddToCart(dish)}
                      >
                        + Add
                      </button>
                    </div>
                  </div>
                ))}
            </div>
          </div>
        ))
      )}
    </section>
  );
}

export default RestaurantMenu;
