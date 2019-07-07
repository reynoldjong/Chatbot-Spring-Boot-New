import React, {Component} from 'react';
import './App.css';
import Navbar from './components/Navbar/Navbar';
import Footer from './components/Footer/Footer';

import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Home from './components/Home/Home';


class App extends Component {
  state ={
    loggedIn: false,
  }
  render(){
    return (
    <div className="App">
      
      <Navbar loggedIn={this.state.loggedIn}/>

      <Home/>
     

      <Footer/>
    </div>
  
  );
}
}

export default App;
