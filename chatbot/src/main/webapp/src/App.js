import React, {Component} from 'react';
import Chatbot from './containers/Chatbot/Chatbot';
import './App.css';

import Navbar from './components/Navbar/Navbar';
import Hero from './components/Hero/Hero';
import LogoBar from './components/Navbar/LogoBar/LogoBar';
import MiddleNav from './components/Navbar/MiddleNav/MiddleNav';
import Footer from './components/Footer/Footer';


class App extends Componenet {
  render(){
    return (
    <div className="App">
      <LogoBar/>
      <Navbar/>
      <MiddleNav/>
      <Hero/>
      <Chatbot/>
      <Footer/>
    </div>
  
  );
}
}

export default App;
