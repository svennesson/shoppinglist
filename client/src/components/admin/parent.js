'use strict'

import React from 'react'

import auth from '../../api/auth'

export default class Admin extends React.Component {
    componentWillMount () {
        if (!auth.isLoggedIn()) {
            this.props.history.replaceState(null, '/')
        }
        if (!auth.hasRole('admin')) {
            this.props.history.replaceState(null, '/')
        }
    }

    render () {
        return this.props.children
    }
}
