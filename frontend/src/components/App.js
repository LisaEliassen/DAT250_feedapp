import React, { Component } from 'react';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Polls from './Polls';
import Polls2 from './Polls2'
import Vote from './Vote'

class App extends Component {
  render() {
    return (
        <Router>
            <Switch>
                <Route exact path='/' component={Home}/>
                <Route path='/polls' exact={true} component={Polls}/>
                <Route path='/vote/:id' component={Vote}/>
                <Route path='/polls/:id' component={Polls2}/>
            </Switch>
        </Router>
    )
  }
}

export default App;
