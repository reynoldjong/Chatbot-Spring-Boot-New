import React from 'react';
import Hero from './Hero/Hero';
import AboutUs from './AboutUs/AboutUs';
import Features from './Features/Features';
import Chatbot from '../../containers/Chatbot/Chatbot';
import Navbar from './Navbar/Navbar';
import Login from './Login/Login';
import Footer from './Footer/Footer';
const home = (props) =>{
    document.body.style = 'background: white';
    return(
        <React.Fragment>
            <Login showModal={props.showModal} loginHandler={props.loginHandler}  modalClickHandler={props.modalClickHandler}/>
            <Navbar loggedIn={props.loggedIn} logOutHandler={props.logOutHandler} modalClickHandler={props.modalClickHandler}/>
            <Hero/>
            <div className='container' style={{width:'80%'}}>
            <AboutUs/>
            <Features/>
            </div>
            <Chatbot/>
           <Footer/>
        </React.Fragment>
    );


};

export default home;