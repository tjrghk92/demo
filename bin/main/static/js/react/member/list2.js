import React, { Component } from 'react';
import axios from 'axios';
import ListItem from './listItem';
import Pagination from "react-js-pagination";

import 'core-js/stable';
import 'regenerator-runtime/runtime';

import '../../../css/common/paging.css';
import queryString from 'query-string';

class List extends Component {
  constructor(props) {
      super(props);

    let query = queryString.parse(location.search);

    this.state = {
      loading: false,
      ItemList: [],
      
      param : {
        memberId : query.memberId || "",
        memberName : query.memberName || "",
        pageIndex : parseInt(query.pageIndex) || 1
      },

      temp : {
        memberId : query.memberId || "",
        memberName : query.memberName || "",
      }
      
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.loadItem = this.loadItem.bind(this);
    
  }

  componentDidMount() {
    this.loadItem();
  }

  componentDidUpdate(prevProps, prevState){
    if(prevState.param.pageIndex !== this.state.param.pageIndex){
      this.loadItem(); 
    }
  }

  async loadItem() {
    await axios({
      method: 'get',
      url: './api/list',
      params: {
        pageIndex: this.state.param.pageIndex, 
        memberId: this.state.param.memberId, 
        memberName: this.state.param.memberName 
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
    var tempParam = {
      memberId : this.state.temp.memberId,
      memberName : this.state.temp.memberName,
    };

    Object.assign(tempParam, {
      [e.target.name] : e.target.value
    });
    
    this.setState({
      temp : tempParam,
    });
  }
  
  onSubmit(e) {
    e.preventDefault();
    this.state.param = this.state.temp;
    this.state.param.pageIndex = 1;
    this.loadItem();
    
  }

  handlePageChange(pageIndex) {
    if(this.state.param.pageIndex !== pageIndex){
      var tempParam = {
        memberId : this.state.param.memberId,
        memberName : this.state.param.memberName,
        pageIndex : pageIndex
      };
      
      this.setState({
        param : tempParam,
      });
    }
  }

  render() {
    const {ItemList, pageMap, param} = this.state;
    const {memberId, memberName} = this.state.temp;
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
          <ListItem list={ItemList} param={param}/>
          <div className="paging">
            <Pagination
                activePage={param.pageIndex}
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
