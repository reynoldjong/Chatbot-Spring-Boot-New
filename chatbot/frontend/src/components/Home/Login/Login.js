import React from 'react';
import classes from './Login.module.css';
import TextField from '@material-ui/core/TextField';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
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
        <Container>
          <form onSubmit={props.loginHandler} autocomplete="off" method="post">

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