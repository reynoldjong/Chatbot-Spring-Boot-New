import React from 'react';
import classes from './Navbar.module.css';
import image from './images/DFILogo.png';

const navbar = () =>{
    return(
        <React.Fragment>
            <nav>
            <div className="nav-wrapper  blue darken-1" >
            
                <a href="https://www.digitalfinanceinstitute.org/" className={classes.NavbarLogo} > <img src={image} alt="DFI Logo"/> </a>
    
         
            <ul id="nav-mobile" class="right hide-on-med-and-down">
                <li><a href="sass.html">Login</a></li>
                <li><a href="badges.html">Register</a></li>
                <li><a href="badges.html">Help</a></li>
            </ul>
            </div>
        </nav>
      </React.Fragment>
            
    );
}

export default navbar;