import React from 'react';
import repo from '../../requests/genres'

export default class Genres extends React.Component {

    state = {
        genres: []
    }

    componentDidMount() {
        const _this = this;
        repo.getGenres(_this)
            .then(genres => {
                this.setState({
                    genres: genres
                });
            })
            .then(res => {
                if (res.ok) {
                    this.setState({
                        genres: res.res
                    });
                } else {
                    if (res.d == 'auth') {
                        this.setState({
                            auth: res.el
                        });
                    }
                }
                return res;
            })
            .catch(e => {

            });
    }

    genreLine = (genre, index) => {
        return <div className='entity-line' key={index}>
            <div>{index + 1}</div>
            <div>{genre.genre}</div>
        </div>
    }

    render() {
        const _this = this;
        return (
            <div id='genres'>
                <h2>Genres</h2>
                {_this.state.genres.map(_this.genreLine)}
            </div>
        );
    }
}