import React from 'react';
import RatingTable from '../RatingTable';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<RatingTable  answerRating={[]} />, div);
  });
