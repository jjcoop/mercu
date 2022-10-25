import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function CreateProductForm() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("productInventory");
  const [data, setData] = useState([]);

  const [productName, setproductName] = useState('');
  const [productPrice, setproductPrice] = useState('');
  const [productDescription, setproductDescription] = useState('');
  const [quantity, setQuantity] = useState('');

  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };

  const fetchData = () => {
    fetch(`http://localhost:8788/${keyword}/parts`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.partList))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      name: event.target.productName.value,
      price: event.target.productPrice.value,
      description: event.target.productDescription.value,
      quantity: event.target.productQuantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    // API endpoint where we send form data.

    const endpoint = `http://localhost:8788/productInventory`;

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

    if (response.status == 201) {
      setOpen(true);
      setproductName('');
      setproductPrice('');
      setproductDescription('');
      setQuantity('');

    }
    else{
      setBadOpen(true);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <br />
        <TextField
          sx={{width: 470}}
          required
          id="outlined-required"
          label="Product Name"
          name="productName"
          onChange={event => setproductName(event.target.value)}
          value={productName}
        />
        <br />
        <br />
        <TextField
          sx={{width: 470}}
          required
          id="outlined-required"
          label="Product Price"
          name="productPrice"
          onChange={event => setproductPrice(event.target.value)}
          value={productPrice}
        />
        <br />
        <TextField
          sx={{width: 470}}
          margin="normal"
          required
          id="outlined-required"
          label="Product Description"
          name="productDescription"
          onChange={event => setproductDescription(event.target.value)}
          value={productDescription}
        />
        <br />
        <TextField
          sx={{width: 470}}
          margin="normal"
          required
          id="outlined-required"
          label="Quantity"
          name="productQuantity"
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
          Create Product
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! Created Product!
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Failed to create the product!
        </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
