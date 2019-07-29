import React from "react";
import PlayArrow from "@material-ui/icons/PlayArrow";
import classes from "./Sound.module.css";
import Speech from "speak-tts";

class Sound extends React.Component {
  state = {
    playing: false
  };

  handlePlay = () => {
    this.speak();
    console.log(this.state.uri);
    this.setState({
      playing: true
    });
  };

  speak = () => {
    const speech = new Speech();

    if (speech.hasBrowserSupport()) {
      // returns a boolean
      console.log("speech synthesis supported");
    }
    speech.setLanguage("en-US");

    speech
      .speak({
        text: this.props.text,
        queue: false, // current speech will be interrupted,
        listeners: {
          onstart: () => {
            console.log("Start utterance");
          },
          onend: () => {
            console.log("End utterance");
          },
          onresume: () => {
            console.log("Resume utterance");
          },
          onboundary: event => {
            console.log(
              event.name +
                " boundary reached after " +
                event.elapsedTime +
                " milliseconds."
            );
          }
        }
      })
      .then(() => {
        console.log("Success !");
      })
      .catch(e => {
        console.error("An error occurred :", e);
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
