import React from 'react';

/**
 * Component for the footer in Home, mostly filler content
 */
const footer = () =>{
    return(
        <React.Fragment>
        <footer className="page-footer blue darken-3">
          <div className="container">
            <div className="row">
              <div className="col l6 s12">
                <h5 className="white-text">DFI Chat Bot</h5>
                <p className="grey-text text-lighten-4">Lorem Ipsum.</p>
              </div>
              <div className="col l4 offset-l2 s12">
                <h5 className="white-text">Helpful Links</h5>
                <ul>
                <li><a className="grey-text text-lighten-3" href="#!">Getting Started</a></li>
                  <li><a className="grey-text text-lighten-3" href="#!">About Us</a></li>
                  <li><a className="grey-text text-lighten-3" href="#!">Terms of Service</a></li>
                  <li><a className="grey-text text-lighten-3" href="#!">Privacy Policy</a></li>
                  
                </ul>
              </div>
            </div>
          </div>
          <div className="footer-copyright">
            <div className="container">
            © 2019 CSC01
            <a className="grey-text text-lighten-4 right" href="#!">Lorem Ipsum</a>
            </div>
          </div>
        </footer>
        </React.Fragment>
 
    );

}

export default footer;
