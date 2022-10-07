import React, { useState } from 'react'
import './Search.css'
import {GoSearch} from 'react-icons/go'


const Search = ({onSearch}) => {

    const optionsCategory = ["ALL", "SWEATS", "DRINKS", "MEAT_PRODUCTS", "MILK_PRODUCTS"]
    const optionsPrice = ["ascending", "descending"]
    const optionsAvailable = [ "All", "Only available"]

    const [searchName, setSearchName] = useState('')
    const [searchCategory, setSearchCategory] = useState('ALL')
    const [searchPrice, setSearchPrice] = useState('ascending')
    const [searchAvailable, setSearchAvailable] = useState('Only available')

  return (
    <div className="container">
        <div className="row">
            <div id="search-bar-btn">
                <input type="text" className="form-control" 
                value={searchName} onChange={event => setSearchName(event.target.value)}/>
                <GoSearch />
                <button className="btn btn-success" onClick={() => onSearch(searchName, searchCategory, searchPrice, searchAvailable)}>Search</button>
            </div>
            <div className="d-flex justify-content-center" id="sort-bar">
                <select value={searchCategory} onChange={event => setSearchCategory(event.target.value)} onClick={() => onSearch(searchName, searchCategory, searchPrice, searchAvailable)}>
                    {
                        optionsCategory.map((opt, index) => {
                            return(
                                <option key={index} value={opt}>{opt}</option>
                            )
                        })
                    }
                </select>
                <select value={searchPrice} onChange={event => setSearchPrice(event.target.value)} onClick={() => onSearch(searchName, searchCategory, searchPrice, searchAvailable)}>
                    {
                        optionsPrice.map((opt, index) => {
                            return(
                                <option key={index} value={opt}>{opt}</option>
                            )
                        })
                    }
                </select>
                <select value={searchAvailable} onChange={event => setSearchAvailable(event.target.value)} onClick={() => onSearch(searchName, searchCategory, searchPrice, searchAvailable)}>
                    {
                        optionsAvailable.map((opt, index) => {
                            return(
                                <option key={index} value={opt}>{opt}</option>
                            )
                        })
                    }
                </select>
            </div>
        </div>
    </div>
  )
}

export default Search