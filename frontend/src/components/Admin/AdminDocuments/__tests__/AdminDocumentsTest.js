import React from 'react';
import AdminDocuments from '../AdminDocuments';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<AdminDocuments loggedin={true} logOutHandler={()=>{return "re-indexed"}} addFileHandler={()=>{return "added"}} files={[]}removeFileHandler={()=>{return "removed"}}/>, div);
  });