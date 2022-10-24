import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";
import Typography from '@mui/material/Typography';

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

function encodeThatProduct(productString){
  const returnField = encodeURIComponent(productString)
  return returnField
}


export default function LookupSale() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [data, setData] = useState([]);

  const [salesAmount, setSalesAmount] = useState([]);
  const [tmp, setContact] = useState('');

  const [product, setProduct] = useState('');

  const fetchData = () => {
    fetch(`http://localhost:8788/productInventory`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.productList))
      .catch((err) => console.error(err));
  };

  const fetchSalesData = () => {
    fetch(`http://localhost:8790/bi-sales/product/?productName=${encodeThatProduct(inputValue)}`)
      .then((response) => response.json())
      .then((data) => console.log("Total:", data))
      //.then((data) => setSalesAmount(data))
      .catch((err) => console.error(err));
  }

  useEffect(() => {
    
      fetchData();
    
  }, []);


  return (
    <div>
      <form>
        <Autocomplete
          inputValue={product}
          onChange={(e,v)=>setProduct(v?.name||v)}
          getOptionLabel={(option) => `${option.name}`}
          isOptionEqualToValue={(option, value) => option.name === value.name}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
          }}
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
        <br />
        <Button
          color="success"
          sx={{ width: 250, marginTop: 2 }}
          variant="contained"
          endIcon={<SendIcon />}
          onClick={fetchSalesData()}
        >
          Search
        </Button>
      </form>
    </div>
  );
}
