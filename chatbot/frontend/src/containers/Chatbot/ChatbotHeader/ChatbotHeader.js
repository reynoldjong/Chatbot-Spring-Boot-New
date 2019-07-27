import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import IconButton from "@material-ui/core/IconButton";
import Minimize from "@material-ui/icons/Minimize";
import Feedback from "@material-ui/icons/Feedback";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import MenuIcon from '@material-ui/icons/Menu';
import { makeStyles, useTheme } from "@material-ui/core/styles";
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import FeedbackModal from '../Feedback/Feedback';
const useStyles = makeStyles(theme => ({
  root: {
    flex: '1 ',
    borderTopLeftRadius: "10px",
    borderTopRightRadius: "10px"
  },
  shadow: {
    borderTopLeftRadius: "10px",
    borderTopRightRadius: "10px"
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {flexGrow:1},
  menuButton: {
    
   
    right:'5px',

   
  },

}));
/**
 * Componenet representing header of chatbot
 */
const ChatbotHeader = props => {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);

  function handleClick(event) {
    setAnchorEl(event.currentTarget);
  }

  function handleClose() {
    setAnchorEl(null);
  }
  return (<React.Fragment>
  
    <AppBar position="static" className={classes.root}>
     
      <Box boxShadow={3} >

        <Toolbar>
        <IconButton edge="start" onClick={handleClick} className={classes.menuButton} color="inherit" aria-label="menu">
            <MenuIcon />
          </IconButton>
         
        <Typography variant="h5" className={classes.title}>
            {props.title}
          </Typography>
         
         
          <IconButton
              edge="end"
              aria-label="account of current user"
              style={{position:'relative',bottom:'5px'}}
              onClick={props.clickHandler}
              color="inherit"
            >
              <Minimize style={{position:'relative',bottom:'5px'}}/>
            </IconButton>
         
        </Toolbar>
      </Box>
    </AppBar>

      <Menu
      id="simple-menu"
      anchorEl={anchorEl}
      keepMounted
      open={Boolean(anchorEl)}
      onClose={handleClose}
    >
      <MenuItem onClick={handleClose}>  <FeedbackModal /></MenuItem>
      
    </Menu>
    </React.Fragment>
  );
};

export default ChatbotHeader;

/**
 * <IconButton
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="Minimize"
            onClick={props.clickHandler}
          >
            <Button><Feedback className={classes.menuButton} style={{ fontSize: "1.3em" }} /></Button>
            <Button    onClick={props.clickHandler}> <Minimize className={classes.menuButton} style={{ fontSize: "1.3em" }} /></Button>
            
          </IconButton>
 */