import React from 'react';
import classes from './Navbar.module.css';
import { Link } from "react-router-dom";
const navbar = () =>{
    return(
        <React.Fragment>

     
        <nav className={classes.Navbar}>
        <div className={ "nav-wrapper  blue-grey darken-4"}>
          <a  style={{position:'relative',float:'left',clear:'both', marginLeft:'20px'}}href="/" className="brand-logo">DFI Cloud</a>
          <ul id="nav-mobile" class="right hide-on-med-and-down">
          <li><a href="/admin/">Dashboard</a></li>
            <li> <a href="/admin/documents">Documents</a></li>
            <li><a href="/admin/documents">Users</a></li>
            <li><a href="collapsible.html">Statistics</a></li>
          </ul>
         
        </div>

      </nav>
      <div className={classes.Dashboard + ' z-depth-1'}>
              <h5>Dashboard</h5>
          </div>
      </React.Fragment>
    );
}

export default navbar;