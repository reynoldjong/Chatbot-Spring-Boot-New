import React from 'react';
/**
 * Compoenent representing a row in a table with an uploaded document and the abillity to remove it
 * @param {props} props:
 *  @param {string} name - name of a file
 *  @param {string} type - type of a file
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const documentRow = (props) =>{
    return( 
    <React.Fragment>
          <tr>
            <td>{props.name}</td>
            <td>{props.date}</td>
            <td><button onClick={props.removeSiteHandler} className="btn red">Remove</button></td>
          </tr>
    </React.Fragment>
    );
};

export default documentRow;