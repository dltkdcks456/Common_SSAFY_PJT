/* eslint-disable */
import { all, call, fork, put, takeLatest } from '@redux-saga/core/effects';
import {
  createCourseAPI,
  createCourseCommentAPI,
  deleteCourseCommentAPI,
  loadCourseAPI,
  loadCourseDetailAPI,
  loadCourseLikeAPI,
  loadSaveCourseAPI,
  saveCourseAPI,
  searchCourseAPI,
  setCourseLikeAPI,
  updateCourseAPI,
  updateCourseCommentAPI,
} from '../apis/courseApi';
import {
  CREATE_COURSE_COMMENT_FAILURE,
  CREATE_COURSE_COMMENT_REQUEST,
  CREATE_COURSE_COMMENT_SUCCESS,
  CREATE_COURSE_FAILURE,
  CREATE_COURSE_REQUEST,
  CREATE_COURSE_SUCCESS,
  DELETE_COURSE_COMMENT_FAILURE,
  DELETE_COURSE_COMMENT_REQUEST,
  DELETE_COURSE_COMMENT_SUCCESS,
  LIKE_CLICK_COURSE_FAILURE,
  LIKE_CLICK_COURSE_REQUEST,
  LIKE_CLICK_COURSE_SUCCESS,
  LOAD_COURSE_DETAIL_FAILURE,
  LOAD_COURSE_DETAIL_REQUEST,
  LOAD_COURSE_DETAIL_SUCCESS,
  LOAD_COURSE_FAILURE,
  LOAD_COURSE_REQUEST,
  LOAD_COURSE_SUCCESS,
  LOAD_LIKE_COURSE_FAILURE,
  LOAD_LIKE_COURSE_REQUEST,
  LOAD_LIKE_COURSE_SUCCESS,
  LOAD_SAVE_COURSE_FAILURE,
  LOAD_SAVE_COURSE_REQUEST,
  LOAD_SAVE_COURSE_SUCCESS,
  SAVE_COURSE_FAILURE,
  SAVE_COURSE_REQUEST,
  SAVE_COURSE_SUCCESS,
  SEARCH_COURSE_FAILURE,
  SEARCH_COURSE_REQUEST,
  SEARCH_COURSE_SUCCESS,
  UPDATE_COURSE_COMMENT_FAILURE,
  UPDATE_COURSE_COMMENT_REQUEST,
  UPDATE_COURSE_COMMENT_SUCCESS,
  UPDATE_COURSE_FAILURE,
  UPDATE_COURSE_REQUEST,
  UPDATE_COURSE_SUCCESS,
} from '../modules/courseModule';

// 경로 추천 목록 조회
function* loadCourse(action) {
  try {
    const result = yield call(loadCourseAPI, action.data);
    yield put({
      type: LOAD_COURSE_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_COURSE_FAILURE,
    });
  }
}

function* watchLoadCourse() {
  yield takeLatest(LOAD_COURSE_REQUEST, loadCourse);
}

// 경로 추천 검색
function* searchCourse(action) {
  try {
    const result = yield call(searchCourseAPI, action.data);
    yield put({
      type: SEARCH_COURSE_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: SEARCH_COURSE_FAILURE,
    });
  }
}

function* watchSearchCourse() {
  yield takeLatest(SEARCH_COURSE_REQUEST, searchCourse);
}

// 경로 추천 상세 정보 조회
function* loadCourseDetail(action) {
  try {
    const result = yield call(loadCourseDetailAPI, action.data);
    yield put({
      type: LOAD_COURSE_DETAIL_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_COURSE_DETAIL_FAILURE,
    });
  }
}

function* watchLoadCourseDetail() {
  yield takeLatest(LOAD_COURSE_DETAIL_REQUEST, loadCourseDetail);
}

// 경로 추천 작성
function* createCourse(action) {
  try {
    yield call(createCourseAPI, action.data);
    yield put({
      type: CREATE_COURSE_SUCCESS,
    });
    const navigate = action.data.navigate;
    navigate(`/course`);
  } catch (error) {
    // console.log(error);
    yield put({
      type: CREATE_COURSE_FAILURE,
    });
  }
}

function* watchCreateCourse() {
  yield takeLatest(CREATE_COURSE_REQUEST, createCourse);
}

// 경로 추천 댓글 작성
function* createCourseComment(action) {
  try {
    yield call(createCourseCommentAPI, action.data);
    yield put({
      type: CREATE_COURSE_COMMENT_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: CREATE_COURSE_COMMENT_FAILURE,
    });
  }
}

function* watchCreateCourseComment() {
  yield takeLatest(CREATE_COURSE_COMMENT_REQUEST, createCourseComment);
}

// 경로 추천 댓글 수정
function* updateCourseComment(action) {
  try {
    yield call(updateCourseCommentAPI, action.data);
    yield put({
      type: UPDATE_COURSE_COMMENT_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: UPDATE_COURSE_COMMENT_FAILURE,
    });
  }
}

function* watchUpdateCourseComment() {
  yield takeLatest(UPDATE_COURSE_COMMENT_REQUEST, updateCourseComment);
}

// 경로 추천 댓글 삭제
function* deleteCourseComment(action) {
  try {
    yield call(deleteCourseCommentAPI, action.data);
    yield put({
      type: DELETE_COURSE_COMMENT_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: DELETE_COURSE_COMMENT_FAILURE,
    });
  }
}

function* watchDeleteCourseComment() {
  yield takeLatest(DELETE_COURSE_COMMENT_REQUEST, deleteCourseComment);
}

// 경로 추천 좋아요 클릭
function* setCourseLike(action) {
  try {
    yield call(setCourseLikeAPI, action.data);
    yield put({
      type: LIKE_CLICK_COURSE_SUCCESS,
    });
    const navigate = action.data.navigate;
    // console.log('되나?');
    navigate(`/course/detail`, {
      state: { courseId: action.data.courseBoardId },
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LIKE_CLICK_COURSE_FAILURE,
    });
  }
}

function* watchSetCourseLike() {
  yield takeLatest(LIKE_CLICK_COURSE_REQUEST, setCourseLike);
}

// 경로 추천 좋아요 조회
function* loadCourseLike(action) {
  try {
    const result = yield call(loadCourseLikeAPI, action.data);
    yield put({
      type: LOAD_LIKE_COURSE_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: LOAD_LIKE_COURSE_FAILURE,
    });
  }
}

function* watchLoadCourseLike() {
  yield takeLatest(LOAD_LIKE_COURSE_REQUEST, loadCourseLike);
}

// 경로 추천 수정
function* updateCourse(action) {
  try {
    yield call(updateCourseAPI, action.data);
    yield put({
      type: UPDATE_COURSE_SUCCESS,
    });
    const navigate = action.data.navigate;
    navigate(`/course/detail`, {
      state: { courseId: action.data.course_board_id },
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: UPDATE_COURSE_FAILURE,
    });
  }
}

function* watchUpdateCourse() {
  yield takeLatest(UPDATE_COURSE_REQUEST, updateCourse);
}

// 경로 추천 저장(찜)
function* saveCourse(action) {
  try {
    yield call(saveCourseAPI, action.data);
    yield put({
      type: SAVE_COURSE_SUCCESS,
    });
  } catch (error) {
    // console.log(error);
    yield put({
      type: SAVE_COURSE_FAILURE,
    });
  }
}

function* watchSaveCourse() {
  yield takeLatest(SAVE_COURSE_REQUEST, saveCourse);
}

// 경로 추천 찜 목록 조회
function* loadSaveCourse(action) {
  try {
    const result = yield call(loadSaveCourseAPI, action.data);
    yield put({
      type: LOAD_SAVE_COURSE_SUCCESS,
      data: result,
    });
  } catch (error) {
    // console.log('저장한 경로가 없습니다.');
    yield put({
      type: LOAD_SAVE_COURSE_FAILURE,
    });
  }
}

function* watchLoadSaveCourse() {
  yield takeLatest(LOAD_SAVE_COURSE_REQUEST, loadSaveCourse);
}

export default function* courseSaga() {
  yield all([
    fork(watchCreateCourse),
    fork(watchLoadCourse),
    fork(watchLoadCourseDetail),
    fork(watchCreateCourseComment),
    fork(watchUpdateCourseComment),
    fork(watchDeleteCourseComment),
    fork(watchSearchCourse),
    fork(watchSetCourseLike),
    fork(watchLoadCourseLike),
    fork(watchUpdateCourse),
    fork(watchSaveCourse),
    fork(watchLoadSaveCourse),
  ]);
}
