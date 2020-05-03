import React from "react";
import Particles from "react-particles-js";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
/**
 * Component for the hero image in Home
 */
const useStyles = makeStyles(theme => ({
  root: {
    zIndex: "-999",
    width: "100vw",
    height: "90vh",
    backgroundImage: "linear-gradient(#1e3c72, #2a5298)",
    overflowY: "hidden"
  },

  title: {
    flexGrow: 1,
    top: "30%",
    position: "relative",
    color: "white"
  }
}));

const Hero = () => {
  const classes = useStyles();
  const particleOptions = {
    particles: {
      number: {
        value: 80
      }
    }
  };
  return (
    <React.Fragment>
      <Container maxWidth={false} className={classes.root}>
        <Typography
          className={classes.title}
          component="h3"
          variant="h3"
          align="center"
        >
          DFI's Next Solution
        </Typography>
        <Box className={classes.title} marginTop={5}>
          <Typography component="h6" variant="h6" align="center">
            Think Tank for Financial Technology, Artificial Intelligence &
            Blockchain
          </Typography>
        </Box>
        <Particles params={particleOptions} />
      </Container>
    </React.Fragment>
  );
};
export default Hero;
