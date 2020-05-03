import React from 'react';
import FeedbackTable from '../FeedbackTable';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<FeedbackTable feedback={[]} />, div);
  });
