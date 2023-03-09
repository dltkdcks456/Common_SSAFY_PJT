/* eslint-disable */
import axios from 'axios';
import { BASE_URL } from '../../utils/urls';

// 경로 추천 조회
export async function loadCourseAPI({ page }) {
  const result = await axios.get(`${BASE_URL}course/`, { params: { page } });
  // console.log('결과 나왔다', result.data);
  return result.data;
}

// 경로 추천 검색
export async function searchCourseAPI({ page, keyword }) {
  // console.log(page, keyword);
  const result = await axios.get(`${BASE_URL}course/search`, {
    params: { page, keyword },
  });
  // console.log('결과 나왔다', result.data);
  return result.data;
}

// 경로 추천 상세 정보 조회
export async function loadCourseDetailAPI({ courseId, user }) {
  // console.log('상세 정보 조회', courseId, user);
  const result = await axios.get(`${BASE_URL}course/${courseId}`, {
    headers: { userId: user.user_id },
  });
  // console.log('상세 정보 결과', result.data);
  return result.data;
}

// 경로 추천 생성
export async function createCourseAPI({ user_id, title, content, course_id }) {
  const result = await axios.post(`${BASE_URL}course/`, {
    user_id,
    title,
    content,
    course_id,
  });
  return result.data;
}

// 경로 추천 수정
export async function updateCourseAPI({
  user_id,
  title,
  content,
  course_id,
  course_board_id,
}) {
  const result = await axios.put(`${BASE_URL}course/`, {
    user_id,
    title,
    content,
    course_id,
    course_board_id,
  });

  return result.data;
}

// GPX 파일 업로드
export async function createGPX({ formData }) {
  const result = await axios({
    method: 'post',
    url: `${BASE_URL}course/custom/`,
    data: formData,
    headers: {
      'Content-type': 'multipart/form-data',
    },
  });
  return result.data;
}

// 경로 추천 게시글 삭제
export async function onDeleteDetailAPI({ courseBoardId }) {
  // console.log('제거', courseBoardId);
  await axios.delete(`${BASE_URL}course/${courseBoardId}`);
}

// 경로 추천 댓글 생성
export async function createCourseCommentAPI({
  user_id,
  content,
  course_board_id,
}) {
  console.log(user_id, content, course_board_id);
  const result = await axios.post(`${BASE_URL}course/comment/`, {
    user_id,
    content,
    course_board_id,
  });
  return result.data;
}

// 경로 추천 댓글 수정
export async function updateCourseCommentAPI({
  content,
  course_board_comment_id,
}) {
  // console.log(content, course_board_comment_id);
  const result = await axios.put(`${BASE_URL}course/comment/`, {
    content,
    course_board_comment_id,
  });
  return result.data;
}

// 경로 추천 댓글 삭제
export async function deleteCourseCommentAPI({ courseBoardCommentId }) {
  const result = await axios.delete(
    `${BASE_URL}course/comment/${courseBoardCommentId}`,
  );
  return result.data;
}

// 경로 추천 좋아요 클릭
export async function setCourseLikeAPI({ courseBoardId, user_id }) {
  // console.log('좋아요 클릭');
  await axios.post(`${BASE_URL}course/like/`, {
    course_board_id: courseBoardId,
    user_id,
  });
}

// 경로 추천 좋아요 조회
export async function loadCourseLikeAPI({ courseBoardId, userId }) {
  // console.log('좋아요 조회', courseBoardId, userId);
  const result = await axios.get(`${BASE_URL}course/like/`, {
    params: { courseBoardId, userId },
  });
  return result.data;
}

// 경로 추천 저장하기
export async function saveCourseAPI({ user_id, course_id }) {
  // console.log('꾸에엑', user_id, course_id);
  await axios.post(`${BASE_URL}course/custom/myCourse`, { user_id, course_id });
}

// 경로 추천 삭제하기
export async function deleteCourseAPI({ user_id, course_id }) {
  // console.log('유저아이디', user_id);
  // console.log('유저타입', typeof user_id);
  // console.log('코스', course_id);
  await axios.delete(
    `${BASE_URL}course/custom/myCourse/${course_id}/${user_id}`,
  );
}

// 경로 추천 저장 리스트 불러오기
export async function loadSaveCourseAPI({ user_id }) {
  const result = await axios.get(`${BASE_URL}course/custom/myCourse`, {
    params: { user_id },
  });
  return result.data;
}
