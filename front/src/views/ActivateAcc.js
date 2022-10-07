import React, {useState, useContext} from 'react'
import './ActivateAcc.css'
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


const ActivateAcc = () => {

  let navigate = useNavigate();
  const [code, setCode] = useState('');
  const [email, setEmail] = useState('');   

  function handleVerify(event) {
    event.preventDefault();
    axios.post('http://localhost:9000/activate', {email: email, code: parseInt(code)}
      ).then((response) => {
        toast.success(response.data, {
          position: "bottom-left",theme: 'colored'
        });
        return navigate("/login");
      }).catch(error => {
        if (error.response.status === 400) {
            toast.error("You need to add email and code!",
            {position: "bottom-left", theme: "colored"});
        } else {
            toast.error(error.response.data,
            {position: "bottom-left", theme: "colored"});
        }
      });   
  }

  return (
    <div className="container">
        <div className="background-container form-control">
            <h2>Verify.</h2>
            <div className="items-container">
                <label className="form-label">Email:</label>
                <input type="email" className="form-control input-activation"
                value={email} onChange={event => setEmail(event.target.value)}></input>
            </div>
            <div className="items-container">
                <label className="form-label">Code:</label>
                <input type="text" className="form-control input-activation"
                value={code} onChange={event => setCode(event.target.value)}></input>
            </div>
            <div className="btn-container">
                <button onClick={handleVerify} id="activation-btn">Verify</button>
            </div>
        </div>
        <ToastContainer />
    </div>
  )
}

export default ActivateAcc