import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function CreateStoreSale() {
  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [pKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const [pData, setProductData] = useState([]);

  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);

  const [test, setTest] = useState('');
  const [product, setProduct] = useState('');
  const [quantity, setQuantity] = useState('');
  const [resetBool, setReset] = React.useState(false)
  

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };

  const fetchData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8789/${keyword}/store`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.storeList))
      .catch((err) => console.error(err));
  };

  const fetchProductData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    setInterval(() => {
      fetchData();
      fetchProductData();
    }, 1000);
  }, []);


  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      productName: productValue,
      quantity: event.target.quantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8789/${keyword}/store/${inputId}`;

    // Form the request for sending data to the server.
    const options = {
      // The method is POST because we are sending data.
      method: "POST",
      // Tell the server we're sending JSON.
      headers: {
        "Content-Type": "application/json",
      },
      // Body of the request is the JSON data we created above.
      body: JSONdata,
    };

    // Send the form data to our forms API on Vercel and get a response.
    const response = await fetch(endpoint, options);

    // Get the response data from server as JSON.
    // If server returns the name submitted, that means the form works.
    const result = await response.json();

    if(response.status == 201){
      setReset(true)
      setOpen(true);
      setTest('');
      setProduct('');
      setQuantity('');
    }
    else{
      setBadOpen(true);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
      <Autocomplete
          disableClearable
          //key={resetBool}
          inputValue={test}
          onChange={(e,v)=>setTest(`${v.id}: ${v.address}`)}
          
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(option) => `${option.id}: ${option.address}`}
          onInputChange={(event, newInputValue) => {
            //setTest(newInputValue)
            setInputValue(newInputValue);
            setInputId(newInputValue.substring(0, newInputValue.indexOf(':')));
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{width: "75%"}}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Store" />
              <br />
            </div>
          )}
        />
        <br />
        <Autocomplete
          key={resetBool}
          disableClearable
          inputValue={product}
          //onChange={(e,v)=>setProduct(v?.name||v)}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(x) => `${x.name}: ${x.id}`}
          onInputChange={(event, newproductValue) => {
            setProduct(newproductValue)
            setProductId(newproductValue.replace(/\D/g, ""));
            setProductValue(newproductValue.substring(0, newproductValue.indexOf(':')));
          }}
          disablePortal
          id="combo-box-demo"
          options={pData}
          sx={{width: "75%"}}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Product" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          required
          sx={{width: "75%"}}
          id="outlined-required"
          label="Quantity"
          name="quantity"
          onChange={event => setQuantity(event.target.value)}
          value={quantity}
        />
        <br />
        <Button
          color="success"
          sx={{ width: "75%", marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Create Store Sale
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! New Store Sale Created
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Fail! Couldn't create new store sale!
        </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
