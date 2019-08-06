import React from 'react';
import ChatbotBody from '../ChatbotBody';
import ReactDOM from 'react-dom';


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(
    <ChatbotBody 
    showClickHandler={()=>{return 'hello'}}
    messages= {[
      {
        type: "bot",
        watson: {
          message:
            "Hello and welcome to DFI!  The future Chatbot, click a message to highlight it"
        },
        lucene: {}
      }
    ]}
    showing={ {question: 0, answer: 0} }/>
    , div);
  });