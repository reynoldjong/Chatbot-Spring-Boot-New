import React, { useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Message from './Message/Message';
//'#F4F4F4'
const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2),
    backgroundColor: 'white',
    maxHeight: '500px',
    overflowY: 'scroll'
  },

}));
const ChatbotBody = (props) => {
  // Everytime the component is rendered we need to scroll to bottom
  useEffect(() => {
    scrollBottom();
  });


  const scrollBottom = () => {
    console.log('called');
    if (document.getElementById("bottom")) {
      document.getElementById("bottom").scrollIntoView({ behavior: 'smooth' });
    }
  }

  const classes = useStyles();
  const length = props.messages.length;

  let messages = props.messages.map((item, index) => {
    console.log(props);
    if (index === props.showing['question'] || index === props.showing['answer']) {
      
      return (<Message showClickHandler={() => props.showClickHandler( item.type,index,)}   key={index} showing={true} type={item.type} id="bottom" text={item.message} picture={item.picture} link={item.link} />)
    }
    else {
      return (<Message  showClickHandler={() => props.showClickHandler( item.type,index)} key={index} showing={false} type={item.type} id="bottom" text={item.message} picture={item.picture} link={item.link} file={item.file}/>)
    }
  });

  // Div with id at bottom is used as a dummy div to ensure that we
  // can keep the chat at the bottom
  return (
    <div>
      <Paper className={classes.root} id="out" style={{ maxHeight: '60vh', minHeight: '50vh' }}>
        {
          messages
        }
        <div id="bottom" style={{ float: 'left', clear: 'both' }}>
        </div>
      </Paper>
    </div>

  );

}

export default ChatbotBody;