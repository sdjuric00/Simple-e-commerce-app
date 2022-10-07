import React, {useState} from 'react'
import './Modal.css'

const ChangeProfileModal = ({setOpenModal, user, onSave}) => {

    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);

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
          <h1>Change Profile</h1>
        </div>
        <div className="container">
            <div className="row change-profile-form">
                <label className="form-label">First name:</label>
                <input type="text" className="form-control" id="firstName" 
                value={firstName} onChange={event => setFirstName(event.target.value)}/>
                <label className="form-label">Last name:</label>
                <input type="text" className="form-control" id="lastName" 
                value={lastName} onChange={event => setLastName(event.target.value)}/>
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
          <button className="btn btn-success" onClick={event => onSave(event, {firstName, lastName})}>Save</button>
        </div>
      </div>
    </div>
  )
}

export default ChangeProfileModal