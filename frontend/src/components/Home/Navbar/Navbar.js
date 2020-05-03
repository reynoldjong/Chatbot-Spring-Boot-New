import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import logo from "./images/DFILogo.png";
import { Link } from "react-router-dom";
const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    position: "absolute",
    width: "100%",
    overflowX:'hidden',
  },
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: {
    flexGrow: 1,
    marginRight: theme.spacing(5),
    color:'white',
    '&:hover': { color:'rgb(38, 166, 154)',transition:'0.5s ease-in'},
  },
  button: {
    margin: theme.spacing(1)
  },
  input: {
    display: "none"
  },
  navbar: {
    backgroundColor: "rgba(0,0,0,0.0)"
  }
}));
/**
 *  Component for the navigation bar in the home page
 * @param {props} props:
 *  @param {bool} loggedin - bool represented whether the user is logged in or not
 *  @param {function} logOutHandler - function for logging the user out
 */
const Navbar2 = props => {
  const classes = useStyles();
  let navbarLinks = null;

  if (props.loggedIn) {
    navbarLinks = (
      <React.Fragment>
        <Link to="/admin" >
        <Typography variant="h6" className={classes.title} >Admin Dashboard</Typography>
        </Link>
          <Button
            onClick={props.logOutHandler}
            variant="contained"
            color="secondary"
            className={classes.button}
          >
            Logout
          </Button>
    
      </React.Fragment>
    );
  } else {
    navbarLinks = (
      <Button
        onClick={props.modalClickHandler}
        variant="contained"
        color="secondary"
        className={classes.button}
      >
        Admin Login
      </Button>
    );
  }

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.navbar} elevation={0}>
        <Toolbar>
          <Typography href="https://www.digitalfinanceinstitute.org/" variant="h6" className={classes.title}>
          <img src={logo} alt="DFI Logo" />
          </Typography>

          {navbarLinks}
        </Toolbar>
      </AppBar>
    </div>
  );
};
export default Navbar2;
