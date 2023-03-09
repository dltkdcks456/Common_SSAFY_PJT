/* eslint-disable */
import React, { useCallback, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import Swal from 'sweetalert2';
import Button from '../../components/commons/button';
import InputContainer from '../../components/commons/inputContainer';
import NowContainer from '../../components/commons/nowLocation';
import { EDIT_PWD_REQUEST } from '../../store/modules/userModule';
import { Container, Desc, InputBlock, MainContainer } from './PwdEdit.style';

const pwdEdit = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const back = e => {
    e.preventDefault();
    navigate(-1);
  };

  // 오류메시지 상태저장
  const [pwdMessage, setPwdMessage] = useState('');
  const [pwdChkMessage, setPwdChkMessage] = useState('');

  // 유효성 검사
  const [isPwd, setIsPwd] = useState(false);
  const [isPwdChk, setIsPwdChk] = useState(false);

  // 비밀번호 변경 변수 목록
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const inputCurrentPassword = e => {
    setCurrentPassword(e.target.value);
  };

  // 비밀번호 유효성 검사
  const onChangePassword = useCallback(e => {
    const passwordRegex =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
    const passwordCurrent = e.target.value;
    setNewPassword(passwordCurrent);

    if (!passwordRegex.test(passwordCurrent)) {
      setPwdMessage('숫자+영문자+특수문자로 8자리 이상 입력하세요');
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
      setConfirmPassword(passwordCheckCurrent);

      if (newPassword === passwordCheckCurrent) {
        setPwdChkMessage('');
        setIsPwdChk(true);
      } else {
        setPwdChkMessage('비밀번호가 틀립니다.');
        setIsPwdChk(false);
      }
    },
    [newPassword],
  );

  const passwordEdit = e => {
    e.preventDefault();
    if (newPassword === '') {
      alert('비밀번호를 입력해주세요');
    } else if (confirmPassword === '') {
      alert('비밀번호확인을 입력해주세요');
    } else if (!isPwd) {
      Swal.fire({
        title: '비밀번호 변경 실패',
        text: '올바른 비밀번호를 입력해주세요',
        icon: 'error',
      });
    } else if (!isPwdChk) {
      Swal.fire({
        title: '비밀번호 변경 실패',
        text: '재확인 비밀번호가 일치하지 않습니다',
        icon: 'error',
      });
    } else {
      dispatch({
        type: EDIT_PWD_REQUEST,
        data: {
          nowPassword: currentPassword,
          newPassword: confirmPassword,
          navigate,
        },
      });
    }
  };

  return (
    <>
      <NowContainer desc="비 밀 번 호 변 경" />
      <Container>
        <MainContainer>
          <InputContainer
            desc="현재 비밀번호"
            star
            type="password"
            onChange={inputCurrentPassword}
            name="currentPassword"
            width="12rem"
          />
          <InputContainer
            desc="새 비밀번호"
            star
            type="password"
            onChange={onChangePassword}
            name="password"
            isValid={isPwd}
            errMsg={pwdMessage}
            width="12rem"
          />
          <InputContainer
            desc="비밀번호 확인"
            star
            type="password"
            onChange={onChangePasswordCheck}
            name="passwordConfirm"
            isValid={isPwdChk}
            errMsg={pwdChkMessage}
            width="12rem"
          />
          <InputBlock>
            <Button
              name="수정"
              // bc="white"
              width="5rem"
              height="3rem"
              mt="3rem"
              mr="0.5rem"
              // hoverColor="#a2a2a2"
              onClick={passwordEdit}
              type="submit"
            />
            <Button
              name="취소"
              // bc="white"
              width="5rem"
              height="3rem"
              mt="3rem"
              ml="0.5rem"
              // hoverColor="#a2a2a2"
              onClick={back}
            />
          </InputBlock>
        </MainContainer>
      </Container>
    </>
  );
};

export default pwdEdit;
