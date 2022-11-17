import React, { Component } from 'react';
import Home from './Home';
import {BrowserRouter as Router, createBrowserRouter, Route, RouterProvider, Switch} from 'react-router-dom';
import Polls from './Polls';
import Vote from './Vote';
import Register from './Register';
import Login from './Login';

const router = createBrowserRouter([
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
        element: <Login />,
    }
]);

class App extends Component {
  render() {
    return (
        <RouterProvider router={router}/>
    )
  }
}

export default App;
