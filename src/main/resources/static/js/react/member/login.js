import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      csrf : document.querySelector("#csrf").value
    };

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
    location.href = e.target.dataset.link;
  }

  render() {
    const {csrf, memberId, memberPass} = this.state;
    const {onChange, handleClick} = this;
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
        <form action="./loginProcess" method="post" name="frm">
          <div style={style.div}>
              <p style={style.p}>아이디 :</p> <input type="text" name="memberId" value={memberId == null ? "" : memberId} onChange={onChange}/> 
          </div>
          <div style={style.div}>
              <p style={style.p}>비밀번호 :</p> <input type="text" name="memberPass" value={memberPass == null ? "" : memberPass} onChange={onChange}/> 
              <input style={style.input} type="submit" value="로그인"/>
          </div>
          <input type="hidden" name="_csrf" value={csrf == null ? "" : csrf} />
        </form>
        <div><Link to="/member/signup">회원가입</Link>  <a href = "#" onClick={handleClick } data-link="/" >메인으로</a></div>
      </div>
    );
  }
}


export default Login;
