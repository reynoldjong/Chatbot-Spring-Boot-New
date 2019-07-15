import React from 'react';
import ChatbotHeader from './ChatbotHeader/ChatbotHeader';
import Container from '@material-ui/core/Container';
import ChatbotBody from './ChatbotBody/ChatbotBody';
import Box from '@material-ui/core/Box';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import MessageInput from './MessageInput/MessageInput';
import Fade from '@material-ui/core/Fade';
import IconButton from '@material-ui/core/IconButton';
import Add from '@material-ui/icons/Add';
import Fab from '@material-ui/core/Fab';
import Switch from '@material-ui/core/Switch';
import Grow from '@material-ui/core/Grow';
import axios from 'axios';
import qs from 'qs';


const useStyles = makeStyles(theme => ({
  root: {
    backgroundColor: theme.palette.background.paper,
    width: 450,
    position: 'relative',
    minHeight: 200,

  },
  fab: {
    position: 'fixed',
    bottom: theme.spacing(2),
    right: theme.spacing(2),
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
    messages: [],
    showChatbot: true
  });

  const classes = useStyles();

const convertToGet = (message) =>{
  const messageArray = message.split(" ");
  const length = messageArray.length;
  let getMessage = "";
  messageArray.forEach(function (item, index) {
    if(index === length -1){
  
      getMessage += item;
    
    }
    else{
     
      getMessage += item + "+";
      
     }
    
  });
  return getMessage;
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
     
      axios.get("/userquery?"+ getMessage,).then((response) => {
        console.log(response);
        let botMessage =  {};
        botMessage['message'] = response['data']['text'];

        if(response['data']['image']){
        botMessage['picture'] = response['data']['image'].replace(/['"]+/g, '');
      }
        if(response['data']['url']){
          botMessage['link'] = response['data']['url'];
        }
        botMessage['type'] = 'bot';
       
        const newMessagesBot = [...newMessages, botMessage]
        setValues({ ...values, messages: newMessagesBot });
      
        })
          .catch(function (error) {
            console.log(error);
         });
     
    }

   

  }

  const chatbotClickHandler = () =>{
    const newStatus = !values.showChatbot;
    setValues({ ...values, showChatbot: newStatus });

  }

  let chatbot = null;

  if (values.showChatbot) {
    chatbot = (
      <React.Fragment>
     <Grow in={values.showChatbot}>
        <Container className={classes.fab} maxWidth="false" style={{ maxWidth: '450px' }}>
          <ChatbotHeader title="DFI Chatbot" clickHandler={chatbotClickHandler}/>
          <ChatbotBody messages={values.messages} />
          <MessageInput addMessageHandler={addMessageHandler} />


        </Container>
        </Grow>
      </React.Fragment>
    )
  }

  else {
    chatbot = (
      
      <Fab edge="start" className={classes.fab} color="secondary" aria-label="Menu" onClick={chatbotClickHandler}>
         <Add />
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