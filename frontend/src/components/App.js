import React, { Component } from 'react';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Polls from './Polls';
import Vote from './Vote'

class App extends Component {
  render() {
    return (
        <Router>
            <Switch>
                <Route exact path='/' component={Home}/>
                <Route path='/polls' exact={true} component={Polls}/>
                <Route path='/vote/:id' component={Vote}/>
            </Switch>
        </Router>
    )
  }
}

export default App;
