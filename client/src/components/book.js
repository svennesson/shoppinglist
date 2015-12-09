import React from 'react';
import { Link } from 'react-router';
import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import Input from 'react-bootstrap/lib/Input';
import Button from 'react-bootstrap/lib/Button';
import Panel from 'react-bootstrap/lib/Panel';
import Loader from 'react-loader';
import cx from 'classnames'

import moment from 'moment';
import dateFormat from '../util/date-format';

const me = `{
    me { name, mobilePhone }
}`;

class Book extends React.Component {
    constructor(...args) {
        super(...args);

        const { name, mobilePhone } = this.props.data.me

        this.state = {
            name,
            mobilePhone,

            intervals: [],
            loadingIntervals: false
        }
    }

    componentWillMount () {
        if (null == this.props.location.state ||
            null == this.props.location.state.mission ||
            null != this.props.location.state.mission.orderId) {
            this._transition()
        }
    }

    _fetchDeliveryIntervals() {
        const postalCode = this.refs.postalCode.getValue()
        const newState = { postalCode, selectedInterval: null, intervalErrorMessage: null }

        if ('' === postalCode) {
            newState.intervals = []
        } else {
            newState.loadingIntervals = true

            const query = `{
                intervals (postalCode: "${postalCode}") {
                    collection {start, stop},
                    delivery {start, stop},
                    collectionPointIds
                }
            }`

            graphql(query)
                .then(({ intervals }) => this.setState({ intervals, loadingIntervals: false }))
                .catch(err => {
                    console.error(err)
                    this.setState({
                        intervals: [],
                        loadingIntervals: false,
                        intervalErrorMessage: 'Inga interval för postkod'
                    })
                })
        }

        this.setState(newState)
    }

    _createOrder (e) {
        e.preventDefault()

        if (!this._valid()) {
            return
        }

        const { name, mobilePhone, street, postalCode, city, doorCode, selectedInterval } = this.state
        const orderCreation = `mutation createOrder {
            order: createOrder (
                missionId: ${this.props.params.id},
                intervalCollectionStart: "${selectedInterval.collection.start}",
                intervalCollectionStop: "${selectedInterval.collection.stop}",
                intervalDeliveryStart: "${selectedInterval.delivery.start}",
                intervalDeliveryStop: "${selectedInterval.delivery.stop}",
                collectionPointId: ${selectedInterval.collectionPointIds[0]},
                deliveryName: "${name}",
                deliveryTelephoneNumber: "${mobilePhone}",
                deliveryDoorCode: "${doorCode}",
                deliveryAddressStreet: "${street}",
                deliveryAddressPostalCode: "${postalCode}",
                deliveryAddressCity: "${city}"
            ) { id }
        }`

        graphql(orderCreation)
            .then(::this._transition)
            .catch(err => console.error(err))
    }

    _valid() {
        const { name, mobilePhone, street, postalCode, city, selectedInterval } = this.state

        return null != name && '' !== name
            && null != mobilePhone && '' !== mobilePhone
            && null != street && '' !== street
            && null != postalCode && '' !== postalCode
            && null != city && '' !== city
            && null != selectedInterval
    }

    _transition () {
        this.props.history.pushState(null, 'account')
    }

    _selectInterval (interval) {
        this.setState({ selectedInterval: interval })
    }

    _renderBookButton() {
        return (
            <Button type='submit' bsStyle='primary' bsSize='large' block disabled={!this._valid()} style={{marginTop: 20, marginBottom: 20}}>Boka hemleverans</Button>
        )
    }

    _renderInterval(interval, i) {
        const dateFormat = 'YYYY-MM-DD'
        const hourFormat = 'HH:mm'

        const mStart = moment.unix(interval.delivery.start / 1000)
        const mStop = moment.unix(interval.delivery.stop / 1000)

        const { selectedInterval } = this.state
        const active = (
            null != selectedInterval &&
            selectedInterval.collection.start === interval.collection.start &&
            selectedInterval.collection.stop === interval.collection.stop &&
            selectedInterval.delivery.start === interval.delivery.start &&
            selectedInterval.delivery.stop === interval.delivery.stop
        )
        const btnClasses = { active }

        return (
            <Button key={i} className={cx(btnClasses)} bsSize='large' block onClick={this._selectInterval.bind(this, interval)}>
                {mStart.format(dateFormat)} {mStart.format(hourFormat)} - {mStop.format(hourFormat)}
            </Button>
        )
    }

    _renderEmptyPostalcode () {
        if (null != this.state.postalCode && '' !== this.state.postalCode) {
            return null
        }

        return <p className='gray-light'>Ange din postkod</p>
    }

    _renderNoIntervals () {
        if (null == this.state.intervalErrorMessage) {
            return null
        }

        return <p className='text-error'>{this.state.intervalErrorMessage}</p>
    }

    _renderIntervals() {
        return (
            <Row>
                <Col md={4} mdOffset={4} className='text-center panel-text-size'>
                    <p className='text-left'>Välj leveranstillfälle</p>
                    <Loader loaded={!this.state.loadingIntervals} lines={11} length={12} width={6} radius={13} scale={0.5}/>
                    {this._renderEmptyPostalcode()}
                    {this._renderNoIntervals()}
                    {this.state.intervals.map(::this._renderInterval)}
                    {this._renderBookButton()}
                </Col>
            </Row>
        )
    }

    render() {
        const { mission } = this.props.location.state
        const { me } = this.props.data

        const addressWrapper = (
            <Row>
                <Col xs={4}>
                    <Input required type='text' ref='postalCode' placeholder='11528' onChange={::this._fetchDeliveryIntervals}/>
                </Col>
                <Col xs={8}>
                    <Input required type='text' ref='city' placeholder='Stockholm' onChange={assign(this, 'city')}/>
                </Col>
            </Row>
        );

        return (
            <Grid fluid={true}>
                <Row>
                    <Col md={4} mdOffset={4} className='text-left'>
                        <h3>{mission.sender}</h3>
                        <p>Paketet anlände {dateFormat(mission.status.createdAt)}</p>
                     </Col>
                </Row>
                <Row>
                    <Col md={4} mdOffset={4} className='text-center'>
                        <h4 style={{paddingBottom: 7}}>Boka hemleverans</h4>
                    </Col>
                </Row>
                <form onSubmit={::this._createOrder}>
                    <Row>
                        <Col md={4} mdOffset={4}>
                            <Input required type='text' ref='name' label='Namn *' defaultValue={me.name} onChange={assign(this, 'name')}/>
                            <Input required type='text' ref='mobilePhone' label='Telefonnummer *' defaultValue={me.mobilePhone} onChange={assign(this, 'mobilePhone')}/>
                            <Input required type='text' ref='street' label='Adress *' placeholder='Grevgatan 9' onChange={assign(this, 'street')}/>
                            {addressWrapper}
                            <Input type='text' ref='doorCode' label='Portkod' placeholder='1337' onChange={assign(this, 'doorCode')}/>
                        </Col>
                    </Row>
                    {this._renderIntervals()}
                </form>
            </Grid>
        );
    }
}

export default graphqlComponent(me)(Book);
