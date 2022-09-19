import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";
import Box from "@mui/material";

//GET LINK: http://localhost:8788/productInventory/parts

export default function CreateProductForm() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
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
      alert(
        "Created Product: " +
          "\nProduct Name: " + event.target.productName.value + 
          "\nProduct Price: " + event.target.productPrice.value +
          "\nProduct Description: " + event.target.productDescription.value +
          "\nQuantity: " + event.target.productQuantity.value +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <br />
        <TextField
          required
          id="outlined-required"
          label="Product Name"
          name="productName"
        />
        <br />
        <br />
        <TextField
          required
          id="outlined-required"
          label="Product Price"
          name="productPrice"
        />
        <br />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Product Description"
          name="productDescription"
        />
        <br />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Quantity"
          name="productQuantity"
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
      </form>
    </div>
  );
}
