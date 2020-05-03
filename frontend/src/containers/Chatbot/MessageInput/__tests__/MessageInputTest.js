import React from 'react';
import MessageInput from '../MessageInput';
import ReactDOM from 'react-dom';

// Test for messageinput component
// Requires a mock function
it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<MessageInput addMessageHandler={()=>{return 'mock'}} />, div);
  });