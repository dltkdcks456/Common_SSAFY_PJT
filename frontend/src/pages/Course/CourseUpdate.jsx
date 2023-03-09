/* eslint-disable */
/* global kakao */

import React, { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import axios from 'axios';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { UPDATE_COURSE_REQUEST } from '../../store/modules/courseModule';
import './Map.css';
import { BASE_URL } from '../../utils/urls';
import styled from 'styled-components';
import { createGPX } from '../../store/apis/courseApi';
const Test = styled.label`
  display: inline-block;
  padding: 0.5em 0.75em;
  color: white;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: black;
  cursor: pointer;
  border: 1px solid #ebebeb;
  border-bottom-color: #e2e2e2;
  border-radius: 0.25em;
`;

const FileInput = styled.label`
  margin-top: 8px;
  margin-left: 8px;
  padding: 6px 25px;
  height: 22px;
  background-color: white;
  border-radius: 4px;
  color: black;
  border: solid 1px #a3a3a3;
  cursor: pointer;
`;

const StyledButton = styled.button`
  width: ${props => (props.width ? props.width : '10rem')};
  height: ${props => (props.height ? props.height : '2rem')};
  font-size: ${props => (props.fontSize ? props.fontSize : '1rem')};
  color: ${props => (props.color ? props.color : '')};
  background-color: ${props => (props.bc ? props.bc : '#c4c4c4')};
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  padding: ${props => (props.padding ? props.padding : '')};
  /* border: 'solid 0.5px'; */
  border: ${props => (props.border ? props.border : 'solid 0.5px')};
  border-color: #a3a3a3;
  border-radius: ${props => (props.br ? props.br : '5px')};
  font-family: ${props => (props.font ? props.font : 'Pretendard-SemiBold')};
  z-index: ${props => (props.z ? props.z : '')};
  transition: 0.2s;
  cursor: pointer;

  &:hover {
    transition: 0.3s;
    background-color: ${props =>
      props.hoverColor ? props.hoverColor : '#a2a2a2'};
  }

  &:disabled {
    background-color: whitesmoke;
    cursor: no-drop;
  }
`;

const Button = props => (
  <StyledButton {...props} disabled={props.disabled}>
    {props.name}
  </StyledButton>
);

const StyleContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const StyledCommentInput = styled.input`
  width: ${props => (props.width ? props.width : '95%')};
  height: ${props => (props.height ? props.height : '2rem')};
  border-radius: ${props => (props.br ? props.br : '5px')};
  border: 1px solid #a3a3a3;
  padding-left: 1rem;
  font-size: 1.3rem;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  font-family: 'Pretendard-Regular';
  width: 68.7vw;
  padding: 0.5rem;
`;

const StyledCourseInput = styled.input`
  width: ${props => (props.width ? props.width : '95%')};
  height: ${props => (props.height ? props.height : '2rem')};
  border-radius: ${props => (props.br ? props.br : '5px')};
  border: 1px solid #a3a3a3;
  padding-left: 1rem;
  font-size: 1rem;
  margin-top: ${props => (props.mt ? props.mt : '')};
  margin-left: ${props => (props.ml ? props.ml : '')};
  margin-bottom: ${props => (props.mb ? props.mb : '')};
  margin-right: ${props => (props.mr ? props.mr : '')};
  font-family: 'Pretendard-Regular';
  width: 20vw;
  /* padding: 0.5rem; */
`;

const StyledCourseInputContainer = styled.div`
  display: flex;
  margin-left: 14.5vw;
`;

const CourseUpdate = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [courseBoardId, setCourseBoardId] = useState('');
  const { user } = useSelector(state => state.myPage);
  const [courseId, setCourseId] = useState('');
  const [userId, setUserId] = useState('');
  const [GPXText, setGPXText] = useState('');
  const [fileName, SetFileName] = useState('파일선택');

  const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr'],
    ['ul', 'ol', 'task'],
    ['table', 'link'],
    ['code'],
    ['scrollSync'],
  ];

  const [latLon, setLatLon] = useState('');

  const { state } = useLocation();
  const editorRef = useRef();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const contentChange = () => {
    const data = editorRef.current.getInstance().getHTML();
    setContent(data);
    // console.log(data);
  };

  const titleChange = e => {
    setTitle(e.target.value);
  };

  const onClick = () => {
    navigate(`/course/detail`, { state: { courseId: state.courseBoardId } });
  };

  useEffect(() => {
    setTitle(state.title);
    setCourseBoardId(state.courseBoardId);
    setContent(state.content);
    setCourseId(state.courseId.courseId);
    setUserId(state.userId);
    SetFileName(state.courseId.name);
    setGPXText(state.courseId.title);
  }, []);

  const onSubmit = e => {
    e.preventDefault();
    dispatch({
      type: UPDATE_COURSE_REQUEST,
      data: {
        user_id: userId,
        title,
        content,
        course_board_id: courseBoardId,
        course_id: courseId,
        navigate,
      },
    });
  };

  /* 카카오맵 */
  const [kakaoMap, setKakaoMap] = useState(null);
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [level, setLevel] = useState(4);
  const [courseLineData, setCourseLineData] = useState([]);
  const [boundary, setBoundary] = useState([]);

  // GPX 위도 경도 받아오기
  const getLatLon = async () => {
    const result = await axios.get(`${BASE_URL}course/custom/`, {
      params: {
        course_id: courseId,
      },
    });
    setLatLon(result.data);
  };

  const getCourseFileData = async () => {
    await getLatLon();
  };

  const getBoundary = () => {
    // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
    const bounds = new window.kakao.maps.LatLngBounds();
    for (let i = 2; i < latLon.length; i++) {
      const data = new window.kakao.maps.LatLng(latLon[i][0], latLon[i][1]);
      // console.log('data', data);
      setCourseLineData(prev => [...prev, data]);
      // LatLngBounds 객체에 좌표를 추가합니다
      bounds.extend(data);
    }
    setBoundary(bounds);
  };

  // 카카오맵 기본 설정
  useEffect(() => {
    if (!kakaoMap) {
      const mapScript = document.createElement('script');
      mapScript.type = 'text/javascript';
      mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.REACT_APP_KAKAO_MAP_KEY}&autoload=false&libraries=services,clusterer,drawing`;
      mapScript.async = true;
      document.head.appendChild(mapScript);

      const onLoadKakaoMap = () => {
        window.kakao.maps.load(() => {
          const container = document.getElementById('map');
          const options = {
            center: new window.kakao.maps.LatLng(latitude, longitude),
            level,
          };
          const map = new window.kakao.maps.Map(container, options);
          setKakaoMap(map);
        });
      };
      mapScript.addEventListener('load', onLoadKakaoMap);

      return () => mapScript.removeEventListener('load', onLoadKakaoMap);
    }
  }, [latitude, longitude, kakaoMap]);

  /* 카카오맵 */

  useEffect(() => {
    // 파일 저장 시 데이터를 불러와서 미리 보기를 띄워준다.
    if (courseId && !latLon) {
      getCourseFileData();
    }

    if (latLon && (boundary.length === 0 || courseLineData.length === 0)) {
      getBoundary();
    }

    // 지도에 표시할 선 생성
    if (boundary && courseLineData.length > 0 && kakaoMap) {
      const polyline = new window.kakao.maps.Polyline({
        path: courseLineData, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께 입니다
        strokeColor: 'red', // 선의 색깔입니다
        strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid', // 선의 스타일입니다
      });
      polyline.setMap(kakaoMap);
      kakaoMap.setBounds(boundary);
    }
  }, [courseId, boundary, courseLineData, kakaoMap, latLon]);

  // 파일 입력 시 GPX 파일 전송
  const onChange = e => {
    e.preventDefault();
    if (!e.target.value) {
      return;
    }
    const file = e.target.files[0];
    const formData = new FormData();
    formData.append('gpxFile', file);
    formData.append('user_id', user.user_id);

    const currCourseId = createGPX({ formData });

    const getData = () => {
      currCourseId.then(data => setCourseId(data));
    };
    getData();
  };

  const onGPXTileChange = e => {
    setGPXText(e.target.value);
  };
  return (
    <div>
      {content && (
        <>
          <hr />
          <div>
            <div className="map_wrap">
              <div
                id="map"
                style={{
                  width: '70%',
                  height: '100%',
                  position: 'absolute',
                  overflow: 'hidden',
                  left: '50%',
                  transform: 'translate(-50%, 0%)',
                }}
              />
            </div>
          </div>
          <StyledCourseInputContainer>
            <StyledCourseInput
              mt="8px"
              onChange={onGPXTileChange}
              required
              type="text"
              value={GPXText}
              placeholder="추천 경로에 대한 제목을 설정해주세요"
            />
            <FileInput htmlFor="input-file">업로드</FileInput>
            <input
              type="file"
              id="input-file"
              name="gpx"
              accept=".gpx"
              onChange={onChange}
              style={{ display: 'none' }}
            />
          </StyledCourseInputContainer>
          <StyleContainer></StyleContainer>
          <StyleContainer>
            <form onSubmit={onSubmit}>
              <hr />
              <div>
                <StyledCommentInput
                  required
                  value={title}
                  onChange={titleChange}
                  id="title"
                  type="text"
                  placeholder="제목을 입력해주세요."
                />
              </div>
              <hr />
              <Editor
                initialValue={content}
                placeholder="내용을 입력해주세요."
                previewStyle="vertical"
                height="600px"
                initialEditType="wysiwyg"
                plugins={[colorSyntax]}
                language="ko-KR"
                toolbarItems={toolbarItems}
                ref={editorRef}
                onChange={contentChange}
              />
              <hr />

              <Button
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
                name="작성"
              />
              <Button
                width="5rem"
                ml="0.1rem"
                mr="0.1rem"
                mt="0.2rem"
                height="2.3rem"
                bc="white"
                name="취소"
                onClick={onClick}
              ></Button>
            </form>
          </StyleContainer>
        </>
      )}
      <div style={{ height: '20px' }}></div>
    </div>
  );
};
export default CourseUpdate;
