import React from 'react';
import Hero from './Hero/Hero';
import AboutUs from './AboutUs/AboutUs';
import Features from './Features/Features';
const home = () =>{
    return(
        <React.Fragment>
            <Hero/>
            <div className='container'>
            <AboutUs/>
            <Features/>
            </div>
           
        </React.Fragment>
    );


};

export default home;