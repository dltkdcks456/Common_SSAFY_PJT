/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { getRecordList } from '../../store/apis/myPageApi';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import { localMonth } from '../../utils/DateFormatter';
import { SelectBox } from './UserHealth.style';

const MyHealth = ({ user }) => {
  const options = ['전체', '칼로리', '운동 거리', '평균 속도', '운동 시간'];
  const [value, setValue] = useState(options[0]);
  const [health, setHealth] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const getRecord = () => {
    getRecordList({ nickname: user.nickname })
      .then(res => {
        setIsLoading(true);
        // console.log(res.data);
        for (let i = 0; i < res.data.length; i++) {
          const tmp = {
            updateTime: localMonth(new Date(res.data[i].updateTime)),
            '칼로리(kcal)': res.data[i].calories,
            '운동 거리(m)': res.data[i].distance,
            '평균 속도(km/h)': res.data[i].speed,
            '운동 시간(s)': res.data[i].time,
          };
          setHealth(prev => [...prev, tmp]);
        }
      })
      .catch(err => console.log(err));
  };
  useEffect(() => {
    if (!isLoading) {
      getRecord();
    }
  }, [isLoading]);
  const onChange = e => {
    e.preventDefault();
    setValue(e.target.value);
  };
  // const date = new Date(health[0].updateTime);
  return (
    <>
      {health.length > 0 ? (
        <>
          <div
            style={{
              display: 'flex',
              fontFamily: 'Pretendard-Regular',
              width: '990px',
            }}
          >
            <SelectBox
              onChange={onChange}
              value={value}
              width="7rem"
              placeholder="전체"
              ml="auto"
            >
              {options.map(item => (
                <option value={item} key={item}>
                  {item}
                </option>
              ))}
            </SelectBox>
          </div>
          <LineChart
            width={1000}
            height={500}
            data={health}
            style={{ fontFamily: 'Pretendard-Regular' }}
          >
            <CartesianGrid stroke="#f5f5f5" />
            <XAxis dataKey="updateTime" />
            <YAxis />
            <Tooltip />
            <Legend />
            {(value === '전체' || value === '칼로리') && (
              <Line
                type="monotone"
                dataKey="칼로리(kcal)"
                stroke="#8884d8"
                activeDot={{ r: 8 }}
                isAnimationActive={false}
              />
            )}
            {(value === '전체' || value === '운동 거리') && (
              <Line
                type="monotone"
                dataKey="운동 거리(m)"
                stroke="#82ca9d"
                activeDot={{ r: 8 }}
                isAnimationActive={false}
              />
            )}
            {(value === '전체' || value === '평균 속도') && (
              <Line
                type="monotone"
                dataKey="평균 속도(km/h)"
                stroke="#eee78e"
                activeDot={{ r: 8 }}
                isAnimationActive={false}
              />
            )}
            {(value === '전체' || value === '운동 시간') && (
              <Line
                type="monotone"
                dataKey="운동 시간(s)"
                stroke="#ca8282"
                activeDot={{ r: 8 }}
                isAnimationActive={false}
              />
            )}
          </LineChart>
        </>
      ) : (
        <div>데이터가 없습니다.</div>
      )}
    </>
  );
};

export default MyHealth;
