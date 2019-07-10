import React from 'react';
import classes from './DocumentsTable.module.css';
import DocumentRow from './DocumentRow/DocumentRow';

const documentsTable = (props) => {
    return(
        <React.Fragment>
            
            <table className={ classes.fixedHeader + " striped"}>
        <thead className={"z-depth-1"}>
           
          <tr>
              <th>File Name</th>
              <th>Modified</th>
              <th>Type</th>
              <th>Remove</th>
          </tr>
      
        </thead>

        <tbody>
          <tr>
            <td>Alvin</td>
            <td>Eclair</td>
            <td>$0.87</td>
            <td>$0.87</td>
          </tr>
          <tr>
            <td>Alvin</td>
            <td>Eclair</td>
            <td>$0.87</td>
            <td>$0.87</td>
          </tr>
       
        </tbody>
      </table>
        
        </React.Fragment>
    );
}

export default documentsTable;