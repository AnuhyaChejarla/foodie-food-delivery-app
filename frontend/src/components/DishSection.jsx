
function DishSection({
  dishes,
  loadingDishes,
  dishError,
  currentDish,
  setSelectedDish,
  setActiveTab,
}) {
  return (
    <section className="dish-section" id="dishes">
      <div className="dish-heading">
        <div>
          <span className="eyebrow">
            DISCOVER OUR MENU
          </span>
          <h2>Explore delicious dishes</h2>
        </div>

        <span className="dish-count">
          {dishes.length} Available Dishes
        </span>
      </div>

      <div className="dish-carousel">
        {loadingDishes ? (
          <p>Loading dishes...</p>
        ) : dishError ? (
          <p>{dishError}</p>
        ) : dishes.length === 0 ? (
          <p>No available dishes found.</p>
        ) : (
          dishes.map((dish) => (
            <button
              key={dish.id}
              className={
                currentDish?.id === dish.id
                  ? "dish-card selected"
                  : "dish-card"
              }
              onClick={() => {
                setSelectedDish(dish);
                setActiveTab("Overview");
              }}
            >
              <img
                src={dish.image}
                alt={dish.name}
              />

              <h3>{dish.name}</h3>
              <p>{dish.description}</p>

              <span className="dish-card-price">
                ₹{dish.price} · ⭐ {dish.rating}
              </span>
            </button>
          ))
        )}
      </div>
    </section>
  );
}

export default DishSection;
