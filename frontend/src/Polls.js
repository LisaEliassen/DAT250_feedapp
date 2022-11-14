import React from "react";
import axios from "axios";
import Table from 'react-bootstrap/Table';

class Polls extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            polls: []
        }
    }
    getData(){
        axios.get('http://localhost:8080/polls').then(res => {
            console.log(res.data);
            this.setState({
                polls:res.data
            })
        })
    }
    componentDidMount(){
        this.getData()
    }
    render() {
        const {polls} = this.state
        return (
            <div>
                <h2>List of polls:</h2>
                <table style={{
                    width: '50%'
                }}>
                    <tbody style={{
                        background: 'lightgrey',
                        color: 'black',
                    }}>
                        <tr style={{
                            fontWeight: 'bold',
                        }}>
                            <td>ID</td>
                            <td>Name</td>
                            <td>Category</td>
                            <td>Description</td>
                            <td>Result</td>
                        </tr>
                    {
                        polls.map((poll, i) => {
                            return (
                                <tr key={i}>
                                    <td>{poll.pollID}</td>
                                    <td>{poll.name}</td>
                                    <td>{poll.category}</td>
                                    <td>{poll.description}</td>
                                    <td>{poll.result}</td>
                                </tr>
                            )
                        })
                    }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default Polls;
