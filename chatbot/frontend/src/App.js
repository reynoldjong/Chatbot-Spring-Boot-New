import React, {Component} from 'react';
import './App.css';
import Footer from './components/Footer/Footer';
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Home from './components/Home/Home';
import Admin from './components/Admin/Admin';
import axios from 'axios';

class App extends Component {
  state ={
    loggedIn: true,
    showModal:false,
  }
  
  login = (ev) =>{

  }

  render(){
    return (
    <div className="App">

      <Router>
       
        
        <Route 
          exact path="/" 
          render={(props)=> <Home {...props} loggedIn={this.state.loggedIn} showModal={this.state.showModal}/>}
          />

        
        <Route 
          path="/admin/" 
          render={(props)=> <Admin {...props} loggedIn={this.state.loggedIn}/>}
          />

        <Router/>

      </Router>
      <Footer/>
    </div>
  
  );
}
}

export default App;
