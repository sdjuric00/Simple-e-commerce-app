import React from 'react'
import {BsBox} from 'react-icons/bs'

const Product = ({product, productDetails, deleteProduct, addToCart}) => {
  return (
    <div className="card product-card">
        <img src={"http://localhost:9000/assets/images/" + product.image} className="card-img-top" alt="Product" />
        <div className="card-body">
            <p className="card-text badge text-bg-primary product-card-price">{product.price}&euro;</p>
            <p className={(product.quantity > 0) ? "card-text badge text-bg-warning product-card-quantity":
            "card-text badge text-bg-danger product-card-quantity" }>{product.quantity} <BsBox /></p>
            <h5 className="card-title">{product.name}</h5>
            <p className="card-text">
            {
                (product.description.length > 24) ? (product.description.substring(0, 24) + "...") : (product.description)
            }           
            </p>
            <div className="card-buttons">
                {
                (localStorage.getItem("userRole") === "REGULAR_USER") ? (
                    <>
                        <button className="btn btn-success card-btn" onClick={() => addToCart(product)}>Add</button>
                        <button className="btn btn-success card-btn" onClick={() => productDetails(product)}>Details</button>
                    </> 
                ) : (localStorage.getItem("userRole") === "ADMIN") ? (
                    <>
                        <button className="btn btn-danger card-btn" onClick={() => deleteProduct(product.productId)}>Delete</button>
                        <button className="btn btn-success card-btn" onClick={() => productDetails(product)}>Details</button>
                    </>
                ) : (
                    <>
                        <button className="btn btn-success card-btn" onClick={() => productDetails(product)}>Details</button>
                    </>
                )
                }
            </div>
        </div>
    </div>  
  )
}

export default Product