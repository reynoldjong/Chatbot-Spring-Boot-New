import React from 'react';
import classes from './Login.module.css';

const login = (props) => {
  let displayStyle = null;
  if (props.showModal === true) {
    displayStyle = { display: 'block' };
  }
  else {
    displayStyle = { display: 'none' };
  }
  console.log(displayStyle);
  return (
    <div style={displayStyle} className={classes.background}>


      <div className={classes.Modal}>
        <form onSubmit={props.loginHandler} method="post">
          <h4>Sign in</h4>
          <div className="row">
            <div className="input-field col s12">

              <input type="text" id="username" class="autocomplete" />
              <label for="username">Username</label>
            </div>
          </div>

          <div className="row">
            <div className="input-field col s12">

              <input type="password" id="password" className="autocomplete" />
              <label for="password">Password</label>
            </div>
          </div>

          <button type="submit" href="#!" className="waves-effect waves-light btn modal-trigger">Login</button>
          <button style={{marginLeft:"20px"}}className="waves-effect waves-light btn modal-trigger" onClick={props.modalClickHandler}>Cancel</button>
        </form>




      </div>
    </div>
  );
};


export default login;