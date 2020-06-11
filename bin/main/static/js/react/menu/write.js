import React, { Component } from 'react';
import axios from 'axios';
import queryString from 'query-string';
import 'react-sortable-tree/style.css';
import SortableTree from 'react-sortable-tree';
import '../../../css/common/menu.css';
import 'core-js/stable';
import 'regenerator-runtime/runtime';



class Write extends Component {
  constructor(props) {
    super(props);

    let query = queryString.parse(location.search);
   
    this.state = {
      csrf : document.querySelector("#csrf").value,
      treeData: [
        { title: 'Chicken', children: [{ title: 'Egg' }] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] },
        { title: 'Fish', children: [{ title: 'fingerline'}] }
      ],
     
    };

    this.nodeClicked = this.nodeClicked.bind(this);

   

    //this.loadItem();
  }

  componentDidMount() {
  }

  componentDidUpdate(prevProps, prevState){
   
  }

  async loadItem() {
    // await axios({
    //   method: 'get',
    //   url: './api/write',
    //   params: {
    //     no: this.state.no 
    //   }
    // }).then(({data}) => {
    //   this.setState({ 
    //     Item: data.rtnMap
    //   });   
      
    // });
  }

  nodeClicked(e, rowInfo){
    console.log(rowInfo)
    if (event.target.className.includes('collapseButton') ||
        event.target.className.includes('expandButton')) {
        // ignore the event
    } else {
        this.setState({selectedNodeId: rowInfo.node.id});
    }

    
  }
  
  

  render() {
    const {csrf} = this.state;
    const style = {
      createBtn : {
        marginBottom : "20px"
      },
      marginLeftBtn : {
        marginLeft : "20px"
      }
    }
   

    return (
      <div>
        <h4>메뉴</h4>
        <div style={style.createBtn}>
          <button type="button">생성</button>
        </div>
        <div className="menuBox">
          <div className="tree">
            
            <SortableTree
                treeData={this.state.treeData}
                onChange={treeData => this.setState({ treeData })}
                getNodeKey={({ node,treeIndex }) => node.id = treeIndex}
                generateNodeProps={rowInfo => {
                  let nodeProps =  { onClick: e => this.nodeClicked(e, rowInfo) };
                  if (this.state.selectedNodeId === rowInfo.node.id) {
                      nodeProps.className = 'selected-node';
                  }
                  return nodeProps;
                }}
            />
          </div>
          <div className="form" >
            <div className="formData">
              <form action="./signupProcess" method="post" name="frm">
                <input type="hidden" name="_csrf" value={csrf || ""} />
                <div className="dataBox">
                  <label>메뉴 순번 : </label>
                  <input type="hidden" name="order" />
                </div>
                <div className="dataBox">
                  <label>이름 : </label>
                  <input type="text" className="name" name="name"  /> 
                </div>
                <div className="dataBox">
                  <label>path : </label>
                  <input type="text" className="path" name="path"  /> 
                </div>
                <div className="dataBox">
                  <label>노출여부 : </label>
                  <input type="radio" name="useFlag" value="1" /> 노출
                  <input type="radio" name="useFlag" value="0" /> 미노출
                </div>
                <div className="dataBox">
                  <label>새창여부 : </label>
                  <input type="radio" name="blankFlag" value="1" /> 예
                  <input type="radio" name="blankFlag" value="0" /> 아니오
                </div>
                <div>
                  <button type="button">적용</button> 
                  <button style={style.marginLeftBtn} type="button">삭제</button>
                </div>
              </form>
            </div>
          </div>
        </div>
        
      </div>
    );
  }
}


export default Write;
