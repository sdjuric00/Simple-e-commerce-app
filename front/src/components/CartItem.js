import React from 'react'

const CartItem = ({cartItem, onRemove, onAdd}) => {
  return (
    <div className="cart-item-row">
        <img alt="cart item" src={"http://localhost:9000/assets/images/" + cartItem.product.image} className="cart-item-image"></img>
        <p>{cartItem.product.name}</p>
        <button onClick={() => onAdd(cartItem.product)}>+</button>
        <p>{cartItem.quantity} x {cartItem.product.price} &euro;</p>
        <button onClick={() => onRemove(cartItem.product.productId)}>-</button>
        <p>{cartItem.quantity * cartItem.product.price} &euro;</p>
    </div>
  )
}

export default CartItem