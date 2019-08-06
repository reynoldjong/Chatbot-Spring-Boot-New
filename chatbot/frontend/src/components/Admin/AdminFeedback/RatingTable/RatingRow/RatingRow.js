import React from 'react';
/**
 * Component representing a row in a table with an uploaded document and the abillity to remove it
 * @param {props} props:
 *  @param {int} good - number of good votes
 *  @param {int} bad - number of bad votes
 */
const ratingRow = (props) =>{
    return( 
          <tr>
            <td>{props.message}</td>
            <td>{props.good}</td>
            <td>{props.bad}</td>
          </tr>
    );
};

export default ratingRow;