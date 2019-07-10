import React from "react";
import classes from "./Admin.module.css";
import AdminControls from "./AdminControls/AdminControls";
import Navbar from "./Navbar/Navbar";
const admin = props => {
  let admin = null;

  if (props.loggedIn) {
    document.body.style = "background: rgba(0,0,0,0.05);";
    admin = (
      <React.Fragment>
        <Navbar />
      </React.Fragment>
    );
  } else {
    admin = <h2>Not Logged in</h2>;
  }

  return <div className={classes.Admin}>{admin}</div>;
};

export default admin;
