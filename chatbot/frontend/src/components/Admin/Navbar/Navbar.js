import React from 'react';
import classes from './Navbar.module.css';
const navbar = () =>{
    return(
        <React.Fragment>

     
        <nav className={classes.Navbar}>
        <div className={classes.Navbar + " nav-wrapper  blue-grey darken-3"}>
          <a  style={{position:'relative',float:'left', marginLeft:'40px'}}href="www.google.com" className="brand-logo">DFI Cloud</a>
          <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="sass.html">Docs</a></li>
            <li><a href="badges.html">Support</a></li>
            <li><a href="collapsible.html">Resources</a></li>
          </ul>
        </div>
      </nav>
      </React.Fragment>
    );
}

export default navbar;