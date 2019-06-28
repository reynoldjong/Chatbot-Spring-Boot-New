import React from 'react';
import classes from './Chatbot.module.css';
import robot from './images/robot.png';

class Chatbot extends React.Component{
    
    state = {
        chatlog:[],
    };


    render(){
        return(
            <div className={classes.Chatbot}>

                <div className={classes.Header + " blue"}>
                    <div className={classes.Robot}>
                        <img src={robot} width="40px" alt=""/>
                    </div>
                        <h4 className={classes.Title}>Chatbot</h4> 
                </div>
                <div className={classes.Body}>
                   </div>

                   <div classname={classes.Send}>
                       <label for="send" style={{float:'left', marginLeft:'4px'}}>Send message</label>
                       <input name="send" ></input>
                       </div>



            </div>
        );
    }
}

export default Chatbot;