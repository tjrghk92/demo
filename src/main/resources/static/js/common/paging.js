import React, { Component } from 'react';
import Pagination from "react-js-pagination";
import {connect} from 'react-redux';
import { paging, search } from './action/action';

class Paging extends Component {
    //constructor(props) {
        //super(props);

        // let pageIndex = 1;
        // let cntPage = 10;
        // let totalCnt = parseInt(this.props.totalCnt.value);
        // let pageSize = 10;

        // if(typeof this.props.pageIndex != "undefined" && this.props.pageIndex.value != "" )
        //     pageIndex = parseInt(this.props.pageIndex.value);
       
        // if(typeof this.props.cntPage != "undefined" && this.props.cntPage.value != "" )
        //     cntPage = parseInt(this.props.cntPage.value);
       
        // if(typeof this.props.pageSize != "undefined" && this.props.pageSize.value != "" )
        //     pageSize = parseInt(this.props.pageSize.value);

            
        // this.state = {
        //     activePage: this.props.pageIndex,
        //     cntPage: cntPage,
        //     totalCnt: totalCnt,
        //     pageSize: pageSize
        // };
    //}
    
    handlePageChange(pageIndex) {
        console.log(`active page is ${pageIndex}`);
        
        if(this.props.pageIndex !== pageIndex){
            const pageMap = {
                pageIndex: pageIndex
            }
            this.props.goPage(pageMap);
        }
        
    }

    render() {
        return (
            <div className="paging">
                <Pagination
                    activePage={this.props.pageIndex}
                    itemsCountPerPage={this.props.cntPage}
                    totalItemsCount={this.props.totalCnt}
                    pageRangeDisplayed={this.props.pageSize}
                    prevPageText={'prev'}
                    nextPageText={'next'}
                    onChange={this.handlePageChange.bind(this)}
                />
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        pageIndex: state.page.pageIndex,
        cntPage: state.page.cntPage,
        totalCnt: state.page.totalCnt,
        pageSize: state.page.pageSize,
    };
};

const mapDispatchToProps = (Dispatch) => {
    return {
        goPage: (pageMap) => Dispatch(paging(pageMap))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Paging);