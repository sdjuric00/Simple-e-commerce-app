import './App.css';
import Navbar from './components/navbar/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './views/Login'
import Registration from './views/Registration'
import ActivateAcc from './views/ActivateAcc';
import Home from './views/Home';
import Cart from './views/Cart';
import Orders from './views/Orders';
import Profile from './views/Profile';
import 'bootstrap/dist/css/bootstrap.css';
import { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';


function App() {

  const [cartItems, setCartItems] = useState([])

  function addToCart(product) {
    const exist = cartItems.find((x) => x.product.productId === product.productId);
    if (exist) {
      setCartItems(
        cartItems.map((x) =>
          x.product.productId === product.productId ? { product: product, quantity: ((product.quantity >= exist.quantity + 1) ? exist.quantity + 1 : product.quantity)} : x
        )
      );
      showMessage(true, "Product is added successfully.")
    } else {
      if (product.quantity > 0) {
        setCartItems([...cartItems, { product: product, quantity: 1}]);
        showMessage(true, "Product is added successfully.")
      } else showMessage(false, "Product is out of stock.")
    }
  }

  function onRemove(productId) {
    const exist = cartItems.find((x) => x.product.productId === productId);
    if (exist.quantity === 1) {
      setCartItems(cartItems.filter((x) => x.product.productId !== productId));
    } else {
      setCartItems(
        cartItems.map((x) =>
          x.product.productId === productId ? { ...exist, quantity: exist.quantity - 1} : x
        )
      );
    }
  }

  function showMessage(succ, message) {
    if (succ) {
        toast.success(message, {
          position: "bottom-left",theme: 'colored'});
    } else {
      toast.error(message,
        {position: "bottom-left", theme: "colored"});
    }
  }

  function cleanCart() {
    setCartItems([])
  }

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path='/' exact element={<Home></Home>} />
        <Route path='/orders' exact element={<Orders></Orders>} />
        <Route path='/profile' exact element={<Profile></Profile>} />
        <Route path='/home' exact element={<Home addToCart={addToCart}></Home>} />
        <Route path='/cart' exact element={<Cart cartItems={cartItems} onRemove={onRemove} onAdd={addToCart} showMessage={showMessage} cleanCart={cleanCart}></Cart>} />
        <Route path='/activate-account' exact element={<ActivateAcc></ActivateAcc>} />
        <Route path='/login' exact element={<Login></Login>} />
        <Route path='/registration' element={<Registration></Registration>} />
      </Routes>
      <ToastContainer></ToastContainer>
    </Router>
  );
}

export default App;
