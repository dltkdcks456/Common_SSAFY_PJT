/* eslint-disable */
import { all, call, fork, put, takeLatest } from '@redux-saga/core/effects';
import { myPageAPI } from '../apis/myPageApi';
// import Swal from 'sweetalert2';
// import { CONTEXT_URL } from '../../utils/urls';
import {
  MY_PAGE_FAILURE,
  MY_PAGE_REQUEST,
  MY_PAGE_SUCCESS,
} from '../modules/myPageModule';

// 마이페이지
function* myPage(action) {
  try {
    const result = yield call(myPageAPI, action.data);
    yield put({ type: MY_PAGE_SUCCESS, data: result.data });
  } catch (err) {
    yield put({ type: MY_PAGE_FAILURE, data: err });
  }
}

function* watchMyPage() {
  yield takeLatest(MY_PAGE_REQUEST, myPage);
}

export default function* myPageSaga() {
  yield all([fork(watchMyPage)]);
}
