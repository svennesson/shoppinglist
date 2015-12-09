'use strict'

import './less/app.less';
import './util/polyfill';

import React from 'react';
import { render } from 'react-dom';
import createBrowserHistory from 'history/lib/createBrowserHistory'
import { IndexRedirect, Router, Route, IndexRoute } from 'react-router';

import Components from './components/index'

const {
    Admin,
    Login,
    Main
} = Components

const app = (
    <Router history={createBrowserHistory()}>
        <Route path='/' component={Main}>
            <IndexRoute component={Login}/>
            <Route path='login' component={Login}/>
            <Route path='admin' component={Admin.Parent}>
            </Route>
        </Route>
    </Router>
)

render(app, document.getElementById('content'))
