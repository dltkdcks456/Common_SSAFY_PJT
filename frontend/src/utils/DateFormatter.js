/* eslint-disable */
import moment from 'moment';
import 'moment/locale/ko';

export const localDate = time => moment(time).format('YYYY-MM-DD');

export const localTime = time => moment(time).format('LT');

export const localDateTime = time => moment(time).format('LL');

export const localStart = time => moment(time).format('yyyy-MM-DD HH:mm:ss');

export const localStartTime = time => moment(time).format('yyyy년 MM월 DD일 HH시 mm분');

export const localMonth = time => moment(time).format('MM-DD');
