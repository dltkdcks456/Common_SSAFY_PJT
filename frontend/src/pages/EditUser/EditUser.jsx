/* eslint-disable */
import React, { useState, useCallback, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';
import { address } from '../../components/Address';
import { Timer } from '../../components/Timer';
import {
  checkNickAPI,
  sendMailAPI,
  checkCertiAPI,
  imageUploadAPI,
} from '../../store/apis/userApi';
import { EDIT_USER_REQUEST } from '../../store/modules/userModule';
import { BASE_URL } from '../../utils/urls';
import {
  Container,
  Image,
  InputBlock,
  // Logo,
  MainContainer,
  SelectBox,
  Wrapper,
} from './EditUser.style';
// import logo from '../../assets/images/rideway-low-resolution-logo-black-on-white-background.png';
import Button from '../../components/commons/button';
import InputContainer from '../../components/commons/inputContainer';
import Input from '../../components/commons/input';
import ValidContainer from '../../components/commons/validContainer';
import NowContainer from '../../components/commons/nowLocation';

const editUser = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { user } = useSelector(state => state.myPage);
  useEffect(() => {}, [user]);
  const token = sessionStorage.getItem('userToken');

  // 회원정보수정 변수 목록
  const [si, setSi] = useState(user.si);
  const [gun, setGun] = useState(user.gun);
  const [dong, setDong] = useState(user.dong);
  const [email, setEmail] = useState(user.email);
  const [emailSend, setEmailSend] = useState(true);
  const [emailCerti, setEmailCerti] = useState(true);
  const [timer, setTimer] = useState(false);
  const [nick, setNick] = useState(user.nickname);
  const [weight, setWeight] = useState(user.weight ? user.weight : '');
  const [bikeweight, setBikeweight] = useState(
    user.cycle_weight ? user.cycle_weight : '',
  );
  // const [imagePath, setImagePath] = useState(user.imagePath);
  const [open, setOpen] = useState(user.open);

  // 프로필 사진 변수
  const url = `${BASE_URL}user/imageDownloadBy/${user.image_path}`;
  const [update, setUpdate] = useState(false);
  const [image, setImage] = useState({
    image_file: '',
    preview_URL: url,
  });

  let inputRef;

  // 프로필 사진 저장
  const saveImage = e => {
    e.preventDefault();
    const fileReader = new FileReader();

    if (e.target.files[0]) {
      fileReader.readAsDataURL(e.target.files[0]);
    }
    fileReader.onload = () => {
      setImage({
        image_file: e.target.files[0],
        preview_URL: fileReader.result,
      });
    };
    setUpdate(true);
  };

  // 프로필 사진 삭제
  const deleteImage = () => {
    setImage({
      image_file: '',
      preview_URL: `${BASE_URL}user/imageDownloadBy/images/profile/default.png`,
    });
    setUpdate(true);
    // console.log('프로필 사진 삭제 버튼 눌리면 기본 이미지로 바꿔야됨!!');
  };

  // 프로필 사진 등록 요청
  const sendImageToServer = async () => {
    if (update) {
      if (image.image_file) {
        const formData = new FormData();
        formData.append('imageFile', image.image_file);
        formData.append('token', token);
        await imageUploadAPI(formData);
        // console.log('이미지 등록 완료');
        // setImage({
        //   image_file: '',
        //   preview_URL: 'img/default_image.png',
        // });
      } else {
        const formData = new FormData();
        formData.append('token', token);
        await imageUploadAPI(formData);
        // console.log('기본 이미지로 등록 완료');
      }
    }
  };

  // 이미지 파일 초기화
  const resetValue = e => (e.target.value = null);
  const resetRef = refParam => (inputRef = refParam);

  // 주소지 목록
  const options1 = ['시를 선택해주세요', ...Object.keys(address)];
  const [options2, setOptions2] = useState([]);
  const [options3, setOptions3] = useState([]);

  // 주소 최신화
  useEffect(() => {
    if (si === '시를 선택해주세요') {
      setOptions2([]);
      setOptions3([]);
    } else {
      setOptions2(['구를 선택해주세요', ...Object.keys(address[si])]);
      setOptions3([]);
    }
    if (si && gun) {
      if (gun === '구를 선택해주세요') {
        setOptions3([]);
      } else {
        setOptions3(['동을 선택해주세요', ...address[si][gun]]);
      }
    }
  }, []);

  // 중복 체크 및 이메일 인증 체크
  const [nickCheck, setNickCheck] = useState(true);
  const [emailCheck, setEmailCheck] = useState(true);

  // 오류메시지 상태저장
  const [nickMessage, setNickMessage] = useState('');
  const [emailMessage, setEmailMessage] = useState('');

  // 유효성 검사
  const [isNick, setIsNick] = useState(true);
  const [isEmail, setIsEmail] = useState(true);

  // Handler Function
  const inputSi = e => {
    if (e.target.value === '시를 선택해주세요') {
      setOptions2([]);
      setGun('');
      setOptions3([]);
      setDong('');
    } else {
      setOptions2([
        '구를 선택해주세요',
        ...Object.keys(address[e.target.value]),
      ]);
      setGun('');
      setOptions3([]);
      setDong('');
    }
    setSi(e.target.value);
  };
  const inputGun = e => {
    if (e.target.value === '구를 선택해주세요') {
      setOptions3([]);
      setDong('');
    } else {
      setOptions3(['동을 선택해주세요', ...address[si][e.target.value]]);
      setDong('');
    }
    setGun(e.target.value);
  };
  const inputDong = e => {
    setDong(e.target.value);
  };
  const inputEmailCerti = e => {
    setEmailCerti(e.target.value);
  };
  const inputNick = e => {
    setNick(e.target.value);
    setNickCheck(false);
    setNickMessage('');
  };
  const inputWeight = e => {
    const Regex = /^[0-9]{0,2}$/;
    if (Regex.test(e.target.value)) {
      setWeight(e.target.value);
    }
  };
  const inputBikeweight = e => {
    const Regex = /^[0-9]{0,2}$/;
    if (Regex.test(e.target.value)) {
      setBikeweight(e.target.value);
    }
  };
  const inputOpen = e => {
    setOpen(e.target.checked);
  };

  // 이메일 유효성 검사
  const onChangeEmail = useCallback(e => {
    const emailRegex =
      /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);
    setEmailCheck(false);
    setEmailSend(false);
    setEmailCerti(false);

    if (!emailRegex.test(emailCurrent)) {
      setEmailMessage('이메일 형식이 틀렸습니다. 다시 확인해주세요');
      setIsEmail(false);
    } else {
      setEmailMessage('');
      setIsEmail(true);
    }
  }, []);

  // Email 인증 보내기  ==>  String으로 return이 와서 지금은 다 true임
  const sendMail = async e => {
    e.preventDefault();
    try {
      setEmailSend(false);
      setTimer(false);
      const result = await sendMailAPI(email);
      setTimer(true);
      setEmailSend(true);
      return result;
    } catch {
      setEmailSend(false);
      setTimer(false);
      setEmailMessage('이미 존재하는 이메일입니다');
      setIsEmail(false);
      return false;
    }
  };

  // Email 인증 확인  ==>  String으로 return이 와서 지금은 다 true임
  const checkCerti = async e => {
    e.preventDefault();
    try {
      const result = await checkCertiAPI(emailCerti);
      setEmailCheck(true);
      alert('인증이 완료되었습니다.');
      return result;
    } catch {
      setEmailCheck(false);
      alert('인증번호가 틀립니다.');
      return false;
    }
  };

  // 닉네임 중복 체크
  const CheckNick = async e => {
    e.preventDefault();
    const result = await checkNickAPI(nick);
    // console.log(result);
    if (!result) {
      setNickMessage('사용가능한 닉네임입니다');
      setNickCheck(true);
      setIsNick(true);
    } else {
      setNickMessage('이미 존재하는 닉네임입니다');
      setNickCheck(false);
      setIsNick(false);
    }
  };

  // 취소 버튼
  const back = e => {
    e.preventDefault();
    navigate(-1);
  };

  // 수정 버튼
  const editUserBtn = e => {
    e.preventDefault();
    if (si === '' || gun === '' || dong === '') {
      alert('주소를 입력해주세요');
    } else if (email === '') {
      alert('이메일을 입력해주세요');
    } else if (nick === '') {
      alert('닉네임을 입력해주세요');
    } else if (!nickCheck) {
      alert('닉네임 중복검사를 해주세요');
    } else if (!isEmail) {
      alert('올바른 이메일을 입력해주세요');
    } else {
      // console.log({
      //   si,
      //   gun,
      //   dong,
      //   email,
      //   nick,
      //   weight,
      //   bikeweight,
      //   open,
      //   token,
      // });
      dispatch({
        type: EDIT_USER_REQUEST,
        data: {
          si,
          gun,
          dong,
          email,
          nick,
          weight,
          bikeweight,
          open,
          token,
          navigate,
        },
      });
    }
  };

  return (
    <>
      <NowContainer desc="회 원 정 보 수 정" />
      <Container duration="">
        {user && (
          <MainContainer>
            {/* <Link to="/user/editPwd">비밀번호 변경</Link>
            <Link to="/user/delete">회원탈퇴</Link> */}
            {/* <MenuWrapper> */}
            <InputBlock>
              <Wrapper>
                <div className="desc">프로필 사진</div>
                <div className="btnInput">
                  <input
                    type="file"
                    accept="image/*"
                    onChange={saveImage}
                    // 클릭할 때마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
                    // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있다
                    onClick={resetValue}
                    ref={resetRef}
                    style={{ display: 'none' }}
                  />
                  <Image src={image?.preview_URL} alt="프로필 이미지" />
                  <div>
                    <Button
                      onClick={() => inputRef.click()}
                      name="수정"
                      width="4rem"
                      fontSize="0.9rem"
                      mr="0.25rem"
                    />
                    <Button
                      onClick={deleteImage}
                      name="삭제"
                      width="4rem"
                      ml="0.25rem"
                      fontSize="0.9rem"
                    />
                  </div>
                </div>
              </Wrapper>
            </InputBlock>
            {/* </MenuWrapper> */}
            <form onSubmit={editUserBtn}>
              <InputBlock>
                <Wrapper>
                  <div className="desc" width="small">
                    주소
                  </div>
                  <SelectBox
                    onChange={inputSi}
                    value={si}
                    placeholder="시"
                    width="6rem"
                  >
                    {options1.map(item => (
                      <option value={item} key={item}>
                        {item}
                      </option>
                    ))}
                  </SelectBox>
                  <SelectBox
                    onChange={inputGun}
                    value={gun}
                    placeholder="구"
                    width="6rem"
                  >
                    {options2?.map(item => (
                      <option value={item} key={item}>
                        {item}
                      </option>
                    ))}
                  </SelectBox>
                  <SelectBox
                    onChange={inputDong}
                    value={dong}
                    placeholder="동"
                    width="6rem"
                  >
                    {options3?.map(item => (
                      <option value={item} key={item}>
                        {item}
                      </option>
                    ))}
                  </SelectBox>
                </Wrapper>
              </InputBlock>
              <InputContainer
                desc="이메일"
                onChange={onChangeEmail}
                value={email}
                name="email"
                isValid={isEmail}
                errMsg={email.length > 0 ? emailMessage : ''}
                width="17rem"
              />
              {!emailCheck && (
                <Wrapper>
                  <div className="desc">
                    <div className="star"></div>
                  </div>
                  <div className="btnInput">
                    <Input
                      width="8rem"
                      onChange={inputEmailCerti}
                      // value={emailCerti}
                      name="mailcerti"
                      placeholder="인증번호 입력"
                    />
                    {emailSend ? (
                      <div className="btnInput">
                        <Timer />
                        <Button
                          name="확인"
                          width="5rem"
                          height="2rem"
                          ml="1rem"
                          onClick={checkCerti}
                        />
                      </div>
                    ) : (
                      <Button
                        name="인증번호 전송"
                        width="8rem"
                        height="2rem"
                        ml="1rem"
                        onClick={sendMail}
                      />
                    )}
                  </div>
                </Wrapper>
              )}
              <Wrapper>
                <div className="desc">닉네임</div>
                <div className="btnInput">
                  <Input
                    width={!nickCheck ? '11rem' : '17rem'}
                    onChange={inputNick}
                    name="nickname"
                    value={nick}
                  />
                  {!nickCheck && (
                    <div className="btnInput">
                      <Button
                        name="중복검사"
                        width="5rem"
                        height="2rem"
                        ml="1rem"
                        onClick={CheckNick}
                      />
                    </div>
                  )}
                </div>
              </Wrapper>
              {nick.length > 0 && !nickCheck && (
                <Wrapper mt="0">
                  <div className="desc">
                    <div className="star"></div>
                  </div>
                  <ValidContainer
                    isValid={nickCheck}
                    errMsg={nick.length > 0 && nickMessage}
                  />
                </Wrapper>
              )}
              <InputContainer
                desc="몸무게"
                onChange={inputWeight}
                value={weight === 0 || weight === '0' ? '' : weight}
                name="weight"
                width="17rem"
                type="number"
              />
              <InputContainer
                desc="자전거 무게"
                onChange={inputBikeweight}
                value={bikeweight === 0 || bikeweight === '0' ? '' : bikeweight}
                name="weight"
                width="17rem"
                type="number"
              />
              <InputBlock>
                <Wrapper>
                  <div className="desc">정보 공개 여부</div>
                  <Input
                    type="checkbox"
                    onChange={inputOpen}
                    width="1.5rem"
                    checked={open}
                  />
                </Wrapper>
              </InputBlock>
              <InputBlock>
                <Button
                  name="변경"
                  mt="1rem"
                  mr="0.5rem"
                  height="3rem"
                  width="5rem"
                  type="submit"
                  onClick={sendImageToServer}
                />
                <Button
                  name="취소"
                  mt="1rem"
                  ml="0.5rem"
                  bc="#C4C4C4"
                  height="3rem"
                  width="5rem"
                  hoverColor="#a2a2a2"
                  onClick={back}
                />
              </InputBlock>
              <div style={{ display: 'flex', justifyContent: 'center' }}>
                <Link
                  to="/user/delete"
                  style={{
                    textDecoration: 'none',
                    color: 'black',
                    marginTop: '2rem',
                  }}
                >
                  회원탈퇴
                </Link>
              </div>
            </form>
          </MainContainer>
        )}
      </Container>
    </>
  );
};

export default editUser;
