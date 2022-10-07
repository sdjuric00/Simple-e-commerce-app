import React, {useState, useEffect} from "react";
import "./Modal.css";
import {BsBox} from 'react-icons/bs'
import {MdOutlineProductionQuantityLimits} from 'react-icons/md'
import {AiFillStar} from 'react-icons/ai'
import axios from "axios";

function ProductDetailsModal({ setOpenModal, selectedProduct }) {

  const [reviews, setReviews] = useState([])

  useEffect(() => {
    axios.get('http://localhost:9000/reviews/' + selectedProduct.productId + "/" + "rate",
    {headers: {'authorization': localStorage.getItem("token") }})
        .then(response =>{
            setReviews(response.data)
        }
        ).catch(error => {
            console.log("something went wrong...")
      });
  }  );

  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <div className="titleCloseBtn">
          <button
            onClick={() => {
              setOpenModal(false);
            }}
          >
            X
          </button>
        </div>
        <div className="modal-title">
          <h1>{selectedProduct.name}</h1>
        </div>
        <div className="modal-body">
          <p>Description: { (selectedProduct.description !== "") ? (selectedProduct.description)
              : ("There is no description for this product.")
            }</p>
          <p>Category: {selectedProduct.category}</p>
          <p className="badge text-bg-primary product-badge">Price: {selectedProduct.price} &euro;</p>
          <p className="badge text-bg-warning product-badge">Quantity: {selectedProduct.quantity} <BsBox /></p>
          <p className="badge text-bg-success product-badge">Sold: {selectedProduct.sold} <MdOutlineProductionQuantityLimits /></p>
        </div>
        <div className="reviews-product">
            <h3>Reviews:</h3>
            { (reviews.length > 0) ? (
              reviews.map((review, index) => {
              return (
                <div className="review-row">
                  {index + 1}.
                  <span className="review-span">
                    <p>{review.user.firstName + " " + review.user.lastName}</p>
                    <p>{review.rate} <AiFillStar /></p>
                    <p>{review.time.split("T")[0]}</p>
                  </span>
                </div>
              )
            })
            ) : (<div className="review-row">There is no reviews for this product.</div>)
            }
        </div>
        <div className="modal-footer">
          <button
            onClick={() => {
              setOpenModal(false);
            }}
            id="cancelBtn"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

export default ProductDetailsModal;