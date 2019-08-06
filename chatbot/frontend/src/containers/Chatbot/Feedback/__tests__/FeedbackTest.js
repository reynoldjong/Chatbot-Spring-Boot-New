import React from 'react';
import Feedback from '../Feedback';
import ReactDOM from 'react-dom';

// Check component renders
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<Feedback/>, div);
  });