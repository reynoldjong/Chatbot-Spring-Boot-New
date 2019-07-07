import React from 'react';


const admin = (props) =>{
    let admin = null;
    console.log(props);
    if(props.loggedIn){
        admin = (
           <h2>goerkpogfer</h2>
        );
    }
    else{
        admin = <h2>Not Logged in</h2>
    }
    return(
        <React.Fragment>
        <h2>you made it</h2>
        </React.Fragment>
       
    )

}

export default admin;