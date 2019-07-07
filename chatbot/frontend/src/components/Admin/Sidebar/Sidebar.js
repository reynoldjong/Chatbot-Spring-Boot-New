import React from 'react';
import classes from './Sidebar.module.css';
import { Link } from "react-router-dom";
const sidebar = () =>{
    return(
        <div className={classes.Sidebar}>
            
            <h5>Dashboard</h5>
            <Link to="/"> <a> Home </a> </Link>
            <Link to="/">  <a href="/"> Add/Remove </a></Link>
            <Link to="/">   <a href="/"> View Users </a></Link>
            
            <Link to="/">  <a href="/"> View Stats </a></Link>

        </div>

    )
}
export default sidebar;