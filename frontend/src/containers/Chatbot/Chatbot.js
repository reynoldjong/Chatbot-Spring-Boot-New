import React from "react";
import ChatbotHeader from "./ChatbotHeader/ChatbotHeader";
import Container from "@material-ui/core/Container";
import ChatbotBody from "./ChatbotBody/ChatbotBody";
import { makeStyles } from "@material-ui/core/styles";
import MessageInput from "./MessageInput/MessageInput";
import Chat from "@material-ui/icons/Chat";
import Fab from "@material-ui/core/Fab";
import Grow from "@material-ui/core/Grow";
import axios from "axios";
import shadow from "./Chatbot.module.css";
import media from "./Chatbot.module.css";

const useStyles = makeStyles(theme => ({
  root: {
    display: "flex",
    flexDirection: "column",
    flexWrap: "no-wrap",
    padding: "0px"
  },

  bottomRightPosition: {
    position: "fixed",
    bottom: "3vh",
    right: "1vw"
  },
  paper: {
    position: "absolute",
    width: 400,
    backgroundColor: theme.palette.background.paper,
    border: "2px solid #000",
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 4),
    outline: "none"
  }
}));

// bot has the following props:
//  1) text
//  2) link
//  3) picture

// User has the following props:
// 1) text

/**
 * Component representing DFI's chatbot
 */
const Chatbot = () => {
  const classes = useStyles();

  /**
   * State holds information on messages sent by user and chatbot,
   * @param {bool} showChatbot - boolean that will show the chatbot if true otherwise false
   * @param {dict} showing -contains keys for question and answer which represent an index for the visible question and answer
   */
  const [values, setValues] = React.useState({
    messages: [
      {
        type: "bot",
        watson: {
          message:
            "Hello and welcome to DFI!  The future Chatbot, click a message to highlight it"
        },
        lucene: {}
      }
    ],
    showChatbot: true,
    showing: { question: 0, answer: 0 }
  });

  React.useEffect(() => {
    console.log("refreshed");
  });

  /**
   *  Function controls which message is visible.  If a user message is clicked that messages index and that
   * index + 1 becomes the question answer pair in showing.  If a bot message is clicked then that index and that index -1 becomes
   * the answer question pair
   * @param {string} type- type of message clicked (bot or user)
   * @param {int} index - index of the message being clicked in the functional state
   *                        messages array
   */
  const showClickHandler = (type, index) => {
    // Need length of messages array to check for out of bounds cases
    const length = values.messages.length;
    
    if (type === "bot") {
      // Make sure that this doesn't apply to greeting message which has index 0
      if (index !== 0) {
        setValues({
          ...values,
          showing: { question: index, answer: index - 1 }
        });
      }
    } else {
      // Make sure there is an answer to follow up with
      if (index + 1 < length) {
        setValues({
          ...values,
          showing: { question: index + 1, answer: index }
        });
      }
    }
  };

  /**
   * Converts a users question into  GET url
   * @param {string} message
   */
  const convertToGet = message => {
    const messageArray = message.split(" ");
    const length = messageArray.length;
    let getMessage = "";
    messageArray.forEach(function(item, index) {
      if (index === length - 1) {
        getMessage += item;
      } else {
        getMessage += item + "+";
      }
    });
    return getMessage;
  };

  /**
   * Retreives what watson returns in the text portion of its JSON
   * @response
   */
  const getWatsonMessage = response => {
    let message = "";
    if (response["data"]["watson"]["text"]) {
      message = response["data"]["watson"]["text"];
    }
    return message;
  };

  /**
   * Retreives what Lucene returns in the text portion of its JSON
   * @response
   */
  const getLuceneMessage = response => {
    let message = "";
    if (response["data"]["lucene"]["text"]) {
      message = response["data"]["lucene"]["text"];
    }
    return message;
  };

  /**
   * Retreives what Lucene returns in the file passage portion of its JSON
   * @response
   */
  const getLuceneFilePassage = response => {
    let file = "";
    if (response["data"]["lucene"]["file"]) {
      file = response["data"]["lucene"]["file"]["passage"];
    }
    return file;
  };

  /**
   * Retreives what Lucene returns in the file passage portion of its JSON
   * @response
   */
  const getLuceneFilename = response => {
    let filename = "";
    if (response["data"]["lucene"]["file"]) {
      filename = response["data"]["lucene"]["file"]["filename"];
    }
    return filename;
  };

  /**
   * Retreives what Watson returns in the file passage portion of its JSON
   * @response
   */
  const getWatsonFileName = response => {
    let filename = "";
    if (response["data"]["watson"]["file"]) {
      filename = response["data"]["watson"]["file"]["filename"];
    }
    return filename;
  };
  /**
   * Retreives what Watson returns in the file passage portion of its JSON
   * @response
   */
  const getWatsonFilePassage = response => {
    let file = "";
    if (response["data"]["watson"]["file"]) {
      file = response["data"]["watson"]["file"]["passage"];
    }
    return file;
  };

  /**
   * Retreives what Watson returns in the image portion of its JSON
   * @response
   */
  const getWatsonImage = response => {
    let image = null;

    if (response["data"]["watson"]["image"]) {
      image = response["data"]["watson"]["image"].replace(/['"]+/g, "");
    }

    return image;
  };

  /**
   * Retreives what Lucene returns in the image portion of its JSON
   * @response
   */
  const getLuceneImage = response => {
    let image = null;
    if (response["data"]["lucene"]["image"]) {
      image = response["data"]["lucene"]["image"].replace(/['"]+/g, "");
    }
    return image;
  };

  /**
   * Retreives what Watson returns in the url portion of its JSON
   * @response
   */
  const getWatsonLink = response => {
    let link = null;
    if (response["data"]["watson"]["url"]) {
      link = response["data"]["watson"]["url"];
    }

    return link;
  };

  /**
   * Retreives what Lucene returns in the url portion of its JSON
   * @response
   */
  const getLuceneLink = response => {
    let link = null;

    if (response["data"]["lucene"]["url"]) {
      link = response["data"]["lucene"]["url"];
    }
    return link;
  };

  /**
   * Builds a JSON with every Watson element in it to be used in the Message component
   * @response
   */
  const getWatsonObject = response => {
    let watsonObject = {};
    watsonObject["picture"] = getWatsonImage(response);
    watsonObject["link"] = getWatsonLink(response);
    watsonObject["filename"] = getWatsonFileName(response);
    watsonObject["file"] = getWatsonFilePassage(response);
    watsonObject["type"] = "bot";
    watsonObject["message"] = getWatsonMessage(response);
    return watsonObject;
  };
  /**
   * Builds a JSON with every Lucene element in it to be used in the Message component
   * @response
   */
  const getLuceneObject = response => {
    let luceneMessage = {};
    luceneMessage["picture"] = getLuceneImage(response);
    luceneMessage["link"] = getLuceneLink(response);
    luceneMessage["filename"] = getLuceneFilename(response);
    luceneMessage["file"] = getLuceneFilePassage(response);
    luceneMessage["type"] = "bot";
    luceneMessage["message"] = getLuceneMessage(response);
    return luceneMessage;
  };

    /**
   * Function scrolls to bottom when new messages are added
   */
  const scrollBottom = () => {
    if (document.getElementById("bottom")) {
      document.getElementById("bottom").scrollIntoView({ behavior: "smooth" });
    }
  };

  /**
   *  Sends a get request to /userQuery with the users message
   * and also adds their message to messages in the functional state
   * @event
   */
  const addMessageHandler = event => {
    event.preventDefault();
    const target = event.target;
    const userMessage = target.userMessage.value;
    // reset the submit field
    target.userMessage.value = "";
    // Make sure users message is not empty
    if (userMessage.length === 0 || /^\s*$/.test(userMessage)) {
    } else {
      const newMessages = [
        ...values.messages,
        { type: "user", message: userMessage }
      ];
      setValues({ ...values, messages: newMessages });

      const getMessage = convertToGet(userMessage);

      axios
        .get("/userquery?" + getMessage)
        .then(response => {
          // build the botMessage response object

          let botMessage = {};
          botMessage["watson"] = getWatsonObject(response);
          botMessage["lucene"] = getLuceneObject(response);
          botMessage["type"] = "bot";

          const newMessagesBot = [...newMessages, botMessage];
          // The current selected question should be the last two items in the messages array
          const arrayLength = newMessagesBot.length;
          setValues({
            ...values,
            showing: { question: arrayLength - 1, answer: arrayLength - 2 },
            messages: newMessagesBot
          });
         scrollBottom();
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    scrollBottom();
  };

  /**
   * Function changes the function states showChatbot field
   * to either true or false determining whether the chatbot
   * is visible or not
   */
  const chatbotClickHandler = () => {
    const newStatus = !values.showChatbot;
    setValues({ ...values, showChatbot: newStatus });
  };

  let chatbot = null;
  // if the showChatbot field is not false display the chatbot
  // object otherwise display the icon
  if (values.showChatbot) {
    chatbot = (
      <React.Fragment>
        <Grow in={values.showChatbot}>
          <Container
            className={
              media.bottomRightPosition +
              " " +
              classes.root +
              " " +
              shadow.Shadow +
              " " +
              media.Chatbot
            }
            
          >
            <ChatbotHeader
              title="DFI Agent Alex"
              clickHandler={chatbotClickHandler}
            />

            <ChatbotBody
              showClickHandler={showClickHandler}
              messages={values.messages}
              showing={values.showing}
            />

            <MessageInput addMessageHandler={addMessageHandler} />
          </Container>
        </Grow>
      </React.Fragment>
    );
  } else {
    chatbot = (
      <Fab
        edge="start"
        className={media.bottomRightPosition}
        color="secondary"
        aria-label="Menu"
        onClick={chatbotClickHandler}
      >
        <Chat />
      </Fab>
    );
  }

  return (
    <React.Fragment>
      {chatbot}
      <audio
        src=""
        style={{ display: "none" }}
        preload="none"
        controls="controls"
        type="audio/mp3"
        id="player"
      />
    </React.Fragment>
  );
};

export default Chatbot;
