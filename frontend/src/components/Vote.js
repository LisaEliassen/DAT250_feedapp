import React, {useEffect, useState} from "react";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import useToken from "./useToken";
import axios from "axios";

export default function Vote() {
    let { id } = useParams();
    const navigate = useNavigate();
    const { token } = useToken();
    const [poll, setPoll] = useState([]);
    const [vote, setVote] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/polls/'+ id
        ).then((res) => res.json())
            .then((poll) => {
                setPoll(poll);
            });
    }, []);


    const handleChange = (event) => {
        setVote(event.target.value);
    }

    const handlePollResultNav = () => {
        navigate('/poll_result/'+id, {replace:true});
    }

    const sendVote = () => {
        console.log(vote);
        const json = JSON.stringify({
            vote: vote
        });
        axios.post('http://localhost:8080/votes/'+ id + '/' + token.userID, json, {
            headers: {
                'Content-Type': 'application/json'
            }}
        ).then(res => {
            console.log(res.data)
        })
            .catch(error => {
                console.log(error.response)
            });


        handlePollResultNav();
    }

    if (token != null) {
        return(
            <div>
                <AppNavbar/>
                <Container>
                    <h1>Vote on poll {id}</h1>
                    <h3>Question: {poll.title}</h3>
                    <Form onSubmit={sendVote}>
                        <FormGroup>
                            <div className="radio">
                                <label>
                                    <input
                                        type="radio"
                                        value="Yes"
                                        checked={vote === "Yes"}
                                        onChange={handleChange}
                                    />
                                    Yes
                                </label>
                            </div>
                            <div className="radio">
                                <label>
                                    <input
                                        type="radio"
                                        value="No"
                                        checked={vote === "No"}
                                        onChange={handleChange}
                                    />
                                    No
                                </label>
                            </div>
                        </FormGroup>
                        <br></br>
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
    else {
        return(
            <div>
                <AppNavbar/>
                <Container>
                    You need to be logged in to vote!
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


}
