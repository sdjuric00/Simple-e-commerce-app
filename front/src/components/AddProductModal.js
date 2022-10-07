import React, { useState } from 'react'
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
import { useNavigate } from "react-router-dom";


const AddProductModal = ({setOpenModal}) => {

  const [name, setName] = useState('')
  const [category, setCategory] = useState('')
  const [price, setPrice] = useState('')
  const [quantity, setQuantity] = useState('')  
  const [description, setDescription] = useState('')
  let navigate = useNavigate();  
  
  const options = ["SWEATS", "DRINKS", "MEAT_PRODUCTS", "MILK_PRODUCTS"]

  function isInt(value) {
    var er = /^-?[0-9]+$/;
    return er.test(value);
   }

  function validateFormData() {
    if (name.length < 5) {
      toast.error("Product name is too short. Name must have at least 5 characters.",
      {position: "bottom-left", theme: "colored"});
      return false;
    } else if (!isInt(price) || parseInt(price) < 0) {
      toast.error("Price must be number greater than 0.",
      {position: "bottom-left", theme: "colored"});
      return false;
    } else if (!isInt(quantity) || parseInt(quantity) < 0) {
      toast.error("Quantity must be number greater than 0.",
      {position: "bottom-left", theme: "colored"});
      return false;
    }
    
    return true;
  }

  function onSave(event) {
    event.preventDefault();
    if (validateFormData()) {
      let obj = {name: name, category: category, price: parseFloat(price), quantity: parseInt(quantity), 
      description: (description.length === 0) ? "" : description, image: "default-product.jpg", sold: 0,
    }
      axios.post('http://localhost:9000/product', obj, {headers: {'authorization': localStorage.getItem("token") }}
      ).then((response) => {
        toast.success("Product addition successful!",
        {position: "bottom-left", theme: "colored"});
        setOpenModal(false)
        return navigate("/home");
      }).catch(error => {
          if (error.response.status === 400) 
            toast.error("Data from form is in wrong format!",
            {position: "bottom-left", theme: "colored"});
          else 
            toast.error(error.response.data,
            {position: "bottom-left", theme: "colored"});
      });   
    }
  }


  return (
    <div className="modalBackground">
      <div className="modalContainer">
        <ToastContainer />
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
          <h1>Product add</h1>
        </div>
        <div className="container">
            <div className="row">
                <div className="col-6">
                    <label className="form-label">Name:</label>
                    <input type="text" className="form-control"
                    value={name} onChange={event => setName(event.target.value)} />
                    <label className="form-label">Price:</label>
                    <input type="text" className="form-control" 
                    value={price} onChange={event => setPrice(event.target.value)}/>
                    <label className="form-label">Category:</label>
                    <select onChange={event => setCategory(event.target.value)}>
                        {
                            options.map((opt, index) => {
                                return(
                                    <option value={opt}>{opt}</option>
                                )
                            })
                        }
                    </select>
                </div>
                <div className="col-6">
                    <label className="form-label">Quantity:</label>
                    <input type="number" className="form-control" 
                    value={quantity} onChange={event => setQuantity(event.target.value)}/>
                    <label className="form-label">Description:</label>
                    <input type="text" className="form-control"
                    value={description} onChange={event => setDescription(event.target.value)}/>
                </div>
            </div>
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
          <button className="btn btn-success" onClick={onSave}>Save</button>
        </div>
      </div>
    </div>
  )
}

export default AddProductModal