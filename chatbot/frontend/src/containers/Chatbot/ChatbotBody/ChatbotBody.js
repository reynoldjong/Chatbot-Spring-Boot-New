import React from 'react';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import Box from '@material-ui/core/Box';
import Message from './Message/Message';
import MessageInput from '../MessageInput/MessageInput';
const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2),
    backgroundColor:'#F4F4F4'
   
  },
 
}));
const ChatbotBody = (props) =>{
    const classes = useStyles();
    return(
        <div>
          
             

             
             <Paper className={classes.root} style={{height:'500px'}}>
           
                <Message text="hello this is some text how look" type='bot'/>
             
                {props.messages.map((item, index) => (
                   <Message key={index} type={item.type} text={item.message} />
                ))}
            </Paper>
         

        </div>
    );
}

export default ChatbotBody;