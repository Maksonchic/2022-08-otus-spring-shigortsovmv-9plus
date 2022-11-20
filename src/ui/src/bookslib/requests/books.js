import config from '../../custom-config';

function getBooks(author) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/books/author/${author}`)
        .then(res => res.json());
}

function removeBook(bookId) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/books`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `bookId=${bookId}`
    })
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        });
}

function insertBook(params) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/books`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `title=${params.title}&page_count=${params.page_count}&authorNickName=${params.authorNickName}&genre=${params.genre}`
    })
        .then(res => res.json())
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        });
}

function getBookComment(bookId) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/comments/${bookId}`)
        .then(res => res.json())
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        });
}

function removeBookComment(commentId) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/comments`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `commentId=${commentId}`
    })
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        });
}

function addBookComment(bookId, message) {
    return fetch(`${config.protocol}://${config.host}:${config.port}${config.path}/api/v1/comments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `bookId=${bookId}&message=${message}`
    })
        .then(res => res.json())
        .then(res => {
            if (res.status > 299 || res.status < 200) throw new Error(res.error);
            return res;
        })
        .catch(alert);
}

export default {
    getBooks: getBooks,
    insertBook: insertBook,
    removeBook: removeBook,
    getBookComment: getBookComment,
    addBookComment: addBookComment,
    removeBookComment: removeBookComment
}