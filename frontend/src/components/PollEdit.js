import React from "react";
import axios from "axios";
import AppNavbar from "./AppNavbar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {Link} from "react-router-dom";

class PollEdit extends React.Component {
    emptyPoll = {
        title: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            poll: this.emptyPoll
        };
        this.handleChange = this.handleChange.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const poll = await (await axios.get(`http://localhost:8080/polls/${this.props.match.params.id}`));
            console.log(poll)
            this.setState({item: poll});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let poll = {...this.state.poll};
        poll[name] = value;
        this.setState({poll});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {poll} = this.state;

        await fetch('http://localhost:8080/polls' + (poll.pollID ? '/' + poll.pollID : ''), {
            method: (poll.pollID) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(poll),
        });
        this.props.history.push('/polls');
    }


    render() {
        const {poll} = this.state;
        const title = <h2>{poll.pollID}</h2>;

        return (
            <div>
                <AppNavbar/>
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="title">Title</Label>
                            <Input type="text" name="title" id="title" value={poll.title || ''}
                                   onChange={this.handleChange} autoComplete="title"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="category">Category</Label>
                            <Input type="text" name="category" id="category" value={poll.category || ''}
                                   onChange={this.handleChange} autoComplete="category"/>
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/polls">Cancel</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        )
    }
}
export default PollEdit;
