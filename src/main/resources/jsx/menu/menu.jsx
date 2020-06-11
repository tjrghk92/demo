import React from 'react';
import ReactDOM from 'react-dom';

import {createStore} from 'redux';
import {Provider} from 'react-redux'
import reducers from '../../static/js/common/reducers/index';

import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Write from '../../static/js/react/menu/write';



class MenuPage extends React.Component {

    render() {
        return(     
            <React.Fragment>
                <BrowserRouter>
                    <Switch>
                        <Route path="/menu/write" component={Write} />
                    </Switch>
                </BrowserRouter>
            </React.Fragment>
        );
    }
}

const render = () => { 
    ReactDOM.render(
        <MenuPage/>
        ,document.querySelector('#root')
    );
};

render();


