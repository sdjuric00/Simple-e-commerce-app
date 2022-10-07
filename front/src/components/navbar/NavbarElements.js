import { FaBars } from 'react-icons/fa';
import { NavLink as Link } from 'react-router-dom';
import styled from 'styled-components';
  
export const Nav = styled.nav`
  background: #112240;
  height: 80px;
  display: flex;
  justify-content: space-between;
  padding: 0.2rem calc((100vw - 1000px) / 2);
  z-index: 12;
  /* Third Nav */
  /* justify-content: flex-start; */
`;
  
export const NavLink = styled(Link)`
  color: white;
  display: flex;
  align-items: center;
  text-transform: uppercase;
  text-decoration: none;
  padding: 0 1rem;
  height: 110%;
  font-size: 1.4rem;
  cursor: pointer;
  border-radius: 2px;

  &.active, &:hover {
    background:#1DB954; 
    transition: .5s;
    color: white;
  }
`;

export const NavLogo = styled(Link)`
  color: #1DB954;
  margin-right: 10%;
  font-weight: bold;
  display: flex;
  align-items: center;
  text-transform: uppercase;
  text-decoration: none;
  padding: 0 1rem;
  height: 100%;
  font-size: 1.8rem;
  cursor: pointer;
  border-radius: 2px;

  &.active, &:hover {
    color: #1DB954;
  }
`;
  
export const Bars = styled(FaBars)`
  display: none;
  color: white;
  @media screen and (max-width: 768px) {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    transform: translate(-100%, 75%);
    font-size: 1.8rem;
    cursor: pointer;
  }
`;
  
export const NavMenu = styled.div`
  display: flex;
  align-items: center;
  margin-right: -10px;
  /* Second Nav */
  /* margin-right: 24px; */
  /* Third Nav */
  width: 100vw;
  white-space: nowrap;
  @media screen and (max-width: 768px) {
    display: none;
  }
`;
  
export const NavBtn = styled.nav`
  display: flex;
  align-items: center;
  margin-right: 24px;
  /* Third Nav */
  /* justify-content: flex-end;*/
  @media screen and (max-width: 768px) {
    display: none;
  }
`;

export const CartLogo = styled.span`
  font-size: 2.5rem;
`;
  
export const NavBtnLink = styled(Link)`
  border-radius: 4px;
  white-space: nowrap;
  font-size: 1.4rem;
  background: #1DB954;
  padding: 10px 22px;
  color: white;
  outline: none;
  border: none;
  cursor: pointer;
  transition: all 0.5s ease-in-out;
  text-decoration: none;
  /* Second Nav */
  margin-left: 24px;
  &:hover {
    transition: all 0.2s ease-in-out;
    background: white;
    font-weight: bold;
    color: #1DB954;
  }
`;