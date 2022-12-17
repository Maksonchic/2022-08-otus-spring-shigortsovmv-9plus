import config from '../../custom-config';

function getGenres(_parent) {
    // return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/genres`)
    return fetch(`/api/v1/genres`)
        .then(res => res.json());
}

export default {
    getGenres: getGenres
}