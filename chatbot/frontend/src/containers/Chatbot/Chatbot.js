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




const Chatbot = () => {

  // Functional State

  const [values, setValues] = React.useState({
    messages: [],
    showChatbot: true
  });

  const classes = useStyles();

  const addMessageHandler = (event) => {

    event.preventDefault();
    const target = event.target;
    const message = target.userMessage.value;
    target.userMessage.value = "";
    if (message.length === 0 || /^\s*$/.test(message)) {
      console.log("empty");
    }
    else {
      const newMessages = [...values.messages, { 'type': 'user', 'message': message }]

      setValues({ ...values, messages: newMessages });
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