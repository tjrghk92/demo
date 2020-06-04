import React, {Component}  from 'react';
import {connect} from 'react-redux'
import { paging, search } from './action/action';

class Test extends Component {

    render() {
        return (
            <button onClick={this.props.addNumber}>클릭해줘염</button>
        )
    }
}

let mapDispatchToProps = (dispatch, /*ownProps*/) => {
    return {
        addNumber: () => dispatch(paging())
    };
};

export default connect(null, mapDispatchToProps)(Test);