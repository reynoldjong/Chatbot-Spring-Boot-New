import React from "react";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import classes from "./Message.module.css";
import SoundClass from "./Sound/Sound";
import Rating from './Rating/Rating';
/**
 * Component representing a repsosne from watson and lucene
 * @param {*} props
 *  @param {string} watsonText = message from Watson
 *  @param {string} watsonPicture = picture from Watson
 *  @param {string} watsonLink = Link from Watson
 *  @param {string} watsonFile = File from Watson
 *  @param {string} luceneText = message from Lucene
 *  @param {string} lucenePicture = picture from Lucene
 *  @param {string} luceneLink = link from Watson
 *  @param {string} luceneFile = file from Lucene
 */
const Message = props => {
  // If we recieve information from watson or lucene the messages will be filled in and displayed
  let message = null;
  let errorMessage = null;
  let messageWatson = null;
  let messageLucene = null;
  let rootClasses = null;
  let textToSpeechMessageWatson = null;
  let textToSpeechMessageLucene = null;

  /**
   * Generates a text to speech message based off of everything watson responds with
   * @param props
   * @param {string} errorMessage - if there is nothing in error message then this means watson has something
   * to respond with otherwise the text to speech message becomes the error message
   */
  const generateTextToSpeechMessage = (props, errorMessage) => {
    let message = "";
    if (errorMessage != null) {
      message = errorMessage;
    } else {
      if (props.watsonText) {
        message += props.watsonText + " ";
      }
      if (props.watsonFilename && props.watsonFile) {
        message +=
          "Also, look at what we found from " +
          props.watsonFilename +
          " " +
          props.watsonFile;
      }
    }
    return message;
  };

  /**
   * Generates a text to speech message based off of everything Lucene responds with
   * @param props
   * @param {string} errorMessage - if there is nothing in error message then this means Lucene has something
   * to respond with otherwise the text to speech message becomes the error message
   */
  const generateTextToSpeechMessageLucene = (props, errorMessage) => {
    let message = "";
    if (errorMessage != null) {
      message = errorMessage;
    } else {
      if (props.luceneText) {
        message += props.luceneText + " ";
      }
      if (props.luceneFilename && props.luceneFile) {
        message +=
          "Also, look at what we found from " +
          props.luceneFilename +
          " " +
          props.luceneFile;
      }
    }
    return message;
  };

  if (
    !props.luceneText &&
    !props.lucenePicture &&
    !props.luceneLink &&
    !props.luceneFile &&
    !props.watsonText &&
    !props.watsonPicture &&
    !props.watsonLink &&
    !props.watsonFile
  ) {
    errorMessage =
      "Oops, I couldn't find that.  I've dispatched the whole DFI team to resolve this";
  }

  textToSpeechMessageWatson = generateTextToSpeechMessage(props, errorMessage);
  textToSpeechMessageLucene = generateTextToSpeechMessageLucene(
    props,
    errorMessage
  );

  if (props.showing) {
    rootClasses = classes.root;
  } else {
    rootClasses = classes.root + " " + classes.OpacityLow;
  }
  if (props.type === "bot") {
    messageWatson = (
      <React.Fragment>
       

        <Grid
          container
          spacing={1}
          style={{ margin: "10px" }}
          className={rootClasses}
          onClick={props.showClickHandler}
        >
          
          <Grid item xs={2} style={{ marginRight: "0px" }}>
            <div className={classes.avatar2} />
          </Grid>
          
          <Grid item xs={10}>
          <SoundClass text={textToSpeechMessageWatson} />
            <p
              style={{
                textAlign: "left",
                fontWeight: "500",
                color: "#424242",
                fontSize: "0.8em",
                letterSpacing: "0.0.8em",
                marginBottom: "5px"
              }}
            >
              Alex's response{" "}
            </p>
            <Box
              boxShadow={1}
              style={{ border: "1px solid rgba(0,0,0,0.03)" }}
              className={classes.text + " " + classes.textBot}
            >
              <p
                style={{
                  fontSize: "1.0em",
                  padding: "0px",
                  marginTop: "0px",
                  marginBottom: "0px",
                  wordWrap: "break-word"
                }}
              >
                {props.watsonText ? props.watsonText : null}

                {errorMessage}
                {props.watsonLink ? (
                  <a
                    href={props.watsonLink}
                    style={{ display: "block", wordBreak: "break-word" }}
                  >
                    {props.watsonLink}
                  </a>
                ) : null}
              </p>
              {props.watsonPicture ? (
                <img
                  src={props.watsonPicture}
                  width="100px"
                  height="100px"
                  alt="DFI visual"
                />
              ) : null}
              {props.watsonFile ? (
                <p>
                  Look at what we found from{props.watsonFilename}: <br />
                  {props.watsonFile}
                </p>
              ) : null}
             
            </Box>
            <Rating style={{position:'relative', textAight:'right'}} message={props.watsonText}/>
          </Grid>
        
        </Grid>
       
       
      </React.Fragment>
    );
    if (
      props.luceneText ||
      props.lucenePicture ||
      props.luceneLink ||
      props.luceneFile
    ) {
      messageLucene = (
        <React.Fragment>
        
          <Grid
            container
            spacing={1}
            className={rootClasses}
            onClick={props.showClickHandler}
          >
            <Grid item xs={2} style={{ marginRight: "0px" }}>
              <div className={classes.avatar2} />
            </Grid>
            <Grid item xs={10}>
            <SoundClass text={textToSpeechMessageLucene} />
              <p
                style={{
                  textAlign: "left",
                  fontWeight: "500",
                  color: "#424242",
                  fontSize: "0.8em",
                  letterSpacing: "0.0.8em",
                  marginBottom: "5px"
                }}
              >
                Alex's response{" "}
               
              </p>
              <Box
                boxShadow={1}
                style={{ border: "1px solid rgba(0,0,0,0.03)" }}
                className={classes.text + " " + classes.textBot}
              >
                <p
                  style={{
                    fontSize: "1.0em",
                    padding: "0px",
                    marginTop: "0px",
                    marginBottom: "0px",
                    wordWrap: "break-word"
                  }}
                >
                  {props.luceneText ? props.luceneText : null}
                  {props.luceneLink ? (
                    <a
                      href={props.luceneLink}
                      style={{ display: "block", wordBreak: "break-word" }}
                    >
                      {props.luceneLink}
                    </a>
                  ) : null}{" "}
                </p>

                {props.lucenenPicture ? (
                  <img
                    src={props.lucenePicture}
                    width="100px"
                    height="100px"
                    alt="DFI visual"
                  />
                ) : null}

                {props.luceneFile ? (
                  <React.Fragment>
                    <p>Look at what we found from:{props.luceneFilename}</p>{" "}
                    <p>{props.luceneFile}</p>
                  </React.Fragment>
                ) : null}
              </Box>
            </Grid>
          </Grid>
        </React.Fragment>
      );
    }
  } else {
    message = (
      <div className={rootClasses} onClick={props.showClickHandler}>
        <Grid container spacing={1}>
          <Grid item xs={12} />
          <Grid item xs={12}>
            <Box
              boxShadow={1}
              style={{ border: "1px solid rgba(0,0,0,0.03)",  float: "right",
              backgroundColor: "#26a69a",
              color: "white" }}
              className={classes.textHuman + " " + classes.text}
             
            >
              <p
                style={{
                  fontSize: "1.0em",
                  padding: "0px",
                  marginTop: "0px",
                  marginBottom: "0px"
                }}
              >
                {props.text}
              </p>
            </Box>
          </Grid>
        </Grid>
      </div>
    );
  }

  return (
    <React.Fragment>
      {message}
      {messageWatson}
      {messageLucene}
    </React.Fragment>
  );
};

export default Message;
