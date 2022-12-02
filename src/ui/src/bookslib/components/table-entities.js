import React from 'react';
import { Link } from "react-router-dom";

export default class Entities extends React.Component {
    render() {
        return (
            <div id='entities-changer-container'>
                <Link className='elements' to="/genres">Жанры</Link>
                <Link className='elements' to="/authors">Авторы</Link>
            </div>
        );
    }
}
