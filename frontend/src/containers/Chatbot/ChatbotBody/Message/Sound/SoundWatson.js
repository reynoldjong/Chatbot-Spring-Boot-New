import React from "react";
import PlayArrow from "@material-ui/icons/PlayArrow";
import classes from "./Sound.module.css";
import axios from "axios";
import qs from "qs";

/**
 * Component represents a playbutton that users can click to play a message
 * that Lucene or Watson responsds with
 */
class SoundWatson extends React.Component {
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

  speak = async () => {
    const data = {
      ttsText: this.props.text
    }
    await axios
    .post("/tts", qs.stringify(data))
    .then(response => {
      let audio = new Audio("data:audio/wav;base64," + response['data']);
      audio.play()
    })
    .catch(e => {
      console.error("error", e)
    })
  }


  render() {
    return (
      <div>
        <PlayArrow className={classes.playButton} onClick={this.handlePlay} />
      </div>
    );
  }
}

export default SoundWatson;
