import React from 'react';
import CrawlerTable from '../CrawlerTable';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CrawlerTable sites={[]} removeSiteHandler={()=>{return "removed"}}/>, div);
  });