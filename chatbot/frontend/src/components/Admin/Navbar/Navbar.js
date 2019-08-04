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
    flexGrow: 1
  },
  menuButton: {
    marginRight: theme.spacing(1)
  },
  title: {
    marginRight: theme.spacing(2),
    marginLeft: theme.spacing(1),
    flexGrow: 1,

    color: "white",
    "&:hover": { color: "rgb(38, 166, 154)", transition: "0.5s ease-in" }
  },
  button: {
    margin: theme.spacing(1)
  }
}));
/**
 *  Component for the navigation bar in the home page
 * @param {props} props:
 *  @param {bool} loggedin - bool represented whether the user is logged in or not
 *  @param {function} logOutHandler - function for logging the user out
 */
const Navbar = props => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Typography href="https://www.digitalfinanceinstitute.org/"variant="h6" className={classes.title}>
           
              <img src={logo} alt="DFI Logo" />{" "}
      
          </Typography>

          <Typography variant="h6" align="left" style={{marginRight:'20px'}}>
            <Link className={classes.title} to="/">
              {" "}
              Home{" "}
            </Link>
            <Link className={classes.title} to="/admin/crawler">
              {" "}
              Crawler{" "}
            </Link>
            <Link className={classes.title} to="/admin">
              Document Management{" "}
            </Link>
            <Link className={classes.title} to="/admin/feedback">
              {" "}
              Chatbot Ratings
            </Link>
            <Link className={classes.title} to="/admin/reset">
              {" "}
              Data Reset
            </Link>
          </Typography>
          <Button
            onClick={props.logOutHandler}
            variant="contained"
            color="secondary"
            className={classes.menuButton}
          >
            Logout
          </Button>
        </Toolbar>
      </AppBar>
    </div>
  );
};
export default Navbar;
