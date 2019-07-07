import React from 'react';
import classes from './AdminLogin.module.css';

const adminLogin = (props) => {
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
        <form>
          <h4>Sign in</h4>
          <div class="row">
            <div class="input-field col s12">

              <input type="text" id="email" class="autocomplete" />
              <label for="email">Email</label>
            </div>
          </div>

          <div class="row">
            <div class="input-field col s12">

              <input type="password" id="password" class="autocomplete" />
              <label for="password">Password</label>
            </div>
          </div>

          <button href="#!" className="waves-effect waves-light btn modal-trigger">Login</button>
        </form>




      </div>
    </div>
  );
};


export default adminLogin;