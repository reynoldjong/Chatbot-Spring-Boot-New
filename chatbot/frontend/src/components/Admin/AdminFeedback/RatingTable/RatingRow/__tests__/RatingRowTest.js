import React from 'react';
import RatingRow from '../RatingRow';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<RatingRow message="hello" good={5} bad={20} />, div);
  });
