import React from 'react';
import { Link } from 'react-router';
import Navbar from 'react-bootstrap/lib/Navbar';
import Nav from 'react-bootstrap/lib/Nav';
import NavItem from 'react-bootstrap/lib/NavItem';
import LinkContainer from 'react-router-bootstrap/lib/LinkContainer';

import auth from '../api/auth'
import Http from '../api/http'

export default class Navigation extends React.Component {
    _logout (e) {
        e.preventDefault()
        new Http()
            .path('api/users/logout')
            .accept('application/vnd.rogue.users-v1+json')
            .type('application/vnd.rogue.users-v1+json')
            .post()
        auth.clear()
        this.props.history.push('/')
    }

    _renderBrand () {
        let href = '/'
        if (auth.isLoggedIn()) {
            href = 'lists'
        }

        return <Link to={href}>Shoppinglist</Link>
    }

    _renderLeftMenu () {
        const menuItems = []
        return <Nav>{menuItems}</Nav>
    }

    _renderRightMenu () {
        const menuItems = []
        if (auth.isLoggedIn()) {
            if (auth.hasRole('admin')) {
                menuItems.unshift(
                    <LinkContainer key='admin' to='admin'>
                        <NavItem>Admin</NavItem>
                    </LinkContainer>
                )
            }

            menuItems.push(
                <NavItem key='logout' onClick={::this._logout}>Logga ut</NavItem>
            )
        } else {
            menuItems.push(
                <LinkContainer key='login' to='login'>
                    <NavItem>Logga in</NavItem>
                </LinkContainer>
            )
        }

        return <Nav pullRight>{menuItems}</Nav>
    }

    render () {
        return (
            <Navbar inverse fluid>
                <Navbar.Header>
                    <Navbar.Brand>
                        {this._renderBrand()}
                    </Navbar.Brand>
                    <Navbar.Toggle/>
                </Navbar.Header>

                <Navbar.Collapse>
                    {this._renderLeftMenu()}
                    {this._renderRightMenu()}
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
