'use strict';

function normalizedDate(date) {
    const withLeadingZero = num => `${num < 10 ? '0' : ''}${num}`
    return `${date.getFullYear()}-${withLeadingZero(date.getMonth() + 1)}-${withLeadingZero(date.getDate())}`
}