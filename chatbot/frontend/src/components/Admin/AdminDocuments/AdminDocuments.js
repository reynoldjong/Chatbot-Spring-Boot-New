import React from "react";
import DocumentTable from './DocumentsTable/DocumentsTable';
const adminDocuments = (props) => {
  return (
    <React.Fragment>
     <div className="container">

        <h3>Upload a File</h3>
        <form onSubmit={props.addFileHandler}>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </p>
        <input type="file" className="validate"/>
        <button type="submit" className="btn blue">upload file</button>
        </form>
       <DocumentTable/>
      
        </div>
    </React.Fragment>
  );
};

export default adminDocuments;
