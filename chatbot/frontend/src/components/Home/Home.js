import React from 'react';
import Hero from './Hero/Hero';
import AboutUs from './AboutUs/AboutUs';
const home = () =>{
    return(
        <React.Fragment>
            <Hero/>
            <div className='container'>
            <AboutUs/>
            </div>
           
        </React.Fragment>
    );


};

export default home;