import React, {useEffect, useState} from "react";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup} from "reactstrap";
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

    const voteDiv = () => {
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

    if (!poll.openPoll) {
        return(
            <div>
                <AppNavbar/>
                <Container>
                    <div>You cannot vote on a closed poll!</div>
                    <br></br>
                    <Button>
                        <Link to="/login">Login</Link>
                    </Button>
                    <Button>
                        <Link to="/register">Sign up</Link>
                    </Button>
                    <Button>
                        <Link to="/polls">Go to polls</Link>
                    </Button>
                </Container>
            </div>
        );
    }
    else if (poll.openPoll) {
        if (token != null) {
            return voteDiv();
        }
        else {
            if (poll.publicPoll) {
                return voteDiv();
            }
            else {
                return(
                    <div>
                        <AppNavbar/>
                        <Container>
                            <div>You need to be logged in to vote on a private poll!</div>
                            <br></br>
                            <Button>
                                <Link to="/login">Login</Link>
                            </Button>
                            <Button>
                                <Link to="/register">Sign up</Link>
                            </Button>
                            <Button>
                                <Link to="/polls">Go to polls</Link>
                            </Button>
                        </Container>
                    </div>
                );
            }
        }
    }
}
