import React from 'react';
import classes from './Navbar.module.css';
import image from './images/DFILogo.png';
import { Link } from "react-router-dom";

const navbar = (props) =>{
    let navbarLinks = null;

    if(props.loggedIn){
        navbarLinks = (
            <React.Fragment>
             
              <Link to="/admin/"> <li>Admin Dashboard</li> </Link>
              <Link to="/admin/">   <li>Account</li></Link>
              <Link to="/"> <li>Logout</li></Link>
            </React.Fragment>

        )
    }
    else{
        navbarLinks = (
            <React.Fragment>

       
                <Link to="/admin/">  <li>Login</li> </Link>
                <Link to="/"> <li>Register</li></Link>
                <Link to="/">  <li>Help</li></Link>
                </React.Fragment>
        );

    }
    return(
        <React.Fragment>
            <div className={classes.NavbarFixed}>
            <nav className='transparent z-depth-0'>

            <div className="nav-wrapper" >
                <a href="https://www.digitalfinanceinstitute.org/" className={classes.NavbarLogo} > <img src={image} alt="DFI Logo"/> </a>

            <ul id="nav-mobile" className={ classes.Display + " right hide-on-med-and-down"} style={{display:'inline-block'}} >
               {navbarLinks}
            </ul>

            </div>
        </nav>
        </div>
      </React.Fragment>
            
    );
}

export default navbar;