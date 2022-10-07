import React, {useEffect, useState} from 'react'
import CartItem from '../components/CartItem'
import './Cart.css'
import axios from 'axios'
import { useNavigate } from "react-router-dom";

const Cart = ({cartItems, onRemove, onAdd, showMessage, cleanCart}) => {

    const [totalPrice, setTotalPrice] = useState('')
    let navigate = useNavigate();
  
    function makeOrder(event) {
        event.preventDefault()
        let cartDTO = {email: localStorage.getItem("email"), products: []}
        for (let i = 0; i < cartItems.length; i++)
            cartDTO.products.push({productId: cartItems[i].product.productId, quantity: parseInt(cartItems[i].quantity), price:  parseFloat(cartItems[i].product.price)})

        axios.post('http://localhost:9000/order', cartDTO,
            {headers: {'authorization': localStorage.getItem("token") }} 
        ).then((response) => {
            cleanCart([])
            showMessage(true, "You successfully made order! Order id is " + response.data)
            return navigate("/home");
        }).catch(error => {
            if (error.response.status === 400) 
                showMessage(false, error.response.data)
            else 
                showMessage(false, error.response.data)
        });   
        
    }

    function getTotalPrice() {
        let totalP = 0
        for (let i = 0; i < cartItems.length; i++) {
            totalP += cartItems[i].quantity * cartItems[i].product.price
        }
        return totalP
    }
  

    useEffect(() => {
        setTotalPrice(() => getTotalPrice())
    }, [cartItems]  );

  return (
    <div className="container">
        <div className="row">
            <div className="cart-item-rows">
                {
                    (cartItems.length > 0) ? (
                        cartItems.map((item, index) => {
                            return (
                                <CartItem key={index} cartItem={item} onRemove={onRemove} onAdd={onAdd}></CartItem>
                            )
                        })
                    ) : (
                        <p id="search-no-products-result">There is no selected products in cart. Go shop!</p> 
                    )
                }
            </div>
            {
                (cartItems.length > 0) ? (
                    <div className="cart-items-footer">
                        <h3>Total: {totalPrice}</h3>
                        <button className="btn btn-success order-btn" onClick={makeOrder}>Order</button>
                    </div>
                ) : (<></>)
            }
        </div>
    </div>
  )
}

export default Cart