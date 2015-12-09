import React from 'react';
import Link from 'react-router';
import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import Input from 'react-bootstrap/lib/Input';
import Button from 'react-bootstrap/lib/Button';
import Alert from 'react-bootstrap/lib/Alert'

import auth from '../api/auth'
import Http from '../api/http'

class Login extends React.Component {
    constructor (props) {
        super(props)
        this.state = {}
    }

    _closeAlert () {
        this.setState({alertVisible: false})
    }

    _showAlert () {
        this.setState({alertVisible: true})
    }

    _renderLoginFailed () {
        if (this.state.alertVisible) {
            return (
                <Alert bsStyle='danger' onDismiss={::this._closeAlert} dismissAfter={3000} style={{marginTop: "10px"}}>
                    Fel email och/eller lösenord, vänligen försök igen.
                </Alert>
            )
        }
        
        return null;
    }

    _goToLists () {
        this.props.history.push('lists')
    }

    _login (e) {
        e.preventDefault()
        const email = this.refs.email.getValue()
        const password = this.refs.password.getValue()

        new Http('/api/login')
            .accept('application/json')
            .type('application/json')
            .post({ email, password })
            .then(res => {
                console.log(res)
                auth.save(res.accessToken, res.role)
                this._goToLists()
            })
            .catch(err => {
                this._showAlert()
                console.error(err)
            })
    }

    _valid () {
        const { email, password } = this.state
        return null != email && null != password
    }

    _onChange (type) {
        return () => this.setState({ [type]: this.refs[type].getValue() })
    }

    render() {
        return (
            <div>
                <Row>
                    <Col md={4} mdOffset={4}>
                        <form onSubmit={::this._login}>
                            <fieldset>
                                <legend>Logga in</legend>
                                <div className='form-group'>
                                    <Input required type='text' ref='email' label='E-post' placeholder='jonas.svensson@email.se' value={this.state.email} onChange={this._onChange('email')}/>
                                    <Input required type='password' ref='password' label='Lösenord' placeholder='********' value={this.state.password} onChange={this._onChange('password')}/>
                                    <Button type='submit' bsStyle='primary' disabled={!this._valid()}>Logga in</Button>
                                    {this._renderLoginFailed()}
                                </div>
                            </fieldset>
                        </form>
                    </Col>
                </Row>
            </div>
         );
    }
}

export default Login;
