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
      message: [],
  });

  const classes = useStyles();

  const addMessageHandler = (event) =>{
    event.preventDefault();
    const target = event.target;
    const message = target.userMessage.value;
    const newMessages = [...values.message, {'type':'user','message':message}]
    setValues({ ...values, [message]: newMessages });
    
  }

    return(
        <React.Fragment>
            
             <Container className={classes.fab} maxWidth="false" style={{maxWidth:'450px'}}>
                <ChatbotHeader title="DFI Chatbot"/>
                <ChatbotBody/>
                <MessageInput addMessageHandler={addMessageHandler}/>
                
                
            </Container>
        </React.Fragment>
    );
};

export default Chatbot;