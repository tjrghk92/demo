import React, { Component } from 'react';
import axios from 'axios';
import queryString from 'query-string';


import 'core-js/stable';
import 'regenerator-runtime/runtime';



class Write extends Component {
  constructor(props) {
    super(props);

    let query = queryString.parse(location.search);
   
    this.state = {
      csrf : document.querySelector("#csrf").value,
     
    };

   

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
  
  

  render() {
    
    return (
      <div>
        <h4>메뉴</h4>
        
        <div>
    
        </div>
        
      </div>
    );
  }
}


export default Write;
