import React from 'react';
import classes from './DocumentsTable.module.css';
import DocumentRow from './DocumentRow/DocumentRow';
/**
 * Compoenent representing a table of all the files in the  database
 * @param {props} props:
 *  @param {list} files - a list containing every file in Files.db
 *  @param {function} addFileHandler - function which adds a file to the database
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const documentsTable = (props) => {

  return (
    <React.Fragment>
      <table className={classes.fixedHeader + " striped"}>
        <thead className={"z-depth-1"}>
          <tr>
            <th>Name</th>
            <th>Date</th>
            <th>Remove</th>
          </tr>
        </thead>
        <tbody>
          {props.sites.map((item, i) => {
            return <DocumentRow key={i} removeSiteHandler={(url) => props.removeSiteHandler(item['seed'])} name={item['seed']} date={item['date']}/>
          }

          )}

        </tbody>
      </table>

    </React.Fragment>
  );
}

export default documentsTable;