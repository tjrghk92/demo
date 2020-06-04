import React from 'react';
import ReactDOM from 'react-dom';

//import 'react-app-polyfill/ie11';
//import 'react-app-polyfill/stable';
 
class MainPage extends React.Component {
 
    render() {
        return <div className="main">메인 페이지</div>;
    }
 
}
 
ReactDOM.render(<MainPage/>, document.getElementById('root'));
