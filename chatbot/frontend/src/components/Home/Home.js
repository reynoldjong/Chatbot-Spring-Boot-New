import React from 'react';
import Hero from './Hero/Hero';
import AboutUs from './AboutUs/AboutUs';
import Features from './Features/Features';
import Chatbot from '../../containers/Chatbot/Chatbot';
import Navbar from './Navbar/Navbar';

const home = (props) =>{
    return(
        <React.Fragment>
            <Navbar loggedIn={props.loggedIn}/>
            <Hero/>
            <div className='container' style={{width:'90%'}}>
            <AboutUs/>
            <Features/>
            </div>
            <Chatbot/>
           
        </React.Fragment>
    );


};

export default home;