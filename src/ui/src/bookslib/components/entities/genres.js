import React from 'react';
import repo from '../../requests/genres'

export default class Genres extends React.Component {

    state = {
        genres: []
    }

    componentDidMount() {
        repo.getGenres()
            .then(res => this.setState({ genres: res}))
            .catch(alert); 
    }

    genreLine = (genre, index) => {
        return <div className='entity-line' key={index}>
                    <div>{index+1}</div>
                    <div>{genre.genre}</div>
                </div>
    }

    render() {
        return (
            <div id='genres'>
                <h2>Genres</h2>
                { this.state.genres.map(this.genreLine) }
            </div>
        );
    }
}