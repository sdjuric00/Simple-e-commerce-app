import React from 'react'

const ProfileData = ({user, setOpenModal}) => {
  return (
    <div className="container">
        <div className="row">
            <div className="col-6">
                <h1 id="profile-full-name">{user.firstName + " " + user.lastName}</h1>
                <div id="user-data-profile">
                    <p>Email: {user.email}</p>
                    <p>DOB: {user.dateOfBirth}</p>
                </div>
            </div>
            <div className="col-6">
                <div id="profile-image-container">
                    <img id="profile-image" src={"http://localhost:9000/assets/images/" + user.image} alt="User"></img>
                </div>
                <p id="acc-type-badge" className="badge rounded-pill text-bg-success">{user.accType}</p>
            </div>
            <div id="change-profile-container">
                <button id="change-button" className="btn btn-success" onClick={setOpenModal}>Change</button>
            </div>
        </div>
    </div>
  )
}

export default ProfileData