import React from 'react';
import './css/App.css';
import {Link} from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import useToken from "./useToken";

export default function Home() {
    const { token, deleteToken} = useToken();

    const handleLogOut = () => {
        deleteToken();
        window.location.reload();
    }

    if (token != null) {
        return (
            <div>
                <h3>Feedback Application</h3>
                <Container fluid>
                    <Button onClick={handleLogOut}>Logout</Button>
                    <br></br>
                    <Button>
                        <Link to="/polls">Go to polls</Link>
                    </Button>
                </Container>
            </div>
        )
    }
    else {
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
                    <br></br>
                    <Button>
                        <Link to="/polls">Go to polls</Link>
                    </Button>
                </Container>
            </div>
        )
    }
}
