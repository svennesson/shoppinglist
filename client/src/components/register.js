import React from 'react';
import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import Input from 'react-bootstrap/lib/Input';
import Button from 'react-bootstrap/lib/Button';
import Alert from 'react-bootstrap/lib/Alert'

import auth from '../api/auth'
import Http from '../api/http'

class Register extends React.Component {
    constructor (props) {
        super(props)
        this.state = {}
    }

    render() {
        return (
            <div>
                <Row>
                    <Col md={4} mdOffset={4}>
                        <form onSubmit={::this._register}>
                            <fieldset>
                                <legend>Registrera dig</legend>
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

export default Register;
