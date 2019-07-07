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
        <form>
          <h4>Sign in</h4>
          <div className="row">
            <div className="input-field col s12">

              <input type="text" id="email" class="autocomplete" />
              <label for="email">Email</label>
            </div>
          </div>

          <div className="row">
            <div className="input-field col s12">

              <input type="password" id="password" className="autocomplete" />
              <label for="password">Password</label>
            </div>
          </div>

          <button href="#!" className="waves-effect waves-light btn modal-trigger">Login</button>
        </form>




      </div>
    </div>
  );
};


export default login;