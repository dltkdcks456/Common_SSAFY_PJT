/* eslint-disable */
import produce from 'immer';

const initialStatement = {
  // 추천 경로 생성
  createCourseLoading: false,
  createCourseDone: false,
  createCourseError: null,
  updateCourseLoading: false,
  updateCourseDone: false,
  updateCourseError: null,
  searchCourseLoading: false,
  searchCourseDone: false,
  searchCourseError: null,
  loadCourseLoading: false,
  loadCourseDone: false,
  loadCourseError: null,
  loadCourseDetailLoading: false,
  loadCourseDetailDone: false,
  loadCourseDetailError: null,
  createCourseCommentLoading: false,
  createCourseCommentDone: false,
  createCourseCommentError: null,
  updateCourseCommentLoading: false,
  updateCourseCommentDone: false,
  updateCourseCommentError: null,
  deleteCourseCommentLoading: false,
  deleteCourseCommentDone: false,
  deleteCourseCommentError: null,
  setCourseLikeLoading: true,
  setCourseLikeDone: false,
  setCourseLikeError: null,
  // 경로 추천 좋아요 클릭
  loadCourseLikeLoading: false,
  loadCourseLikeDone: false,
  loadCourseLikeError: null,
  saveCourseLoading: false,
  saveCourseDone: false,
  saveCourseError: null,
  loadSaveCourseLoading: false,
  loadSaveCourseDone: false,
  loadSaveCourseError: null,
  page: 0,
  // 추천 경로 목록 집합
  courses: {},
  // 좋아요 조회
  like: false,
  courseDetailData: '',
  likeCount: 0,
  loadSaveCourse: [],
};

// 추천 경로 목록 조회
export const LOAD_COURSE_REQUEST = 'LOAD_COURSE_REQUEST';
export const LOAD_COURSE_SUCCESS = 'LOAD_COURSE_SUCCESS';
export const LOAD_COURSE_FAILURE = 'LOAD_COURSE_FAILURE';
// 추천 경로 검색
export const SEARCH_COURSE_REQUEST = 'SEARCH_COURSE_REQUEST';
export const SEARCH_COURSE_SUCCESS = 'SEARCH_COURSE_SUCCESS';
export const SEARCH_COURSE_FAILURE = 'SEARCH_COURSE_FAILURE';
// 추천 경로 디테일 조회
export const LOAD_COURSE_DETAIL_REQUEST = 'LOAD_COURSE_DETAIL_REQUEST';
export const LOAD_COURSE_DETAIL_SUCCESS = 'LOAD_COURSE_DETAIL_SUCCESS';
export const LOAD_COURSE_DETAIL_FAILURE = 'LOAD_COURSE_DETAIL_FAILURE';
// 추천 경로 작성
export const CREATE_COURSE_REQUEST = 'CREATE_COURSE_REQUEST';
export const CREATE_COURSE_SUCCESS = 'CREATE_COURSE_SUCCESS';
export const CREATE_COURSE_FAILURE = 'CREATE_COURSE_FAILURE';
// 추천 경로 수정
export const UPDATE_COURSE_REQUEST = 'UPDATE_COURSE_REQUEST';
export const UPDATE_COURSE_SUCCESS = 'UPDATE_COURSE_SUCCESS';
export const UPDATE_COURSE_FAILURE = 'UPDATE_COURSE_FAILURE';
// 추천 경로 댓글 작성
export const CREATE_COURSE_COMMENT_REQUEST = 'CREATE_COURSE_COMMENT_REQUEST';
export const CREATE_COURSE_COMMENT_SUCCESS = 'CREATE_COURSE_COMMENT_SUCCESS';
export const CREATE_COURSE_COMMENT_FAILURE = 'CREATE_COURSE_COMMENT_FAILURE';
// 추천 경로 댓글 수정
export const UPDATE_COURSE_COMMENT_REQUEST = 'UPDATE_COURSE_COMMENT_REQUEST';
export const UPDATE_COURSE_COMMENT_SUCCESS = 'UPDATE_COURSE_COMMENT_SUCCESS';
export const UPDATE_COURSE_COMMENT_FAILURE = 'UPDATE_COURSE_COMMENT_FAILURE';
// 추천 경로 댓글 삭제
export const DELETE_COURSE_COMMENT_REQUEST = 'DELETE_COURSE_COMMENT_REQUEST';
export const DELETE_COURSE_COMMENT_SUCCESS = 'DELETE_COURSE_COMMENT_SUCCESS';
export const DELETE_COURSE_COMMENT_FAILURE = 'DELETE_COURSE_COMMENT_FAILURE';
// 추천 경로 좋아요 클릭
export const LIKE_CLICK_COURSE_REQUEST = 'LIKE_CLICK_COURSE_REQUEST';
export const LIKE_CLICK_COURSE_SUCCESS = 'LIKE_CLICK_COURSE_SUCCESS';
export const LIKE_CLICK_COURSE_FAILURE = 'LIKE_CLICK_COURSE_FAILURE';
// 추천 경로 좋아요 조회
export const LOAD_LIKE_COURSE_REQUEST = 'LOAD_LIKE_COURSE_REQUEST';
export const LOAD_LIKE_COURSE_SUCCESS = 'LOAD_LIKE_COURSE_SUCCESS';
export const LOAD_LIKE_COURSE_FAILURE = 'LOAD_LIKE_COURSE_FAILURE';
// 추천 경로 찜하기
export const SAVE_COURSE_REQUEST = 'SAVE_COURSE_REQUEST';
export const SAVE_COURSE_SUCCESS = 'SAVE_COURSE_SUCCESS';
export const SAVE_COURSE_FAILURE = 'SAVE_COURSE_FAILURE';
// 추천 경로 찜 목록 조회
export const LOAD_SAVE_COURSE_REQUEST = 'LOAD_SAVE_COURSE_REQUEST';
export const LOAD_SAVE_COURSE_SUCCESS = 'LOAD_SAVE_COURSE_SUCCESS';
export const LOAD_SAVE_COURSE_FAILURE = 'LOAD_SAVE_COURSE_FAILURE';

const reducer = (state = initialStatement, action) =>
  produce(state, draft => {
    switch (action.type) {
      case LOAD_COURSE_REQUEST:
        draft.loadCourseLoading = true;
        draft.loadCourseDone = false;
        draft.loadCourseError = null;
        break;
      case LOAD_COURSE_SUCCESS:
        draft.loadCourseLoading = false;
        draft.loadCourseDone = true;
        draft.courses = action.data;
        break;
      case LOAD_COURSE_FAILURE:
        draft.loadCourseDone = false;
        draft.loadCourseError = action.error;
        break;
      case SEARCH_COURSE_REQUEST:
        draft.searchCourseLoading = true;
        draft.searchCourseDone = false;
        draft.searchCourseError = null;
        break;
      case SEARCH_COURSE_SUCCESS:
        draft.searchCourseLoading = false;
        draft.searchCourseDone = true;
        draft.courses = action.data;
        break;
      case SEARCH_COURSE_FAILURE:
        draft.searchCourseDone = false;
        draft.searchCourseError = action.error;
        break;
      case LOAD_COURSE_DETAIL_REQUEST:
        draft.loadCourseDetailLoading = true;
        draft.loadCourseDetailDone = false;
        draft.loadCourseDetailError = null;
        break;
      case LOAD_COURSE_DETAIL_SUCCESS:
        draft.loadCourseDetailLoading = false;
        draft.loadCourseDetailDone = true;
        draft.courseDetailData = action.data;
        break;
      case LOAD_COURSE_DETAIL_FAILURE:
        draft.loadCourseDetailDone = false;
        draft.loadCourseDetailError = action.error;
        break;
      case CREATE_COURSE_REQUEST:
        draft.createCourseLoading = true;
        draft.createCourseDone = false;
        draft.createCourseError = null;
        break;
      case CREATE_COURSE_SUCCESS:
        draft.createCourseLoading = false;
        draft.createCourseDone = true;
        break;
      case CREATE_COURSE_FAILURE:
        draft.createCourseDone = false;
        draft.createCourseError = action.error;
        break;
      case UPDATE_COURSE_REQUEST:
        draft.updateCourseLoading = true;
        draft.updateCourseDone = false;
        draft.updateCourseError = null;
        break;
      case UPDATE_COURSE_SUCCESS:
        draft.updateCourseLoading = false;
        draft.updateCourseDone = true;
        draft.courseDetailData = '';
        break;
      case UPDATE_COURSE_FAILURE:
        draft.updateCourseDone = false;
        draft.updateCourseError = action.error;
        break;
      case CREATE_COURSE_COMMENT_REQUEST:
        draft.createCourseCommentLoading = true;
        draft.createCourseCommentDone = false;
        draft.createCourseCommentError = null;
        break;
      case CREATE_COURSE_COMMENT_SUCCESS:
        draft.createCourseCommentLoading = false;
        draft.createCourseCommentDone = true;
        break;
      case CREATE_COURSE_COMMENT_FAILURE:
        draft.createCourseCommentDone = false;
        draft.createCourseCommentError = action.error;
        break;
      case UPDATE_COURSE_COMMENT_REQUEST:
        draft.updateCourseCommentLoading = true;
        draft.updateCourseCommentDone = false;
        draft.updateCourseCommentError = null;
        break;
      case UPDATE_COURSE_COMMENT_SUCCESS:
        draft.updateCourseCommentLoading = false;
        draft.updateCourseCommentDone = true;
        break;
      case UPDATE_COURSE_COMMENT_FAILURE:
        draft.updateCourseCommentDone = false;
        draft.updateCourseCommentError = action.error;
        break;
      case DELETE_COURSE_COMMENT_REQUEST:
        draft.deleteCourseCommentLoading = true;
        draft.deleteCourseCommentDone = false;
        draft.deleteCourseCommentError = null;
        break;
      case DELETE_COURSE_COMMENT_SUCCESS:
        draft.deleteCourseCommentLoading = false;
        draft.deleteCourseCommentDone = true;
        break;
      case DELETE_COURSE_COMMENT_FAILURE:
        draft.deleteCourseCommentDone = false;
        draft.deleteCourseCommentError = action.error;
        break;
      case LIKE_CLICK_COURSE_REQUEST:
        draft.setCourseLikeLoading = true;
        draft.setCourseLikeDone = false;
        draft.setCourseLikeError = null;
        break;
      case LIKE_CLICK_COURSE_SUCCESS:
        draft.setCourseLikeLoading = false;
        draft.setCourseLikeDone = true;
        break;
      case LIKE_CLICK_COURSE_FAILURE:
        draft.setCourseLikeDone = false;
        draft.setCourseLikeError = action.error;
        break;
      case LOAD_LIKE_COURSE_REQUEST:
        draft.loadCourseLikeLoading = true;
        draft.loadCourseLikeDone = false;
        draft.loadCourseLikeError = null;
        break;
      case LOAD_LIKE_COURSE_SUCCESS:
        draft.loadCourseLikeLoading = false;
        draft.loadCourseLikeDone = true;
        draft.like = action.data;
        break;
      case LOAD_LIKE_COURSE_FAILURE:
        draft.loadCourseLikeDone = false;
        draft.loadCourseLikeError = action.error;
        break;
      case SAVE_COURSE_REQUEST:
        draft.saveCourseLoading = true;
        draft.saveCourseDone = false;
        draft.saveCourseError = null;
        break;
      case SAVE_COURSE_SUCCESS:
        draft.saveCourseLoading = false;
        draft.saveCourseDone = true;
        break;
      case SAVE_COURSE_FAILURE:
        draft.saveCourseDone = false;
        draft.saveCourseError = action.error;
        break;
      case LOAD_SAVE_COURSE_REQUEST:
        draft.loadSaveCourseLoading = true;
        draft.loadSaveCourseDone = false;
        draft.loadSaveCourseError = null;
        break;
      case LOAD_SAVE_COURSE_SUCCESS:
        draft.loadSaveCourseLoading = false;
        draft.loadSaveCourseDone = true;
        draft.loadSaveCourse = action.data;
        break;
      case LOAD_SAVE_COURSE_FAILURE:
        draft.loadSaveCourseDone = false;
        draft.loadSaveCourseError = action.error;
        break;

      default:
        break;
    }
  });
export default reducer;
