import { all, call, fork, put, takeLatest } from '@redux-saga/core/effects';
import { customAlert, s1000, w1000 } from '../../utils/alarm';
// import { createChatRoomAPI1 } from '../apis/chatApi';
import {
  createMeetingAPI,
  deleteMeetingAPI,
  exitMeetingAPI,
  getListAPI,
  participationAPI,
  searchRoomAPI,
  updateMeetingAPI,
} from '../apis/meetingApi';
import {
  DELETE_MEETING_FAILURE,
  DELETE_MEETING_REQUEST,
  DELETE_MEETING_SUCCESS,
  GET_LIST_FAILURE,
  GET_LIST_REQUEST,
  GET_LIST_SUCCESS,
  MEETING_CREATE_FAILURE,
  MEETING_CREATE_REQUEST,
  MEETING_CREATE_SUCCESS,
  MEETING_EXIT_FAILURE,
  MEETING_EXIT_REQUEST,
  MEETING_EXIT_SUCCESS,
  MEETING_UPDATE_FAILURE,
  MEETING_UPDATE_REQUEST,
  MEETING_UPDATE_SUCCESS,
  PARTICIPATION_FAILURE,
  PARTICIPATION_REQUEST,
  PARTICIPATION_SUCCESS,
  SEARCH_LIST_FAILURE,
  SEARCH_LIST_REQUEST,
  SEARCH_LIST_SUCCESS,
} from '../modules/meetingModule';

// 모임방 불러오기
function* getList(action) {
  try {
    const result = yield call(getListAPI, action.data);
    yield put({ type: GET_LIST_SUCCESS, data: result.data });
  } catch (err) {
    yield put({ type: GET_LIST_FAILURE, data: err });
  }
}

function* watchGetList() {
  yield takeLatest(GET_LIST_REQUEST, getList);
}

// 모임방 생성
function* create(action) {
  try {
    const result = yield call(createMeetingAPI, action.data);
    yield put({ type: MEETING_CREATE_SUCCESS, data: result.data });
    // const data = { token: action.data.token, name: action.data.title };
    // createChatRoomAPI1(data);
    customAlert(s1000, '모임방 생성');
    setTimeout(() => {
      window.location.reload();
    }, 1000);
  } catch (err) {
    customAlert(w1000, '생성 실패');
    yield put({ type: MEETING_CREATE_FAILURE, data: err });
  }
}

function* watchCreate() {
  yield takeLatest(MEETING_CREATE_REQUEST, create);
}

// 모임방 수정
function* update(action) {
  try {
    const result = yield call(updateMeetingAPI, action.data);
    yield put({ type: MEETING_UPDATE_SUCCESS, data: result.data });
    customAlert(s1000, '모임방 수정');
    setTimeout(() => {
      window.location.reload();
    }, 1000);
  } catch (err) {
    customAlert(w1000, '수정 실패');
    yield put({ type: MEETING_UPDATE_FAILURE, data: err });
  }
}

function* watchUpdate() {
  yield takeLatest(MEETING_UPDATE_REQUEST, update);
}

// 모임방 참여
function* participation(action) {
  try {
    const result = yield call(exitMeetingAPI, action.data);
    yield put({ type: MEETING_EXIT_SUCCESS, data: result.data });
  } catch (err) {
    yield put({ type: MEETING_EXIT_FAILURE, data: err });
  }
}

function* watchParticipation() {
  yield takeLatest(MEETING_EXIT_REQUEST, participation);
}

// 모임방 나가기
function* exit(action) {
  try {
    const result = yield call(participationAPI, action.data);
    yield put({ type: PARTICIPATION_SUCCESS, data: result.data });
  } catch (err) {
    yield put({ type: PARTICIPATION_FAILURE, data: err });
  }
}

function* watchExit() {
  yield takeLatest(PARTICIPATION_REQUEST, exit);
}

// 모임방 삭제
function* deleteMeeting(action) {
  try {
    const result = yield call(deleteMeetingAPI, action.data);
    yield put({ type: DELETE_MEETING_SUCCESS, data: result.data });
    window.location.reload();
  } catch (err) {
    yield put({ type: DELETE_MEETING_FAILURE, data: err });
  }
}

function* watchDeleteMeeting() {
  yield takeLatest(DELETE_MEETING_REQUEST, deleteMeeting);
}

// 모임방 검색
function* searchMeeting(action) {
  try {
    const result = yield call(searchRoomAPI, action.data);
    yield put({ type: SEARCH_LIST_SUCCESS, data: result.data });
  } catch (err) {
    yield put({ type: SEARCH_LIST_FAILURE, data: err });
  }
}

function* watchSearchMeeting() {
  yield takeLatest(SEARCH_LIST_REQUEST, searchMeeting);
}

export default function* meetingSaga() {
  yield all([fork(watchGetList)]);
  yield all([fork(watchCreate)]);
  yield all([fork(watchUpdate)]);
  yield all([fork(watchParticipation)]);
  yield all([fork(watchExit)]);
  yield all([fork(watchDeleteMeeting)]);
  yield all([fork(watchSearchMeeting)]);
}
