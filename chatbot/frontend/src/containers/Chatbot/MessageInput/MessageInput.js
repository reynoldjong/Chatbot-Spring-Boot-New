import React from 'react';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import SendIcon from '@material-ui/icons/Send';
import Icon from '@material-ui/core/Icon';
import { grey } from '@material-ui/core/colors';
import Paper from '@material-ui/core/Paper';
import removeClasses from './MessageInput.module.css';

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
        borderTop: '1px solid #ccc',
        margin: '0px',
        padding: '0px',
        height:'100px'
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
    textField: {
        marginBottom: '5px',
        marginTop:'0px'


    },
    icon:{
        marginTop:'6px',
  
    }
}));


const MessageInput = (props) => {

    const classes = useStyles();
    return (

        <div className={classes.root}>
             <form onSubmit={props.addMessageHandler} autocomplete="off">
            <Paper>


                <Grid container spacing={0}>
               
                    <Grid item xs={10}>
                 
                        <div className={removeClasses.Remove}>
                           

                            <TextField
                                id="standard-name"
                                label="message"
                                name="userMessage"
                                className={classes.textField}
                                autocomplete="off"
                                style={{ width: '95%' }}
                                margin="normal"
                              
                            />
                        </div>
                    </Grid>
                    <Grid item xs={2}>
                        <Fab color="primary" aria-label="Send" className={classes.icon} type="submit">
                            <SendIcon />
                        </Fab>
                      
                    </Grid>
              
                </Grid>
            </Paper>
            </form> 
        </div>
    );
};

export default MessageInput;