import React from 'react';
import classes from './Navbar.module.css';
const navbar = () =>{
    return(
        <React.Fragment>

     
        <nav className={classes.Navbar}>
        <div className={ " nav-wrapper  blue-grey darken-4"}>
          <a  style={{position:'relative',float:'left', marginLeft:'20px'}}href="www.google.com" className="brand-logo">DFI Cloud</a>
          <ul id="nav-mobile" class="right hide-on-med-and-down">
          <li><a href="/">Home</a></li>
            <li><a href="sass.html">Documents</a></li>
            <li><a href="badges.html">Users</a></li>
            <li><a href="collapsible.html">Statistics</a></li>
          </ul>
         
        </div>
        <div className={classes.Dashboard + ' z-depth-1'}>
              <h5>Dashboard</h5>
          </div>
      </nav>
      </React.Fragment>
    );
}

export default navbar;