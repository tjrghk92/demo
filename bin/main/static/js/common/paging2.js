import React, { Component } from 'react';
import Pagination from "react-js-pagination";


class Paging extends Component {
    constructor(props) {
        super(props);

        this.state = {
            activePage: this.props.pageIndex,
            cntPage: this.props.cntPage,
            totalCnt: this.props.totalCnt,
            pageSize: this.props.pageSize
        };
    }
    
    handlePageChange(pageIndex) {
        console.log(`active page is ${pageIndex}`);
    }

    render() {
        console.log(this.props)
        
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


export default Paging;