import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import Paper from '@mui/material/Paper';
import Title from "../Title";
const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

function encodeThatProduct(productString) {
  const returnField = encodeURIComponent(productString)
  return returnField
}
const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function LookupSale() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [data, setData] = useState([]);
  const [date, setDate] = useState("");
  const [productName, setProductName] = useState("Product Realtime Revenue");

  const [salesAmount, setSalesAmount] = useState(0);
  const [tmp, setContact] = useState('');

  const [product, setProduct] = useState('');
  function currencyFormat(num) {
    const new_num = Number(num)
    return '$' + new_num.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
  }
  const fetchData = () => {
    fetch(`http://localhost:8788/productInventory`)
      .then((response) => response.json())
      .then((data) => {
        setData(data._embedded.productList);
      })
      .catch(error => console.warn(error));
  };

  const fetchSalesData = () => {
    fetch(`http://localhost:8790/bi-sales/product/?productName=${encodeThatProduct(inputValue)}`)
      .then((response) => response.json())
      .then((responseData) => {
        setSalesAmount(responseData);
      })
      .catch(error => console.warn(error));

  }

  useEffect(() => {
    fetchData();
  }, []);


  return (
    <div>
      <Grid container spacing={2}>
        <Grid item xs={7}>
          <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 150 }}>
            <Title>Look Up Sales By Product</Title>

            <form>
              <Autocomplete
                disableClearable
                inputValue={product}
                onChange={(e, v) => setProduct(v?.name || v)}
                getOptionLabel={(option) => `${option.name}`}
                isOptionEqualToValue={(option, value) => option.name === value.name}
                onInputChange={(event, newInputValue) => {
                  setInputValue(newInputValue);
                  setDate(new Date().toLocaleString());
                  setProductName(newInputValue);
                  fetchSalesData();
                }}
                onClick={fetchSalesData()}
                disablePortal
                id="combo-box-demo"
                options={data}
                sx={{ width: 400 }}
                renderInput={(params) => (
                  <div>
                    <TextField {...params}
                      label="Product" />
                    <br />
                  </div>
                )}
              />
            </form>
          </Paper>
        </Grid>
        <Grid item xs={5}>
          <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 150 }}>
            <Title>{productName}</Title>
            <Typography component="p" variant="h4">
              {currencyFormat(salesAmount)}
            </Typography>
            <Typography id="d" color="text.secondary" sx={{ flex: 1 }}>
              {date}
            </Typography>
          </Paper>
        </Grid>
      </Grid>
    </div>
  );
}
