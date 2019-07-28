import React, { useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Message from './Message/Message';
import media from './ChatbotBody.module.css';

//'#F4F4F4'
const useStyles = makeStyles(theme => ({
  root: {
    
    backgroundColor: 'white',
    overflowX:'hidden',
    overflowY: 'scroll',
    flex:'1',
    maxHeight:'420px',
    height:'50vh',
    
   
    borderBottomRightRadius:'0px',
    borderBottomLeftRadius:'0px',
    borderTopRightRadius:'0px',
    borderTopLeftRadius:'0px',
  },

}));
/**
 * Componenet representing the body of the chatbot
 */
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
console.log(props.messages);
  let messages = props.messages.map((item, index) => {
    console.log(item);
   
    if(item.type === "bot"){

   // If the index is the same as question or answer we doon't change the opacity
    if (index === props.showing['question'] || index === props.showing['answer']) {

      return (<Message showClickHandler={() => props.showClickHandler(item.type, index)} 
      key={index} 
      showing={true} type={item.type} 
      id="bottom"
      watsonText={item.watson.message} 
      watsonPicture={item.watson.picture} 
      watsonLink={item.watson.link}
      watsonFilename={item.watson.filename}
      watsonFile={item.watson.file}
      luceneText={item.lucene.message}
      luceneFile={item.lucene.file}
      luceneFilename={item.lucene.filename}
      lucenePicture={item.lucene.picture}
      luceneLink={item.lucene.link}
      />)
    }
    else {
      return (<Message showClickHandler={() => props.showClickHandler(item.type, index)}
       key={index} showing={false} 
       type={item.type} 
       id="bottom"
       watsonText={item.watson.message} 
       watsonPicture={item.watson.picture} 
       watsonLink={item.watson.link}
       watsonFilename={item.watson.filename}
       watsonFile={item.watson.file}
       luceneText={item.lucene.message}
       luceneFile={item.lucene.file}
       luceneFilename={item.lucene.filename}
       lucenePicture={item.lucene.picture}
       luceneLink={item.lucene.link}
       />)
    }
  
  }
  else{
    if (index === props.showing['question'] || index === props.showing['answer']) {

      return (<Message showClickHandler={() => props.showClickHandler(item.type, index)} 
      key={index} 
      showing={true} type={item.type} 
      id="bottom"
      text={item.message} 
      picture={item.picture} link={item.link} />)
    }
    else {
      return (<Message showClickHandler={() => props.showClickHandler(item.type, index)}
       key={index} showing={false} 
       type={item.type} 
       id="bottom"
        text={item.message}
        picture={item.picture}
         link={item.link} 
         file={item.file} />)
    }
  }
  }
    );
   ;

  // Div with id at bottom is used as a dummy div to ensure that we
  // can keep the chat at the bottom
  return (
    <div>
      <Paper className={media.root} id="out">
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