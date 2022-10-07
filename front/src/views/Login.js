import React, { useState } from 'react'
import { NavLink } from 'react-router-dom';
import axios from 'axios'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from "react-router-dom";
import {useAuth} from "../components/AuthContext"

const Login = () => {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  let navigate = useNavigate();
  const { setAuth, setRole } = useAuth();


  function handleSubmit(event) {
    event.preventDefault();
     axios
      .post('http://localhost:9000/login', {
        email: email,
        password: password
      })
      .then((response) => {
        localStorage.setItem("token", "Bearer " + response.data.token)
        localStorage.setItem("userRole", response.data.accType)
        localStorage.setItem("email", response.data.email)
        setAuth(true)
        setRole(response.data.accType)
        toast.success("You are successfully logged " + response.data.firstName + "!", {
          position: "bottom-left",
          theme: 'colored'
        });
        return navigate("/home");
      }).catch(error => {
          setAuth(false)
          setRole(null)
          if (error.response.status === 401) {
            toast.error("An error occured. Your email or password is wrong!", {
            position: "bottom-left",
            theme: 'colored'
          });
          } else {
            setAuth(false)
            setRole(null)
            toast.error("An error occured. Your email or password is in wrong format!", {
            position: "bottom-left",
            theme: 'colored'
          });
          }
      });   
  }

  return (
    <div className='container login-container'>
      <div className='form-control form-login-style'>
        <div className="mb-3">
          <label className="form-label">Email address:</label>
          <input type="email" className="form-control" id="email" aria-describedby="emailHelp"
          value={email} onChange={event => setEmail(event.target.value)} />
        </div>
        <div className="mb-3">
          <label className="form-label">Password:</label>
          <input type="password" className="form-control" id="password" 
          value={password} onChange={event => setPassword(event.target.value)}/>
        </div>
        <div>
          <NavLink className="register-link-login" to='/registration'>Don't have an account? Register here!</NavLink>
        </div>
        <button className="login-button" onClick={handleSubmit}>Log in</button>
      </div>
      <ToastContainer />
    </div>
  )
}

export default Login
