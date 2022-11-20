import React from 'react';
import repo from '../../requests/books'
import {  } from "react-router-dom";

export default class Books extends React.Component {

    state = {
        books: [],
        author: window.location.pathname.substring('/books/'.length)
    }

    componentDidMount() {
        repo.getBooks(this.state.author)
            .then(res => {
                this.setState({books: res});
            })
            .catch(alert);
    }

    bookLine = (book, index) => {
        // debugger
        // const [searchParams, setSearchParams] = useSearchParams();
        // searchParams.get("__firebase_request_key")
        return <div key={book.id} className='entity-line'>
                    <div>{index+1}</div>
                    <div className='bookTitle'>{book.title}</div>
                    <div>{book.pageCount}</div>
                    <div>{book.genre.genre}</div>
                    <button onClick={()=>this.showCommentWindow(book.id, book.title)}>show comments</button>
                    <button onClick={(e)=>this.removeBook(e, book.id)}>remove</button>
                </div>
    }

    showCommentWindow = (bookId, title) => {
        repo.getBookComment(bookId)
            .then((res) => {
                const sm = document.createElement("div");
                const header = document.createElement("h4");
                const comments = document.createElement("div");
                const addNewMessageField = document.createElement("input");
                const addNew = document.createElement("button");
                const close = document.createElement("button");
                close.innerText = "Close";
                close.addEventListener('click', () => { sm.remove(); }, false);
                addNew.innerText = "Add new";
                addNew.addEventListener('click', () => {
                    repo.addBookComment(bookId, addNewMessageField.value)
                        .then(drawBookComms);
                }, false);
                header.innerText = "Comments for \"" + title + "\"";
                sm.style.margin = 'auto';
                sm.style.top = 0;
                sm.style.bottom = 0;
                sm.style.left = 0;
                sm.style.right = 0;
                sm.style.width = '600px';
                sm.style.height = '400px';
                sm.style.backgroundColor = 'white';
                sm.style.position = 'absolute';
                sm.style.border = '1px grey solid';
                sm.appendChild(header);
                const drawBookComms = (coms) => {
                    comments.innerHTML = "";
                    for (let com of coms) {
                        const c = document.createElement("div");
                        const c1 = document.createElement("span");
                        const c2 = document.createElement("button");
                        c1.innerText = com.message;
                        c1.style.width = '500px';
                        c2.innerText = 'remove';
                        c2.style.width = '99px';
                        c2.style.color = 'red';
                        c2.addEventListener('click', () => {
                            repo.removeBookComment(com.id)
                                .then(() => {
                                    c.remove();
                                }).catch(alert);
                        }, false);
                        c.appendChild(c1);
                        c.appendChild(c2);
                        comments.appendChild(c);
                    }
                }
                drawBookComms(res);
                sm.appendChild(comments);
                sm.appendChild(addNewMessageField);
                sm.appendChild(addNew);
                sm.appendChild(close);
                document.body.appendChild(sm);
            })
            .catch(alert);
    }

    removeBook = (e, bookId) => {
        repo.removeBook(bookId)
            .then(() => { e.target.parentElement.remove(); })
            .catch(alert);
    }

    insertBook = (e) => {
        const _this = this;
        const els = e.target.parentElement.getElementsByTagName("input");
        const params = {authorNickName: _this.state.author};
        params[els[0].name] = els[0].value;
        params[els[1].name] = els[1].value;
        params[els[2].name] = els[2].value;
        // params[els[3].name] = els[3].value;
        repo.insertBook(params)
            .then((bookDto) => {
                _this.setState({books:[...this.state.books, bookDto]});
            })
            .catch((ex) => alert(ex));
    }

    render() {
        return (
            <div id='books'>
                <h2>Books of {this.state.author}</h2>
                { this.state.books.map(this.bookLine) }
                { <div>
                    <input name="title" placeholder='book title' />
                    <input name="page_count" placeholder='book page_count' />
                    {/* <input name="authorNickName" placeholder='book authorNickName' /> */}
                    <input name="genre" placeholder='book genre' />
                    <button onClick={this.insertBook}>create new book</button>
                </div> }
            </div>
        );
    }
}