import { useEffect, useState } from "react";
import "./App.css";
import mockProductData from "./mockData";

const API_BASE_URL = import.meta.env.VITE_API_URL || "/api";

function App() {
    const [products, setProducts] = useState([]);
    const [cart, setCart] = useState([]);
    const [customerId, setCustomerId] = useState(1);

    const [showLogin, setShowLogin] = useState(false);
    const [showRegister, setShowRegister] = useState(false);
    const [showCart, setShowCart] = useState(false);

    const [loginData, setLoginData] = useState({
        email: "",
        password: "",
    });

    const [registerData, setRegisterData] = useState({
        name: "",
        email: "",
        password: "",
        phone: "",
        gender: "Female",
        address: "",
        role: "CUSTOMER",
        city: "",
        state: "",
        pincode: "",
    });

    const [message, setMessage] = useState("");

    // Load products
    const loadProducts = async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/products`);

            if (!response.ok) {
                throw new Error("Unable to load products");
            }

            const data = await response.json();
            setProducts(data);
        } catch (error) {
            console.error(error);
            setMessage("Unable to load products");
        }
    };

    // Load customer cart
    const loadCart = async () => {
        try {
            const response = await fetch(
                `${API_BASE_URL}/cart/customer/${customerId}`
            );

            if (!response.ok) {
                return;
            }

            const data = await response.json();
            setCart(data);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadProducts();
        loadCart();
    }, [customerId]);

    // Add product to cart
    const addToCart = async (product) => {
        try {
            const response = await fetch(`${API_BASE_URL}/cart`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    customerId: customerId,
                    productId: product.id,
                    quantity: 1,
                }),
            });

            if (!response.ok) {
                throw new Error("Unable to add product to cart");
            }

            await loadCart();
            setMessage(`${product.productName} added to cart`);
        } catch (error) {
            console.error(error);
            setMessage("Unable to add product to cart");
        }
    };

    // Login
    const handleLogin = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(loginData),
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "Login failed");
            }

            if (data.id) {
                setCustomerId(data.id);
            }

            setShowLogin(false);
            setMessage(data.message || "Login successful");
            await loadCart();
        } catch (error) {
            console.error(error);
            setMessage(error.message || "Login failed");
        }
    };

    // Register
    const handleRegister = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch(`${API_BASE_URL}/auth/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(registerData),
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "Registration failed");
            }

            if (data.id) {
                setCustomerId(data.id);
            }

            setShowRegister(false);
            setMessage("Registration successful. You can now login.");

            setRegisterData({
                name: "",
                email: "",
                password: "",
                phone: "",
                gender: "Female",
                address: "",
                role: "CUSTOMER",
                city: "",
                state: "",
                pincode: "",
            });
        } catch (error) {
            console.error(error);
            setMessage(error.message || "Registration failed");
        }
    };

    // Place order
    const placeOrder = async () => {
        try {
            if (cart.length === 0) {
                setMessage("Your cart is empty");
                return;
            }

            const orderResponse = await fetch(`${API_BASE_URL}/orders`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    customerId: customerId,
                }),
            });

            if (!orderResponse.ok) {
                throw new Error("Unable to place order");
            }

            const orderData = await orderResponse.json();

            // Payment
            const paymentResponse = await fetch(`${API_BASE_URL}/payments`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    orderId: orderData.id,
                    paymentMethod: "UPI",
                }),
            });

            if (!paymentResponse.ok) {
                throw new Error("Payment failed");
            }

            // Clear backend cart
            await fetch(`${API_BASE_URL}/cart/clear/${customerId}`, {
                method: "DELETE",
            });

            setCart([]);
            setShowCart(false);
            setMessage("Order placed and payment successful!");
        } catch (error) {
            console.error(error);
            setMessage(error.message || "Unable to place order");
        }
    };

    return (
        <div className="app">

            {/* Header */}
            <header className="header">
                <h2>E-Commerce</h2>

                <nav>
                    <button onClick={() => setShowLogin(true)}>Login</button>
                    <button onClick={() => setShowRegister(true)}>Register</button>
                    <button onClick={() => setShowCart(true)}>
                        Cart ({cart.length})
                    </button>
                </nav>
            </header>

            {/* Hero Section */}
            <section className="hero">
                <p>WELCOME TO OUR STORE</p>

                <h1>
                    Shop Smart.
                    <br />
                    Shop Easy.
                </h1>

                <span>
          Discover quality products and enjoy a simple and convenient
          shopping experience.
        </span>

                <button
                    className="shop-button"
                    onClick={() =>
                        document
                            .getElementById("products")
                            ?.scrollIntoView({ behavior: "smooth" })
                    }
                >
                    Shop Now
                </button>
            </section>

            {/* Message */}
            {message && (
                <div className="message">
                    {message}
                </div>
            )}

            {/* Products */}
            <section id="products" className="products-section">
                <h2>Featured Products</h2>

                {products.length === 0 ? (
                    <p>No products available.</p>
                ) : (
                    <div className="product-grid">
                        {products.map((product) => {
                            const image =
                                mockProductData[product.productName]?.image;

                            return (
                                <div className="product-card" key={product.id}>

                                    <img
                                        src={image}
                                        alt={product.productName}
                                        className="product-image"
                                    />

                                    <h3>{product.productName}</h3>

                                    <h4>₹{product.price}</h4>

                                    <p>{product.brand}</p>

                                    <button
                                        className="add-cart-button"
                                        onClick={() => addToCart(product)}
                                    >
                                        Add to Cart
                                    </button>

                                </div>
                            );
                        })}
                    </div>
                )}
            </section>

            {/* Footer */}
            <footer>
                © 2026 E-Commerce. All Rights Reserved.
            </footer>

            {/* Login Modal */}
            {showLogin && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h2>Login</h2>

                        <form onSubmit={handleLogin}>
                            <input
                                type="email"
                                placeholder="Email"
                                value={loginData.email}
                                onChange={(e) =>
                                    setLoginData({
                                        ...loginData,
                                        email: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                type="password"
                                placeholder="Password"
                                value={loginData.password}
                                onChange={(e) =>
                                    setLoginData({
                                        ...loginData,
                                        password: e.target.value,
                                    })
                                }
                                required
                            />

                            <button type="submit">Login</button>
                            <button
                                type="button"
                                onClick={() => setShowLogin(false)}
                            >
                                Cancel
                            </button>
                        </form>
                    </div>
                </div>
            )}

            {/* Register Modal */}
            {showRegister && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h2>Register</h2>

                        <form onSubmit={handleRegister}>
                            <input
                                placeholder="Name"
                                value={registerData.name}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        name: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                type="email"
                                placeholder="Email"
                                value={registerData.email}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        email: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                type="password"
                                placeholder="Password"
                                value={registerData.password}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        password: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                placeholder="Phone"
                                value={registerData.phone}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        phone: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                placeholder="Address"
                                value={registerData.address}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        address: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                placeholder="City"
                                value={registerData.city}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        city: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                placeholder="State"
                                value={registerData.state}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        state: e.target.value,
                                    })
                                }
                                required
                            />

                            <input
                                placeholder="Pincode"
                                value={registerData.pincode}
                                onChange={(e) =>
                                    setRegisterData({
                                        ...registerData,
                                        pincode: e.target.value,
                                    })
                                }
                                required
                            />

                            <button type="submit">Register</button>

                            <button
                                type="button"
                                onClick={() => setShowRegister(false)}
                            >
                                Cancel
                            </button>
                        </form>
                    </div>
                </div>
            )}

            {/* Cart Modal */}
            {showCart && (
                <div className="modal-overlay">
                    <div className="modal cart-modal">

                        <h2>Your Cart</h2>

                        {cart.length === 0 ? (
                            <p>Your cart is empty.</p>
                        ) : (
                            <>
                                {cart.map((item) => (
                                    <div className="cart-item" key={item.id}>
                                        <strong>{item.productName}</strong>
                                        <span>
                      Quantity: {item.quantity}
                    </span>
                                        <span>
                      ₹{item.totalPrice}
                    </span>
                                    </div>
                                ))}

                                <button onClick={placeOrder}>
                                    Place Order & Pay
                                </button>
                            </>
                        )}

                        <button onClick={() => setShowCart(false)}>
                            Close
                        </button>

                    </div>
                </div>
            )}

        </div>
    );
}

export default App;