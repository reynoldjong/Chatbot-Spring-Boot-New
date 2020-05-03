import React from 'react';
import Chatbot from '../Chatbot';
import ReactDOM from 'react-dom';


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<Chatbot />, div);
  });