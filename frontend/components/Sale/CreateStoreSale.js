import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

export default function CreateStoreSale() {
  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [pKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const [pData, setProductData] = useState([]);

  const fetchData = () => {
    fetch(`http://localhost:8789/${keyword}/store`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.storeList))
      .catch((err) => console.error(err));
  };

  const fetchProductData = () => {
    fetch(`http://localhost:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    fetchData();
    fetchProductData();
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

    const endpoint = `http://localhost:8789/${keyword}/store/${inputId}`;

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
        "Created Store Sale: " +
          "\nStore ID: " + inputId + 
          "\nStore Address: " + inputValue +
          "\nQuantity: " + event.target.quantity.value +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
      <Autocomplete
          getOptionLabel={(option) => `${option.id}: ${option.address}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.substring(0, newInputValue.indexOf(':')));
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Store" />
              <br />
            </div>
          )}
        />
        <br />
        <Autocomplete
          getOptionLabel={(x) => `${x.name}: ${x.id}`}
          onInputChange={(event, newproductValue) => {
            setProductId(newproductValue.replace(/\D/g, ""));
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
          Create Store Sale
        </Button>
      </form>
    </div>
  );
}
