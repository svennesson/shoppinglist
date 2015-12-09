'use strict'

function getRegex (re) {
    return new RegExp(re, 'gm')
}

function replace (str, re) {
    if (null == str) {
        return null
    }

    return str.replace(re, '')
}

export default function trim (str, t = '\\s') {
    return replace(str, getRegex(`^${t}+|${t}+$`))
}

export function trimLeft (str, t = '\\s') {
    return replace(str, getRegex(`^${t}+`))
}

export function trimRight (str, t = '\\s') {
    return replace(str, getRegex(`${t}+$`))
}
