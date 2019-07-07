import React from 'react';
import classes from './LogoBar.module.css';

const LogoBar = () =>{
    return(
        
        <div className={classes.Bar + " blue darken-2"}>
        <div className={classes.LogoBar}>

        <div className={classes.Logo}>
                    647-285-4436
                    
                </div>
              

                <div className={classes.Logo}>
                  <a className={classes.Link}href="www.google.com"> facebook</a> 
            
                </div>
               
               
                <div className={classes.Logo}>
                    twitter
               
                </div>
                <div className={classes.Logo}>
                    email
               
                </div>
               
           
           </div>
        </div>
    );
};

export default LogoBar