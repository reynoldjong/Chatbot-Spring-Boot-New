import React from 'react';
import DragAndDrop from '../DragAndDrop';
import ReactDOM from 'react-dom';

// Component test
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<DragAndDrop />, div);
  });