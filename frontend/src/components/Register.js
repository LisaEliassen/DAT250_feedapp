import React from "react";
import axios from "axios";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link, useNavigate} from "react-router-dom";

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            firstName: "",
            lastName: "",
            password: "",
            passwordVerify: "",
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        event.preventDefault();
        const target = event.target;
        this.setState({
            [target.name]: target.value,
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        if (this.state.password === this.state.passwordVerify) {
            const json = JSON.stringify({
                username: this.state.username,
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                password: this.state.password,
            });
            //console.log(json);
            axios.post('http://localhost:8080/users', json, {
                headers: {
                    // Overwrite Axios's automatically set Content-Type
                    'Content-Type': 'application/json'
                }}
            ).then(res => console.log(res.data))
                .catch(error => {
                    console.log(error.response)
                });
            this.props.navigate('/login');
        }
    }

    render() {
        return (
            <div>
                <AppNavbar/>
                <Container>
                    <h3>Sign up</h3>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="username">Username</Label>
                            <Input type="text" name="username" id="username"
                                   onChange={this.handleInputChange} autoComplete="username"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="firstName">Firstname</Label>
                            <Input type="text" name="firstName" id="firstName"
                                   onChange={this.handleInputChange} autoComplete="firstName"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="lastName">Lastname</Label>
                            <Input type="text" name="lastName" id="lastName"
                                   onChange={this.handleInputChange} autoComplete="lastName"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="password">Password</Label>
                            <Input type="password" name="password" id="password"
                                   onChange={this.handleInputChange} autoComplete="password"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="passwordVerify">Confirm Password</Label>
                            <Input type="password" name="passwordVerify" id="passwordVerify"
                                   onChange={this.handleInputChange} autoComplete="passwordVerify"/>
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/">Cancel</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

// Wrap and export
export default function(props) {
    const navigate = useNavigate();

    return <Register {...props} navigate={navigate} />;
}
