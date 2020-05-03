import React from 'react';
import Rating from '../Rating';
import ReactDOM from 'react-dom';

// Check component renders
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<Rating message="hello world" />, div);
  });