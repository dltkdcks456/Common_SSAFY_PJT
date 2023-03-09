/* eslint-disable default-param-last */
const LIGHT_THEME = 'THEME/LIGHT_THEME';
const DARK_THEME = 'THEME/DARK_THEME';

export const ligthTheme = () => ({
  type: LIGHT_THEME,
});

export const darkTheme = () => ({
  type: DARK_THEME,
});

const initialState = {
  isDarkMode: false,
};

const theme = (state = initialState, action) => {
  switch (action.type) {
    case LIGHT_THEME:
      return {
        isDarkMode: false,
      };
    case DARK_THEME:
      return {
        isDarkMode: true,
      };
    default:
      return state;
  }
};

export default theme;
