/* eslint-disable */
/* global kakao */

import React, { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import axios from 'axios';
import Swal from 'sweetalert2';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { CREATE_COURSE_REQUEST } from '../../store/modules/courseModule';
import { createGPX } from '../../store/apis/courseApi';
import { BASE_URL } from '../../utils/urls';
import './Map.css';

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

const CourseCreate = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const { user } = useSelector(state => state.myPage);
  const [courseId, setCourseId] = useState('');
  const [fileChk, setFileChk] = useState(false);
  const [GPXText, setGPXText] = useState('');
  const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr'],
    ['ul', 'ol', 'task'],
    ['table', 'link'],
    ['code'],
    ['scrollSync'],
  ];
  const [latLon, setLatLon] = useState('');

  const editorRef = useRef();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const contentChange = () => {
    const data = editorRef.current.getInstance().getHTML();
    setContent(data);
  };

  const onGPXTileChange = e => {
    setGPXText(e.target.value);
  };

  const titleChange = e => {
    setTitle(e.target.value);
  };

  const onSubmit = e => {
    e.preventDefault();
    if (fileChk && title && content && GPXText) {
      dispatch({
        type: CREATE_COURSE_REQUEST,
        data: {
          user_id: user.user_id,
          title,
          content,
          course_id: courseId,
          navigate,
        },
      });
      axios.put(`${BASE_URL}course/custom/title`, {
        title: GPXText,
        course_id: courseId,
      });
    } else {
      Swal.fire({
        title: '?????? ?????? ??????',
        text: '??????, ??????, ????????? ?????? ??????????????????',
        icon: 'error',
      });
    }
  };

  /* ???????????? */
  const [kakaoMap, setKakaoMap] = useState(null);
  const [latitude, setLatitude] = useState(33.450701);
  const [longitude, setLongitude] = useState(126.570667);
  const [level, setLevel] = useState(4);
  const [courseLineData, setCourseLineData] = useState([]);
  const [boundary, setBoundary] = useState([]);
  const [location, setLocation] = useState(false);

  // GPX ?????? ?????? ????????????
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

  // ???????????? ?????? ??????
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

  /* ???????????? */

  useEffect(() => {
    // ?????? ?????? ??? ???????????? ???????????? ?????? ????????? ????????????.
    if (courseId && !latLon) {
      getCourseFileData();
    }

    if (latLon && (boundary.length === 0 || courseLineData.length === 0)) {
      getBoundary();
    }

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
  }, [courseId, boundary, courseLineData, kakaoMap, latLon]);

  // ?????? ?????? ??? GPX ?????? ??????
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
    setFileChk(true);
  };

  const onClick = () => {
    navigate(`/course`);
  };

  return (
    <div>
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
          placeholder="?????? ????????? ?????? ????????? ??????????????????"
        />
        <FileInput htmlFor="input-file">?????????</FileInput>
        <input
          type="file"
          id="input-file"
          name="gpx"
          accept=".gpx"
          onChange={onChange}
          style={{ display: 'none' }}
        />
      </StyledCourseInputContainer>
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
              placeholder="????????? ??????????????????."
            />
          </div>
          <hr />
          <Editor
            placeholder="????????? ??????????????????."
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
            name="??????"
          />
          <Button
            width="5rem"
            ml="0.1rem"
            mr="0.1rem"
            mt="0.2rem"
            height="2.3rem"
            bc="white"
            name="??????"
            onClick={onClick}
          ></Button>
        </form>
      </StyleContainer>
      <div style={{ height: '20px' }}></div>
    </div>
  );
};
export default CourseCreate;
