import React from 'react';
import classes from './Navbar.module.css';
import image from './images/DFILogo.png';

const navbar = (props) =>{
    let navbarLinks = null;

    if(props.loggedIn){
        navbarLinks = (
            <React.Fragment>
                <li><a href="sass.html">Logout</a></li>
                <li><a href="badges.html">Admin Dashboard</a></li>
                <li><a href="badges.html">Account</a></li>
            </React.Fragment>

        )
    }
    else{
        navbarLinks = (
            <React.Fragment>

       
            <li><a href="sass.html">Login</a></li>
                <li><a href="badges.html">Register</a></li>
                <li><a href="badges.html">Help</a></li>
                </React.Fragment>
        );

    }
    return(
        <React.Fragment>
            <div className={classes.NavbarFixed}>
            <nav className='transparent z-depth-0'>

            <div className="nav-wrapper" >
                <a href="https://www.digitalfinanceinstitute.org/" className={classes.NavbarLogo} > <img src={image} alt="DFI Logo"/> </a>

            <ul id="nav-mobile" class="right hide-on-med-and-down" >
               {navbarLinks}
            </ul>

            </div>
        </nav>
        </div>
      </React.Fragment>
            
    );
}

export default navbar;