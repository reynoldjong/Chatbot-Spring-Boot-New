import React from 'react';
import AdminCrawler from '../AdminCrawler';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<AdminCrawler loggedin={true} logOutHandler={()=>{return "re-indexed"}}  sites={[]}removeSiteHandler={()=>{return "removed"}}/>, div);
  });