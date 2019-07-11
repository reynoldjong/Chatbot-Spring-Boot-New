import React from "react";
import DocumentTable from './DocumentsTable/DocumentsTable';
import classes from './AdminDocuments.module.css';
const adminDocuments = (props) => {
  return (
    <React.Fragment>
     <div className="container">
        <div className={classes.Card}>

       
        <h3>Upload a File</h3>
        <form onSubmit={props.addFileHandler} method="post" style={{marginBottom:'40px'}}>
        <p style={{textAlign:'left'}}>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </p>
        <input type="file" name="file" className="validate"/>
        <button type="submit" className="btn waves-effect waves-light blue">upload file</button>
        </form>
        </div>
        <div className={classes.Card}>

       <DocumentTable/>
       </div>
        </div>
    </React.Fragment>
  );
};

export default adminDocuments;
