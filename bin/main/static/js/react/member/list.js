import React, { Component } from 'react';
import axios from 'axios';
import ListItem from './listItem';
import Paging from '../../common/paging';
import {connect} from 'react-redux';
import { paging, search } from '../../common/action/action';


class List extends Component {
    constructor(props) {
        super(props);

      this.state = {
        loading: false,
        ItemList: []
      };

      this.param = {
        memberId : "",
        memberName : ""
      }

      this.onChange = this.onChange.bind(this);
      this.onSubmit = this.onSubmit.bind(this);
      this.loadItem = this.loadItem.bind(this);
    }

    componentDidMount() {
      this.loadItem();
    }

    componentDidUpdate(prevProps) {
      if (this.props.pageIndex !== prevProps.pageIndex) {
         this.loadItem();
      }
    }

    async loadItem() {
      await axios({
        method: 'get',
        url: './api/list',
        params: {
          pageIndex: this.props.pageIndex, 
          memberId: this.param.memberId, 
          memberName: this.param.memberName 
        }
      }).then(({data}) => {
        this.setState({ 
          loading: true,
          ItemList: data.rtnMap.list
        });   
        this.props.goSearch(data.rtnMap.pageMap);
      });
    }
    
    onChange(e) {
      this.setState({
        [e.target.name] : e.target.value
      });
    }
    
    onSubmit(e) {
      e.preventDefault();

      Object.assign(this.param, {
        memberId: this.state.memberId,
        memberName: this.state.memberName,
      });
      
      this.loadItem();
    }

    render() {
      const {ItemList, memberId, memberName} = this.state;
      const {onChange, onSubmit} = this;
        return (
          <div>
            <h4>회원 리스트</h4>
            <form onSubmit={onSubmit}>
              <div>
                <label>id</label>
                <input type="text" name="memberId" value={memberId == null ? "" : memberId}  onChange={onChange}/>
              </div>
              <div>
                <label>name</label>
                <input type="text" name="memberName"  value={memberName == null ? "" : memberName} onChange={onChange}/>
              </div>
              <div><button type="submit">전송</button></div>
            </form>
            <div>
              <ListItem list={ItemList}/>
              <Paging />
            </div>
          </div>
        );
      }
}

const mapStateToProps = (state) => {
  return {
      pageIndex: state.page.pageIndex,
  };
};

const mapDispatchToProps = (Dispatch) => {
  return {
      goSearch: (pageMap) => Dispatch(search(pageMap))
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(List);
