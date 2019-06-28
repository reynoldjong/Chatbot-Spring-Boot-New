import React from 'react';
import classes from './Message.module.css';
import User from './images/user.png';
const message = (props) => {
    return(
        <div className={classes.Message }>
            <img className={classes.User} src={User} width="50px" alt=""/>
            <p className={classes.Blue}>{props.text}</p>
        </div>
    );
};

export default message;