import React from "react";
import Hero from "./Hero/Hero";
import AboutUs from "./AboutUs/AboutUs";
import Features from "./Features/Features";
import Chatbot from "../../containers/Chatbot/Chatbot";
import Navbar from "./Navbar/Navbar";
import Login from "./Login/Login";
import Footer from "./Footer/Footer";
/**
 *
 * @param {props} props:
 *  @param {bool} loggedin - bool represented whether the user is logged in or not
 *  @param {bool} showModal - bool representing whether the login modal should be visible or hidden
 *  @param {function} modalClickHandler - function for changing the state in App for showModal (controls whether the modal is visible or not)
 *  @param {function} loginHandler - function for authenticating the user in the backend and updating the state for loggedIn
 *  @param {function} logOutHandler - function for logging the user out
 */
const home = props => {
  document.body.style = "background: white";

  return (
    <React.Fragment>
      <Login
        showModal={props.showModal}
        loginHandler={props.loginHandler}
        modalClickHandler={props.modalClickHandler}
      />
      <Navbar
        loggedIn={props.loggedIn}
        logOutHandler={props.logOutHandler}
        modalClickHandler={props.modalClickHandler}
      />
      <Hero />
      <div className="container" style={{ width: "80%" }}>
        <AboutUs />
        <Features />
      </div>

      <Footer />
      <Chatbot />
    </React.Fragment>
  );
};

export default home;
