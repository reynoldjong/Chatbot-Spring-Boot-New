import React, {useEffect} from 'react';
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
      if(length === index){
        return(<Message key={index} type={item.type} text={item.message}/>)
      }
      else{
        return(
        <React.Fragment>

      
        <Message key={index} type={item.type} id="bottom" text={item.message} />
       
      
        </React.Fragment>)
      }
    
     



   })
  )
  // Div with id at bottom is used as a dummy div to ensure that we
  // can keep the chat at the bottom
    return(
        <div>
          
             

             
             <Paper className={classes.root} id="out" style={{height:'500px'}}>
           
                <Message text="hello this is some text how look" type='bot'/>
             
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