import React from 'react';
import classes from './Hero.module.css';
import image from './images/office.jpg'
import Particles from 'react-particles-js';
const hero = () =>{
    const particleOptions={
        particles:{
            number:{
                value:80
            }
        }
    };

    return(
        <div className={classes.Hero + ' z-depth-3'}>
            <div className={classes.TextPosition}>

           
            <h3 className={classes.HeroText}>DFI's Next Solution</h3>
            <h5 className={classes.HeroText}>Think Tank for Financial Technology, Artificial Intelligence & Blockchain</h5>
            <button className='waves-effect waves-light btn' style={{marginTop:'50px'}}> Learn More</button>
            </div>
            <Particles params={particleOptions} />
        </div>
    );
};  

export default hero;