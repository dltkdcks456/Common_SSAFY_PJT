/* eslint-disable */
import React, { useState, useCallback } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { address } from '../../components/Address';
import {
  checkIdAPI,
  checkNickAPI,
  sendMailAPI,
  checkCertiAPI,
} from '../../store/apis/userApi';
import { SIGN_UP_REQUEST } from '../../store/modules/userModule';
import {
  Container,
  Desc,
  InputBlock,
  Logo,
  MainContainer,
  SelectBox,
  Title,
  Wrapper,
} from './Signup.style';
import { Timer } from '../../components/Timer';
import InputContainer from '../../components/commons/inputContainer';
import Button from '../../components/commons/button';
import Input from '../../components/commons/input';
import ValidContainer from '../../components/commons/validContainer';
import logo from '../../assets/images/rideway-low-resolution-logo-black-on-white-background.png';
import NowContainer from '../../components/commons/nowLocation';

const SignUp = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 주소지 목록
  const options1 = ['시', ...Object.keys(address)];
  const [options2, setOptions2] = useState(['구']);
  const [options3, setOptions3] = useState(['동']);

  // 중복 체크 및 이메일 인증 체크
  const [idCheck, setIdCheck] = useState(false);
  const [nickCheck, setNickCheck] = useState(false);
  const [emailCheck, setEmailCheck] = useState(false);

  // 오류메시지 상태저장
  const [idMessage, setIdMessage] = useState('');
  const [nickMessage, setNickMessage] = useState('');
  const [ageMessage, setAgeMessage] = useState(
    '"-" 없이 숫자만 입력해주세요 ex) 1990-01-01',
  );
  const [emailMessage, setEmailMessage] = useState('');
  const [pwdMessage, setPwdMessage] = useState('');
  const [pwdChkMessage, setPwdChkMessage] = useState('');

  // 유효성 검사
  const [isId, setIsId] = useState(false);
  const [isNick, setIsNick] = useState(false);
  const [isAge, setIsAge] = useState(false);
  const [isEmail, setIsEmail] = useState(false);
  const [isPwd, setIsPwd] = useState(false);
  const [isPwdChk, setIsPwdChk] = useState(false);

  // 회원가입 변수 목록
  const [name, setName] = useState('');
  const [age, setAge] = useState('');
  const [si, setSi] = useState('');
  const [gun, setGun] = useState('');
  const [dong, setDong] = useState('');
  const [email, setEmail] = useState('');
  const [emailSend, setEmailSend] = useState(false);
  const [emailCerti, setEmailCerti] = useState(false);
  const [id, setId] = useState('');
  const [pwd, setPwd] = useState('');
  const [pwdchk, setPwdchk] = useState('');
  const [nick, setNick] = useState('');
  const [gender, setGender] = useState('');
  const [weight, setWeight] = useState(0);
  const [bikeweight, setBikeweight] = useState(0);
  // const [imagePath, setImagePath] = useState();
  const [open, setOpen] = useState(false);

  // 생년월일 유효성 검사
  const [dicObj, setDicObj] = useState({
    1: 31,
    3: 31,
    4: 30,
    5: 31,
    6: 30,
    7: 31,
    8: 31,
    9: 30,
    10: 31,
    11: 30,
    12: 31,
  });

  // Handler Function
  const inputAge = e => {
    let birth = e.target.value;
    if (birth.length > 0 && birth.length <= 10) {
      birth = birth
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,4})(\d{0,2})(\d{0,2})$/g, '$1-$2-$3')
        /* eslint-disable-next-line */
        .replace(/(\-{1,2})$/g, '');
      setAge(birth);
      setAgeMessage('"-" 없이 숫자만 입력해주세요 ex) 1990-01-01');
      setIsAge(false);
    } else if (birth.length === 0) {
      setAge(birth);
      setIsAge(false);
    }
    if (birth.length === 10) {
      const year = parseInt(age.slice(0, 4), 10);
      if ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0)
        setDicObj({ ...dicObj, 2: 29 });
      else setDicObj({ ...dicObj, 2: 28 });
      if (
        parseInt(age.slice(0, 4), 10) > 2023 ||
        parseInt(age.slice(5, 7), 10) > 12 ||
        parseInt(age.slice(5, 7), 10) < 1 ||
        parseInt(birth.slice(8, 10), 10) < 1 ||
        parseInt(birth.slice(8, 10), 10) >
          parseInt(dicObj[parseInt(birth.slice(5, 7), 10)], 10)
      ) {
        setAgeMessage('올바른 생년월일을 입력해주세요');
        setIsAge(false);
        setAge(birth);
      } else {
        setAgeMessage('');
        setIsAge(true);
        setAge(birth);
      }
    }
  };
  const inputSi = e => {
    if (e.target.value === '시') {
      setOptions2(['구']);
      setGun('');
      setOptions3(['동']);
      setDong('');
    } else {
      setOptions2(['구', ...Object.keys(address[e.target.value])]);
      setGun('');
      setOptions3(['동']);
      setDong('');
    }
    setSi(e.target.value);
  };
  const inputGun = e => {
    if (e.target.value === '구') {
      setOptions3(['동']);
      setDong('');
    } else {
      setOptions3(['동', ...address[si][e.target.value]]);
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
  const inputName = e => {
    setName(e.target.value);
  };
  const inputNick = e => {
    setNick(e.target.value);
    setNickCheck(false);
    setNickMessage('닉네임 중복확인을 해주세요');
  };
  const inputGender = e => {
    setGender(e.target.value);
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

  // ID 유효성 검사
  const onChangeID = useCallback(e => {
    const idCurrent = e.target.value;
    setId(idCurrent);
    setIdCheck(false);
    const idRegex = /^[a-z0-9]{5,20}$/;
    if (!idRegex.test(idCurrent)) {
      setIdMessage(
        '영어 소문자 + 숫자 조합으로 5자 이상 20자 이하로 입력해주세요!',
      );
      setIsId(false);
    } else {
      setIdMessage('ID 중복확인을 해주세요.');
      setIsId(true);
    }
  }, []);

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
      setEmailMessage('이메일 형식이 틀렸습니다. 다시 확인해주세요!');
      setIsEmail(false);
    } else {
      setEmailMessage('');
      setIsEmail(true);
    }
  }, []);

  // 비밀번호 유효성 검사
  const onChangePassword = useCallback(e => {
    const passwordRegex =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    const passwordCurrent = e.target.value;
    setPwd(passwordCurrent);

    if (!passwordRegex.test(passwordCurrent)) {
      setPwdMessage(
        '숫자+영문자+특수문자로 8자리 이상 16자 이하로 입력해주세요!',
      );
      setIsPwd(false);
    } else {
      setPwdMessage('');
      setIsPwd(true);
    }
  }, []);

  // 비밀번호 확인 유효성 검사
  const onChangePasswordCheck = useCallback(
    e => {
      const passwordCheckCurrent = e.target.value;
      setPwdchk(passwordCheckCurrent);

      if (pwd === passwordCheckCurrent) {
        setPwdChkMessage('');
        setIsPwdChk(true);
      } else {
        setPwdChkMessage('비밀번호가 틀려요. 다시 확인해주세요 :(');
        setIsPwdChk(false);
      }
    },
    [pwd],
  );

  // Email 인증 보내기  ==>  String으로 return이 와서 지금은 다 true임
  const sendMail = async e => {
    e.preventDefault();
    try {
      setEmailSend(false);
      const result = await sendMailAPI(email);
      setEmailSend(true);
      return result;
    } catch {
      setEmailSend(false);
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

  // 아이디 중복 체크
  const CheckId = async e => {
    e.preventDefault();
    const result = await checkIdAPI(id);
    // console.log(result);
    if (!result) {
      setIdMessage('사용가능한 ID입니다');
      setIsId(true);
      setIdCheck(true);
    } else {
      setIdMessage('이미 존재하는 ID입니다');
      setIsId(false);
      setIdCheck(false);
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

  // 뒤로가기 버튼
  const onCancle = () => {
    navigate(-1);
  };

  // 회원가입 버튼
  const signUpBtn = e => {
    e.preventDefault();
    if (name === '') {
      alert('이름을 입력해주세요');
    } else if (age === '') {
      alert('생년월일을 입력해주세요');
    } else if (
      parseInt(age.slice(0, 4), 10) > 2023 ||
      parseInt(age.slice(5, 7), 10) > 12 ||
      parseInt(age.slice(5, 7), 10) < 1 ||
      parseInt(age.slice(8, 10), 10) < 1 ||
      parseInt(age.slice(8, 10), 10) > dicObj[parseInt(age.slice(5, 7), 10)]
    ) {
      alert('올바른 생년월일을 입력해주세요');
    } else if (si === '' || gun === '' || dong === '') {
      alert('주소를 입력해주세요');
    } else if (email === '') {
      alert('이메일을 입력해주세요');
    } else if (id === '') {
      alert('아이디를 입력해주세요');
    } else if (pwd === '') {
      alert('비밀번호를 입력해주세요');
    } else if (pwdchk === '') {
      alert('비밀번호확인을 입력해주세요');
    } else if (nick === '') {
      alert('닉네임을 입력해주세요');
    } else if (gender === '') {
      alert('성별을 입력해주세요');
    } else if (pwd !== pwdchk) {
      alert('비밀번호를 확인해주세요');
    } else if (!idCheck) {
      alert('ID 중복검사를 해주세요');
    } else if (!nickCheck) {
      alert('닉네임 중복검사를 해주세요');
    } else if (!isId) {
      alert('올바른 ID를 입력해주세요');
    } else if (!isEmail) {
      alert('올바른 이메일을 입력해주세요');
    } else if (!isPwd) {
      alert('올바른 비밀번호를 입력해주세요');
    } else if (!isPwdChk) {
      alert('비밀번호가 일치하지 않습니다');
    } else {
      dispatch({
        type: SIGN_UP_REQUEST,
        data: {
          name,
          age,
          si,
          gun,
          dong,
          email,
          id,
          pwd,
          pwdchk,
          nick,
          gender,
          weight,
          bikeweight,
          open,
          navigate,
        },
      });
    }
  };

  return (
    <>
      <Container duration="0.5s">
        <MainContainer>
          <Title>회 원 가 입</Title>
          {/* <Logo src={logo} /> */}
          <InputContainer
            desc="이름"
            star
            onChange={inputName}
            name="name"
            width="20rem"
          />
          <InputContainer
            desc="생년월일"
            star
            onChange={inputAge}
            value={age}
            name="age"
            isValid={isAge}
            errMsg={age.length > 0 ? ageMessage : ''}
            width="20rem"
          />
          <InputBlock>
            <Wrapper>
              <div className="desc">
                <div className="star">*</div>
                주소
              </div>
              <SelectBox
                onChange={inputSi}
                value={si}
                placeholder="시"
                width="7rem"
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
                width="7rem"
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
                width="7rem"
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
            star
            onChange={onChangeEmail}
            value={email}
            name="email"
            isValid={isEmail}
            errMsg={email.length > 0 ? emailMessage : ''}
            width="20rem"
          />
          {!emailCheck && (
            <Wrapper>
              <div className="desc">
                <div className="star"></div>
              </div>
              <div className="btnInput">
                <Input
                  width="9.5rem"
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
                      width="6.5rem"
                      height="2rem"
                      ml="1rem"
                      weight=""
                      onClick={checkCerti}
                    />
                  </div>
                ) : (
                  <Button
                    name="인증번호 전송"
                    width="9.5rem"
                    height="2rem"
                    ml="1rem"
                    weight=""
                    onClick={sendMail}
                  />
                )}
              </div>
            </Wrapper>
          )}
          <Wrapper>
            <div className="desc">
              <div className="star">*</div>
              아이디
            </div>
            <div className="btnInput">
              <Input
                width={!idCheck ? '14rem' : '20rem'}
                onChange={onChangeID}
                name="id"
                value={id}
                // placeholder="아이디"
              />
              {!idCheck && (
                <div className="btnInput">
                  <Button
                    name="중복검사"
                    width="5rem"
                    height="2rem"
                    ml="1rem"
                    weight=""
                    onClick={CheckId}
                  />
                </div>
              )}
            </div>
          </Wrapper>
          {id.length > 0 && !idCheck && (
            <Wrapper mt="0">
              <div className="desc">
                <div className="star"></div>
              </div>
              <ValidContainer
                isValid={idCheck}
                errMsg={id.length > 0 && idMessage}
                width="30rem"
              />
            </Wrapper>
          )}
          <InputContainer
            desc="비밀번호"
            star
            onChange={onChangePassword}
            value={pwd}
            name="password"
            isValid={isPwd}
            errMsg={pwd.length > 0 ? pwdMessage : ''}
            width="20rem"
            type="password"
          />
          <InputContainer
            desc="비밀번호 확인"
            star
            onChange={onChangePasswordCheck}
            value={pwdchk}
            name="passwordCheck"
            isValid={isPwdChk}
            errMsg={pwdchk.length > 0 ? pwdChkMessage : ''}
            width="20rem"
            type="password"
          />
          <Wrapper>
            <div className="desc">
              <div className="star">*</div>
              닉네임
            </div>
            <div className="btnInput">
              <Input
                width={!nickCheck ? '14rem' : '20rem'}
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
                    weight="semi-bold"
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
          <InputBlock>
            <Wrapper>
              <div className="desc">
                <div className="star">*</div>
                성별
              </div>
              <SelectBox
                onChange={inputGender}
                value={gender}
                placeholder="성별을 선택해주세요"
                width="21.3rem"
              >
                <option value="">성별</option>
                <option value="male">남자</option>
                <option value="female">여자</option>
              </SelectBox>
            </Wrapper>
          </InputBlock>
          <InputContainer
            desc="몸무게"
            onChange={inputWeight}
            value={weight === '0' || weight === 0 ? '' : weight}
            name="weight"
            width="20rem"
            type="number"
          />
          <InputContainer
            desc="자전거 무게"
            onChange={inputBikeweight}
            value={bikeweight === '0' || bikeweight === 0 ? '' : bikeweight}
            name="weight"
            width="20rem"
            type="number"
          />
          <InputBlock>
            <Wrapper>
              <div className="desc">정보 공개 여부</div>
              <Input type="checkbox" onChange={inputOpen} width="1.5rem" />
            </Wrapper>
          </InputBlock>
          <InputBlock>
            <Button
              name="회원가입"
              width="8rem"
              mt="1rem"
              mr="0.5rem"
              height="2.5rem"
              onClick={signUpBtn}
            />
            <Button
              name="뒤로가기"
              width="8rem"
              mt="1rem"
              ml="0.5rem"
              bc="#C4C4C4"
              height="2.5rem"
              hoverColor="#a2a2a2"
              onClick={onCancle}
            />
          </InputBlock>
        </MainContainer>
      </Container>
    </>
  );
};

export default SignUp;
