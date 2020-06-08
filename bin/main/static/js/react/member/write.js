import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import queryString from 'query-string';
import Fileupload from '../../common/fileupload';

import 'core-js/stable';
import 'regenerator-runtime/runtime';



class Write extends Component {
  constructor(props) {
    super(props);

    let query = queryString.parse(location.search);
    let rtnParam = decodeURIComponent(query.rtnParam);

    this.state = {
      csrf : document.querySelector("#csrf").value,
      Item: {},
      no: query.no,
      rtnParam : rtnParam
    };

    this.onChange = this.onChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.loadItem = this.loadItem.bind(this);

    this.loadItem();
  }

  componentDidMount() {
  }

  componentDidUpdate(prevProps, prevState){
   
  }

  async loadItem() {
    await axios({
      method: 'get',
      url: './api/write',
      params: {
        no: this.state.no 
      }
    }).then(({data}) => {
      this.setState({ 
        Item: data.rtnMap
      });   
      
    });
  }
  
  onChange(e) {
    let item = Object.assign(this.state.Item, {
                [e.target.name] : e.target.value
               });

    this.setState({
      Item : item
    });

  }
  
  onSubmit(e) {
    e.preventDefault();
    
    
  }

  handleClick(e){
    e.preventDefault();
    
    switch(e.target.nodeName){
      case "BUTTON": 
        document.querySelector(".tempArea").firstChild.click();
        break;
      default:
        console.log("이벤트 활성화");
    }
    
  }

  render() {
    const {Item, csrf} = this.state;
    const {onChange, onSubmit, handleClick} = this;
    let maxSize =  document.querySelector("#maxSize").value;
    let extns =  document.querySelector("#extns").value;
    
    return (
      <div>
        <h4>회원 상세</h4>
        <form action="./update" method="post" name="frm" encType="multipart/form-data">
          <input type="hidden" name="_csrf" value={csrf || ""} />
          <input type="hidden" name="no" value={Item.no || "" } />
          <div>
            <label>ID : </label>
            <label>{Item.memberId}</label>
          </div>
          <div>
              <input type="radio" name="memberType" value="10" checked={Item.memberType === "10"} onChange={onChange}/> 일반
              <input type="radio" name="memberType" value="11" checked={Item.memberType === "11"} onChange={onChange}/> 특수
          </div>
          <div>
            <label>이름 : </label>
            <input type="text" name="memberName" value={Item.memberName || '' } onChange={onChange}/>
          </div>
          <div>
            <label>가입일 : </label>
            <label>{Item.regDtm}</label>
          </div>
          <button type="button" onClick={handleClick}>파일업로드</button>
          <Fileupload maxSize={maxSize} extns={extns} maxFile={3} atchName={"atchFileImg"} fileId={Item.fileId} />
          <div><button type="submit">수정</button></div>
        </form>
        <div>
    
        </div>
        <div><Link to={"/member/list?" + this.state.rtnParam}>돌아가기</Link></div>
      </div>
    );
  }
}


export default Write;
