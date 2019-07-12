import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Home from './components/Home/Home';
import Admin from './components/Admin/Admin';
import axios from 'axios';
import qs from 'qs';

class App extends Component {
  state ={
    loggedIn: true,
    showModal:false,
    files:[],
  }
  
  modalClickHandler = () =>{
    const loggedIn = !this.state.loggedIn;
    this.setState({
      ...this.state,
      showModal:loggedIn
    });
    console.log('Done');

  }

  logOutHandler = () =>{
    const loggedOut = false;
    this.setState({
      ...this.state,
      loggedIn:loggedOut
    });
  }

  loginHandler = (event) =>{
    event.preventDefault();
    const target = event.target;
    const username1 = target.elements.username.value;
    const password1 = target.elements.password.value;

    const data ={
      username:username1,
    password:password1    }

   axios.post('/login', qs.stringify(data),)
     .then( (response) =>{
     const status = response['data']['authenticated'];
     this.setState({
      ...this.state,
      showModal:false,
      loggedIn:status
    });
    })
 .catch(function (error) {
     console.log(error);
   });

   
  }

  addFileHandler = (event) => {
    event.preventDefault();
    const target = event.target;
    const file2 = target.file.files[0];
    console.log(file2.name);
    
    let data = new FormData();

    data.append("action", "upload");
    data.append("file", file2);



    let data2 = {
      action:"upload",
      file:file2
    }
    axios.post('/handlefiles', data,).then( (response) =>{
      console.log('uploaded a file?');
     })
   .catch(function (error) {
       console.log(error);
     });


    /*
    axios.post('/handlefiles', data,).then( (response) =>{
      console.log(response);
     })
   .catch(function (error) {
       console.log(error);
     });
    */
  }

  viewAllFilesHandler = () => {

    axios.get('/handlefiles',)
     .then( (response) =>{
       const data = response['data']['files'];
      this.setState({
        files:data
      })
    })
 .catch(function (error) {
     console.log(error);
   });

  }



 



  render(){
    return (
    <div className="App">

      <Router>
       
        
        <Route 
          exact path="/" 
          render={(props)=> <Home {...props} 
            loggedIn={this.state.loggedIn} 
            showModal={this.state.showModal} 
            modalClickHandler={this.modalClickHandler}
            loginHandler={this.loginHandler}
            logOutHandler={this.logOutHandler}
            />}
          />

        
        <Route 
          path="/admin" 
          render={(props)=> <Admin {...props} files={this.state.files} viewAllFilesHandler={this.viewAllFilesHandler} addFileHandler={this.addFileHandler} loggedIn={this.state.loggedIn}/>}
          />

        <Router/>

      </Router>

    </div>
  
  );
}
}

export default App;
