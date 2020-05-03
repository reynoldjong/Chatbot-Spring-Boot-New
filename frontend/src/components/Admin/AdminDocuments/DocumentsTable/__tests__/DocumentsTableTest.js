import React from 'react';
import DocumentsTable from '../DocumentsTable';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<DocumentsTable files={[]} addFileHandler={()=>{return 'true'}} removeFileHandler={()=>{return 'false'}}/>, div);
  });