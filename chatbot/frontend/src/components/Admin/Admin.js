import React from "react";
import classes from "./Admin.module.css";
import AdminDocuments from "./AdminDocuments/AdminDocuments";
import { BrowserRouter as Route } from "react-router-dom";
import Navbar from "./Navbar/Navbar";

/**
 * Componenet represents main admin page
 */
const admin = props => {
  // If the user is not logged in we will display an error message otherwise
  // the admin dashboard will be displayed
  let admin = null;

  if (props.loggedIn) {
    document.body.style = "background: rgba(0,0,0,0.05);";
    admin = (
      <React.Fragment>
        <Navbar logOutHandler={props.logOutHandler} />

        <Route
          path="/admin"
          render={props => (
            <AdminDocuments
              {...props}
              files={props.files}
              removeFileHandler={props.removeFileHandler}
              viewAllFilesHandler={props.viewAllFilesHandler}
              addFileHandler={props.addFileHandler}
            />
          )}
        />
      </React.Fragment>
    );
  } else {
    admin = <h2>Not Logged in</h2>;
  }

  return <div className={classes.Admin}>{admin}</div>;
};

export default admin;

//<AdminDocuments files={props.files} removeFileHandler={props.removeFileHandler} viewAllFilesHandler={props.viewAllFilesHandler} addFileHandler={props.addFileHandler} />
