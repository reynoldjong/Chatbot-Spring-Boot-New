import React from 'react';
import classes from './Hero.module.css';
import image from './images/office.jpg'
const hero = () =>{
    return(
        <div className={classes.Hero}>
            <img src={image} alt="offices" width="1920" height="900"/>
        </div>
    );
};  

export default hero;