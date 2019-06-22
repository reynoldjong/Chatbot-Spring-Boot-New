import React from 'react';
import './App.css';

import Navbar from './components/Navbar/Navbar';
import Hero from './components/Hero/Hero';
import LogoBar from './components/Navbar/LogoBar/LogoBar';
import MiddleNav from './components/Navbar/MiddleNav/MiddleNav';
import Footer from './components/Footer/Footer';
function App() {
  return (
    <div className="App">
      <LogoBar/>
      <Navbar/>
      <MiddleNav/>
      <Hero/>
      <Footer/>
    </div>
  
  );
}

export default App;
