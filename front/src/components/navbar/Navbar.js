import React from 'react'
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
  NavLogo,
  CartLogo
} from './NavbarElements';
import { useAuth } from "../AuthContext";
import {FiLogOut} from 'react-icons/fi'
import {AiOutlineShoppingCart} from 'react-icons/ai'
import {CgProfile} from 'react-icons/cg'

const Navbar = () => {

  const { setAuth, setRole, role } = useAuth();
  
  const onLogOut = () => {
    setAuth(false);
    setRole(null)
    localStorage.setItem("email", null);
    localStorage.setItem("userRole", null)
    localStorage.setItem("token", null)
    this.props.history.push('/login')
  }

  return (
    <>
      <Nav>
        <Bars />
        { 
          (role === "ADMIN") ? (
            <>
              <NavMenu>
                <NavLogo to='/home'>
                  Baki Shop
                </NavLogo>
                <NavLink to='/home'>
                  Admin
                </NavLink>
                <NavLink to='/orders'>
                  Orders
                </NavLink>
                <NavLink to='/profile'>
                  <CartLogo><CgProfile /></CartLogo>
                </NavLink>
              </NavMenu>
              <NavBtn>
                <NavBtnLink onClick={onLogOut}>Log Out <FiLogOut /></NavBtnLink>
              </NavBtn>
            </>
          ) : (role === "REGULAR_USER") ? (
            <>
              <NavMenu>
                <NavLogo to='/home'>
                  Baki Shop
                </NavLogo>
                <NavLink to='/home'>
                  Products
                </NavLink>
                <NavLink to='/orders'>
                  Orders
                </NavLink>
                <NavLink to='/cart'>
                  <CartLogo><AiOutlineShoppingCart /></CartLogo>
                </NavLink>
                <NavLink to='/profile'>
                  <CartLogo><CgProfile /></CartLogo>
                </NavLink>
              </NavMenu>
              <NavBtn>
                <NavBtnLink onClick={onLogOut}>Log Out <FiLogOut /></NavBtnLink>
              </NavBtn>
            </>
          ) : (
            <>
              <NavMenu>
              <NavLogo to='/home'>
                Baki Shop
              </NavLogo>
              <NavLink to='/home'>
                Products
              </NavLink>
              <NavLink to='/registration'>
                Registration
              </NavLink>
              </NavMenu>
              <NavBtn>
                <NavBtnLink to='/login'>Log In</NavBtnLink>
              </NavBtn>
            </>
          )
        }
        
      </Nav>
    </>
  )
}

export default Navbar
