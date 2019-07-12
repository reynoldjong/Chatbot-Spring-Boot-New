import React from "react";
import classes from "./Admin.module.css";
import AdminDocuments from "./AdminDocuments/AdminDocuments";
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Navbar from "./Navbar/Navbar";
import Dashboard from './Dashboard/dashboard';
const admin = (props) => {
  let admin = null;

  if (props.loggedIn) {
    document.body.style = "background: rgba(0,0,0,0.03);";
    admin = (

      <React.Fragment>
          <Navbar />
          <AdminDocuments files={props.files} viewAllFilesHandler={props.viewAllFilesHandler} addFileHandler={props.addFileHandler} />
        
     
      
      </React.Fragment>

    );

  } 
  else {
    admin = <h2>Not Logged in</h2>;
  }

  return <div className={classes.Admin}>{admin}</div>;
};

export default admin;
/*
<Route 
exact path="/admin/" 
render={(props)=> <Dashboard {...props}  />}
/>


<Route 
path="/admin/documents/" 
render={(props)=> <AdminDocuments {...props} addFileHandler={props.addFileHandler} />}
/>
*/