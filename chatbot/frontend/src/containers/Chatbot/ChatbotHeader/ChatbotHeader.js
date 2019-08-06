import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";
import IconButton from "@material-ui/core/IconButton";
import Minimize from "@material-ui/icons/Minimize";
import Toolbar from "@material-ui/core/Toolbar";
import MenuIcon from "@material-ui/icons/Menu";
import { makeStyles } from "@material-ui/core/styles";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import FeedbackModal from "../Feedback/Feedback";
import media from "./ChatbotHeader.module.css";
const useStyles = makeStyles(theme => ({
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: { flexGrow: 1 }
}));
/**
 * Component representing header of chatbot
 */
const ChatbotHeader = props => {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);

  // Opens chatbot menu
  function handleClick(event) {
    setAnchorEl(event.currentTarget);
  }
  // Closes chatbot menu
  function handleClose() {
    setAnchorEl(null);
  }
  return (
    <React.Fragment>
      <AppBar position="static" className={media.root}>
        <Box boxShadow={3}>
          <Toolbar>
            <IconButton
              edge="start"
              onClick={handleClick}
              className={classes.menuButton}
              color="inherit"
              aria-label="menu"
            >
              <MenuIcon />
            </IconButton>

            <Typography variant="h5" className={classes.title}>
              {props.title}
            </Typography>

            <IconButton
              edge="end"
              aria-label="account of current user"
              style={{ position: "relative", bottom: "5px" }}
              onClick={props.clickHandler}
              color="inherit"
            >
              <Minimize style={{ position: "relative", bottom: "5px" }} />
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
        <MenuItem onClick={handleClose}>
          {" "}
          <FeedbackModal />
        </MenuItem>
      </Menu>
    </React.Fragment>
  );
};

export default ChatbotHeader;
