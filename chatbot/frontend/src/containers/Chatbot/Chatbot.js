import React from 'react';
import ChatbotHeader from './ChatbotHeader/ChatbotHeader';
import Container from '@material-ui/core/Container';
import ChatbotBody from './ChatbotBody/ChatbotBody';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import MessageInput from './MessageInput/MessageInput';
import Chat from '@material-ui/icons/Chat';
import Fab from '@material-ui/core/Fab';
import Grow from '@material-ui/core/Grow';
import axios from 'axios';

const useStyles = makeStyles(theme => ({
  root: {
    backgroundColor: theme.palette.background.paper,
    width: 450,
    position: 'relative',
  },

  bottomRightPosition: {
    position: 'fixed',
    bottom: 15,
    right: 15,

  },

}));

// bot has the following props:
//  1) text
//  2) link
//  3) picture

// User has the following props:
// 1) text
const Chatbot = () => {

  // Functional State

  const [values, setValues] = React.useState({
    messages: [
      { 'type': 'bot', 'watson':{ 'message': 'Hello and welcome to DFI!  The future Chatbot, click a message to highlight it'}, 'lucene':{} }, 
    { 'type': 'user', 'message': 'Hello Chatbot' },
     { 'type': 'bot', 'watson':{'message': 'Hello and welcome to DFI the future chatbot' }, 'lucene':{} },
     { 'type': 'bot', 'watson':{}, 'lucene':{} }
    ],
    showChatbot: true,
    showing: { 'question': 0, 'answer': 1 }
  });

  const classes = useStyles();

  const showClickHandler = ( type,index) => {
    // Need length of messages array to check for out of bounds cases
    const length = values.messages.length;
    console.log(index, type);
    if(type === "bot"){
      // Make sure that this doesn't apply to greeting message which has index 0
      if (index != 0){
        console.log("here")
      setValues({
        ...values,
        showing:{'question':index, 'answer':index - 1}
      });
    }
    }
    else{
      // Make sure there is an answer to follow up with
      if(index + 1 < length){
      setValues({
        ...values,
        showing:{'question':index + 1, 'answer':index}
      });
    }
    }

  }
  const convertToGet = (message) => {
    const messageArray = message.split(" ");
    const length = messageArray.length;
    let getMessage = "";
    messageArray.forEach(function (item, index) {
      if (index === length - 1) {

        getMessage += item;

      }
      else {

        getMessage += item + "+";

      }

    });
    return getMessage;
  }

  const getWatsonMessage = (response) =>{
    let message = "";
    if(response['data']['watson']['text']){

      message = response['data']['watson']['text'];
    }
    return message;
  }

    const getLuceneMessage = (response) =>{
      let message = "";
      if(response['data']['lucene']['text']){
        message =  response['data']['lucene']['text']
      }
      return message;

    }

    const getLuceneFilePassage = (response) =>{
      let file = "";
      if(response['data']['lucene']['file']){
        file = response['data']['lucene']['file']['passage']
      }
      return file;
    }

    const getWatsonFilePassage = (response) =>{
      let file = "";
      if (response['data']['watson']['file']) {
        file = response['data']['watson']['file']['passage'];
      }
      return file;
  
    }
   

  const getWatsonImage = (response) =>{
    let image = null;

    if(response['data']['watson']['image']){
     image = response['data']['watson']['image'].replace(/['"]+/g, '');
    }
  
    return image;
  }

  const getLuceneImage = (response) =>{
    let image = null;
    if(response['data']['lucene']['image']){
      image = response['data']['lucene']['image'].replace(/['"]+/g, '');
    }
    return image;

  }

  const getWatsonLink = (response) =>{
    let link = null;
    if (response['data']['watson']['url']) {
      link = response['data']['watson']['url'];
    }
    else if(response['data']['lucene']['url']){
      link = response['data']['lucene']['url'];
    }
    return link;
  }

  const getLuceneLink = (response) =>{
    let link = null;

    if(response['data']['lucene']['url']){
      link = response['data']['lucene']['url'];
    }
    return link;
  }

  const getWatsonObject = (response) =>{
    let watsonObject = {}
    watsonObject['picture'] = getWatsonImage(response);
    watsonObject['link'] = getWatsonLink(response);
    watsonObject['file'] = getWatsonFilePassage(response);
    watsonObject['type'] = 'bot';
    watsonObject['message'] = getWatsonMessage(response);
    return watsonObject

  }

  const getLuceneObject = (response) =>{
    let luceneMessage = {}
    luceneMessage['picture'] = getLuceneImage(response);
    luceneMessage['link'] = getLuceneLink(response);
    luceneMessage['file'] = getWatsonFilePassage(response);
    luceneMessage['type'] = 'bot';
    luceneMessage['message'] = getLuceneMessage(response);
    return luceneMessage

  }

 


  const addMessageHandler = (event) => {

    event.preventDefault();
    const target = event.target;
    const userMessage = target.userMessage.value;
    target.userMessage.value = "";
    if (userMessage.length === 0 || /^\s*$/.test(userMessage)) {
      console.log("empty");
    }
    else {
      const newMessages = [...values.messages, { 'type': 'user', 'message': userMessage }]
      setValues({ ...values, messages: newMessages });

      console.log(userMessage);
      const getMessage = convertToGet(userMessage);

      axios.get("/userquery?" + getMessage).then((response) => {
        console.log(response);
        let botMessage = {};
        botMessage['watson'] = getWatsonObject(response);
        botMessage['lucene'] = getLuceneObject(response);
        botMessage['type'] = 'bot';
      
        // botMessage['picture'] = getWatsonImage(response);
        // botMessage['link'] = getWatsonLink(response);
        // botMessage['file'] = getWatsonFilePassage(response);
        // botMessage['type'] = 'bot';
        // botMessage['message'] = getWatsonMessage(response);
        
        // if((botMessage['link'] != null || botMessage['file'] != null) && botMessage['message'] === "I couldn't find that!"){
        //   botMessage['message'] = 'Here you go!';
        // }
        

        const newMessagesBot = [...newMessages, botMessage]
        // The current selected question should be the last two items in the messages array
        const arrayLength = newMessagesBot.length;
        setValues({ ...values, 
          showing:{'question':arrayLength - 1, 'answer':arrayLength - 2},
          messages: newMessagesBot });

      })
        .catch(function (error) {
          console.log(error);
        });

    }



  }

  const chatbotClickHandler = () => {
    const newStatus = !values.showChatbot;
    setValues({ ...values, showChatbot: newStatus });

  }

  let chatbot = null;

  if (values.showChatbot) {
    chatbot = (
      <React.Fragment>

        <Grow in={values.showChatbot}>

          <Container className={classes.bottomRightPosition} maxWidth="false" style={{ width: '30vw', minWidth: '400px', maxWidth: '500px' }}>
            <ChatbotHeader title="DFI Chatbot" clickHandler={chatbotClickHandler} />
            <ChatbotBody  showClickHandler={showClickHandler} messages={values.messages} showing={values.showing} />
            <MessageInput addMessageHandler={addMessageHandler} />
          </Container>

        </Grow>

      </React.Fragment>
    )
  }

  else {
    chatbot = (

      <Fab edge="start" className={classes.bottomRightPosition} color="secondary" aria-label="Menu" onClick={chatbotClickHandler}>
        <Chat />
      </Fab>

    )
  }

  return (
    <React.Fragment>

      {chatbot}
    </React.Fragment>
  );
};

export default Chatbot;