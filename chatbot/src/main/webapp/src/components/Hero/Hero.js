import React from 'react';
import classes from './Hero.module.css';
import image from './images/office.jpg'
const hero = () =>{
    return(
        <div className={classes.Hero}>
            <img className={classes.Image} src={image} alt="offices"/>
        </div>
    );
};  

export default hero;