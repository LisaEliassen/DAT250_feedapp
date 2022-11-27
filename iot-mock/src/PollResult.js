import {Link, useNavigate, useParams} from "react-router-dom";
import useToken from "./useToken";
import React, {useEffect, useState} from "react";
import {Button, ButtonGroup, Container, Table} from "reactstrap";

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

    return(
        <div>
            <Container fluid>
                <h1>Current Result for Poll {id}</h1>
                <h3>Question: {poll.title}</h3>
                <br></br>
                <div>
                    Yes count: {poll.yesCount}
                </div>
                <div>
                    No count: {poll.noCount}
                </div>
                <br></br>
                <Button onClick={() => refreshPage()}>Refresh</Button>
            </Container>
        </div>
    );
}
