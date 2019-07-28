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
      <table className={classes.fixedHeader + " striped"}>
        <thead className={"z-depth-1"}>
          <tr>
            <th>Message</th>
            <th>Ratting</th>
          </tr>
        </thead>
        <tbody>
          {props.feedback.map((item, i) => {
            const message= item['message'];
            const rating = itemArray['rating'];
            return <DocumentRow key={i}  message={message} rating={rating}/>
          }

          )}

        </tbody>
      </table>

    </React.Fragment>
  );
}

export default documentsTable;