import React from 'react';
import repo from '../../requests/authors'
import { Link } from 'react-router-dom';

export default class Authors extends React.Component {

    state = {
        authors: []
    }

    componentDidMount() {
        repo.getAuthors()
            .then(res => this.setState({ authors: res}))
            .catch(alert); 
    }

    authorLine = (author, index) => {
        return <div key={author.id} className='entity-line'>
                    <div>{index+1}</div>
                    <div>{author.nickName}</div>
                    <div>{author.lastName}</div>
                    <div>{author.firstName}</div>
                    <div>{author.middleName}</div>
                    <button onClick={(e) => this.remove(e, author)}>remove</button>
                    <Link to={'/books/'+author.nickName}>Author's books</Link>
                </div>
    }

    insertAuthor = (e) => {
        const _this = this;
        const els = e.target.parentElement.getElementsByTagName("input");
        const params = {};
        params[els[0].name] = els[0].value;
        params[els[1].name] = els[1].value;
        params[els[2].name] = els[2].value;
        params[els[3].name] = els[3].value;
        repo.insertAuthor(params)
            .then((authorDto) => {
                _this.setState({authors:[...this.state.authors, authorDto]});
            })
            .catch((ex) => alert(ex));
    }

    remove = (e, author) => {
        repo.removeAuthor(author.nickName)
            .then(() => { e.target.parentElement.remove(); })
            .catch(alert);
    }

    render() {
        return (
            <div id='authors'>
                <h2>Authors</h2>
                { this.state.authors.map(this.authorLine) }
                { <div>
                    <input name="nickName" placeholder='nickName' />
                    <input name="lastName" placeholder='lastName' />
                    <input name="firstName" placeholder='firstName' />
                    <input name="middleName" placeholder='middleName' />
                    <button onClick={this.insertAuthor}>create new author</button>
                </div> }
            </div>
        );
    }
}