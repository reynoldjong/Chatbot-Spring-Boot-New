import React from 'react';
import Sound from '../Sound';
import ReactDOM from 'react-dom';


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<Sound text="hello world" />, div);
  });