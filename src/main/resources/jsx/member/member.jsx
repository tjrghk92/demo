import React from 'react';
import ReactDOM from 'react-dom';

import {createStore} from 'redux';
import {Provider} from 'react-redux'
import reducers from '../../static/js/common/reducers/index';

import { BrowserRouter, Route, Switch } from 'react-router-dom';

import List2 from '../../static/js/react/member/list2';
import Write from '../../static/js/react/member/write';
import Login from '../../static/js/react/member/login';
import Signup from '../../static/js/react/member/signup';



//import 'react-app-polyfill/ie11';
//import 'react-app-polyfill/stable';

class MemberPage extends React.Component {

    render() {
        return(     
            <React.Fragment>
                <BrowserRouter>
                    <Switch>
                        <Route path="/member/list" component={List2} />
                        <Route path="/member/write" component={Write} />
                        <Route path="/member/login" component={Login} />
                        <Route path="/member/signup" component={Signup} />
                    </Switch>
                </BrowserRouter>
            </React.Fragment>
        );
    }
}

const store = createStore(reducers);

const render = () => { 
    ReactDOM.render(
        <Provider store={store}>
             <React.Fragment>
                <MemberPage/>
            </React.Fragment>
        </Provider>, 
        document.querySelector('#root')
    );
};

store.subscribe(render);
render();


