import React from 'react';
import Hero from './Hero/Hero';
import AboutUs from './AboutUs/AboutUs';
import Features from './Features/Features';
import Chatbot from '../../containers/Chatbot/Chatbot';
import Navbar from './Navbar/Navbar';
import Login from './Login/Login';
const home = (props) =>{
    return(
        <React.Fragment>
            <Login showModal={props.showModal} modalClickHandler={props.modalClickHandler}/>
            <Navbar loggedIn={props.loggedIn}  modalClickHandler={props.modalClickHandler}/>
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