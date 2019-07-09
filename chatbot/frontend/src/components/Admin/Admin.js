import React from 'react';
import classes from './Admin.module.css';
import Sidebar from './Sidebar/Sidebar';
import AdminControls from './AdminControls/AdminControls';
import Navbar from './Navbar/Navbar';
const admin = (props) =>{
    let admin = null;
   
    if(props.loggedIn){
        admin = (
            <React.Fragment>
       <Navbar/>
          
               
               <Sidebar/>
              
           
          
        
                </React.Fragment>
        );
    }
    else{
        admin = <h2>Not Logged in</h2>
    }


    return(
        <div className={classes.Admin}>
            {admin}
        </div>
      
       
    )

}

export default admin;