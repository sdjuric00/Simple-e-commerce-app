import React from 'react'
import Order from '../components/Order';

const OrdersTable = ({orders}) => {
  return (
    <div className="orders-rows">
    {
        (orders.length > 0) ? (
        <table className="table table-striped">
            <thead>
                <th>Order id</th>
                <th>Date</th>
                <th>Total price</th>
            </thead>
            <tbody>
                {
                    orders.map((order, index) => {
                    return (
                        <Order key={index} order={order}></Order>
                    )
                    })    
                }
            </tbody>    
        </table>    
        ) : (
            <p id="search-no-products-result">There is no orders to show! Go shop and make a order :D</p> 
        )
    }                        
    </div>
  )
}

export default OrdersTable