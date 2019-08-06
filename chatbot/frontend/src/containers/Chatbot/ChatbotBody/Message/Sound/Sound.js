import React from "react";
import PlayArrow from "@material-ui/icons/PlayArrow";
import classes from "./Sound.module.css";
import Speech from "speak-tts";

/**
 * Component represents a playbutton that users can click to play a message
 * that Lucene or Watson responsds with
 */
class Sound extends React.Component {
  /**
   * playing controls whether or not the text to speech message plays
   */
  state = {
    playing: false
  };

  /**
   * Sets the state of playing to true and activates the speak function
   */
  handlePlay = () => {
    this.speak();
    
    this.setState({
      playing: true
    });
  };

  /**
   * Plays back the message from Watson or Lucene using speak-tts
   */
  speak = () => {
    const speech = new Speech();

    if (speech.hasBrowserSupport()) {
      // returns a boolean
     // console.log("speech synthesis supported");
    }
    speech.setLanguage("en-US");

    speech
      .speak({
        text: this.props.text,
        queue: false, // current speech will be interrupted,
        listeners: {
          onstart: () => {
         //   console.log("Start utterance");
          },
          onend: () => {
        //    console.log("End utterance");
          },
          onresume: () => {
         //   console.log("Resume utterance");
          },
          onboundary: event => {
          /*  console.log(
              event.name +
                " boundary reached after " +
                event.elapsedTime +
                " milliseconds."
            );*/
          }
        }
      })
      .then(() => {
        //console.log("Success !");
      })
      .catch(e => {
       // console.error("An error occurred :", e);
      });
  };
  render() {
    return (
      <div>
        <PlayArrow className={classes.playButton} onClick={this.handlePlay} />
      </div>
    );
  }
}

export default Sound;
