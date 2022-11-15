import React from "react";
import axios from "axios";
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import {Button, ButtonGroup, Container, Table} from "reactstrap";

class Polls extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            polls: []
        }
        this.remove = this.remove.bind(this);
    }

    getData(){
        axios.get('http://localhost:8080/polls').then(res => {
            console.log(res.data);
            this.setState({
                polls:res.data
            })
        })
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

        const pollList = polls.map((poll, i) => {
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
                </Container>
            </div>
        )
    }
}

export default Polls;
