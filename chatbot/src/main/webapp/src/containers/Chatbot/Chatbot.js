import React from 'react';
import classes from './Chatbot.module.css';


class Chatbot extends React.Component{
    
    state = {
        chatlog:[],
    };


    render(){
        return(
            <div className={classes.Chatbot}>

            </div>
        );
    }
}

export default Chatbot;