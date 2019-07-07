import React, {Component} from 'react';
import './App.css';
import Navbar from './components/Navbar/Navbar';
import Footer from './components/Footer/Footer';

import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import Home from './components/Home/Home';
import Admin from './components/Admin/Admin';

class App extends Component {
  state ={
    loggedIn: true,
  }
  render(){
    return (
    <div className="App">

      <Router>
        <Navbar loggedIn={this.state.loggedIn}  home={Home} admin={Admin}/>
        
        <Route path="/" exact component={Home}/>

        
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
