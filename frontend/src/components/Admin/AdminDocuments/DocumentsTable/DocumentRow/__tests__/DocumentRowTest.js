import React from 'react';
import DocumentRow from '../DocumentRow';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<DocumentRow name="name" type=".exe" date="01/02/20" removeFileHandler={()=>{return 'false'}}/>, div);
  });