import React, {Component} from 'react';
import Chatbot from './containers/Chatbot/Chatbot';
import './App.css';

import Navbar from './components/Navbar/Navbar';
import LogoBar from './components/Navbar/LogoBar/LogoBar';
import MiddleNav from './components/Navbar/MiddleNav/MiddleNav';
import Footer from './components/Footer/Footer';

import Home from './components/Home/Home';

class App extends Component {
  state ={
    loggedIn: false,
  }
  render(){
    return (
    <div className="App">
      <Navbar/>
      <Home/>

      <Chatbot/>
      <Footer/>
    </div>
  
  );
}
}

export default App;
