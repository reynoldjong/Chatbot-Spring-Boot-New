import React from 'react';
/**
 * Component representing a row in a table with an uploaded document and the abillity to remove it
 * @param {props} props:
 *  @param {string} name - name of a file
 *  @param {string} type - type of a file
 *  @param {string} date - date the file was added
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const documentRow = (props) =>{
    return( 
    <React.Fragment>
          <tr>
            <td>{props.name}</td>
            <td>{props.type}</td>
            <td>{props.date}</td>
            <td><button onClick={props.removeFileHandler} className="btn red">Remove</button></td>
          </tr>
    </React.Fragment>
    );
};

export default documentRow;