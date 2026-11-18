
function CartDrawer({
  showCart,
  setShowCart,
  cart,
  totalItems,
  totalPrice,
  updateQuantity,
  onCheckout,
}) {
	
	const deliveryFee = totalPrice > 250 ? 0 : 30;
	const grandTotal = totalPrice + deliveryFee;
	
	
	
  if (!showCart) return null;

  return (
    <div
      className="cart-overlay"
      onClick={() => setShowCart(false)}
    >
      <div
        className="cart-drawer"
        onClick={(event) => event.stopPropagation()}
      >
        <div className="cart-header">
          <div>
            <span className="eyebrow">YOUR SELECTION</span>
            <h2>My Cart 🛒</h2>
          </div>

          <button
            className="close-cart"
            onClick={() => setShowCart(false)}
          >
            ✕
          </button>
        </div>

        {cart.length === 0 ? (
          <div className="empty-cart">
            <span>🛍️</span>
            <h3>Your cart is waiting!</h3>
            <p>
              Choose something delicious to get started.
            </p>
          </div>
        ) : (
          <>
            <div className="cart-list">
              {cart.map((item) => (
                <div className="cart-item" key={item.id}>
                  <img src={item.image} alt={item.name} />

                  <div className="cart-item-info">
                    <h3>{item.name}</h3>
                    <p>₹{item.price}</p>

                    <div className="quantity-control">
                      <button
                        onClick={() =>
                          updateQuantity(item.id, -1)
                        }
                      >
                        −
                      </button>

                      <span>{item.quantity}</span>

                      <button
                        onClick={() =>
                          updateQuantity(item.id, 1)
                        }
                      >
                        +
                      </button>
                    </div>
                  </div>

                  <strong>
                    ₹{item.price * item.quantity}
                  </strong>
                </div>
              ))}
            </div>


			<div className="cart-summary">
			  <h3 className="summary-title">Order Summary</h3>

			  <div>
			    <span>Items ({totalItems})</span>
			    <strong>₹{totalPrice}</strong>
			  </div>

			  <div>
			    <span>Delivery Fee</span>
			    <strong className={deliveryFee === 0 ? "free-delivery" : ""}>
			      {deliveryFee === 0 ? "FREE" : `₹${deliveryFee}`}
			    </strong>
			  </div>

			  <div className="summary-total">
			    <span>Total Payable</span>
			    <strong>₹{grandTotal}</strong>
			  </div>

			  <p className="delivery-note">
			    {deliveryFee === 0
			      ? "🎉 Congratulations! You unlocked free delivery."
			      : `Add ₹${251 - totalPrice} more to unlock free delivery!`}
			  </p>

			  <button
			    className="primary-button checkout-button"
			    onClick={onCheckout}
			    
			  >
			    Proceed to Checkout →
			  </button>
			</div>

          </>
        )}
      </div>
    </div>
  );
}

export default CartDrawer;
