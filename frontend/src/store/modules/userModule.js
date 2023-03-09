/* eslint-disable */
import produce from 'immer';

const initialState = {
  // 회원가입
  signUpRequest: false,
  signUpDone: false,
  signUpError: null,
  // 로그인
  logInRequest: false,
  logInDone: false,
  logInError: null,
  // 로그아웃
  logOutRequest: false, // logInDone을 없앨거임
  logOutError: null,
  // 아이디 찾기
  findIdRequest: false,
  findIdDone: false,
  findIdError: null,
  // 비밀번호 찾기
  findPwdRequest: false,
  findPwdDone: false,
  findPwdError: null,
  // 비밀번호 수정
  editPwdRequest: false,
  editPwdDone: false,
  editPwdError: null,
  // 회원정보 수정
  editUserRequest: false,
  editUserDone: false,
  editUserError: null,
  // 회원탈퇴
  deleteUserRequest: false,
  deleteUserDone: false,
  deleteUserError: null,
  // 유저 검색
  searchUserRequest: false,
  searchUserDone: false,
  searchUserError: null,
  searchUsers: [],
  // 유저 상세 정보
  userInfoRequest: false,
  userInfoDone: false,
  userInfoError: null,
  userInfo: [],
  // 채팅 알람 여부
  chatAlarm: false,
};

// 회원가입
export const SIGN_UP_REQUEST = 'SIGN_UP_REQUEST';
export const SIGN_UP_SUCCESS = 'SIGN_UP_SUCCESS';
export const SIGN_UP_FAILURE = 'SIGN_UP_FAILURE';
// 로그인
export const LOG_IN_REQUEST = 'LOG_IN_REQUEST';
export const LOG_IN_SUCCESS = 'LOG_IN_SUCCESS';
export const LOG_IN_FAILURE = 'LOG_IN_FAILURE';
// 로그아웃
export const LOG_OUT_REQUEST = 'LOG_OUT_REQUEST';
export const LOG_OUT_SUCCESS = 'LOG_OUT_SUCCESS';
export const LOG_OUT_FAILURE = 'LOG_OUT_FAILURE';
// 아이디 찾기
export const FIND_ID_REQUEST = 'FIND_ID_REQUEST';
export const FIND_ID_SUCCESS = 'FIND_ID_SUCCESS';
export const FIND_ID_FAILURE = 'LOG_IN_FAILURE';
// 비밀번호 찾기
export const FIND_PWD_REQUEST = 'FIND_PWD_REQUEST';
export const FIND_PWD_SUCCESS = 'FIND_PWD_SUCCESS';
export const FIND_PWD_FAILURE = 'FIND_PWD_FAILURE';
// 비밀번호 변경
export const EDIT_PWD_REQUEST = 'EDIT_PWD_REQUEST';
export const EDIT_PWD_SUCCESS = 'EDIT_PWD_SUCCESS';
export const EDIT_PWD_FAILURE = 'EDIT_PWD_FAILURE';
// 회원정보 수정
export const EDIT_USER_REQUEST = 'EDIT_USER_REQUEST';
export const EDIT_USER_SUCCESS = 'EDIT_USER_SUCCESS';
export const EDIT_USER_FAILURE = 'EDIT_USER_FAILURE';
// 회원탈퇴
export const DELETE_USER_REQUEST = 'DELETE_USER_REQUEST';
export const DELETE_USER_SUCCESS = 'DELETE_USER_SUCCESS';
export const DELETE_USER_FAILURE = 'DELETE_USER_FAILURE';
// 유저 검색
export const SEARCH_USER_REQUEST = 'SEARCH_USER_REQUEST';
export const SEARCH_USER_SUCCESS = 'SEARCH_USER_SUCCESS';
export const SEARCH_USER_FAILURE = 'SEARCH_USER_FAILURE';
export const SEARCH_USER_RESET = 'SEARCH_USER_RESET';
// 유저 상세 정보
export const USER_INFO_REQUEST = 'USER_INFO_REQUEST';
export const USER_INFO_SUCCESS = 'USER_INFO_SUCCESS';
export const USER_INFO_FAILURE = 'USER_INFO_FAILURE';
export const USER_INFO_RESET = 'USER_INFO_RESET';

// 채팅 알람
export const CHAT_ALARM = 'CHAT_ALARM';

const reducer = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      // 회원가입
      case SIGN_UP_REQUEST:
        draft.signUpRequest = true;
        draft.signUpDone = false;
        draft.signUpError = null;
        break;
      case SIGN_UP_SUCCESS:
        draft.signUpRequest = false;
        draft.signUpDone = true;
        break;
      case SIGN_UP_FAILURE:
        draft.signUpRequest = false;
        draft.signUpError = action.error;
        break;
      // 로그인
      case LOG_IN_REQUEST:
        draft.logInRequest = true;
        draft.logInDone = false;
        draft.logInError = null;
        if (draft.signUpDone) {
          draft.signUpDone = false;
        }
        break;
      case LOG_IN_SUCCESS:
        draft.logInRequest = false;
        draft.logInDone = true;
        break;
      case LOG_IN_FAILURE:
        draft.logInRequest = false;
        draft.logInError = action.error;
        break;
      // 로그아웃
      case LOG_OUT_REQUEST:
        draft.logOutRequest = true;
        draft.logOutError = null;
        break;
      case LOG_OUT_SUCCESS:
        draft.logOutRequest = false;
        draft.logInDone = false;
        break;
      case LOG_OUT_FAILURE:
        draft.logOutRequest = false;
        draft.logOutError = action.error;
        break;
      // 아이디 찾기
      case FIND_ID_REQUEST:
        draft.findIdRequest = true;
        draft.findIdDone = false;
        draft.findIdError = null;
        break;
      case FIND_ID_SUCCESS:
        draft.findIdRequest = false;
        draft.logInDone = true;
        break;
      case FIND_ID_FAILURE:
        draft.findIdRequest = false;
        draft.findIdError = action.error;
        break;
      // 비밀번호 찾기
      case FIND_PWD_REQUEST:
        draft.findPwdRequest = true;
        draft.findPwdDone = false;
        draft.findPwdError = null;
        break;
      case FIND_PWD_SUCCESS:
        draft.findPwdRequest = false;
        draft.findPwdDone = true;
        break;
      case FIND_PWD_FAILURE:
        draft.findPwdRequest = false;
        draft.findPwdError = action.error;
        break;
      // 비밀번호 변경
      case EDIT_PWD_REQUEST:
        draft.editPwdRequest = true;
        draft.editPwdDone = false;
        draft.editPwdError = null;
        break;
      case EDIT_PWD_SUCCESS:
        draft.editPwdRequest = false;
        draft.editPwdDone = true;
        break;
      case EDIT_PWD_FAILURE:
        draft.editPwdRequest = false;
        draft.editPwdError = action.error;
        break;
      // 회원정보 수정
      case EDIT_USER_REQUEST:
        draft.editUserRequest = true;
        draft.editUserDone = false;
        draft.editUserError = null;
        break;
      case EDIT_USER_SUCCESS:
        draft.editUserRequest = false;
        draft.editUserDone = true;
        break;
      case EDIT_USER_FAILURE:
        draft.editUserRequest = false;
        draft.editUserError = action.error;
        break;
      // 회원탈퇴
      case DELETE_USER_REQUEST:
        draft.deleteUserRequest = true;
        draft.deleteUserDone = false;
        draft.deleteUserError = null;
        break;
      case DELETE_USER_SUCCESS:
        draft.deleteUserRequest = false;
        draft.deleteUserDone = true;
        break;
      case DELETE_USER_FAILURE:
        draft.deleteUserRequest = false;
        draft.deleteUserError = action.error;
        break;
      // 유저 검색
      case SEARCH_USER_REQUEST:
        draft.searchUserRequest = true;
        draft.searchUserDone = false;
        draft.searchUserError = null;
        break;
      case SEARCH_USER_SUCCESS:
        draft.searchUserRequest = false;
        draft.searchUserDone = true;
        draft.searchUsers = action.data;
        break;
      case SEARCH_USER_FAILURE:
        draft.searchUserRequest = false;
        draft.searchUserError = action.error;
        break;
      case SEARCH_USER_RESET:
        draft.searchUsers = [];
        break;
      // 유저 상세 정보
      case USER_INFO_REQUEST:
        draft.userInfoRequest = true;
        draft.userInfoDone = false;
        draft.userInfoError = null;
        break;
      case USER_INFO_SUCCESS:
        draft.userInfoRequest = false;
        draft.userInfoDone = true;
        draft.userInfo = action.data;
        break;
      case USER_INFO_FAILURE:
        draft.userInfoRequest = false;
        draft.userInfoError = action.error;
        break;
      case USER_INFO_RESET:
        draft.userInfo = [];
        break;
      case CHAT_ALARM:
        draft.chatAlarm = action.data;
        break;
      default:
        break;
    }
  });

export default reducer;
