import React, { useEffect } from "react";
import DocumentTable from "./DocumentsTable/DocumentsTable";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import DragAndDrop from "./DragAndDrop/DragAndDrop";
import axios from "axios";
import qs from 'qs';
import Navbar from '../Navbar/Navbar';
import PieChart from './PieChart/PieChart'

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2)
  },
  dragAndDrop: {
    width: "100%",
    height: "300px",
    backgroundColor: "rgba(0,0,0,0.1)",
    border: "1px solid #ccc",
    padding: theme.spacing(2),
    borderRadius: "5px"
  }
}));

/**
 * Compoenent representing all the documents the admin has uploaded
 * and has the abillity for admins to uplaod or remove documents
 * @param {props} props:
 *  @param {list} files - a list containing every file in Files.db
 *  @param {function} addFileHandler - function which adds a file to the database
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const AdminDocuments = props => {
  // when component is first loaded we should load all the files in the database
  useEffect(() => {
    props.viewAllFilesHandler();
    props.viewGraphHandler();
  }, []);
  const classes = useStyles();

  const [values, setFiles] = React.useState({
    files: []
  });
  const handleDrop = files => {
    let fileList = values.files;
    for (var i = 0; i < files.length; i++) {
      if (!files[i].name) {
        return;
      } else {
        fileList.push(files[i]);
      }
    }
    setFiles({ files: fileList });
  };

  /**
   * Makes a request to the backend through a post request which
   * adds a file to Files.db
   * @event
   */
  const addAllFilesHandler = async listFiles => {
    for (let i = 0; i < listFiles.length; i++) {
      addFileHandler(listFiles[i]);
    }
    // empty fiels lsit
    setFiles({ files: [] });
    props.viewAllFilesHandler();
  };

  /**
   * Makes a request to the backend through a post request which
   * adds a file to Files.db
   * @event
   */
  const addFileHandler = async file => {
    // Get the data from event and add it to a form

    let data = new FormData();
    data.append("action", "upload");
    data.append("file", file);

    await axios
      .post("/handlefiles", data)
      .then(response => {
        // viewAllFilesHandler needs to be called to update the file list being displayed
       
      })
      .catch(function(error) {
        console.log(error);
      });
      props.viewAllFilesHandler();
  };

  /**
   * function downloads an export of all data uploaded to Watson
   */
  const getCsvOfData = async ()=>{
    let data = new FormData();
    data.append("getQueries", "please");

        // Create JSON object
        const data2 = {
          "getQueries": "please",
          
        }

    await axios
      .post("/getdata", qs.stringify(data2))
      .then(response => {
        // viewAllFilesHandler needs to be called to update the file list being displayed
        console.log(response);
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'documents.csv'); //or any other extension
        document.body.appendChild(link);
        link.click();

      })
      .catch(function(error) {
        console.log(error);
      });
  };

  // makes background a bit darker so it's easier on the eyes
  document.body.style = "background: rgba(0,0,0,0.05);";
console.log(props);
  let admin = null;
  if(props.loggedIn === true){
    admin = (<React.Fragment>
      <Navbar logOutHandler={props.logOutHandler}/>
      <Box marginTop={3}>
        <Container maxWidth="md">
          <Paper className={classes.root}>
            <Typography variant="h4" component="h4" align="left">
              File Upload
            </Typography>
            <Typography variant="body1" component="p" align="left">
              Drag and drop or select a file to train the chatbot
            </Typography>
            <Box
              className={classes.dragAndDrop}
              color="secondary"
              marginTop={3}
              align="center"
            >
              <DragAndDrop handleDrop={handleDrop}>
                <div style={{ height: "250px", width: "800px" }}>
                  {values.files.map((file, i) => (
                    <div key={i}>
                      <li>{file.name}</li>
                    </div>
                  ))}
                </div>
              </DragAndDrop>
            </Box>
            <Button
              onClick={() => addAllFilesHandler(values.files)}
              type="submit"
              color="secondary"
              variant="contained"
              style={{
                display: "block",
                marginTop: "20px",
                position: "relative",
                marginLeft: "auto"
              }}
            >
              upload files
            </Button>
          </Paper>
        </Container>
      </Box>

      <Box marginTop={3} marginBottom={3}>
        <Container maxWidth="md">
          <Paper className={classes.root}>
            <Typography variant="h4" component="h4" align="left">
              What's In The Chatbot's Brain?
            </Typography>
            <Typography
              variant="body1"
              component="p"
              align="left"
              style={{ margin: "10px" }}
            >
              Take a look at what DFI's chatbot is thinking about
            </Typography>
            <DocumentTable
              removeFileHandler={props.removeFileHandler}
              files={props.files}
            />
          </Paper>
        </Container>
      </Box>
            <Box marginTop={3} marginBottom={3}>
              <Container maxWidth="md">
                <Paper className={classes.root}>
                  <Typography variant="h4" component="h4" align="left">
                    What Did Users Bomb The Chatbot With?
                  </Typography>
                    <Button
                      onClick={getCsvOfData}
                      type="submit"
                      color="secondary"
                      variant="contained"
                      style={{
                        display: "block",
                        marginTop: "20px",
                        position: "relative",
                        marginLeft: "auto",
                        marginBottom:'20px',
                      }}
                    >
                      export to csv
                    </Button>
                   <PieChart
                     vTitle="vTitle"
                     hTitle="hTitle"
                     initialPoints={props.queries}
                   />
                </Paper>
              </Container>
            </Box>
    </React.Fragment>)
  }
  else{
    admin = <h2>Not Logged In</h2>
  }
  return (
    
    <React.Fragment>
    {admin}
    </React.Fragment>
  );
};

export default AdminDocuments;
