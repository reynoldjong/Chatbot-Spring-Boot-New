import React from 'react';
import Hero from './Hero/Hero';
import AboutUs from './AboutUs/AboutUs';
import Features from './Features/Features';
import Chatbot from '../../containers/Chatbot/Chatbot';
const home = () =>{
    return(
        <React.Fragment>
            <Hero/>
            <div className='container'>
            <AboutUs/>
            <Features/>
            </div>
            <Chatbot/>
           
        </React.Fragment>
    );


};

export default home;