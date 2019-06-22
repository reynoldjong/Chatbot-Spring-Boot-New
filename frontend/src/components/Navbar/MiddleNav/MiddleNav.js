import React from 'react';

const middleNav = () =>{
    return(
        <React.Fragment>
        <nav>
        <div className="nav-wrapper grey lighten-5">
 
          <ul id="nav-mobile" style={{marginLeft:'auto',marginRight:'auto', color:'black'}}className=" hide-on-med-and-down">
            <li><a style={{ color:'black'}} href="sass.html">HOME</a></li>
            <li><a style={{ color:'black'}} href="badges.html">FINTECH</a></li>
            
            <li><a  style={{color:'black'}}href="collapsible.html">NEWS</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">ARTICLES</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">PROJECTS</a></li>
            <li><a  style={{color:'black'}}href="collapsible.html">REPORTS</a></li>
          </ul>
        </div>
      </nav>
      </React.Fragment>
    );
}

export default middleNav;