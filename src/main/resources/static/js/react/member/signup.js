import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Fileupload from '../../common/fileupload';


class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      csrf : document.querySelector("#csrf").value,
      memberType : "10"
    }

    this.onChange = this.onChange.bind(this);
    this.handleClick = this.handleClick.bind(this);
  }

  componentDidMount() {
    
  }

  componentDidUpdate(prevProps, prevState){
  
  }
  
  onChange(e) {
    this.setState({
      [e.target.name] : e.target.value
    });
  }

  handleClick(e){
    e.preventDefault();
    
    switch(e.target.nodeName){
      case "A" :
        location.href = e.target.dataset.link;
        break;
      case "BUTTON": 
        document.querySelector(".tempArea").firstChild.click();
        break;
      default:
        console.log("이벤트 활성화");
    }
    
  }

  render() {
    const {csrf, memberId, memberPass, memberName, memberType} = this.state;
    const {onChange, handleClick } = this;
    let maxSize =  document.querySelector("#maxSize").value;
    let extns =  document.querySelector("#extns").value;

    const style = {
      div : {
        display: "flex",
        alignItems: "center"
      },
      p : {
        float: "left",
        width: "7%"
      },
      input : {
        marginLeft: "10px"
      }
    }
    
    return (
      <div>
        <form action="./signupProcess" method="post" name="frm" encType="multipart/form-data">
          <input type="hidden" name="_csrf" value={csrf || ""} />
          <div>
              <input type="radio" name="memberType" value="10" checked={memberType === "10"} onChange={onChange}/> 일반
              <input type="radio" name="memberType" value="11" checked={memberType === "11"} onChange={onChange}/> 특수
          </div>
          <div style={style.div}>
              <p style={style.p}>아이디 :</p> <input type="text" name="memberId" value={memberId == null ? "" : memberId} onChange={onChange}/> 
          </div>
          <div style={style.div}>
              <p style={style.p}>비밀번호 :</p> <input type="text" name="memberPass" value={memberPass == null ? "" : memberPass} onChange={onChange}/> 
          </div>
          <div style={style.div}>
              <p style={style.p}>이름 :</p> <input type="text" name="memberName" value={memberName == null ? "" : memberName} onChange={onChange}/> 
              <input style={style.input} type="submit" value="가입"/>
          </div>
          <button type="button" onClick={handleClick}>파일업로드</button>
          <Fileupload maxSize={maxSize} extns={extns} maxFile={3} atchName={"atchFileImg"} />
        </form>   
        <div><Link to="/member/login">로그인</Link> <a href = "#" onClick={handleClick } data-link="/" >메인으로</a></div>
      </div>
    );
  }
}


export default Login;
