
import React from 'react';
import { Link } from 'react-router-dom';

const listItem = (props) => {
    return (
        <ul className="list_member">
            { props.list.map((ItemList, index) => (
                <div className="list" key={index}>
                    아이디 <Link to={'/member/write?no='+ ItemList.no} >{ItemList.memberId}</Link> 이름 {ItemList.memberName} 가입일 {ItemList.regDtm}
                </div>
            ))}
        </ul>
    )
}

export default listItem;

