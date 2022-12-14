import {useNavigate, useParams} from "react-router-dom";
import useToken from "./useToken";
import React, {useEffect, useState} from "react";
import AppNavbar from "./AppNavbar";
import {Button, Container} from "reactstrap";


export default function PollResult() {
    let { id } = useParams();
    const navigate = useNavigate();
    const { token } = useToken();
    const [poll, setPoll] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/polls/'+ id
        ).then((res) => res.json())
            .then((poll) => {
                updatePoll(poll);
            });
    }, [poll]);

    const updatePoll = (poll) => {
        setPoll(poll);
    }

    const refreshPage = () => {
        window.location.reload(false);
    }

    const handlePollsNav = () => {
        navigate('/polls', {
            state: {
                token: token,
            }
        });
    }

    return(
        <div>
            <AppNavbar/>
            <Container fluid>
                <h1>Current Result for Poll {id}</h1>
                <h3>Question: {poll.question}</h3>
                <br></br>
                <div>
                    Yes count: {poll.yesCount}
                </div>
                <div>
                    No count: {poll.noCount}
                </div>
                <br></br>
                <Button onClick={() => refreshPage()}>Refresh</Button>
                <Button onClick={() => handlePollsNav()}>Back to polls</Button>
            </Container>
        </div>
    );
}
