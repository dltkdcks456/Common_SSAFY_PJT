/* eslint-disable */
import React, { useState, useEffect, useRef } from 'react';
import RecordSlide from './RecordSlide';
import trash from './../../../assets/images/trash.png'; //자신이 원하는 이미지를 import 하세요.
import {
  Container,
  MainContainer,
  SliderBox,
  SliderCotainer,
  SubContainer,
} from './carousel.style';
import Button from '../button';
// import img2 from "./../img/2.jpg";
// import img3 from "./../img/3.jpg";

export default function Carousel({ img }) {
  const TOTAL_SLIDES = 1; // n개의 슬라이드 : (n-1)
  const [currentSlide, setCurretSlide] = useState(0);
  const slideRef = useRef(null);

  const nextSlide = () => {
    if (currentSlide >= TOTAL_SLIDES) {
      setCurretSlide(0);
    } else {
      setCurretSlide(currentSlide + 1);
    }
  };
  const prevSlide = () => {
    if (currentSlide === 0) {
      setCurretSlide(TOTAL_SLIDES);
    } else {
      setCurretSlide(currentSlide - 1);
    }
  };

  useEffect(() => {
    slideRef.current.style.transition = 'all 0.5s ease-in-out';
    slideRef.current.style.transform = `translateX(-${currentSlide}00%)`;
  }, [currentSlide]);

  return (
    <>
      <MainContainer>
        <div style={{ marginRight: 'auto' }}>
          <Button
            onClick={prevSlide}
            name="<"
            width="2rem"
            height="100%"
            bc="none"
            border="none"
            z="1"
          />
        </div>
        <SubContainer>
          <SliderCotainer ref={slideRef}>
            <SliderBox>
              <RecordSlide />
            </SliderBox>
            <SliderBox>
              <RecordSlide />
            </SliderBox>
            <SliderBox>
              <RecordSlide />
            </SliderBox>
            <SliderBox>
              <RecordSlide />
            </SliderBox>
            <SliderBox>
              <RecordSlide />
            </SliderBox>
            <SliderBox>
              <RecordSlide />
            </SliderBox>
          </SliderCotainer>
        </SubContainer>
        <div style={{ marginLeft: 'auto' }}>
          <Button
            onClick={nextSlide}
            name=">"
            width="2rem"
            height="100%"
            bc="none"
            border="none"
            z="1"
          />
        </div>
      </MainContainer>
    </>
  );
}
