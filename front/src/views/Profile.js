import React, {useEffect, useState} from 'react'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import './Profile.css'
import ChangeProfileModal from '../components/ChangeProfileModal';
import ProfileData from '../components/ProfileData';


const Profile = () => {

    const [user, setUser] = useState('')
    const [showChangeModal, setShowChangeModal] = useState(false)

    function containsNumbers(str) {
        return /[0-9]/.test(str);
    }

    function validateFormData(data) {
        if (data.firstName.length < 2 || data.lastName.length < 2) {
            toast.error("First and last name must be longer than 2 characters.",
            {position: "bottom-left", theme: "colored"});
        return false;
        } else if (containsNumbers(data.firstName) || containsNumbers(data.lastName)) {
            toast.error("First and last cannot contain numbers.",
            {position: "bottom-left", theme: "colored"});
        return false;
        } 

        return true;
    }



    function onSave(event, data) {
        event.preventDefault();
        if (validateFormData(data)) {
            user.firstName = data.firstName
            user.lastName = data.lastName
            axios.put('http://localhost:9000/user', user, {headers: {'authorization': localStorage.getItem("token") }})
            .then(response =>{
                toast.success("Your profile is updated!.", {
                    position: "bottom-left",
                    theme: 'colored'})
            }
            ).catch(error => {
                toast.error(error.response.data, {
                position: "bottom-left",
                theme: 'colored'})
        });
        }
    }

    useEffect(() => {
        axios.get('http://localhost:9000/user-by-email/' + localStorage.getItem("email"), 
            {headers: {'authorization': localStorage.getItem("token") }})
            .then(response => {
                setUser(response.data)
            }).catch(error => {
                toast.error("An error occured. Cannot load the user!", {
                position: "bottom-left",
                theme: 'colored'})
            });
    })

    function changeModalState() {
        setShowChangeModal(!showChangeModal)
    }

  return (
    <>  
        <ToastContainer></ToastContainer>
        {showChangeModal && <ChangeProfileModal setOpenModal={changeModalState} onSave={onSave} user={user} />}
        <ProfileData user={user} setOpenModal={changeModalState}></ProfileData>
    </>
  )
}

export default Profile