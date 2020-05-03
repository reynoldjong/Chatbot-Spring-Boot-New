import React from 'react';
import Message from '../Message';
import ReactDOM from 'react-dom';


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(
    <Message
    key={1}
            showing={true}
            type={'bot'}
            onClick={()=>{return "mock"}}
            watsonText="hello"
            watsonPicture="hello"
            watsonLink="hello"
            watsonFilename="hello"
            watsonFile="hello"
            luceneText="hello"
            luceneFile="hello"
            luceneFilename="hello"
            lucenePicture="hello"
            luceneLink="hello"/>
    , div);
  });

  it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(
    <Message
    key={1}
            showing={true}
            type={'user'}
            onClick={()=>{return "mock"}}
            text="hello"
           />
    , div);
  });