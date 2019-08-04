import React from 'react';
import classes from './DocumentsTable.module.css';
import DocumentRow from './DocumentRow/DocumentRow';
/**
 * Compoenent representing a table of all the files in the  database
 * @param {props} props:
 *  @param {list} feedback - a list containing every file in Files.db
 */
const documentsTable = (props) => {

  return (
    <React.Fragment>
    <div className={classes.ScrollableTable}>
      <table className={classes.fixedHeader + " striped"}>
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
            return <DocumentRow key={i}  comments={comments} rating={rating}/>
          }

          )}

        </tbody>
      </table>
    </div>
    </React.Fragment>
  );
}

export default documentsTable;