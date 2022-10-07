import React, { useEffect, useState } from 'react'
import Products from '../components/Products'
import axios from 'axios'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ProductDetailsModal from '../components/ProductDetailsModal'
import AddProductModal from '../components/AddProductModal';
import Search from '../components/Search';

const Home = ({addToCart}) => {

  const [products, setProducts] = useState([])
  const [detailsProductModal, setDetailsProductModal] = useState(false)
  const [selectedProduct, setSelectedProduct] = useState({})
  const [addProductShow, setAddProductShow] = useState('')
  const [searchHappened, setSearchHappened] = useState(false)

  function onSearch(name, category, price, available) {
    axios.get('http://localhost:9000/product/' + ((name === "") ? "ALL" : name) + "/" 
    + category + "/" + ((available === "Only available") ? "true" : "false")
     + "/" + ((price === "ascending") ? "true" : "false"))
        .then(response =>{
            setSearchHappened(true)
            setProducts(response.data)
        }
        ).catch(error => {
            toast.error("An error occured. Cannot load the products!", {
            position: "bottom-left",
            theme: 'colored'})
      });
  }

  function changeShowAddProduct() {
    setAddProductShow(!addProductShow)
  }

  function deleteProduct(id) {
    axios.delete('http://localhost:9000/product/' + id, {headers: {'authorization': localStorage.getItem("token") }})
        .then(response =>{
            toast.success("Product with id " + response.data + " is successfully deleted.", {
              position: "bottom-left",
              theme: 'colored'})
        }
        ).catch(error => {
            toast.error("An error occured. Cannot load the products!", {
            position: "bottom-left",
            theme: 'colored'})
      });
  }
  
  function productDetailsModal(product) {
    setSelectedProduct(product)
    setDetailsProductModal(!detailsProductModal)
  }

  useEffect(() => {
    axios.get('http://localhost:9000/products-active')
        .then(response =>{
          if (!searchHappened)
            setProducts(response.data)
        }
        ).catch(error => {
            toast.error("An error occured. Cannot load the products!", {
            position: "bottom-left",
            theme: 'colored'})
      });
  }  );


  return (
    <div>
        {detailsProductModal && <ProductDetailsModal setOpenModal={setDetailsProductModal} selectedProduct={selectedProduct}/>}
        {addProductShow && <AddProductModal setOpenModal={setAddProductShow} />}
        <Search onSearch={onSearch}></Search>
        <Products products={products} productDetails={productDetailsModal} deleteProduct={deleteProduct} changeProductShow={changeShowAddProduct} addToCart={addToCart}></Products>
        <ToastContainer />
    </div>
  )
}

export default Home