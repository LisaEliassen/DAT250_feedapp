import React, {useEffect, useState} from "react";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import useToken from "./useToken";
import axios from "axios";

export default function PollEdit() {
    const navigate = useNavigate();
    const { token } = useToken();
    const { id } = useParams();
    const [poll, setPoll] = useState([]);
    const [updatedPoll, setUpdatedPoll] = useState([{
        title: poll.title,
        category: poll.category,
        openPoll: poll.openPoll,
        publicPoll: poll.publicPoll
    }]);
    //console.log(updatedPoll);

    const getPoll = async () => {
        const response = await fetch('http://localhost:8080/polls/'+ id
        ).then((res) => res.json());
        setPoll(response);
        console.log(response);
    }

    useEffect(() => {
        fetch('http://localhost:8080/polls/'+ id
        ).then((res) => res.json())
            .then((poll) => {
                setPoll(poll);
                console.log(poll);
                setUpdatedPoll({
                    title: poll.title,
                    category: poll.category,
                    openPoll: poll.openPoll,
                    publicPoll: poll.publicPoll
                });
            });
        //setPoll(response);

    }, []);
    console.log(updatedPoll);

    const updatePoll = (event) => {
        event.preventDefault();
        console.log(updatedPoll);
        const json = JSON.stringify({
            title: updatedPoll.title,
            category: updatedPoll.category,
            openPoll: updatedPoll.openPoll,
            publicPoll: updatedPoll.publicPoll
        });
        axios.put('http://localhost:8080/polls/'+id, json, {
            headers: {
                'Content-Type': 'application/json'
            }}
        ).then(res => console.log(res.data))
            .catch(error => {
                console.log(error.response)
            });

        navigate('/polls', {replace:true});

    }

    const handleInputChange = (event) => {
        const target = event.target;
        let parameter = target.name;

        //event.preventDefault();
        setUpdatedPoll({
            ...updatedPoll,
            [parameter]: target.value
        })
        //updatedPoll[parameter] = target.value
    }

    const handleOpenPoll = () => {
        setUpdatedPoll({
            ...updatedPoll,
            openPoll: !updatedPoll.openPoll
        });
        //console.log(updatedPoll);
    }

    const handlePublicPoll = () => {
        setUpdatedPoll({
            ...updatedPoll,
            publicPoll: !updatedPoll.publicPoll
        });
        //console.log(updatedPoll);
    }

    if (token == null && token.userID == poll.userID) {
        return(
            <div>
                <AppNavbar/>
                <Container>
                    You need to be logged in as the poll's owner to edit this poll!
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
                    <h3>Edit poll {id}</h3>
                    <Form onSubmit={updatePoll}>
                        <FormGroup>
                            <Label for="title">Title</Label>
                            <Input type="text" name="title" id="title" defaultValue={poll.title || ''}
                                   onChange={handleInputChange} autoComplete="title"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="category">Category</Label>
                            <Input type="text" name="category" id="category" defaultValue={poll.category || ''}
                                   onChange={handleInputChange} autoComplete="category"/>
                        </FormGroup>
                        <FormGroup>
                            Open poll: {(updatedPoll.openPoll) ? "Yes" : "No"}
                            <Button type="button" onClick={handleOpenPoll}>{(updatedPoll.openPoll) ? "Close poll" : "Open poll"}</Button>
                            <br></br>
                            Public poll: {(updatedPoll.publicPoll) ? "Yes" : "No"}
                            <Button type="button" onClick={handlePublicPoll}>Change to {(updatedPoll.publicPoll) ? "private" : "public"}</Button>
                        </FormGroup>

                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>
                        </FormGroup>
                        <FormGroup>
                            <Button color="secondary">
                                <Link to="/polls">Cancel</Link>
                            </Button>
                        </FormGroup>
                        <FormGroup>
                            <Button>Delete poll</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}


