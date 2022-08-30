import { createTheme } from "@mui/material/styles";

const theme = createTheme({
    palette: {
        type: 'light',
        primary: {
          main: '#53acc9',
          light: '#64a4b3',
          dark: '#0a7777',
        },
        secondary: {
          main: 'rgb(220, 0, 78)',
        },
        background: {
          default: '#fff',
          paper: '#fff',
        },
      },
});
export default theme;
