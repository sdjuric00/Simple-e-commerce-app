import React from 'react'

const Order = ({order}) => {
  return (
    <tr>
        <td>{order.orderId}</td>
        <td>{order.time.split("T")[0]}</td>
        <td>{order.total}</td>
        <table class="table mb-0">
            <thead>
                <th>Bought products</th>
            </thead>
            {
                order.products.map((product, index) => {
                    return (
                        <tr key={index}>
                            <td>{product.quantity} x {product.product.name} x {product.price}&euro;</td>
                        </tr>
                    )
                })
            }
        </table>
    </tr>    
  )
}

export default Order