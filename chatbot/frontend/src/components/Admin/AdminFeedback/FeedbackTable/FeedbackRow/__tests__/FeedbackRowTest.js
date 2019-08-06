import React from 'react';
import FeedbackRow from '../FeedbackRow';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<FeedbackRow comments="hello " rating={5} />, div);
  });
