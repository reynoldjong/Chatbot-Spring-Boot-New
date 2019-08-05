import React from 'react';
import classes from './FeedbackTable.module.css';
import FeedbackRow from './FeedbackRow/FeedbackRow';
/**
 * Compoenent representing a table of all the files in the  database
 * @param {props} props:
 *  @param {list} feedback - a list containing every file in Chatbot.db
 */
const feedbackTable = (props) => {

  return (
    <React.Fragment>
 
      <table className={classes.ScrollableTable + " striped"}>
        <thead className={"z-depth-1"}>
          <tr>
            <th>Comments</th>
            <th>Rating</th>
          </tr>
        </thead>
        <tbody>
          {props.feedback.map((item, i) => {
            const comments= item['comments'];
            const rating = item['rating'];
            return <FeedbackRow key={i}  comments={comments} rating={rating}/>
          }

          )}

        </tbody>
      </table>
   
    </React.Fragment>
  );
}

export default feedbackTable;