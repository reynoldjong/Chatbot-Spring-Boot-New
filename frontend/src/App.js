import React from 'react';
import './App.css';

import Navbar from './components/Navbar/Navbar';
import Hero from './components/Hero/Hero';
import LogoBar from './components/Navbar/LogoBar/LogoBar';
import MiddleNav from './components/Navbar/MiddleNav/MiddleNav';
function App() {
  return (
    <div className="App">
      <LogoBar/>
      <Navbar/>
      <MiddleNav/>
      <Hero/>
    </div>
  );
}

export default App;
