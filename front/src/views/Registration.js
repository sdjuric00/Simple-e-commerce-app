import React, { useState } from 'react'
import './Registration.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { Await } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Registration = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [dateOfBirth, setDateOfBirth] = useState('');
  const [image, setImage] = useState("http://localhost:9000/assets/images/default-user.png");
  let navigate = useNavigate();

  function onFileChange(event) {
    setImage(event.target.files[0])
  }

  function containsNumbers(str) {
    return /[0-9]/.test(str);
  }

  function validateFormData() {
    let todaysDate = new Date().toISOString()
    if (email === "" || password.length < 5) {
      toast.error("Email or password is wrong. Password must have at least 5 characters.",
      {position: "bottom-left", theme: "colored"});
      return false;
    } else if (firstName.length < 2 || lastName.length < 2) {
      toast.error("First and last name must be longer than 2 characters.",
      {position: "bottom-left", theme: "colored"});
      return false;
    } else if (containsNumbers(firstName) || containsNumbers(lastName)) {
      toast.error("First and last cannot contain numbers.",
      {position: "bottom-left", theme: "colored"});
      return false;
    } else if (dateOfBirth >= todaysDate) {
      toast.error("Date of birth cannot be in past.",
      {position: "bottom-left", theme: "colored"});
      return false;
    } else if (document.getElementById("image").files.length === 0) {
      toast.error("You need to upload a picture!",
      {position: "bottom-left", theme: "colored"});
      return false;
    }

    return true;
  }

  const onRegister = async (event) => {
    event.preventDefault();
    if (validateFormData()) {
      const formData = new FormData();
      formData.append("picture", image)
      formData.append("email", email)
      formData.append("password", password)
      formData.append("firstName", firstName)
      formData.append("lastName", lastName)
      formData.append("dateOfBirth", dateOfBirth)

      try {
        const message = toast.loading("Please wait...")
        const res = await axios.post('http://localhost:9000/user', formData,
        {headers: {
                'content-type': 'multipart/form-data'}}
        ).then((response) => {
          toast.update(message, {render: "Registration was successsful!", position: 'bottom-left', type: "success", isLoading: false, closeOnClick: true});
          return navigate("/activate-account")
        }).catch(error => {
            if (error.response.status === 400) 
              toast.error("Data is in wronf format!",
              {position: "bottom-left", theme: "colored"});
            else 
              toast.error(error.response.data,
              {position: "bottom-left", theme: "colored"});
        });  
        
        Await(res)
      } catch (e) {
        console.log(e)
      }
    }
  }

  return (
   <div className='container'>
    <div className="row registration-content">
      <div className="col-6">
        <label className="form-label">Email address:</label>
        <input type="email" className="form-control" id="email" aria-describedby="emailHelp"
        value={email} onChange={event => setEmail(event.target.value)} />
        <label className="form-label">Password:</label>
        <input type="password" className="form-control" id="password" 
        value={password} onChange={event => setPassword(event.target.value)}/>
        <label className="form-label">First name:</label>
        <input type="text" className="form-control" id="firstName" 
        value={firstName} onChange={event => setFirstName(event.target.value)}/>
        <label className="form-label">Last name:</label>
        <input type="text" className="form-control" id="lastName" 
        value={lastName} onChange={event => setLastName(event.target.value)}/>
        <label className="form-label">Date of birth:</label>
        <input type="date" className="form-control" id="dateOfBirth" 
        value={dateOfBirth} onChange={event => setDateOfBirth(event.target.value)}/>
      </div>
      <div className="col-6 center-container">
        <img className="image-registration" src={image} alt="User"></img>
        <input type="file" accept="image/*" name="picture" id="image" onChange={onFileChange}/>
      </div>
      <div className='center-container'>
        <button className="btn btn-success" id="register-btn" onClick={onRegister}>Register</button>
      </div>
    </div>
    <ToastContainer />
   </div>
  )
}

export default Registration