import React, {useEffect, useState} from 'react'
import axios from 'axios'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './Orders.css'
import SortOrders from '../components/SortOrders';
import OrdersTable from '../components/OrdersTable';

const Orders = () => {

    const [orders, setOrders] = useState([])
    const [sortByParameter, setSortByParameter] = useState('date')

    useEffect(() => {
        if (localStorage.getItem("userRole") === "REGULAR_USER"){
            axios.get('http://localhost:9000/orders/' + sortByParameter, 
                {headers: {'authorization': localStorage.getItem("token") }})
                .then(response => {
                    setOrders(response.data)
                }).catch(error => {
                    toast.error("An error occured. Cannot load the products!", {
                    position: "bottom-left",
                    theme: 'colored'})
                });
        } else if (localStorage.getItem("userRole") === "ADMIN") {
            axios.get('http://localhost:9000/all-orders/' + sortByParameter, 
                {headers: {'authorization': localStorage.getItem("token") }})
                .then(response => {
                    setOrders(response.data)
                }).catch(error => {
                    toast.error("An error occured. Cannot load the products!", {
                    position: "bottom-left",
                    theme: 'colored'})
                });
        }
    })


  return (
    <div className="container">
        <div className="row">
            <SortOrders setSortByParameter={setSortByParameter} sortByParameter={sortByParameter}></SortOrders>
            <OrdersTable orders={orders}></OrdersTable>
        </div>
        <ToastContainer></ToastContainer>
    </div>
  )
}

export default Orders