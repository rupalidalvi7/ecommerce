import { useEffect, useState } from 'react'
import './App.css'
import mockProductData from './mockData'

function App() {
  const [products, setProducts] = useState([])
  const [cart, setCart] = useState([])
  const [showCart, setShowCart] = useState(false)

  const [showLogin, setShowLogin] = useState(false)
  const [showRegister, setShowRegister] = useState(false)

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const [registerData, setRegisterData] = useState({
    name: '',
    email: '',
    password: '',
    phone: '',
    gender: '',
    address: '',
    city: '',
    state: '',
    pincode: '',
  })

  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  // Get products from backend
  useEffect(() => {
    fetch('/api/products')
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to fetch products')
          }

          return response.json()
        })
        .then((data) => {
          setProducts(data)
          setLoading(false)
        })
        .catch((error) => {
          console.error(error)
          setError('Unable to load products')
          setLoading(false)
        })
  }, [])

  // Get customer's cart
  const fetchCart = async () => {
    try {
      const response = await fetch('/api/cart/customer/1')

      if (!response.ok) {
        throw new Error('Failed to fetch cart')
      }

      const data = await response.json()
      setCart(data)
    } catch (error) {
      console.error(error)
    }
  }

  // Add product to cart
  const handleAddToCart = async (productId) => {
    try {
      const response = await fetch('/api/cart', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          customerId: 1,
          productId: productId,
          quantity: 1,
        }),
      })

      if (!response.ok) {
        throw new Error('Failed to add product to cart')
      }

      const data = await response.json()

      alert(`${data.productName} added to cart!`)

      await fetchCart()
    } catch (error) {
      console.error(error)
      alert('Unable to add product to cart')
    }
  }

  // Update cart quantity
  const handleUpdateQuantity = async (cartItem, newQuantity) => {
    if (newQuantity < 1) {
      return
    }

    try {
      const response = await fetch(`/api/cart/${cartItem.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          customerId: 1,
          productId: cartItem.productId,
          quantity: newQuantity,
        }),
      })

      if (!response.ok) {
        throw new Error('Failed to update cart')
      }

      await fetchCart()
    } catch (error) {
      console.error(error)
      alert('Unable to update quantity')
    }
  }

  // Remove item from cart
  const handleRemoveItem = async (cartId) => {
    try {
      const response = await fetch(`/api/cart/${cartId}`, {
        method: 'DELETE',
      })

      if (!response.ok) {
        throw new Error('Failed to remove item')
      }

      await fetchCart()
    } catch (error) {
      console.error(error)
      alert('Unable to remove item')
    }
  }

  // Clear cart
  const handleClearCart = async () => {
    try {
      const response = await fetch('/api/cart/clear/1', {
        method: 'DELETE',
      })

      if (!response.ok) {
        throw new Error('Failed to clear cart')
      }

      setCart([])
    } catch (error) {
      console.error(error)
      alert('Unable to clear cart')
    }
  }

  // Place order and make payment
  const handlePlaceOrder = async () => {
    if (cart.length === 0) {
      alert('Your cart is empty.')
      return
    }

    try {
      const response = await fetch('/api/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          customerId: 1,
        }),
      })

      if (!response.ok) {
        throw new Error('Failed to place order')
      }

      const data = await response.json()

      const paymentResponse = await fetch('/api/payments', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          orderId: data.id,
          paymentMethod: 'UPI',
        }),
      })

      if (!paymentResponse.ok) {
        throw new Error('Payment failed')
      }

      const payment = await paymentResponse.json()

      alert(
          `Order placed successfully!\n\n` +
          `Order ID: ${data.id}\n` +
          `Total: ₹${data.totalAmount}\n` +
          `Payment Method: ${payment.paymentMethod}\n` +
          `Payment Status: ${payment.paymentStatus}`
      )

      const clearResponse = await fetch('/api/cart/clear/1', {
        method: 'DELETE',
      })

      if (!clearResponse.ok) {
        throw new Error(
            'Order and payment successful, but cart could not be cleared'
        )
      }

      setCart([])
    } catch (error) {
      console.error(error)
      alert(error.message)
    }
  }

  // Login
  const handleLogin = async (event) => {
    event.preventDefault()

    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: email,
          password: password,
        }),
      })

      if (!response.ok) {
        throw new Error('Invalid email or password')
      }

      const data = await response.json()

      alert(`Welcome ${data.name}!`)

      setEmail('')
      setPassword('')
      setShowLogin(false)
    } catch (error) {
      console.error(error)
      alert(error.message)
    }
  }

  // Register
  const handleRegister = async (event) => {
    event.preventDefault()

    try {
      const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: registerData.name,
          email: registerData.email,
          password: registerData.password,
          phone: registerData.phone,
          gender: registerData.gender,
          address: registerData.address,
          role: 'CUSTOMER',
          city: registerData.city,
          state: registerData.state,
          pincode: registerData.pincode,
        }),
      })

      if (!response.ok) {
        throw new Error('Registration failed')
      }

      const data = await response.json()

      alert(data.message || 'Registration successful!')

      setRegisterData({
        name: '',
        email: '',
        password: '',
        phone: '',
        gender: '',
        address: '',
        city: '',
        state: '',
        pincode: '',
      })

      setShowRegister(false)
    } catch (error) {
      console.error(error)
      alert(error.message)
    }
  }

  // Open cart
  const handleCartClick = () => {
    setShowCart(true)
    fetchCart()
  }

  // Total quantity
  const cartCount = cart.reduce(
      (total, item) => total + item.quantity,
      0
  )

  // Total price
  const cartTotal = cart.reduce(
      (total, item) => total + Number(item.totalPrice),
      0
  )

  return (
      <div className="app">

        {/* Navbar */}
        <nav className="navbar">
          <h2 className="logo">E-Commerce</h2>

          <div className="nav-links">
            <a href="/">Home</a>

            <a href="#products">Products</a>

            <button
                className="nav-cart"
                onClick={handleCartClick}
            >
              Cart ({cartCount})
            </button>

            <button
                className="login-button"
                onClick={() => {
                  setShowLogin(true)
                  setShowRegister(false)
                  setShowCart(false)
                }}
            >
              Login
            </button>

            <button
                className="register-button"
                onClick={() => {
                  setShowRegister(true)
                  setShowLogin(false)
                  setShowCart(false)
                }}
            >
              Register
            </button>
          </div>
        </nav>

        {/* Login */}
        {showLogin && (
            <section className="login-section">
              <div className="login-box">

                <h2>Login</h2>

                <form onSubmit={handleLogin}>

                  <input
                      type="email"
                      placeholder="Email"
                      value={email}
                      onChange={(event) => setEmail(event.target.value)}
                      required
                  />

                  <input
                      type="password"
                      placeholder="Password"
                      value={password}
                      onChange={(event) => setPassword(event.target.value)}
                      required
                  />

                  <button type="submit">
                    Login
                  </button>

                </form>

                <button
                    className="close-login-button"
                    onClick={() => setShowLogin(false)}
                >
                  Cancel
                </button>

              </div>
            </section>
        )}

        {/* Register */}
        {showRegister && (
            <section className="register-section">
              <div className="register-box">

                <h2>Register</h2>

                <form onSubmit={handleRegister}>

                  <input
                      type="text"
                      placeholder="Name"
                      value={registerData.name}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            name: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="email"
                      placeholder="Email"
                      value={registerData.email}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            email: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="password"
                      placeholder="Password"
                      value={registerData.password}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            password: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="tel"
                      placeholder="Phone"
                      value={registerData.phone}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            phone: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="text"
                      placeholder="Gender"
                      value={registerData.gender}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            gender: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="text"
                      placeholder="Address"
                      value={registerData.address}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            address: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="text"
                      placeholder="City"
                      value={registerData.city}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            city: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="text"
                      placeholder="State"
                      value={registerData.state}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            state: event.target.value,
                          })
                      }
                      required
                  />

                  <input
                      type="text"
                      placeholder="Pincode"
                      value={registerData.pincode}
                      onChange={(event) =>
                          setRegisterData({
                            ...registerData,
                            pincode: event.target.value,
                          })
                      }
                      required
                  />

                  <button type="submit">
                    Register
                  </button>

                </form>

                <button
                    className="close-register-button"
                    onClick={() => setShowRegister(false)}
                >
                  Cancel
                </button>

              </div>
            </section>
        )}

        {/* Home and Products */}
        {!showCart && !showLogin && !showRegister && (
            <>
              <section className="hero-section">
                <div className="hero-content">

                  <p className="welcome">
                    WELCOME TO OUR STORE
                  </p>

                  <h1>
                    Shop Smart.
                    <br />
                    Shop Easy.
                  </h1>

                  <p className="hero-text">
                    Discover quality products and enjoy a simple
                    and convenient shopping experience.
                  </p>

                  <button
                      className="shop-button"
                      onClick={() =>
                          document
                              .getElementById('products')
                              ?.scrollIntoView({ behavior: 'smooth' })
                      }
                  >
                    Shop Now
                  </button>

                </div>
              </section>

              {/* Products */}
              <section
                  className="products-section"
                  id="products"
              >
                <h2>Featured Products</h2>

                {loading && <p>Loading products...</p>}

                {error && <p>{error}</p>}

                {!loading && !error && (
                    <div className="product-grid">

                      {products.map((product) => (
                          <div
                              className="product-card"
                              key={product.id}
                          >

                            <div className="product-image">
                              <img
                                  src={mockProductData[product.id]?.image}
                                  alt={product.productName}
                              />
                            </div>

                            <h3>{product.productName}</h3>

                            <p>₹{product.price}</p>

                            <small>{product.brand}</small>

                            <button
                                onClick={() =>
                                    handleAddToCart(product.id)
                                }
                            >
                              Add to Cart
                            </button>

                          </div>
                      ))}

                    </div>
                )}
              </section>
            </>
        )}

        {/* Cart */}
        {showCart && !showLogin && !showRegister && (
            <section className="cart-section">

              <button
                  className="back-button"
                  onClick={() => setShowCart(false)}
              >
                ← Back to Products
              </button>

              <h2>My Cart</h2>

              {cart.length === 0 ? (
                  <p>Your cart is empty.</p>
              ) : (
                  <>
                    <div className="cart-list">

                      {cart.map((item) => (
                          <div
                              className="cart-item"
                              key={item.id}
                          >

                            <div>
                              <h3>{item.productName}</h3>

                              <p>
                                Price: ₹{item.price}
                              </p>

                              <div className="quantity-controls">

                                <button
                                    onClick={() =>
                                        handleUpdateQuantity(
                                            item,
                                            item.quantity - 1
                                        )
                                    }
                                >
                                  −
                                </button>

                                <span>
                          {item.quantity}
                        </span>

                                <button
                                    onClick={() =>
                                        handleUpdateQuantity(
                                            item,
                                            item.quantity + 1
                                        )
                                    }
                                >
                                  +
                                </button>

                              </div>

                              <button
                                  className="remove-button"
                                  onClick={() =>
                                      handleRemoveItem(item.id)
                                  }
                              >
                                Remove
                              </button>

                            </div>

                            <strong>
                              ₹{item.totalPrice}
                            </strong>

                          </div>
                      ))}

                    </div>

                    <div className="cart-total">

                      <h3>
                        Total: ₹{cartTotal}
                      </h3>

                      <button
                          className="place-order-button"
                          onClick={handlePlaceOrder}
                      >
                        Place Order
                      </button>

                      <button
                          className="clear-cart-button"
                          onClick={handleClearCart}
                      >
                        Clear Cart
                      </button>

                    </div>
                  </>
              )}

            </section>
        )}

        {/* Footer */}
        <footer>
          <p>
            © 2026 E-Commerce. All Rights Reserved.
          </p>
        </footer>

      </div>
  )
}

export default App