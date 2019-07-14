import React from 'react';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';
import Fab from '@material-ui/core/Fab';
import SendIcon from '@material-ui/icons/Send';
import Icon from '@material-ui/core/Icon';
import { grey } from '@material-ui/core/colors';
import Paper from '@material-ui/core/Paper';
import remove from './MessageInput.module.css';

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


const MessageInput = () => {

    const handleChange = name => event => {
        setValues({ ...values, [name]: event.target.value });
    };

    const [values, setValues] = React.useState({
        name: '',
    });

    const classes = useStyles();
    return (

        <div className={classes.root}>
            <Paper>


                <Grid container spacing={0}>
                    <Grid item xs={10}>
                        <div className={remove.removeStyles}>
                            <TextField
                                id="standard-name"
                                label="Name"
                                className={classes.textField}
                                value={values.name}
                                style={{ width: '95%' }}
                                margin="normal"
                                onChange={handleChange('name')}
                            />
                        </div>
                    </Grid>
                    <Grid item xs={2}>
                        <Fab color="primary" aria-label="Send" className={classes.icon}>
                            <SendIcon />
                        </Fab>
                    </Grid>

                </Grid>
            </Paper>
        </div>
    );
};

export default MessageInput;