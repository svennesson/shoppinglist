import React from 'react';
import Grid from 'react-bootstrap/lib/Grid';

import Navigation from './navigation';
import auth from '../api/auth'
import Http from '../api/http'

export default class Main extends React.Component {
    componentWillMount () {
        
    }

    render () {
        return (
            <div>
                <Navigation {...this.props}/>

                <Grid fluid={true}>
                    {this.props.children}
                </Grid>
            </div>
        )
    }
}
