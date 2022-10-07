import React from 'react'
import Product from './Product'


const Products = ({products, productDetails, deleteProduct, changeProductShow, addToCart}) => {
  return (
    <>
    <div>
      <div className="container">
        <div className="row">
          <div>
            {
              (localStorage.getItem("userRole") === "ADMIN") ? 
              (
                <button className="btn btn-success add-product-btn" onClick={() => changeProductShow()}>Add product</button>
              ) : (<></>)
            }
          </div>
          <div className="card-group">
          {(products.length > 0) ? (
            products.map((product, index) => {
              return (
                <div key={index} className="card-group">
                  <Product product={product} productDetails={productDetails} deleteProduct={deleteProduct} addToCart={addToCart}></Product>
                </div>
              )
            })
          ) : (<p id="search-no-products-result">"There are no products for this critirea."</p>)
          }
          </div>
          
        </div>
        
      </div>
    </div>
    </>
  )
}

export default Products
