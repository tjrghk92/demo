import {PAGING, SEARCH} from '../action/action';

const initialState = {
    pageIndex: 1,
    totalCnt: 10,
    cntPage: 10,
    pageSize: 10 
}

 const page = (state = initialState, action) => {

    if(typeof action.pageMap !== "undefined"){
        typeof action.pageMap.pageIndex !== "undefined" ? state.pageIndex = parseInt(action.pageMap.pageIndex) : state.pageIndex = initialState.pageIndex
        typeof action.pageMap.totalCnt !== "undefined" ? state.totalCnt = parseInt(action.pageMap.totalCnt) : state.totalCnt = initialState.totalCnt
        typeof action.pageMap.cntPage !== "undefined" ? state.cntPage = parseInt(action.pageMap.cntPage) : state.cntPage = initialState.cntPage
        typeof action.pageMap.pageSize !== "undefined" ? state.pageSize = parseInt(action.pageMap.pageSize) : state.pageSize = initialState.pageSize
    }

    switch(action.type) {
        case PAGING:
            return Object.assign({}, state, {
                pageIndex: state.pageIndex,
                totalCnt: state.totalCnt,
                cntPage: state.cntPage,
                pageSize: state.pageSize,
            });
        case SEARCH:
            return Object.assign({}, state, {
                pageIndex: state.pageIndex,
                totalCnt: state.totalCnt,
                cntPage: state.cntPage,
                pageSize: state.pageSize,
            });
        default:
            return state ;
    }
}

export default page;
