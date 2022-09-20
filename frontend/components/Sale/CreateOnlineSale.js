import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";
import Box from "@mui/material";

export default function CreateOnlineSale() {
  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [pKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const [pData, setProductData] = useState([]);

  const fetchProductData = () => {
    fetch(`http://localhost:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    fetchProductData();
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

    if (response.status == 201) {
      alert(
        "Created Online Sale: " +
          "\nCustomer Name: " + event.target.customerName.value + 
          "\nAddress: " + event.target.customerAddress.value +
          "\nProduct: " + productValue +
          "\nQuantity: " + event.target.quantity.value +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
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
        />
        <br />
        <br />
        <TextField
          fullWidth
          required
          id="outlined-required"
          label="Customer Address"
          name="customerAddress"
        />
        <br />
        <br />
        <Autocomplete
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
      </form>
    </div>
  );
}
