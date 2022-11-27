import React, {useEffect, useState} from "react";
import axios from "axios";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import useToken from "./useToken";

function DeviceVote() {
    const { token } = useToken();
    const navigate = useNavigate();
    const [device, setDevice] = useState([]);
    const [poll, setPoll] = useState([]);
    const [vote, setVote] = useState([]);
    const { id } = useParams();

    useEffect(() => {
        fetch('http://localhost:8080/polls/'+id
        ).then((res) => res.json())
            .then((poll) => {
                setPoll(poll);
            });
        if (token != null) {
            fetch('http://localhost:8080/devices/'+token.deviceID
            ).then((res) => res.json())
                .then((device) => {
                    setDevice(device);
                });
        }
    }, [poll, device]);

    const handleInputChange = (event) => {
        setVote(event.target.value);
    }

    const handlePollResultNav = () => {
        navigate('/poll_result/'+id, {replace:true});
    }

    const handleSubmit = e => {
        e.preventDefault();
        console.log(vote);
        const json = JSON.stringify({
            vote: vote
        });
        axios.post('http://localhost:8080/votes/device/'
                + id + '/' + token.deviceID, json, {
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

    return (
        <div>
            <h3>Vote on poll {poll.pollID}</h3>
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <div className="radio">
                        <label>
                            <input
                                type="radio"
                                value="Yes"
                                checked={vote === "Yes"}
                                onChange={handleInputChange}
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
                                onChange={handleInputChange}
                            />
                            No
                        </label>
                    </div>
                </FormGroup>
                <br></br>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>
                    <Button color="secondary">
                        <Link to="/">Cancel</Link>
                    </Button>
                </FormGroup>
            </Form>
        </div>
    );
}

export default DeviceVote;
