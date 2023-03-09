/* eslint-disable */
import { all, call, fork, put, takeLatest } from '@redux-saga/core/effects';
import { getRoomsDetailAPI } from '../apis/chatApi';
import {
  CHAT_LIST_FAILURE,
  CHAT_LIST_REQUEST,
  CHAT_LIST_SUCCESS,
} from '../modules/chatModule';

// 채팅
function* chatList(action) {
  try {
    const result = yield call(getRoomsDetailAPI, action.data);
    yield put({ type: CHAT_LIST_SUCCESS, data: result });
  } catch (err) {
    yield put({ type: CHAT_LIST_FAILURE, data: err });
  }
}

function* watchChatList() {
  yield takeLatest(CHAT_LIST_REQUEST, chatList);
}

export default function* chatSaga() {
  yield all([fork(watchChatList)]);
}
