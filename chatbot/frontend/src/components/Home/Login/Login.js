import React from 'react';
import classes from './Login.module.css';
import TextField from '@material-ui/core/TextField';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';

/**
 *  Component for logging into our Chatbot service (only for admins)
 * @param {props} props:
 *  @param {bool} showModal - bool representing whether the login modal should be visible or hidden
 *  @param {function} loginHandler - function for authenticating the user in the backend and updating the state for loggedIn
 */
const login = (props) => {
  let displayStyle = null;

  // if the showModal prop is true then we show the modal by making display block
  // otherwise the modal is not visible and the display becomes none

  if (props.showModal === true) {
    displayStyle = { display: 'block' };
  }
  else {
    displayStyle = { display: 'none' };
  }
 
  return (
    <div style={displayStyle} className={classes.background}>
      <div className={classes.Modal}>
        <Container>
          <form onSubmit={props.loginHandler} autoComplete="off" method="post">

            <h4>Sign in</h4>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <TextField
                  id="username"
                  label="Username"
                  margin="normal"
                  style={{ width: '100%' }}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  id="password"
                  label="Password"
                  type="password"
                  margin="normal"
                  style={{ width: '100%' }}
                />
              </Grid>
              <Grid item xs={6}>
              <button type="submit" href="#!" className="waves-effect waves-light btn modal-trigger">Login</button>
              </Grid>
              <Grid item xs={6}>
            <button className="waves-effect waves-light btn modal-trigger" onClick={props.modalClickHandler}>Cancel</button>
            </Grid>
            </Grid>
          
          </form>
        </Container>
      </div>
    </div>
  );
};


export default login;