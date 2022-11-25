import React, {useEffect, useState} from "react";
import axios from "axios";
import AppNavbar from './AppNavbar';
import { Link, useLocation, useNavigate} from 'react-router-dom';

import {Button, ButtonGroup, Container, Table} from "reactstrap";
import useToken from "./useToken";

async function getPolls(userID) {
    return fetch('http://localhost:8080/polls/'+userID, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(data => data.json())
}

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
    //remove.bind(this);

    const handlePollCreationNav = () => {
        navigate('/create_poll', {
            state: {
                token: token,
            }
        });
    }

    const getData = async () => {
        const response = await fetch('http://localhost:8080/polls'
        ).then((res) => res.json());
        await setPolls(response);
    }

    useEffect(() => {
        getData();
    }, []);

    const refreshPage = () => {
        window.location.reload(false);
    }

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
                                    <td>{(poll.isOpen) ? "Yes" : "No"}</td>
                                    <td>{(poll.isPublic) ? "Yes" : "No"}</td>
                                    <td>
                                        <ButtonGroup>
                                            <Button size="sm" color="primary" tag={Link} to={"/vote/" + poll.pollID}>Vote</Button>
                                            <Button size="sm" color="danger" onClick={() => remove(poll.pollID)}>Delete</Button>
                                        </ButtonGroup>
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




class PollsC extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            polls: []
        }
        this.remove = this.remove.bind(this);
        this.navigate = this.props.navigate;
        this.location = this.props.location;
        this.token = this.location.state.token;
        this.userID = this.location.state.token;
    }

    getData(){
        axios.get('http://localhost:8080/polls').then(res => {
            //console.log(res.data);
            this.setState({
                polls:res.data
            })
        })
    }

    handlePollCreationNav() {
        this.navigate('/create_poll', {
            state: {
                token: this.token,
            }
        });
    }

    componentDidMount() {
        this.getData()
    }

    async remove(id) {
        await axios.delete('http://localhost:8080/polls/'+id).then(() => {
            let updatedPolls = [...this.state.polls].filter(i => i.pollID !== id);
            this.setState({polls: updatedPolls});
        })
    }
    remove2(id) {
        axios.delete('http://localhost:8080/polls/'+id).then(() => {
            let updatedPolls = [...this.state.polls].filter(i => i.pollID !== id);
            this.setState({polls: updatedPolls});
        })

        fetch(`http://localhost:8080/polls/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedPolls = [...this.state.polls].filter(i => i.pollID !== id);
            this.setState({polls: updatedPolls});
        });
    }

    render() {
        const {polls} = this.state;

        const pollList = polls.map((poll) => {
            const isPublic = (poll.isPublic) ? "Yes":"No";
            const isOpen = (poll.isOpen) ? "Yes":"No";
            return (
                <tr key={poll.pollID}>
                    <td style={{whiteSpace: 'nowrap'}}>{poll.pollID}</td>
                    <td>{poll.title}</td>
                    <td>{poll.category}</td>
                    <td>{isOpen}</td>
                    <td>{isPublic}</td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/vote/" + poll.pollID}>Vote</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(poll.pollID)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            )
        })

        return (
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
                        {pollList}
                        </tbody>
                    </Table>
                    <br></br>
                    <Button onClick={this.handlePollCreationNav()}>Create a poll</Button>
                </Container>
            </div>
        )
    }
}
/*
function WithNavigateAndLocation(props) {
    let navigate = useNavigate();
    let location = useLocation();
    return <Polls {...props} navigate={navigate} location={location} />
}
export default WithNavigateAndLocation;*/
