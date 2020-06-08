
import React from 'react';
import { Link } from 'react-router-dom';
import queryString from 'query-string';

const listItem = (props) => {
    let param = queryString.stringify(props.param);
    param = encodeURIComponent(param);
    
    return (
        <ul className="list_member">
            { props.list.map((ItemList, index) => (
                <div className="list" key={index}>
                    아이디 <Link to={'/member/write?no='+ ItemList.no + "&rtnParam=" + param} >{ItemList.memberId}</Link> 이름 {ItemList.memberName} 가입일 {ItemList.regDtm}
                </div>
            ))}
        </ul>
    )
}

export default listItem;

