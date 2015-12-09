'use strict'

import auth from './auth'
import trim from '../util/trim'

export default class Http {
    constructor (url = '') {
        this._url = url
        this._headers = {}
        this._parts = []
        this._queryParams = {}

        if (auth.isLoggedIn()) {
            this.header('Authorization', auth.get())
        }
    }

    path (part) {
        this._parts.push(part)
        return this
    }

    header (key, val) {
        if (null == this._headers[key]) {
            this._headers[key] = []
        }
        this._headers[key].push(val)

        return this
    }

    accept (val) {
        return this.header('Accept', val)
    }

    type (val) {
        return this.header('Content-Type', val)
    }

    get () {
        return this._request('get')
    }

    post (data, isJson) {
        return this._request('post', data, isJson)
    }

    put (data, isJson) {
        return this._request('put', data, isJson)
    }

    delete (data, isJson) {
        return this._request('delete', data, isJson)
    }

    _request(method, data, isJson = true) {
        return fetch(this._getUrl(), {
            method,
            headers: this._getHeaders(),
            body: null != data ? isJson ? JSON.stringify(data) : data : undefined
        })
        .then(this._status)
        .then(this._parse)
    }

    _getUrl () {
        const parts = this._parts.filter(part => null != part)
            .map(part => part.toString())
            .map(part => trim(part, '/'))
            .join('/')

        let url = this._url
        if ('' !== parts) {
            url = `${url}/${parts}`
        }

        return url
    }

    _getHeaders () {
        return Object.keys(this._headers)
            .reduce((m, header) => {
                m[header] = this._headers[header].join(' ')
                return m
            }, {})
    }

    _status(res) {
        if (200 <= res.status && 300 > res.status) {
            return Promise.resolve(res)
        } else {
            return Promise.reject(res)
        }
    }

    _parse(res) {
        if (res.headers.has('Content-Type')) {
            const contentTypeHeader = res.headers.get('Content-Type')
            const [ contentType ] = contentTypeHeader.split(';')
            if (contentType.endsWith('json')) {
                return res.json()
            }
        }

        return Promise.resolve(res)
    }
}
