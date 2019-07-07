import React from 'react';


const admin = (props) =>{
    let admin = null;

    if(props.loggedIn){
        admin = (
            <React.Fragment>
                <h2>You are logged in</h2>
            </React.Fragment>
        );
    }
    else{
        admin = <h2>Not Logged in</h2>
    }
    return(
     
        {admin}
       
    )

}