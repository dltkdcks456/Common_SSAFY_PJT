/* eslint-disable */
/* global kakao */
import styled from 'styled-components';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Viewer } from '@toast-ui/react-editor';
import { useLocation, useNavigate } from 'react-router';
import axios from 'axios';
import moment from 'moment';
import likeImg from '../../assets/images/like.png';
import eyeImg from '../../assets/images/eye.png';
import {
  LIKE_CLICK_COURSE_REQUEST,
  LOAD_LIKE_COURSE_REQUEST,
  SAVE_COURSE_REQUEST,
} from '../../store/modules/courseModule';
import { MY_PAGE_REQUEST } from '../../store/modules/myPageModule';
import {
  onDeleteDetailAPI,
  loadCourseDetailAPI,
} from '../../store/apis/courseApi';
import { BASE_URL } from '../../utils/urls';
import CourseComment from '../../components/Course/CourseComment';
import NowContainer from '../../components/commons/nowLocation';
import Button from '../../components/commons/button';
import emptyHeartImg from '../../assets/images/emptyheart.png';
import fullHeartImg from '../../assets/images/fullheart.png';
import { deleteCourseAPI } from '../../store/apis/courseApi';
import { customAlert, i1500 } from '../../utils/alarm';
import './Map.css';

const StyledTitleContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledLikeVisited = styled.div`
  display: flex;
  align-items: flex-end;
`;

const StyledLike = styled.div`
  display: flex;
`;

const StyledVisited = styled.div`
  display: flex;
`;

const StyledNickNameTime = styled.div`
  display: flex;
  font-size: 13px;
  justify-content: flex-end;
  margin-top: 5px;
  margin-bottom: 10px;
`;

const StyledUpdateDelete = styled.div`
  /* display: flex;
  justify-content: flex-end; */
  margin-right: 0%;
`;

const StyledLikeBtn = styled.div`
  /* display: flex;
  justify-content: center; */
  margin-left: 0%;
`;

const StyledLikeUpdateDelete = styled.div`
  display: flex;
  justify-content: space-between;
`;

const DetailContainer = styled.div`
  margin-top: 2rem;
  margin-left: 15%;
  margin-right: 15%;
  padding: 2rem 2rem;
  box-sizing: border-box;
  overflow: auto;
  border: 1px solid #a0a0a0;
  border-radius: 10px;
  animation: smoothAppear;
  animation-duration: ${props => (props.duration ? props.duration : '0.5s')};
`;

const CourseDetail = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [courseMapId, setCourseMapId] = useState('');
  const [latLon, setLatLon] = useState('');
  const [detail, setDetail] = useState('');
  const [likeCount, setLikeCount] = useState('');

  const { logInDone } = useSelector(state => state.user);
  const { myPageDone, user } = useSelector(state => state.myPage);
  const userToken = sessionStorage.getItem('userToken');
  const {
    state: { courseId },
  } = useLocation();
  const { like } = useSelector(state => state.course);
  const { setCourseLikeDone } = useSelector(state => state.course);

  const [kakaoMap, setKakaoMap] = useState(null);
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [level, setLevel] = useState(4);
  const [courseLineData, setCourseLineData] = useState([]);
  const [boundary, setBoundary] = useState([]);

  // console.log('????????? ??????', user.user_id);
  // console.log('????????? ??????', detail.userId);
  // ????????? ????????????
  const getLiked = () => {
    dispatch({
      type: LOAD_LIKE_COURSE_REQUEST,
      data: {
        courseBoardId: courseId,
        userId: user.user_id,
      },
    });
  };

  const getDetailCourse = async () => {
    const result = await loadCourseDetailAPI({ courseId, user });
    setDetail(result);
    setLikeCount(result.likeCount);
  };
  const detailTime = moment(detail.regTime).format('YYYY-MM-DD');
  const { updateCourseDone } = useSelector(state => state.course);

  useEffect(() => {
    if (!detail) {
      // console.log('???????????? ??????');
      getDetailCourse();
    }
    if (detail && !courseMapId) {
      setCourseMapId(detail.courseId.courseId);
      setLikeCount(detail.likeCount);
    }
  }, [detail, courseMapId, updateCourseDone]);

  useEffect(() => {
    if (!myPageDone && userToken) {
      dispatch({
        type: MY_PAGE_REQUEST,
        data: {
          token: userToken,
        },
      });
    }
  }, [user, logInDone]);

  useEffect(() => {
    getLiked();
  }, [setCourseLikeDone]);

  // ???????????? ?????? ??????
  const onDelete = async () => {
    try {
      await onDeleteDetailAPI({ courseBoardId: courseId });
    } catch (error) {
      console.log(error);
    }
    navigate(`/course`);
  };

  // ???????????? ?????? ??????
  const onUpdate = async () => {
    try {
      navigate(`/course/update`, { state: detail });
    } catch (error) {
      console.log(error);
    }
  };

  // ????????? ?????? ??????
  const onLike = () => {
    dispatch({
      type: LIKE_CLICK_COURSE_REQUEST,
      data: {
        courseBoardId: courseId,
        user_id: user.user_id,
        navigate,
      },
    });
  };

  // ??? ?????? ??????(?????????)
  const onSaveCourse = () => {
    dispatch({
      type: SAVE_COURSE_REQUEST,
      data: {
        user_id: user.user_id,
        course_id: courseMapId,
      },
    });
  };

  /* ???????????? */

  const getBoundary = () => {
    // ????????? ???????????? ??????????????? ????????? ?????? LatLngBounds ????????? ???????????????
    const bounds = new window.kakao.maps.LatLngBounds();
    for (let i = 2; i < latLon.length; i++) {
      const data = new window.kakao.maps.LatLng(latLon[i][0], latLon[i][1]);
      // console.log('data', data);
      setCourseLineData(prev => [...prev, data]);
      // LatLngBounds ????????? ????????? ???????????????
      bounds.extend(data);
    }
    setBoundary(bounds);
  };

  const getLatLon = async () => {
    // console.log('??????', courseDetailData.courseId.courseId);
    const result = await axios.get(`${BASE_URL}course/custom/`, {
      params: {
        course_id: detail.courseId.courseId,
      },
    });
    // console.log(result);
    setLatLon(result.data);
  };

  useEffect(() => {
    if (!latLon && detail) {
      getLatLon();
    }

    if (latLon && (boundary.length === 0 || courseLineData.length === 0)) {
      getBoundary();
    }
  }, [detail, kakaoMap, latLon]);

  useEffect(() => {
    // ????????? ????????? ??? ??????
    if (boundary && courseLineData.length > 0 && kakaoMap) {
      const polyline = new window.kakao.maps.Polyline({
        path: courseLineData, // ?????? ???????????? ???????????? ?????????
        strokeWeight: 5, // ?????? ?????? ?????????
        strokeColor: 'red', // ?????? ???????????????
        strokeOpacity: 0.7, // ?????? ???????????? ????????? 1?????? 0 ????????? ????????? 0??? ??????????????? ???????????????
        strokeStyle: 'solid', // ?????? ??????????????????
      });
      polyline.setMap(kakaoMap);
      kakaoMap.setBounds(boundary);
    }
  }, [courseLineData, kakaoMap, boundary]);
  // console.log('?????????', detail);
  useEffect(() => {
    if (!kakaoMap) {
      const mapScript = document.createElement('script');
      mapScript.type = 'text/javascript';
      mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.REACT_APP_KAKAO_MAP_KEY}&autoload=false&libraries=services,clusterer,drawing`;
      mapScript.async = true;
      document.head.appendChild(mapScript);

      function locationOk(position) {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        setLatitude(lat);
        setLongitude(lng);
      }

      function locationError() {
        console.log('????????? ?????? ??? ????????????.');
      }

      if (!location) {
        navigator.geolocation.getCurrentPosition(locationOk, locationError);
        setLocation(true);
      }

      const onLoadKakaoMap = () => {
        window.kakao.maps.load(() => {
          const container = document.getElementById('map');
          const options = {
            center: new window.kakao.maps.LatLng(latitude, longitude),
            level: level,
          };
          const map = new window.kakao.maps.Map(container, options);

          // // ????????? ???????????? ??????????????? ????????? ?????? LatLngBounds ????????? ???????????????
          // const bounds = new kakao.maps.LatLngBounds();

          // for (let i = 2; i < latLon.length; i++) {
          //   const data = new window.kakao.maps.LatLng(
          //     latLon[i][0],
          //     latLon[i][1],
          //   );
          //   setCourseLineData(prev => [...prev, data]);
          //   // LatLngBounds ????????? ????????? ???????????????
          //   bounds.extend(data);
          // }
          // setBoundary(bounds);
          setKakaoMap(map);
        });
      };
      mapScript.addEventListener('load', onLoadKakaoMap);

      return () => mapScript.removeEventListener('load', onLoadKakaoMap);
    }
  }, [kakaoMap]);

  const onList = () => {
    navigate(`/course`);
  };
  /* ???????????? */
  // console.log(courseDetailData.content);
  return (
    <div>
      <NowContainer desc="??? ??? ??? ???" />
      <DetailContainer>
        <StyledTitleContainer>
          <h1>{detail.title}</h1>
          <StyledLikeVisited>
            <StyledVisited>
              <img
                src={eyeImg}
                style={{
                  width: '20px',
                  marginLeft: '5px',
                  marginRight: '5px',
                  height: '20px',
                }}
                alt="a"
              ></img>
              <div>{detail.visited}</div>
            </StyledVisited>
            <StyledLike>
              <img
                src={likeImg}
                style={{
                  width: '20px',
                  marginLeft: '5px',
                  marginRight: '5px',
                  height: '20px',
                }}
                alt="a"
              ></img>
              <div>{detail.likeCount}</div>
            </StyledLike>
          </StyledLikeVisited>
        </StyledTitleContainer>
        <hr />
        <StyledNickNameTime>
          <div style={{ fontWeight: 'bolder', marginRight: '5px' }}>
            {detail.userNickname}
          </div>
          <div style={{ fontWeight: 'bolder' }}>{detailTime}</div>
        </StyledNickNameTime>
        <div>
          <div className="map_wrap">
            <div
              id="map"
              style={{
                width: '80%',
                height: '100%',
                position: 'absolute',
                overflow: 'hidden',
                left: '50%',
                transform: 'translate(-50%, 0%)',
              }}
            ></div>
          </div>
        </div>
        {detail.content && <Viewer initialValue={detail.content} />}
        <hr />
        <StyledLikeUpdateDelete>
          <StyledUpdateDelete>
            <Button
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="??? ??????"
              onClick={onList}
            ></Button>
            <Button
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="?????? ??????"
              onClick={onSaveCourse}
            ></Button>
            <Button
              width="5rem"
              ml="0.1rem"
              mr="0.1rem"
              mt="0.2rem"
              height="2.3rem"
              bc="white"
              name="?????? ??????"
              onClick={() => {
                deleteCourseAPI({
                  user_id: user.user_id,
                  course_id: detail.courseId.courseId,
                })
                  .then(() => {
                    customAlert(i1500, '?????? ???????????? ?????? ??????');
                  })
                  .catch(err => {
                    customAlert(i1500, '?????? ???????????? ?????? ??????');
                  });
              }}
            ></Button>
          </StyledUpdateDelete>
          <StyledLikeBtn>
            {like ? (
              <img
                src={fullHeartImg}
                style={{ width: '50px', cursor: 'pointer' }}
                onClick={onLike}
              ></img>
            ) : (
              <img
                src={emptyHeartImg}
                style={{ width: '50px', cursor: 'pointer' }}
                onClick={onLike}
              ></img>
            )}
          </StyledLikeBtn>
          {user && detail && user.user_id === detail.userId ? (
            <StyledUpdateDelete>
              <Button
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
                name="????????????"
                onClick={onUpdate}
              ></Button>
              <Button
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
                name="????????????"
                onClick={onDelete}
              ></Button>
            </StyledUpdateDelete>
          ) : (
            <div style={{ width: '140px' }}></div>
          )}
        </StyledLikeUpdateDelete>
        <hr />
        {courseId && <CourseComment user={user} courseBoardId={courseId} />}
      </DetailContainer>
    </div>
  );
};
export default CourseDetail;
