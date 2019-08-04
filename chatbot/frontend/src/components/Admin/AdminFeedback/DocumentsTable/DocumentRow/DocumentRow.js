import React from 'react';
/**
 * Compoenent representing a row in a table with an uploaded document and the abillity to remove it
 * @param {props} props:
 *  @param {string} name - name of a file
 *  @param {int} rating - type of a file
 */
const documentRow = (props) =>{
    return( 
    <React.Fragment>
          <tr>
            <td>{props.comments}</td>
            <td>{props.rating}</td>
          </tr>
    </React.Fragment>
    );
};

export default documentRow;