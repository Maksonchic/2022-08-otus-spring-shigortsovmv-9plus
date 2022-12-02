import config from '../../custom-config';

function getAuthors() {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/authors`)
        .then(res => res.json());
}

function insertAuthor(params) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/authors`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `nickName=${params.nickName}&lastName=${params.lastName}&firstName=${params.firstName}&middleName=${params.middleName}`
    })
        .then(res => res.json())
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        });
}

function removeAuthor(authorNickName) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/authors`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `authorNickName=${authorNickName}`
    })
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        });
}

export default {
    getAuthors: getAuthors,
    insertAuthor: insertAuthor,
    removeAuthor: removeAuthor
}