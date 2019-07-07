import React from 'react';
import classes from './Admin.module.css';
import Sidebar from './Sidebar/Sidebar';;
import AdminControls from './AdminControls/AdminControls';

const admin = (props) =>{
    let admin = null;
    console.log(props);
    if(props.loggedIn){
        admin = (
            <React.Fragment>

           <div className={classes.Sidebar}>
               <Sidebar/>
               <AdminControls/>
               </div>
            
            <div className={classes.Content}>

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