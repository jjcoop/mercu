import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';


export default function Copyright(props) {
    return (
      <Typography variant="body1" color="text.secondary" align="center" {...props}>
        {'Copyright © '}
        <Link color="inherit" href="/">
          Mercury Cycle
        </Link>{' '}
        {new Date().getFullYear()}
        {'.'}
      </Typography>
    );
  }