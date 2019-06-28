import React from 'react';
import classes from './Message.module.css';

const message = (props) => {
    return(
        <div className={classes.Message}>
            <p>props.text</p>
        </div>
    );
};

export default message;