import React from 'react';
import classes from './AboutUs.module.css';

/**
 * Component for displaying dummy text on the home page
 * about our chatbot, mostly filler content
 */
const aboutUs = () => {
    return (
        <div className={classes.AboutUs}>

            <div className={classes.Picture}>

            </div>
            <div className={classes.Text}>
                <h2>What is a chatbot?</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p>
            </div>
        </div>
    );
};

export default aboutUs;