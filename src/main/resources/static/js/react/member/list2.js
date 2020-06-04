import React, { Component } from 'react';
import axios from 'axios';
import ListItem from './listItem';
import Pagination from "react-js-pagination";

import 'core-js/stable';
import 'regenerator-runtime/runtime';

import '../../../css/common/paging.css';

class List extends Component {
  constructor(props) {
      super(props);

    this.state = {
      loading: false,
      ItemList: [],
      pageIndex: 1
    };

    this.param = {
      memberId : "",
      memberName : ""
    }

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.loadItem = this.loadItem.bind(this);

    this.loadItem();
  }

  componentDidMount() {
   
  }

  componentDidUpdate(prevProps, prevState){
    if (this.state.pageIndex !== prevState.pageIndex) {
      this.loadItem();
    }
  }

  async loadItem() {
    await axios({
      method: 'get',
      url: './api/list',
      params: {
        pageIndex: this.state.pageIndex, 
        memberId: this.param.memberId, 
        memberName: this.param.memberName 
      }
    }).then(({data}) => {
      this.setState({ 
        loading: true,
        ItemList: data.rtnMap.list,
        pageMap : data.rtnMap.pageMap
      });   
      
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

  handlePageChange(pageIndex) {
    if(this.state.pageIndex !== pageIndex){
      this.setState({ 
        pageIndex: pageIndex
      })
    }
  }

  render() {
    const {ItemList, pageMap, memberId, memberName, pageIndex} = this.state;
    const {onChange, onSubmit} = this;
    
    return (
      <div>
        <h4>회원 리스트</h4>
        <form onSubmit={onSubmit}>
          <div>
            <label>id</label>
            <input type="text" name="memberId" value={memberId || ''}  onChange={onChange}/>
          </div>
          <div>
            <label>name</label>
            <input type="text" name="memberName"  value={memberName || ''} onChange={onChange}/>
          </div>
          <div><button type="submit">전송</button></div>
        </form>
        <div>
          <ListItem list={ItemList}/>
          <div className="paging">
            <Pagination
                activePage={pageIndex}
                itemsCountPerPage={typeof pageMap === "undefined" ? 0 : parseInt(pageMap.cntPage)}
                totalItemsCount={typeof pageMap === "undefined" ? 0 : parseInt(pageMap.totalCnt)}
                pageRangeDisplayed={typeof pageMap === "undefined" ? 0 : parseInt(pageMap.pageSize)}
                prevPageText={'prev'}
                nextPageText={'next'}
                onChange={this.handlePageChange.bind(this)}
            />
          </div>
        </div>
        <div><a href = "/member/logout">로그아웃</a> <a href = "/">메인으로</a></div>
      </div>
    );
  }
}


export default List;
