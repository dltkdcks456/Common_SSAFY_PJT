export const lightTheme = {
  bgColor: '#FFFFFF',
  textColor: '#000000',
  widgetColor: '#FFFFFF',
  ContainerColor: '#FFFFFF',
  dropdownColor: '#FFFFFF',
  // menuColor: '#828282',
  menuColor: '#000000',
  timetableBorder: '1px solid #FFFFFF',
  timetableColor: '#F2F2F2',
  timetableOutBorder: '1px solid #FFFFFF',
  timetableOutColor: '#F2F2F2',
  memoBorder: '1px solid #828282',
  memoColor: '#555555',
  noticeHoverColor: '#f2f2f2',
  chatFilter: 'drop-shadow(6px 6px 5px #b8b8b8)',
  borderShadow: 'rgba(99, 99, 99, 0.2) 0px 2px 8px 0px',
  alertColor: 'rgba(255,255,255,0.3)',
  chatboxShadow: 'rgba(0, 0, 0, 0.1) 0px 4px 12px',
};

export const darkTheme = {
  bgColor: '#292929',
  textColor: '#FFFFFF',
  menuColor: '#C7C7C7',
  dropdownColor: '#4D4C4C',
  widgetColor: '#605F5F',
  ContainerColor: '#7C7C7C',
  classColor: '#454545',
  timetableBorder: '1px solid #605F5F',
  timetableColor: '#454545',
  timetableOutBorder: '1px solid #7C7C7C',
  timetableOutColor: '#7C7C7C',
  memoBorder: '1px solid #A19F9F',
  memoColor: '#A19F9F',
  noticeHoverColor: '#646464',
  chatFilter: 'drop-shadow(6px 6px 5px black)',
  borderShadow: 'rgb(50 50 50) 0px 2px 8px 0px',
  alertColor: 'rgba(0,0,0,0.3)',
  chatboxShadow: 'rgb(255 255 255 / 10%) 0px 4px 12px',
};

const calcRem = size => `${size / 16}rem`;

const fontSizes = {
  small: calcRem(14),
  base: calcRem(16),
  lg: calcRem(18),
  xl: calcRem(20),
  xxl: calcRem(22),
  xxxl: calcRem(24),
  titleSize: calcRem(50),
};

const paddings = {
  small: calcRem(8),
  base: calcRem(10),
  lg: calcRem(12),
  xl: calcRem(14),
  xxl: calcRem(16),
  xxxl: calcRem(18),
};

const margins = {
  small: calcRem(8),
  base: calcRem(10),
  lg: calcRem(12),
  xl: calcRem(14),
  xxl: calcRem(16),
  xxxl: calcRem(18),
};

const interval = {
  base: calcRem(50),
  lg: calcRem(100),
  xl: calcRem(150),
  xxl: calcRem(200),
};

const verticalInterval = {
  base: `${calcRem(10)} 0 ${calcRem(10)} 0`,
};

const deviceSizes = {
  mobileS: '320px',
  mobileM: '375px',
  mobileL: '450px',
  tablet: '768px',
  tabletL: '1024px',
};

const colors = {
  black: '#000000',
  white: '#FFFFFF',
  gray_1: '#222222',
  gray_2: '#767676',
  green_1: '#3cb46e',
};

const device = {
  mobileS: `only screen and (max-width: ${deviceSizes.mobileS})`,
  mobileM: `only screen and (max-width: ${deviceSizes.mobileM})`,
  mobileL: `only screen and (max-width: ${deviceSizes.mobileL})`,
  tablet: `only screen and (max-width: ${deviceSizes.tablet})`,
  tabletL: `only screen and (max-width: ${deviceSizes.tabletL})`,
};

export const theme = {
  lightTheme,
  darkTheme,
  fontSizes,
  colors,
  deviceSizes,
  device,
  paddings,
  margins,
  interval,
  verticalInterval,
};
