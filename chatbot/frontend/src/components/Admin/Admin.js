import React from "react";
import classes from "./Admin.module.css";
import AdminDocuments from "./AdminDocuments/AdminDocuments";
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Navbar from "./Navbar/Navbar";
const admin = props => {
  let admin = null;

  if (props.loggedIn) {
    document.body.style = "background: rgba(0,0,0,0.03);";
    admin = (

      <React.Fragment>
          <Navbar />
         
          <Router>
            

           
          <Route 
             path="/admin/" 
          render={(props)=> <AdminDocuments {...props} />}
          />
 
          </Router>
      
      </React.Fragment>

    );

  } 
  else {
    admin = <h2>Not Logged in</h2>;
  }

  return <div className={classes.Admin}>{admin}</div>;
};

export default admin;
