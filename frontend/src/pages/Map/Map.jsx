/* eslint-disable */
/* global kakao */
import styled from 'styled-components';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { LOAD_SAVE_COURSE_REQUEST } from '../../store/modules/courseModule';
import NowContainer from '../../components/commons/nowLocation';
import {
  MY_PAGE_REQUEST,
  MY_PAGE_RESET,
} from '../../store/modules/myPageModule';
import { localDate } from '../../utils/DateFormatter';
import './MainMap.css';
import likeImg from '../../assets/images/like.png';
import eyeImg from '../../assets/images/eye.png';
import { BASE_URL } from '../../utils/urls';
import bicycle from '../../assets/images/bicycle.png';
import mountain from '../../assets/images/mountain.png';
import check from '../../assets/images/check.png';
import { useNavigate } from 'react-router';
import { KoreaBike } from './data';

export const ListBody = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  width: 22rem;
  height: 37rem;
  overflow-y: auto;
  margin-left: 10px;
  /* padding: 1rem; */
  /* overflow-y: scroll; */
  border-radius: 5px;
  box-shadow: rgba(99, 99, 99, 0.5) 0px 1px 2px 0px;
  &::-webkit-scrollbar-track {
    box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    background-color: #f5f5f5;
  }
  &::-webkit-scrollbar {
    width: 5px;
    background-color: #f5f5f5;
  }

  &::-webkit-scrollbar-thumb {
    background-color: #8b8b8b;
  }
`;

const ListCourseItem = styled.div`
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 21rem;
  padding: ${props => (props.p ? props.p : '1rem')};
  /* height: 10rem; */
  margin-top: ${props => (props.mt ? props.mt : '0.5rem')};
  /* margin-left: 0.5rem;
  margin-right: 0.1rem; */
  /* border-radius: 5px; */
  box-shadow: rgba(99, 99, 99, 0.5) 0px 1px 2px 0px;
  font-family: 'Pretendard-Regular';

  :hover {
    background-color: #f5f5f5;
  }
`;

const SubListCourseItem = styled.div`
  display: flex;
`;

const KAKAOMAP = () => {
  // 마커 이미지의 이미지 주소입니다
  const imageSrc =
    'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [district, setTerrain] = useState(false);
  const [bikeroad, setBikeRoad] = useState(false);
  const [korearoad, setKoreanRoad] = useState(false);
  const [course, setCourse] = useState(false);
  const [kakaoMap, setKakaoMap] = useState(null);
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [level, setLevel] = useState(4);
  const [location, setLocation] = useState(false);
  const [kMarkers, setKMarkers] = useState([]);

  const [courseId, setCoursdId] = useState('');
  const [latLon, setLatLon] = useState('');
  const [courseLineData, setCourseLineData] = useState([]);
  const [boundary, setBoundary] = useState([]);

  const [loadList, setLoadList] = useState(false);

  const { myPageDone, user } = useSelector(state => state.myPage);
  const { loadSaveCourse } = useSelector(state => state.course);
  const userToken = sessionStorage.getItem('userToken');

  // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
  function setMarkers(kakaoMap) {
    for (var i = 0; i < kMarkers.length; i++) {
      kMarkers[i].setMap(kakaoMap);
    }
  }

  // "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
  function showMarkers() {
    setMarkers(kakaoMap);
  }

  // "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
  function hideMarkers() {
    setMarkers(null);
  }

  useEffect(() => {
    if (kakaoMap && kMarkers.length === 0) {
      ///////////////////////////
      // console.log('꾸에엑');
      for (let i = 0; i < KoreaBike.length; i++) {
        // console.log(KoreaBike[i]);
        const temp = {
          title: KoreaBike[i].name,
          latlng: new kakao.maps.LatLng(KoreaBike[i].Lat, KoreaBike[i].Lon),
        };

        // 마커 이미지의 이미지 크기 입니다
        const imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        const markerTemp = new kakao.maps.Marker({
          map: kakaoMap, // 마커를 표시할 지도
          position: temp.latlng, // 마커를 표시할 위치
          title: temp.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
        });

        setKMarkers(prev => [...prev, markerTemp]);
      }
      /////////////////////
    }
  });

  const inputCourse = e => {
    // console.log(e.target.checked);
    if (e.target.checked) {
      showMarkers();
      // console.log('보여랏', kMarkers);
      // console.log(kakaoMap);
    } else {
      // console.log('숨어랏', kMarkers);
      hideMarkers();
    }
  };

  useEffect(() => {
    if (!myPageDone && userToken) {
      dispatch({
        type: MY_PAGE_REQUEST,
        data: {
          token: userToken,
        },
      });
    } else if (!userToken) {
      dispatch({
        type: MY_PAGE_RESET,
      });
    }

    dispatch({
      type: LOAD_SAVE_COURSE_REQUEST,
      data: {
        user_id: user.user_id,
      },
    });
  }, [myPageDone]); // dispatch,
  // console.log(courseId);

  useEffect(() => {
    dispatch({
      type: LOAD_SAVE_COURSE_REQUEST,
      data: {
        user_id: user.user_id,
      },
    });
    setLoadList(false);
  }, []);

  //////////////////////////////////////////////////////////////

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

  const getLatLon = async () => {
    // console.log('데헷', courseDetailData.courseId.courseId);
    const result = await axios.get(`${BASE_URL}course/custom/`, {
      params: {
        course_id: courseId,
      },
    });
    // console.log(result);
    setLatLon(result.data);
  };

  useEffect(() => {
    if (courseId) {
      getLatLon();
    }
  }, [courseId]);

  // 클릭 시 해당 경로 불러오기
  useEffect(() => {
    if (!latLon && courseId) {
      getLatLon();
    }

    if (latLon && (boundary.length === 0 || courseLineData.length === 0)) {
      // console.log('야호');
      getBoundary();
    }
  }, [courseId, boundary, latLon]);

  useEffect(() => {
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
      setCourseLineData([]);
      setBoundary([]);
      setCoursdId('');
      setLatLon('');
    }
    // console.log('쿨럭');
    // console.log(boundary);
  }, [courseLineData, kakaoMap, boundary]);

  // checkbox 선택 시 현재 위도, 경도, 래벨을 수정한 후 지도를 랜더링한다.
  const inputTerrain = e => {
    setTerrain(e.target.checked);
    const currentPos = kakaoMap.getCenter();
    const currentLevel = kakaoMap.getLevel();
    setLatitude(currentPos.Ma);
    setLongitude(currentPos.La);
    setLevel(currentLevel);
  };
  const inputBikeRoad = e => {
    setBikeRoad(e.target.checked);
    const currentPos = kakaoMap.getCenter();
    const currentLevel = kakaoMap.getLevel();
    setLatitude(currentPos.Ma);
    setLongitude(currentPos.La);
    setLevel(currentLevel);
  };

  useEffect(() => {
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
      console.log('위치를 찾을 수 없습니다.');
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
        const mapTypes = {
          terrain: kakao.maps.MapTypeId.TERRAIN,
          bicycle: kakao.maps.MapTypeId.BICYCLE,
        };

        const chkTerrain = document.getElementById('chkTerrain');
        const chkBicycle = document.getElementById('chkBicycle');

        for (const type in mapTypes) {
          map.removeOverlayMapTypeId(mapTypes[type]);
        }

        // 지형정보 체크박스가 체크되어있으면 지도에 지형정보 지도타입을 추가합니다
        if (chkTerrain.checked) {
          map.addOverlayMapTypeId(mapTypes.terrain);
        }
        if (chkBicycle.checked) {
          map.addOverlayMapTypeId(mapTypes.bicycle);
        }

        const linePath = [
          new window.kakao.maps.LatLng(33.452344169439975, 126.56878163224233),
          new window.kakao.maps.LatLng(33.452739313807456, 126.5709308145358),
          new window.kakao.maps.LatLng(33.45178067090639, 126.574),
        ];

        // 지도에 표시할 선 생성
        const polyline = new window.kakao.maps.Polyline({
          path: linePath, // 선을 구성하는 좌표배열 입니다
          strokeWeight: 5, // 선의 두께 입니다
          strokeColor: '#FFAE00', // 선의 색깔입니다
          strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle: 'solid', // 선의 스타일입니다
        });

        if (course) {
          polyline.setMap(map);
        }

        // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
        const placeOverlay = new window.kakao.maps.CustomOverlay({ zindex: 1 });
        const contentNode = document.createElement('div'); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
        let markers = []; // 마커를 담을 배열입니다
        let currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다

        // 장소 검색 객체를 생성합니다
        const ps = new kakao.maps.services.Places(map);

        // 지도에 idle 이벤트를 등록합니다
        window.kakao.maps.event.addListener(map, 'idle', searchPlaces);

        // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
        contentNode.className = 'placeinfo_wrap';

        // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
        // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
        addEventHandle(contentNode, 'mousedown', kakao.maps.event.preventMap);
        addEventHandle(contentNode, 'touchstart', kakao.maps.event.preventMap);

        // 커스텀 오버레이 컨텐츠를 설정합니다
        placeOverlay.setContent(contentNode);

        // 각 카테고리에 클릭 이벤트를 등록합니다
        addCategoryClickEvent();

        // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
        function addEventHandle(target, type, callback) {
          if (target.addEventListener) {
            target.addEventListener(type, callback);
          } else {
            target.attachEvent('on' + type, callback);
          }
        }

        // 카테고리 검색을 요청하는 함수입니다
        function searchPlaces() {
          if (!currCategory) {
            return;
          }

          // 커스텀 오버레이를 숨깁니다
          placeOverlay.setMap(null);

          // 지도에 표시되고 있는 마커를 제거합니다
          removeMarker();

          ps.categorySearch(currCategory, placesSearchCB, {
            useMapBounds: true,
          });
        }

        // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
        function placesSearchCB(data, status, pagination) {
          if (status === kakao.maps.services.Status.OK) {
            // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
            displayPlaces(data);
          } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
            // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
          } else if (status === kakao.maps.services.Status.ERROR) {
            // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
          }
        }

        // 지도에 마커를 표출하는 함수입니다
        function displayPlaces(places) {
          // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
          // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
          const order = document
            .getElementById(currCategory)
            .getAttribute('data-order');

          for (let i = 0; i < places.length; i++) {
            // 마커를 생성하고 지도에 표시합니다
            const marker = addMarker(
              new kakao.maps.LatLng(places[i].y, places[i].x),
              order,
            );

            // 마커와 검색결과 항목을 클릭 했을 때
            // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
            (function (marker, place) {
              kakao.maps.event.addListener(marker, 'click', function () {
                displayPlaceInfo(place);
              });
            })(marker, places[i]);
          }
        }

        // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
        function addMarker(position, order) {
          // console.log(typeof order);
          let markerUrl;
          switch (order) {
            case '0':
              markerUrl = './images/mart_marker.png';
              break;
            case '1':
              markerUrl = './images/market_marker.png';
              break;
            case '2':
              markerUrl = './images/hotel_marker.png';
              break;
            case '3':
              markerUrl = './images/restaurant_marker.png';
              break;
            case '4':
              markerUrl = './images/cafe_marker.png';
              break;
            case '5':
              markerUrl = './images/hospital_marker.png';
              break;
            case '6':
              markerUrl = './images/pharmacy_marker.png';
              break;
            default:
              break;
          }
          // console.log(markerUrl);
          const imageSrc = markerUrl,
            imageSize = new kakao.maps.Size(32, 33), // 마커 이미지의 크기
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
            marker = new kakao.maps.Marker({
              position: position, // 마커의 위치
              image: markerImage,
            });

          marker.setMap(map); // 지도 위에 마커를 표출합니다
          markers.push(marker); // 배열에 생성된 마커를 추가합니다

          return marker;
        }

        // 지도 위에 표시되고 있는 마커를 모두 제거합니다
        function removeMarker() {
          for (let i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
          }
          markers = [];
        }

        // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
        function displayPlaceInfo(place) {
          let content =
            '<div class="placeinfo">' +
            '   <a class="title" href="' +
            place.place_url +
            '" target="_blank" title="' +
            place.place_name +
            '">' +
            place.place_name +
            '</a>';

          if (place.road_address_name) {
            content +=
              '    <span title="' +
              place.road_address_name +
              '">' +
              place.road_address_name +
              '</span>' +
              '  <span class="jibun" title="' +
              place.address_name +
              '">(지번 : ' +
              place.address_name +
              ')</span>';
          } else {
            content +=
              '    <span title="' +
              place.address_name +
              '">' +
              place.address_name +
              '</span>';
          }

          content +=
            '    <span class="tel">' +
            place.phone +
            '</span>' +
            '</div>' +
            '<div class="after"></div>';

          contentNode.innerHTML = content;
          placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
          placeOverlay.setMap(map);
        }

        // 각 카테고리에 클릭 이벤트를 등록합니다
        function addCategoryClickEvent() {
          const category = document.getElementById('category'),
            children = category.children;

          for (let i = 0; i < children.length; i++) {
            children[i].onclick = onClickCategory;
          }
        }

        // 카테고리를 클릭했을 때 호출되는 함수입니다
        function onClickCategory() {
          const id = this.id;
          const className = this.className;

          placeOverlay.setMap(null);

          if (className === 'on') {
            currCategory = '';
            changeCategoryClass();
            removeMarker();
          } else {
            currCategory = id;
            changeCategoryClass(this);
            searchPlaces();
          }
        }

        // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
        function changeCategoryClass(el) {
          let category = document.getElementById('category');
          let children = category.children;
          let i;

          for (i = 0; i < children.length; i++) {
            children[i].className = '';
          }

          if (el) {
            el.className = 'on';
          }
        }

        setKakaoMap(map);
      });
    };
    mapScript.addEventListener('load', onLoadKakaoMap);

    return () => mapScript.removeEventListener('load', onLoadKakaoMap);
  }, [district, bikeroad, course, latitude, longitude]);

  return (
    <div>
      <NowContainer desc="지 도" />
      <div className="mapcontainer">
        <div className="mainMap">
          <div className="mainMap_wrap">
            <div
              id="map"
              style={{
                width: '100%',
                height: '100%',
                position: 'relative',
                overflow: 'hidden',
              }}
            ></div>
            <ul id="category">
              <li id="MT1" data-order="0">
                <img src="images/mart_icon.png" style={{ width: '25px' }}></img>
                <p>대형마트</p>
              </li>
              <li id="CS2" data-order="1">
                <img
                  src="images/market_icon.png"
                  style={{ width: '25px' }}
                ></img>{' '}
                <p>편의점</p>
              </li>
              <li id="AD5" data-order="2">
                <img
                  src="images/hotel_icon.png"
                  style={{ width: '25px' }}
                ></img>{' '}
                <p>숙박</p>
              </li>
              <li id="FD6" data-order="3">
                <img
                  src="images/restaurant_icon.png"
                  style={{ width: '25px' }}
                ></img>
                <p>음식점</p>
              </li>
              <li id="CE7" data-order="4">
                <img src="images/cafe_icon.png" style={{ width: '25px' }}></img>
                <p>카페</p>
              </li>
              <li id="HP8" data-order="5">
                <img
                  src="images/hospital_icon.png"
                  style={{ width: '25px' }}
                ></img>
                <p>병원</p>
              </li>
              <li id="PM9" data-order="6">
                <img
                  src="images/pharmacy_icon.png"
                  style={{ width: '25px' }}
                ></img>
                <p>약국</p>
              </li>
            </ul>
          </div>
          <p className="pContainer">
            <input type="checkbox" id="chkTerrain" onClick={inputTerrain} />
            <img src={mountain} style={{ width: '30px' }}></img>
            <input type="checkbox" id="chkBicycle" onClick={inputBikeRoad} />
            <img src={bicycle} style={{ width: '30px' }}></img>
            <input type="checkbox" onClick={inputCourse} />
            <img src={check} style={{ width: '30px' }}></img>
          </p>
        </div>
        {loadSaveCourse.length > 0 ? (
          <>
            <ListBody>
              {loadSaveCourse.length > 0 &&
                loadSaveCourse.map((course, i) => (
                  <ListCourseItem
                    onClick={e => {
                      e.preventDefault();
                      setCoursdId(course.courseId);
                    }}
                    className="listContainer"
                    style={{ width: '92%' }}
                    key={i}
                  >
                    <div style={{ fontWeight: 'bold' }}>{course.title}</div>
                    <SubListCourseItem>
                      <img
                        src={likeImg}
                        style={{
                          width: '15px',
                          marginLeft: '2px',
                          marginRight: '6px',
                          marginTop: '3px',
                          height: '15px',
                        }}
                        alt="a"
                      ></img>
                      <div>{course.like}</div>
                      <img
                        src={eyeImg}
                        style={{
                          width: '15px',
                          marginLeft: '15px',
                          marginRight: '7px',
                          marginTop: '1px',
                          height: '15px',
                        }}
                        alt="a"
                      ></img>
                      <div>{course.visited}</div>
                      <div
                        style={{
                          marginLeft: '15px',
                          fontSize: '13px',
                          marginTop: '2px',
                        }}
                      >
                        {localDate(course.regTime)}
                      </div>
                    </SubListCourseItem>
                  </ListCourseItem>
                ))}
            </ListBody>
          </>
        ) : (
          <ListBody>
            <ListCourseItem>
              <SubListCourseItem>
                <span>저장한 경로가 없습니다❌</span>
              </SubListCourseItem>
            </ListCourseItem>
          </ListBody>
        )}
      </div>
    </div>
  );
};
export default KAKAOMAP;
