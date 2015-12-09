'use strict'

const storageKey = 'shoppinglist'
const authKey = `${storageKey}:auth`
const roleKey = `${storageKey}:role`
const expiresKey = `${storageKey}:expires`

let auth = null
let role = null
let expires = null

try {
    auth = localStorage.getItem(authKey)
    role = localStorage.getItem(roleKey)
    expires = localStorage.getItem(expires)
} catch (e) {
    console.warn(e)
}

export default {
    isLoggedIn () {
        return null != auth
    },

    get () {
        return auth
    },

    save (accessToken, givenRole) {
        const token = accessToken.token
        auth = `Bearer ${token}`
        role = givenRole
        expires = accessToken.expires

        localStorage.setItem(authKey, auth)
        localStorage.setItem(roleKey, role)
        localStorage.setItem(expiresKey, expires)
    },

    clear () {
        auth = null
        role = null
        expires = null
        localStorage.removeItem(authKey)
        localStorage.removeItem(roleKey)
        localStorage.removeItem(expiresKey)
    },

    hasRole (requiredRole) {
        return role != null && role.toLowerCase() === requiredRole.toLowerCase()
    }
}
