/* eslint-disable */
import { all, call, fork, put, takeLatest } from '@redux-saga/core/effects';
import Swal from 'sweetalert2';
// import { useDispatch } from 'react-redux';
// import { Route } from 'react-router';
// import { useNavigate } from 'react-router-dom';
import { customAlert, i1500, s1000, w1000, w1500 } from '../../utils/alarm';
import {
  signUpAPI,
  logInAPI,
  findIdAPI,
  findPwdAPI,
  editPwdAPI,
  editUserAPI,
  userDeleteAPI,
  userSearchAPI,
  userInfoAPI,
} from '../apis/userApi';
import { MY_PAGE_RESET } from '../modules/myPageModule';
import {
  SIGN_UP_REQUEST,
  LOG_IN_REQUEST,
  FIND_ID_REQUEST,
  FIND_PWD_REQUEST,
  EDIT_PWD_REQUEST,
  LOG_IN_SUCCESS,
  LOG_IN_FAILURE,
  LOG_OUT_REQUEST,
  LOG_OUT_SUCCESS,
  LOG_OUT_FAILURE,
  SIGN_UP_SUCCESS,
  FIND_ID_SUCCESS,
  FIND_ID_FAILURE,
  FIND_PWD_SUCCESS,
  FIND_PWD_FAILURE,
  EDIT_PWD_SUCCESS,
  EDIT_PWD_FAILURE,
  EDIT_USER_SUCCESS,
  EDIT_USER_FAILURE,
  EDIT_USER_REQUEST,
  DELETE_USER_SUCCESS,
  DELETE_USER_FAILURE,
  DELETE_USER_REQUEST,
  SEARCH_USER_SUCCESS,
  SEARCH_USER_FAILURE,
  SEARCH_USER_REQUEST,
  USER_INFO_SUCCESS,
  USER_INFO_FAILURE,
  USER_INFO_REQUEST,
  SIGN_UP_FAILURE,
} from '../modules/userModule';
/* eslint-disable */
// 회원가입
function* singUp(action) {
  try {
    const result = yield call(signUpAPI, action.data);
    yield put({ type: SIGN_UP_SUCCESS, data: result.data });
    customAlert(s1000, '회원가입 성공');
    const navigate = action.data.navigate;
    navigate('/user/login');
    // yield put({
    //   type: LOG_IN_REQUEST,
    //   data: {
    //     id: action.data.id,
    //     password: action.data.pwd,
    //     navigate: action.data.navigate,
    //   },
    // });
  } catch (err) {
    yield put({
      type: SIGN_UP_FAILURE,
      data: err,
    });
    customAlert(w1000, '회원가입 실패');
  }
}

function* watchSignup() {
  yield takeLatest(SIGN_UP_REQUEST, singUp);
}

// 로그인
function* logIn(action) {
  try {
    const result = yield call(logInAPI, action.data);
    yield put({ type: LOG_IN_SUCCESS, data: result.data });
    sessionStorage.setItem('userToken', result.data.token);
    customAlert(s1000, '로그인 성공');
    const navigate = action.data.navigate;
    navigate('/');
  } catch (err) {
    customAlert(w1000, '로그인 실패');
    yield put({ type: LOG_IN_FAILURE, data: err });
    // console.log(err);
  }
}

function* watchLogin() {
  yield takeLatest(LOG_IN_REQUEST, logIn);
}

// 로그아웃
function* logOut(action) {
  try {
    // const result = yield call(logOutAPI, action.data);
    yield put({ type: LOG_OUT_SUCCESS });
    sessionStorage.clear();
    yield put({ type: MY_PAGE_RESET });
    const navigate = action.navigate;
    navigate('/');
    window.location.reload();
  } catch (err) {
    // console.log(err);
    yield put({ type: LOG_OUT_FAILURE });
    customAlert(i1500, '로그인이 필요합니다.');
  }
}

function* watchLogOut() {
  yield takeLatest(LOG_OUT_REQUEST, logOut);
}

// 아이디 찾기
function* findId(action) {
  try {
    const result = yield call(findIdAPI, action.data);
    // console.log(result);
    yield put({ type: FIND_ID_SUCCESS, data: result });
    Swal.fire({
      title: '아이디 찾기 완료',
      text: 'ID : ' + result.id,
      icon: 'success',
      confirmButtonText: '로그인',
      confirmButtonColor: 'success',
    }).then(() => {
      const navigate = action.data.navigate;
      navigate('/user/login');
    });
  } catch (err) {
    customAlert(w1500, '아이디 찾기 실패', '아이디와 이메일을 확인해주세요');
    yield put({ type: FIND_ID_FAILURE, data: err });
    // console.log(err);
  }
}

function* watchFindId() {
  yield takeLatest(FIND_ID_REQUEST, findId);
}

// 비밀번호 찾기
function* findPwd(action) {
  try {
    const result = yield call(findPwdAPI, action.data);
    // console.log(result);
    yield put({ type: FIND_PWD_SUCCESS, data: result.data });
    Swal.fire({
      title: ' 비밀번호 찾기',
      text: '등록하신 이메일로 새로운 임시 비밀번호를 전송하였습니다.',
      icon: 'success',
      confirmButtonText: '로그인',
      confirmButtonColor: 'success',
    }).then(() => {
      const navigate = action.data.navigate;
      navigate('/user/login');
    });
  } catch (err) {
    yield put({ type: FIND_PWD_FAILURE, data: err });
    customAlert(
      w1500,
      '비밀번호 찾기 실패',
      '성명, 아이디, 이메일을 확인해주세요',
    );
    // console.log(err);
  }
}

function* watchFindPwd() {
  yield takeLatest(FIND_PWD_REQUEST, findPwd);
}

// 비밀번호 변경
function* editPwd(action) {
  try {
    const result = yield call(editPwdAPI, action.data);
    // console.log(result);
    yield put({ type: EDIT_PWD_SUCCESS, data: result.data });
    customAlert(s1000, '비밀번호 변경 완료');
    setTimeout(() => {
      action.data.navigate('/user/mypage');
    }, 1000);
  } catch (err) {
    yield put({ type: EDIT_PWD_FAILURE, data: err });
    customAlert(w1000, '비밀번호 변경 실패');
  }
}

function* watchEditPwd() {
  yield takeLatest(EDIT_PWD_REQUEST, editPwd);
}

// 회원정보 변경
function* editUser(action) {
  try {
    const result = yield call(editUserAPI, action.data);
    yield put({ type: EDIT_USER_SUCCESS, data: result.data });
    yield put({ type: MY_PAGE_RESET });
    customAlert(s1000, '회원정보 변경 완료');
    setTimeout(() => {
      const navigate = action.data.navigate;
      navigate('/user/mypage');
      window.location.reload();
    }, 1000);
  } catch {
    customAlert(w1000, '회원정보 변경 실패');
    yield put({ type: EDIT_USER_FAILURE, data: err });
  }
}

function* watchEditUser() {
  yield takeLatest(EDIT_USER_REQUEST, editUser);
}

// 회원탈퇴
function* UserDelete(action) {
  try {
    const result = yield call(userDeleteAPI, action.data);
    yield put({ type: DELETE_USER_SUCCESS, data: result.data });
    yield put({ type: MY_PAGE_RESET });
    customAlert(s1000, '회원탈퇴 완료');
    setTimeout(() => {
      const navigate = action.data.navigate;
      sessionStorage.clear();
      navigate('/');
      window.location.reload();
    }, 1000);
  } catch {
    customAlert(w1000, '회원탈퇴 실패', '비밀번호가 틀립니다');
    yield put({ type: DELETE_USER_FAILURE, data: err });
  }
}

function* watchUserDelete() {
  yield takeLatest(DELETE_USER_REQUEST, UserDelete);
}

// 유저 검색
function* UserSearch(action) {
  try {
    const result = yield call(userSearchAPI, action.data);
    yield put({ type: SEARCH_USER_SUCCESS, data: result.data });
  } catch {
    yield put({ type: SEARCH_USER_FAILURE, data: err });
  }
}

function* watchUserSearch() {
  yield takeLatest(SEARCH_USER_REQUEST, UserSearch);
}

// 유저 상세 정보
function* UserInfo(action) {
  try {
    const result = yield call(userInfoAPI, action.data);
    // console.log(result.data);
    yield put({ type: USER_INFO_SUCCESS, data: result.data });
  } catch {
    yield put({ type: USER_INFO_FAILURE, data: err });
  }
}

function* watchUserInfo() {
  yield takeLatest(USER_INFO_REQUEST, UserInfo);
}

export default function* userSaga() {
  yield all([
    fork(watchSignup),
    fork(watchLogin),
    fork(watchLogOut),
    fork(watchFindId),
    fork(watchFindPwd),
    fork(watchEditPwd),
    fork(watchEditUser),
    fork(watchUserDelete),
    fork(watchUserSearch),
    fork(watchUserInfo),
  ]);
}
