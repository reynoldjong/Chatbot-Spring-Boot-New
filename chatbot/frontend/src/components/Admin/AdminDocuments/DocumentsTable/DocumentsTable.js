import React from 'react';
import DocumentRow from './DocumentRow/DocumentRow';
/**
 * Component representing a table of all the files in the  database
 * @param {props} props:
 *  @param {list} files - a list containing every file in Files.db
 *  @param {function} addFileHandler - function which adds a file to the database
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const documentsTable = (props) => {

  return (
    <React.Fragment>
      <table className={" striped"}>
        <thead className={"z-depth-1"}>
          <tr>
            <th>File Name</th>
            <th>File Type</th>
            <th>Last Updated</th>
            <th>Remove</th>
          </tr>
        </thead>
        <tbody>
          {props.files.map((item, i) => {
            const itemArray = item['filename'].split(".");
            const name = itemArray[0];
            const type = itemArray[1];
            return <DocumentRow key={i} removeFileHandler={(fileName) => props.removeFileHandler(name+"."+type)} name={name} type={type} date={item['date']}/>
          }

          )}

        </tbody>
      </table>

    </React.Fragment>
  );
}

export default documentsTable;