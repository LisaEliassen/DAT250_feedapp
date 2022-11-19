import React, { Component, useState} from 'react';
import Home from './Home';
import {BrowserRouter as Router, createBrowserRouter, Route, RouterProvider, Switch} from 'react-router-dom';
import Polls from './Polls';
import Vote from './Vote';
import Register from './Register';
import Login from './Login';
import useToken from './useToken';


function MyRouter() {
    const { token, setToken } = useToken();

    return createBrowserRouter([
        {
            path: "/",
            element: <Home />,
        },
        {
            path: "/polls",
            element: <Polls />,
        },
        {
            path: "/vote/:id",
            element: <Vote />,
        },
        {
            path: "/register",
            element: <Register />,
        },
        {
            path: "/login",
            element: <Login setToken={setToken} />
        }
    ]);
}

function App() {


    /*if(!token) {
        return <Login setToken={setToken} />
    }*/

    return (
        <RouterProvider router={MyRouter()}/>
    )




}

export default App;
