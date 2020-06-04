// 액션 타입 정의
export const PAGING = 'PAGING' ;
export const SEARCH = 'SEARCH' ;


// 액션 생성 함수 정의
export const paging = (pageMap) => ({ type: PAGING, pageMap: pageMap}) ;
export const search = (pageMap) => ({ type: SEARCH, pageMap: pageMap}) ;