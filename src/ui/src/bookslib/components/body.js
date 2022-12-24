import React from 'react';
import '../../styles/body.css';
import Entities from './table-entities';
import { Routes, Route } from 'react-router-dom';
import Genres from './entities/genres';
import Books from './entities/books';
import Authors from './entities/authors';

export default class Body extends React.Component {
    render() {
        return (
            <Routes>
                <Route path='/' element={<Entities />} />
                <Route path='/genres' element={<Genres />} />
                <Route path='/authors' element={<Authors />} />
                <Route path='/books/:author' element={<Books />} />
            </Routes>
        );
    }
}
