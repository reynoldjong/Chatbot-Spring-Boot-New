import React from 'react';
import ChatbotHeader from '../ChatbotHeader';
import ReactDOM from 'react-dom';

// Check component renders
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<ChatbotHeader title="DFI Chatbot Alex"/>, div);
  });