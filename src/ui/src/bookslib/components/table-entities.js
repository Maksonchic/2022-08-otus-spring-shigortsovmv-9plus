import React from 'react';
import { Link } from "react-router-dom";

export default class Entities extends React.Component {

    componentDidMount() {
        try {
            debugger
            fetch('http://localhost:8080/api/v1/genres')
                .then(res => {
                    if (res.status == 403) {
                        throw new Error();
                    }
                });
            } catch(e) {
                debugger
            }
    }

    render() {
        return (
            <div id='entities-changer-container'>
                <Link className='elements' to="/genres">Жанры</Link>
                <Link className='elements' to="/authors">Авторы</Link>
            </div>
        );
    }
}
