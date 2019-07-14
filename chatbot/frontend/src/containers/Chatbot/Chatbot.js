import React from 'react';
import ChatbotHeader from './ChatbotHeader/ChatbotHeader';
import Container from '@material-ui/core/Container';
import ChatbotBody from './ChatbotBody/ChatbotBody';
import Box from '@material-ui/core/Box';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import MessageInput from './MessageInput/MessageInput';

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




const Chatbot = () =>{
    
// Functional State

    const [values, setValues] = React.useState({
      messages: [],
  });

  const classes = useStyles();

  const addMessageHandler = (event) =>{
    
    event.preventDefault();
    const target = event.target;
    const message = target.userMessage.value;
    target.userMessage.value = "";
    if(message.length === 0 || /^\s*$/.test(message) )
   { 
     console.log("empty");
}else{
  const newMessages = [...values.messages, {'type':'user','message':message}]

   setValues({ ...values, messages: newMessages });
}
    
  }

    return(
        <React.Fragment>
            
             <Container className={classes.fab} maxWidth="false" style={{maxWidth:'450px'}}>
                <ChatbotHeader title="DFI Chatbot"/>
                <ChatbotBody messages={values.messages}/>
                <MessageInput addMessageHandler={addMessageHandler}/>
                
                
            </Container>
        </React.Fragment>
    );
};

export default Chatbot;