import React from 'react';
import classes from './Navbar.module.css';
const navbar = () =>{
    return(
        <React.Fragment>

     
        <nav className={classes.Navbar}>
        <div className={classes.Navbar + " nav-wrapper  blue-grey darken-3"}>
          <a href="www.google.com" className="brand-logo">Logo</a>
          <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="sass.html">Sass</a></li>
            <li><a href="badges.html">Components</a></li>
            <li><a href="collapsible.html">JavaScript</a></li>
          </ul>
        </div>
      </nav>
      </React.Fragment>
    );
}

export default navbar;