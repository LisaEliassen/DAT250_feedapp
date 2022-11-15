import React from 'react';
import './css/App.css';
import AppNavbar from './AppNavbar';
import { Link} from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends React.Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link">
                        <Link to="/polls">Polls</Link>
                    </Button>
                </Container>
            </div>
        );
    }
}
export default Home;
