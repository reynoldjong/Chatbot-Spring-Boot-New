import React from 'react';
import AdminReset from '../AdminReset';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<AdminReset loggedin={true} logOutHandler={()=>{return "re-indexed"}} reindexData={()=>{return "re-indexed"}} resetData={()=>{return "removed"}}/>, div);
  });