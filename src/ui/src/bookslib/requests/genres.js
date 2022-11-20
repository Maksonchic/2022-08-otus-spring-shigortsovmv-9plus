import config from '../../custom-config';

function getGenres() {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/genres`)
        .then(res => res.json());
}

export default {
    getGenres: getGenres
}