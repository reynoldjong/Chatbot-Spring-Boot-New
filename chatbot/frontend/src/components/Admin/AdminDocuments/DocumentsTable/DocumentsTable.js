import React from 'react';
import classes from './DocumentsTable.module.css';
import DocumentRow from './DocumentRow/DocumentRow';

const documentsTable = (props) => {
  return (
    <React.Fragment>

      <table className={classes.fixedHeader + " striped"}>
        <thead className={"z-depth-1"}>

          <tr>
            <th>File Name</th>
            <th>File Type</th>
            <th>Remove</th>
          </tr>

        </thead>

        <tbody>
          {props.files.map((item, i) => {
            const itemArray = item.split(".");
            const name = itemArray[0];
            const type = itemArray[1];

            return <DocumentRow key={i} name={name} type={type}/>
          }

          )}

        </tbody>
      </table>

    </React.Fragment>
  );
}

export default documentsTable;