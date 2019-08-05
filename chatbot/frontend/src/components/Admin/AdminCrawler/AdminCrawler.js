import React, { useEffect } from "react";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import Button from "@material-ui/core/Button";
import axios from "axios";
import qs from "qs";
import Navbar from "../Navbar/Navbar";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import CrawlerTable from './CrawlerTable/CrawlerTable';
const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3, 2)
  }
}));

/**
 * Component representing all the documents the admin has uploaded
 * and has the abillity for admins to uplaod or remove documents
 * @param {props} props:
 *  @param {list} feedback - a list containing every file in Files.db
 *  @param {function} addFileHandler - function which adds a file to the database
 *  @param {function} removeFileHandler - function which removes a file from the database
 */
const AdminCrawler = props => {
  // when component is first loaded we should load all the files in the database

  const classes = useStyles();

  const [crawledStatus, setCrawledStatus] = React.useState("");
  const [sites, setSites] = React.useState([]);

  const crawl = async event => {
    // Create JSON object
    let url = event.target.url.value;
    let depth = event.target.depth.value;

    // initialize variables with a default value if the user inputed nothing for some reason
    if (url === null) {
      url = "";
    }

    if (event.target.depth.value === null) {
      depth = 0;
    }

    event.preventDefault();
    const data = {
      action: "crawl",
      url: url,
      depth: depth
    };

    await axios
      .post("/webcrawler", qs.stringify(data))
      .then(response => {
        setCrawledStatus("Successfully crawled " + url);
        getCrawledSites();
      })
      .catch(function(error) {
        setCrawledStatus("An error has occured for " + url);
        console.log(error);
      });
  };

  /**
   * Function retrieves all the crawled sites from the database
   */
  const getCrawledSites = async () =>{
     
    await axios
      .get("/webcrawler")
      .then(response => {
          console.log(response)
        //let listSites = []
        //for(var key in response['date']){
         //   listSites.push(response['data'][key])
        //}
        setSites(response['data']);
      })
      .catch(function(error) {
        console.log(error);
      });
  }
  /**
   * Function removes a site from the list of crawled sites in the database
   * thus removing its data from being displayed back to the user
   * @param {string} url- a site url that is to be removed from the database
   */
  const removeSiteHandler = async url => {
    const data = {
      action: "remove",
      url: url
    };
    await axios
      .post("/webcrawler", qs.stringify(data))
      .then(response => {
      console.log(response)
        getCrawledSites();
      })
      .catch(function(error) {
        console.log(error);
      });
  };
  // When component is loaded crawled sites should be displayed
  useEffect(() => {
    getCrawledSites();
  }, []);
  // Admin dashboard background color should be a bit darker for stylistic reasons
  document.body.style = "background: rgba(0,0,0,0.05);";
  let admin = null;
  if (props.loggedIn) {
    admin = (
      <React.Fragment>
        <Navbar logOutHandler={props.logOutHandler} />

        <Box marginTop={3} marginBottom={3}>
          <Container maxWidth="md">
            <Paper className={classes.root}>
              <Typography variant="h4" component="h4" align="left">
                Crawl Websites To Help The Chatbot Learn
              </Typography>
              <Typography
                variant="body1"
                component="p"
                align="left"
                style={{ margin: "10px" }}
              >
                Input a URL and the Number of pages and let our web crawler deal
                with the rest.
              </Typography>
              <form onSubmit={crawl}>
                <Grid container spacing={0}>
                  <Grid item xs={12}>
                    <TextField
                      id="standard-name"
                      name="url"
                      label="URL"
                      margin="normal"
                      style={{ width: "50%" }}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      id="standard-uncontrolled"
                      name="depth"
                      label="Depth"
                      margin="normal"
                      style={{ width: "50%" }}
                    />
                  </Grid>
                </Grid>

                <Button
                  type="submit"
                  color="secondary"
                  variant="contained"
                  style={{
                    display: "block",
                    marginTop: "20px",
                    position: "relative",
                    marginLeft: "auto",
                    marginBottom: "20px"
                  }}
                >
                  Crawl Website
                </Button>
              </form>
              <Typography>{crawledStatus}</Typography>
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
              Take a look at what websites the chatbot is thinking about
            </Typography>
          

            <CrawlerTable
              sites={sites}
              removeSiteHandler={removeSiteHandler}
            />
          </Paper>
        </Container>
      </Box>
      </React.Fragment>
    );
  } else {
    admin = <h2>Not Logged in</h2>;
  }
  return <React.Fragment>{admin}</React.Fragment>;
};

export default AdminCrawler;
