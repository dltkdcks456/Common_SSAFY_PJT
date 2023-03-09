/* eslint-disable */
import produce from 'immer';

const initialState = {
  // 마이페이지
  myPageRequest: false,
  myPageDone: false,
  myPageError: null,
  user: {},
  // name: '',
  // image_path: '',
  // nickname: '',
  // age: '',
  // si: '',
  // gun: '',
  // dong: '',
  // email: '',
  // weight: 0,
  // permission: '',
  // open: false,
};

// 마이페이지 정보 불러오기
export const MY_PAGE_REQUEST = 'MY_PAGE_REQUEST';
export const MY_PAGE_SUCCESS = 'MY_PAGE_SUCCESS';
export const MY_PAGE_FAILURE = 'MY_PAGE_FAILURE';
export const MY_PAGE_RESET = 'MY_PAGE_RESET';

const reducer = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case MY_PAGE_REQUEST:
        draft.myPageRequest = true;
        draft.myPageDone = false;
        draft.myPageError = null;
        break;
      case MY_PAGE_SUCCESS:
        draft.myPageRequest = false;
        draft.myPageDone = true;
        draft.user = action.data;
        // console.log(draft.user);
        break;
      case MY_PAGE_FAILURE:
        draft.myPageRequest = false;
        draft.myPageError = action.error;
        sessionStorage.clear();
        break;
      case MY_PAGE_RESET:
        draft.myPageRequest = false;
        draft.myPageDone = false;
        draft.user = {};
        break;
      default:
        break;
    }
  });

export default reducer;
