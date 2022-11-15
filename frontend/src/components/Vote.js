import React from "react";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link} from "react-router-dom";

class Vote extends React.Component {
    emptyVote = {

    };
    emptyPoll = {
    };

    constructor(props) {
        super(props);
        this.state = {
            vote: this.emptyVote,
            poll: this.emptyPoll
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const fetchedPoll = await (await fetch(`http://localhost:8080/polls/${this.props.match.params.id}`)).json();
            this.setState({poll: fetchedPoll});
            console.log(fetchedPoll);
        }

    }

    async handleSubmit(event) {
        event.preventDefault();
        const {vote} = this.state;
        const {poll} = this.state;

        await fetch('http://localhost:8080/votes/' + (poll.pollID) + '/2', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(vote),
        });
        this.props.history.push(`/votes/${poll.pollID}/2`);
    }

    render() {
        const {poll} = this.state;
        const {vote} = this.state;
        const title = <h2>{"Vote for poll "+poll.pollID}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="vote">Vote</Label>
                        <Input type="text" name="vote" id="vote"
                               autoComplete="vote"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/polls">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }


}

export default Vote;
