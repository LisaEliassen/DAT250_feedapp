import React, {useState} from "react";
import axios from "axios";
import AppNavbar from './AppNavbar';
import { Link, useLocation, useNavigate} from 'react-router-dom';

import {Button, ButtonGroup, Container, Form, FormGroup, Input, Label, Table} from "reactstrap";
import useToken from "./useToken";


export default function PollCreate() {
    const navigate = useNavigate();
    const { token } = useToken();
    let poll = {
        title: "",
        category: "",
        openPoll: false,
        publicPoll: false
    }

    const sendPoll = () => {
        const json = JSON.stringify({
            title: poll.title,
            category: poll.category,
            openPoll: poll.openPoll,
            publicPoll: poll.publicPoll
        });
        axios.post('http://localhost:8080/polls/'+token.userID, json, {
            headers: {
                'Content-Type': 'application/json'
            }}
        ).then(res => {
            console.log(res.data)
        })
            .catch(error => {
                console.log(error.response)
            });

        navigate('/polls', {
            state: {
                token: token,
            }
        });
    }

    const handleInputChange = (event) => {
        const target = event.target;
        let parameter = target.name;
        if (target.type == "checkbox") {
            if (target.checked == true) {
                poll[parameter] = true;
            }
            else {
                poll[parameter] = false;
            }
            console.log(poll[parameter]);
            console.log(target.checked)
        }
        else {
            event.preventDefault();
            poll[parameter] = target.value
        }
    }

    if (token == null) {
        return(
            <div>
                <AppNavbar/>
                <Container>
                    You need to be logged in to make a poll!
                    <br></br>
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
    else {
        return(
            <div>
                <AppNavbar/>
                <Container>
                    <h3>Create a poll</h3>
                    <Form onSubmit={sendPoll}>
                        <FormGroup>
                            <Label for="title">Title</Label>
                            <Input type="text" name="title" id="title"
                                   onChange={handleInputChange} autoComplete="title"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="category">Category</Label>
                            <Input type="text" name="category" id="category"
                                   onChange={handleInputChange} autoComplete="category"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="openPoll">Open Poll</Label>
                            <Input type="checkbox" name="openPoll" id="openPoll" defaultChecked={false}
                                   onChange={handleInputChange} autoComplete="openPoll"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="publicPoll">Public Poll</Label>
                            <Input type="checkbox" name="publicPoll" id="publicPoll" defaultChecked={false}
                                   onChange={handleInputChange} autoComplete="publicPoll"/>
                        </FormGroup>

                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>
                            <Button color="secondary">
                                <Link to="/polls">Cancel</Link>
                            </Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }

}
