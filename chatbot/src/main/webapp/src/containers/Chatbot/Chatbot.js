import React from 'react';
import classes from './Chatbot.module.css';
import robot from './images/robot.png';
import Message from './Message/Message';
class Chatbot extends React.Component{
    
    state = {
        chatlog:["test message", "Another message"],
    };

    sendMessage = (event) =>{
        event.preventDefault();
        const target = event.target;
        const newMessage = target.elements.send.value;

        let newChatlog = [...this.state.chatlog];
        newChatlog.push(newMessage);

        this.setState({
            chatlog:newChatlog,
        })

        target.elements.send.value = "";
        
    }

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
                   { this.state.chatlog.map((item, i) =>{
                    return <Message key={i} text={item}/>
                   }
                    
                    ) }
                   </div>

                   <div classname={classes.Send}>
                       <form onSubmit={this.sendMessage}>
                       
                       <input  autocomplete="off" name="send" ></input>
                       </form>
                       </div>



            </div>
        );
    }
}

export default Chatbot;