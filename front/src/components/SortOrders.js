import React from 'react'

const SortOrders = ({setSortByParameter, sortByParameter}) => {

    var sortOptions = ["date", "price"]


  return (
    <div className="d-flex justify-content-center sort-orders-container">
    <span id="sort-by-orders">Sort by:</span> 
    <select id="sort-orders-combo" value={sortByParameter} onChange={event => setSortByParameter(event.target.value)}>
        {
            sortOptions.map((opt, index) => {
                return(
                    <option key={index} value={opt}>{opt}</option>
                )
            })
        }
    </select>
</div>
  )
}

export default SortOrders