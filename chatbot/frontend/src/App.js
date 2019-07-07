import React, {Component} from 'react';
import './App.css';
import Navbar from './components/Navbar/Navbar';
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
     

      <Footer/>
    </div>
  
  );
}
}

export default App;
