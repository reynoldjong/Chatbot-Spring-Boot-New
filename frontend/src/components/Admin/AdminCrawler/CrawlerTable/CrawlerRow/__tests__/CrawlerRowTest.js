import React from 'react';
import CrawlerRow from '../CrawlerRow';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CrawlerRow name="hello" date="01/22/20" removeSiteHandler={()=>{return "removed"}}/>, div);
  });