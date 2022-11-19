import React from 'react';
import './css/App.css';
import {createBrowserRouter, Link, RouterProvider} from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import Polls from "./Polls";
import Vote from "./Vote";
import Register from "./Register";
import Login from "./Login";
import useToken from "./useToken";

export default function Home() {
    const { token, setToken } = useToken();
    const handleLoginRedirect = async e => {
        e.preventDefault();

        if (!token) {
            return "/login";
        }
        else {
            return "/polls";
        }
    }

    const handleRegisterRedirect = async e => {
        e.preventDefault();
        return (
            <Register />
        );
    }

    return (
        <div>
            <h3>Feedback Application</h3>
            <Container fluid>
                <Button>
                    <Link to="/login">Login</Link>
                </Button>
                <Button>
                    <Link to="/register">Sign up</Link>
                </Button>
            </Container>
        </div>
    )
}


class HomeC extends React.Component {
    render() {
        return (
            <div>
                <h3>Feedback Application</h3>
                <Container fluid>
                    <Button>
                        <Link to="/login">Login</Link>
                    </Button>
                    <Button>
                        <Link to="/register">Sign up</Link>
                    </Button>
                </Container>
            </div>
        );
    }
}
//export default Home;
