import React from 'react';
/**
 * Compoenent representing a row in a table with feedback
 * @param {props} props:
 *  @param {string} comments -comments made by user
 *  @param {int} rating - rating of chatbot
 */
const feedbackRow = (props) =>{
    return( 

          <tr>
            <td>{props.comments}</td>
            <td>{props.rating}</td>
          </tr>

    );
};

export default feedbackRow;