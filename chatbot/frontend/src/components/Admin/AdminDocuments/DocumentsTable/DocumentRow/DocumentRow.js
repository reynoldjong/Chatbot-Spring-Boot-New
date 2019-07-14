import React from 'react';

const documentRow = (props) =>{
    return( 
    <React.Fragment>
          <tr>
            <td>{props.name}</td>
            <td>{props.type}</td>
            <td><button onClick={props.removeFileHandler} className="btn red">Remove</button></td>
          </tr>
    </React.Fragment>
    );
};

export default documentRow;