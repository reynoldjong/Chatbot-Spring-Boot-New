import React from 'react';

const middleNav = () =>{
    return(
        <React.Fragment>
        <nav>
        <div className="nav-wrapper grey lighten-5">
            <center>
          <ul id="nav-mobile" style={{ color:'black'}}className=" hide-on-med-and-down">
            <li><a style={{ color:'black'}} href="sass.html">HOME</a></li>
            <li><a style={{ color:'black'}} href="badges.html">FINTECH</a></li>
            
            <li><a  style={{color:'black'}}href="collapsible.html">NEWS</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">ARTICLES</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">PROJECTS</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">REPORTS</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">OUR PEOPLE</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">ABOUT US</a></li>
          
          </ul>
          </center>
        </div>
      </nav>
      </React.Fragment>
    );
}

export default middleNav;