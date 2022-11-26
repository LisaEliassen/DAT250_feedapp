import React, {useEffect, useState} from "react";
import axios from "axios";
import AppNavbar from './AppNavbar';
import { Link, useLocation, useNavigate} from 'react-router-dom';

import {Button, ButtonGroup, Container, Table} from "reactstrap";
import useToken from "./useToken";

export default function Polls() {
    const navigate = useNavigate();
    const { token } = useToken();
    const [polls, setPolls] = useState([]);

    const remove = async (id) => {
        await axios.delete('http://localhost:8080/polls/'+id)
            .then(() => {
            let updatedPolls = [...polls].filter(i => i.pollID !== id);
            setPolls(updatedPolls);
        })
    }

    const handlePollCreationNav = () => {
        navigate('/create_poll', {
            state: {
                token: token,
            }
        });
    }

    useEffect(() => {
        fetch('http://localhost:8080/polls'
        ).then((res) => res.json())
            .then((polls) => {
                setPolls(polls);
                console.log(polls);
            });
    }, [polls]);

    const refreshPage = () => {
        window.location.reload(false);
    }

    if (token == null) {
        return(
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/users"></Button>
                    </div>
                    <h3>Polls</h3>
                    <Table className="poll-list" width="70%" style={{textAlign:'left'}}>
                        <thead>
                        <tr>
                            <th width="10%">PollID</th>
                            <th width="25%">Title</th>
                            <th width="15%">Category</th>
                            <th width="15%">Open</th>
                            <th width="10%">Public</th>
                        </tr>
                        </thead>
                        <tbody>
                        {polls && polls.map((poll) => (
                            <tr key={poll.pollID}>
                                <td style={{whiteSpace: 'nowrap'}}>{poll.pollID}</td>
                                <td>{poll.title}</td>
                                <td>{poll.category}</td>
                                <td>{(poll.openPoll) ? "Yes" : "No"}</td>
                                <td>{(poll.publicPoll) ? "Yes" : "No"}</td>
                                <td>
                                    <div>
                                        {poll.publicPoll && (
                                            <ButtonGroup>
                                                <Button size="sm" color="primary" tag={Link} to={"/vote/" + poll.pollID}>Vote</Button>
                                            </ButtonGroup>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <br></br>
                    <Button onClick={() => refreshPage()}>Refresh</Button>
                    <Button onClick={() => handlePollCreationNav()}>Create a poll</Button>
                </Container>
            </div>
        );
    }
    else {
        return(
            <div>
                <AppNavbar/>
                <Container fluid>
                    <h3>Polls</h3>
                    <Table className="poll-list" width="70%" style={{textAlign:'left'}}>
                        <thead>
                        <tr>
                            <th width="10%">PollID</th>
                            <th width="25%">Title</th>
                            <th width="15%">Category</th>
                            <th width="15%">Open</th>
                            <th width="10%">Public</th>
                        </tr>
                        </thead>
                        <tbody>
                        {polls && polls.map((poll) => (
                            <tr key={poll.pollID}>
                                <td style={{whiteSpace: 'nowrap'}}>{poll.pollID}</td>
                                <td>{poll.title}</td>
                                <td>{poll.category}</td>
                                <td>{(poll.openPoll) ? "Yes" : "No"}</td>
                                <td>{(poll.publicPoll) ? "Yes" : "No"}</td>
                                <td>
                                    <div>
                                        {token!=null && (
                                            <ButtonGroup>
                                                {token.userID == poll.userID && (
                                                    <div>
                                                        <Button size="sm" color="primary">
                                                            <Link to={"/vote/" + poll.pollID}>Vote</Link>
                                                        </Button>
                                                        <Button size="sm">
                                                            <Link to={"/edit_poll/" + poll.pollID}>Edit</Link>
                                                        </Button>
                                                        <Button size="sm" color="danger" onClick={() => remove(poll.pollID)}>Delete</Button>
                                                    </div>
                                                )}
                                                {token.userID != poll.userID && (
                                                    <div>
                                                        <Button size="sm" color="primary">
                                                            <Link to={"/vote/" + poll.pollID}>Vote</Link>
                                                        </Button>
                                                    </div>
                                                )}
                                            </ButtonGroup>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <br></br>
                    <Button onClick={() => refreshPage()}>Refresh</Button>
                    <Button onClick={() => handlePollCreationNav()}>Create a poll</Button>
                </Container>
            </div>
        );
    }
}
