import React, {Component} from 'react';
import './App.css';
import Footer from './components/Footer/Footer';
import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Home from './components/Home/Home';
import Admin from './components/Admin/Admin';
import axios from 'axios';

class App extends Component {
  state ={
    loggedIn: false,
    showModal:false,
  }
  
  modalClickHandler = () =>{
    const loggedIn = !this.state.loggedIn;
    this.setState({
      ...this.state,
      showModal:loggedIn
    });
    console.log('Done');

  }

  render(){
    return (
    <div className="App">

      <Router>
       
        
        <Route 
          exact path="/" 
          render={(props)=> <Home {...props} loggedIn={this.state.loggedIn} showModal={this.state.showModal} modalClickHandler={this.modalClickHandler}/>}
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
