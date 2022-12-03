import React, {useEffect, useState} from "react";
import AppNavbar from './AppNavbar';
import {useNavigate} from 'react-router-dom';

import {Button, ButtonGroup, Container, Table} from "reactstrap";
import useToken from "./useToken";

export default function Polls() {
    const navigate = useNavigate();
    const { token, deleteToken } = useToken();
    const [polls, setPolls] = useState([]);
    const [user, setUser] = useState([]);

    const handlePollCreationNav = () => {
        navigate('/create_poll', {replace:true});
    }

    useEffect(() => {
        fetch('http://localhost:8080/polls'
        ).then((res) => res.json())
            .then((polls) => {
                setPolls(polls);
                //console.log(polls);
            });
        if (token != null) {
            fetch('http://localhost:8080/users/'+token.userID
            ).then((res) => res.json())
                .then((user) => {
                    setUser(user);
                });
        }
    }, [polls]);


    const refreshPage = () => {
        window.location.reload(false);
    }

    const handleVote = (pollID) => {
        navigate('/vote/'+pollID);
    }

    const handleEdit = (pollID) => {
        navigate('/edit_poll/'+pollID);
    }

    const handleLogOut = () => {
        deleteToken();
        window.location.reload(false);
    }

    const handleLogin = () => {
        navigate('/login');
    }

    if (token == null) {
        return(
            <div>
                <AppNavbar/>
                <br></br>
                <div>
                    <Button style={{flex: 'auto', width: 60, height: 30, float: 'right', color:"#00005c", marginRight: "43%"}}
                        onClick={handleLogin}>Login</Button>{' '}
                </div>
                <Container fluid>
                    <h3>Polls</h3>
                    <Table className="poll-list" width="70%" style={{textAlign:'left'}}>
                        <thead>
                        <tr>
                            <th width="10%">PollID</th>
                            <th width="25%">Question</th>
                            <th width="15%">Category</th>
                            <th width="15%">Open</th>
                            <th width="10%">Public</th>
                        </tr>
                        </thead>
                        <tbody>
                        {polls && polls.map((poll) => (
                            <tr key={poll.pollID}>
                                <td style={{whiteSpace: 'nowrap'}}>{poll.pollID}</td>
                                <td>{poll.question}</td>
                                <td>{poll.category}</td>
                                <td>{(poll.openPoll) ? "Yes" : "No"}</td>
                                <td>{(poll.publicPoll) ? "Yes" : "No"}</td>
                                <td>
                                    <div>
                                        {(poll.publicPoll && poll.openPoll) && (
                                            <ButtonGroup>
                                                <Button onClick={() => handleVote(poll.pollID)}>Vote</Button>
                                            </ButtonGroup>
                                        )}
                                        {(!poll.publicPoll || !poll.openPoll) && (
                                            <ButtonGroup>
                                                <Button disabled={true}>Vote</Button>
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
                <div>
                    Logged in as: {user.username}
                </div>
                <br></br>
                <div>
                    <Button style={{flex: 'auto', width: 60, height: 30, float: 'right', color:"#00005c", marginRight: "43%"}}
                            onClick={handleLogOut}>Logout</Button>
                </div>
                <Container fluid>
                    <h3>Polls</h3>
                    <Table className="poll-list" width="70%" style={{textAlign:'left'}}>
                        <thead>
                        <tr>
                            <th width="10%">PollID</th>
                            <th width="25%">Question</th>
                            <th width="15%">Category</th>
                            <th width="15%">Open</th>
                            <th width="10%">Public</th>
                        </tr>
                        </thead>
                        <tbody>
                        {polls && polls.map((poll) => (
                            <tr key={poll.pollID}>
                                <td style={{whiteSpace: 'nowrap'}}>{poll.pollID}</td>
                                <td>{poll.question}</td>
                                <td>{poll.category}</td>
                                <td>{(poll.openPoll) ? "Yes" : "No"}</td>
                                <td>{(poll.publicPoll) ? "Yes" : "No"}</td>
                                <td>
                                    <div>
                                        {token!=null && (
                                            <ButtonGroup>
                                                {token.userID == poll.userID && (
                                                    <div>
                                                        <Button disabled={!poll.openPoll}
                                                                onClick={() => handleVote(poll.pollID)}>Vote</Button>
                                                        <Button onClick={() => handleEdit(poll.pollID)}>Edit</Button>
                                                    </div>
                                                )}
                                                {(token.userID != poll.userID && poll.openPoll) && (
                                                    <div>
                                                        <Button disabled={!poll.openPoll}
                                                                onClick={ () => handleVote(poll.pollID)}>Vote</Button>
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
