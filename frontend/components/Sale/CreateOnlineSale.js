import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";
import Box from "@mui/material";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function CreateOnlineSale() {
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

  const [customerAddress, setCustomerAddress] = useState('');
  const [customerName, setCustomerName] = useState('');
  const [quantity, setQuantity] = useState('');
  const [test, setTest] = useState('');

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };


  const fetchProductData = () => {
    fetch(`http://localhost:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    setInterval(() => {
      fetchProductData();
    }, 1000);
  }, []);


  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      customerName: event.target.customerName.value,
      address: event.target.customerAddress.value,
      productName: productValue,
      quantity: event.target.quantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    const endpoint = `http://localhost:8789/${keyword}/online`;

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
      setOpen(true);
      setCustomerName('');
      setCustomerAddress('');
      setQuantity('');
      setTest('');
    }
    else{
      setBadOpen(true);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          required
          id="outlined-required"
          label="Customer Name"
          name="customerName"
          onChange={event => setCustomerName(event.target.value)}
          value={customerName}
        />
        <br />
        <br />
        <TextField
          fullWidth
          required
          id="outlined-required"
          label="Customer Address"
          name="customerAddress"
          onChange={event => setCustomerAddress(event.target.value)}
          value={customerAddress}
        />
        <br />
        <br />
        <Autocomplete
          inputValue={test}
          onChange={(e,v)=>setTest(v?.name||v)}
          getOptionLabel={(x) => `${x.name}: ${x.id}`}
          onInputChange={(event, newproductValue) => {
            setProductValue(newproductValue.substring(0, newproductValue.indexOf(':')));
          }}
          disablePortal
          id="combo-box-demo"
          options={pData}
          sx={{ width: 400 }}
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
          id="outlined-required"
          label="Quantity"
          name="quantity"
          onChange={event => setQuantity(event.target.value)}
          value={quantity}
        />
        <br />
        <Button
          color="success"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Create Online Sale
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! New Online Sale Created
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Fail! Couldn't create new online sale!
        </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
