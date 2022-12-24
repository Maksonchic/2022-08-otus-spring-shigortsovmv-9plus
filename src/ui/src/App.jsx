import logo from './logo.svg';
import './App.css';
import Body from './bookslib/components/body';
import { BrowserRouter, Link } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <header className="App-header">
          <Link to='/'><img src={logo} className="App-logo" alt="logo" /></Link>
          <p>Wonderful Books Lib app</p>
        </header>
        <Body />
      </BrowserRouter>
    </div>
  );
}

export default App;
