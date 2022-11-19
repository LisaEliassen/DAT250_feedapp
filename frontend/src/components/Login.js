import React, {useState} from "react";
import axios from "axios";
import AppNavbar from "./AppNavbar";
import PropTypes from 'prop-types';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import Polls from "./Polls";
import {useNavigate} from "react-router-dom";
import Register from "./Register";


async function loginUser(credentials) {
    return fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}

export default function Login({ setToken }) {
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const navigate = useNavigate();

    const handleSubmit = async e => {
        e.preventDefault();

        const token = await loginUser({
            username,
            password
        });
        if (token != null) {
            setToken(token);
            console.log("Logged in!")
            navigate("/polls", { replace: true });
        }
        else {
            console.log("Wrong username or password")
            navigate("/login", { replace: true });
        }
    }

    return(
        <div className="login-wrapper">
            <h1>Please Log In or Register</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    <p>Username</p>
                    <input type="text" onChange={e => setUserName(e.target.value)} />
                </label>
                <label>
                    <p>Password</p>
                    <input type="password" onChange={e => setPassword(e.target.value)} />
                </label>
                <div>
                    <button type="submit">Log in</button>
                </div>
            </form>
        </div>
    )
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};


class Login2 extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loginUser: {
                username: "",
                password: "",
            },
            databaseUser: {
                username: "",
                password: "",
            },
            loggedIn: false
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        event.preventDefault();
        const target = event.target;
        const property = target.name;
        this.state.loginUser[property] = target.value;
    }

    getUser(username) {
        let databaseUser = {
            username: "",
            password: ""
        }
        axios.get('http://localhost:8080/users/username/'+username.toString()).then(res => {
            console.log(res.data);
            const data = res.data;

            databaseUser.username =data.username.toString()
            databaseUser.password =data.password.toString();
            this.state.databaseUser.username = data.username.toString();
            this.state.databaseUser.password = data.password.toString();
            return databaseUser;
        });
        return databaseUser;
    }

    handleSubmit(event) {
        event.preventDefault();
        const dbUser =  this.getUser(this.state.loginUser.username);
        console.log(this.state.databaseUser);

        if (dbUser.username.toString() === this.state.loginUser.username.toString()
                && dbUser.password.toString() === this.state.loginUser.password.toString()) {
            this.state.loggedIn = true;
            this.props.navigate('/polls');
        }
        else {
            console.log("Wrong username or password");
        }
    }

    render() {
        return (
            <div>
                <AppNavbar/>
                <Container>
                    <h3>Login</h3>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="username">Username</Label>
                            <Input type="text" name="username" id="username"
                                   onChange={this.handleInputChange} autoComplete="username"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="password">Password</Label>
                            <Input type="text" name="password" id="password"
                                   onChange={this.handleInputChange} autoComplete="password"/>
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Login</Button>{' '}
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

/*export default function(props) {
    const navigate = useNavigate();

    return <Login {...props} navigate={navigate} />;
}*/
