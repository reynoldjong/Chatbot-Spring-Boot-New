import React, {useEffect} from 'react';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import Box from '@material-ui/core/Box';
import Message from './Message/Message';
import MessageInput from '../MessageInput/MessageInput';
import robot from './Message/images/robot.png';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2),
    backgroundColor:'#F4F4F4',
    maxHeight:'500px',
   overflowY:'scroll'
  },
 
}));
const ChatbotBody = (props) =>{
  // Everytime the componenet is rendered we need to scroll to bottom
  useEffect(() => {
   scrollBottom();
  });


  const scrollBottom = () =>{
    console.log('called');
      if(document.getElementById("bottom")){
      document.getElementById("bottom").scrollIntoView({ behavior: 'smooth' });}
  }
  
  const classes = useStyles();
  const length = props.messages.length;


  let messages = (
    props.messages.map((item, index) =>{ 
    
        return( <Message key={index} type={item.type} id="bottom" text={item.message} picture={item.picture} link={item.link}/>)

   })
  )
  // Div with id at bottom is used as a dummy div to ensure that we
  // can keep the chat at the bottom
    return(
        <div>
          
             

             
             <Paper className={classes.root} id="out" style={{maxHeight:'50vh'}}>
           
                <Message text="hello this is some text how look" type='bot'/>
                
                <Message text="hello this is some text how look" type='user'/>
                <Message link="www.google.com" text="hello this is some text llo this is some text how loollo this is some text how loollo this is some text how loollo this is some text how loohow look" type='bot' picture={robot} />
              
                {
                  
                 messages
                }
               <div id="bottom" style={{float:'left',clear:'both'}}>
            
            </div>
            </Paper>
          

        </div>
       
    );
    
}

export default ChatbotBody;