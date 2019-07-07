import React from 'react';
import classes from './Admin.module.css';
import Sidebar from './Sidebar/Sidebar';
import AdminControls from './AdminControls/AdminControls';

const admin = (props) =>{
    let admin = null;
   
    if(props.loggedIn){
        admin = (
            <React.Fragment>
                
           <div className={classes.Sidebar}>
               
               <Sidebar/>
              
               </div>
            
            <div className={classes.Content}>
                <div className='container'>

            <h2> Add or Remove a File</h2>
            <AdminControls/>
            </div>
            </div>
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