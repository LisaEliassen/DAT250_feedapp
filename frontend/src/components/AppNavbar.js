import React, {Component} from 'react';
import {Button} from 'reactstrap';
import {Link} from 'react-router-dom';

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <Button>
            <Link to="/"> Home </Link>
        </Button>


    }
}

