import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Modal from "@material-ui/core/Modal";
import Button from "@material-ui/core/Button";
import IconButton from '@material-ui/core/IconButton'
import TextField from "@material-ui/core/TextField";
import axios from "axios";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import CloseIcon from '@material-ui/icons/Close'
import media from "./Feedback.module.css";
import qs from "qs";
const useStyles = makeStyles(theme => ({
  paper: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 4)
  },
  formControl: {
    width: "200px",
    display: "block"
  },
    closeButton: {
      position: 'absolute',
      right: theme.spacing.unit / 2,
      top: theme.spacing.unit / 2,
      color: theme.palette.grey[500],
    }
}));

/**
 * Component represents a modal for users to submit feedback on the chatbot
 */
const Feedback = () => {
  const classes = useStyles();

  /**
   * variables hold info on whetehr modal is open or not
   */
  const [open, setOpen] = React.useState(false);

  /**
   * values used to contain feedback on chatbot
   */
  const [values, setValues] = React.useState({
    rating: 1,
    comment: ""
  });

  /**
   * function opens the modal
   */
  const handleOpen = () => {
    setOpen(true);
  };

  /**
   * function closes the modal
   */
  const handleClose = () => {
    setOpen(false);
  };

  /**
   * Function is sued to submit feedback on how the chatbot is rpeforming
   * @param {int} rating - rating of the chatbot
   * @param {string} comment - a comment the user has about the chatbot
   */
  const submitFeedback = async (rating, comment) => {
    let data = {
      comments: comment,
      rating: rating
    };
    await axios
      .post("/feedback", qs.stringify(data))
      .then(response => {})
      .catch(function(error) {
        console.log(error);
      });
    handleClose();
  };

  /**
   * function is used to record what the user rated the chatbot
   * @event
   */
  function handleChangeSelect(event) {
    setValues(oldValues => ({
      ...oldValues,
      rating: event.target.value
    }));
  }

  /**
   * function is used to record users message on the chatbot
   * @event
   */
  const handleChangeText = event => {
    setValues({ ...values, comment: event.target.value });
  };

  return (
    <div>
      <Button type="button" onClick={handleOpen}>
        Feedback
      </Button>
      <Modal
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
        open={open}
        onClose={handleClose}
      >
        <div className={classes.paper + " " + media.paper}>
        <div>
        <h4 id="modal-title">Let us know how we did!</h4>
        <IconButton aria-label="Close" className={classes.closeButton} onClick={handleClose}>
          <CloseIcon />
        </IconButton>
          <p>Any questions you didn’t get a response?  Let us know here!</p>
        </div>
          <TextField
            id="comment"
            label="Response"
            multiline
            rowsMax="20"
            style={{ width: "100%" }}
            onChange={handleChangeText}
            margin="normal"
            variant="outlined"
          />
          <FormControl className={classes.formControl}>
            <InputLabel htmlFor="rating">How did we do?</InputLabel>
            <Select
              value={values.rating}
              onChange={handleChangeSelect}
              inputProps={{
                name: "Rating",
                id: "rating"
              }}
            >
              <MenuItem value={1}>1</MenuItem>
              <MenuItem value={2}>2</MenuItem>
              <MenuItem value={3}>3</MenuItem>
              <MenuItem value={4}>4</MenuItem>
              <MenuItem value={5}>5</MenuItem>
            </Select>
          </FormControl>

          <Button
            type="button"
            onClick={() => submitFeedback(values.rating, values.comment)}
            variant="contained"
            style={{ float: "right", display: "block" }}
            color="secondary"
          >
            Submit your Response
          </Button>
        </div>
      </Modal>
    </div>
  );
};

export default Feedback;
