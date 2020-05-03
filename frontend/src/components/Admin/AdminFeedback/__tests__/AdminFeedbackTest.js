import React from 'react';
import AdminFeedback from '../AdminFeedback';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<AdminFeedback loggedin={true} logOutHandler={()=>{return "bye"}} addFileHandler={()=>{return "added"}} feedback={[]}removeFileHandler={()=>{return "removed"}}/>, div);
  });