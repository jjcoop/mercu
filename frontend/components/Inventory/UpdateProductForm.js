import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";


export default function UpdateContactForm() {
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [inputId, setInputId] = React.useState("");
  const [data, setData] = useState([]);
  const [pData, setProductData] = useState([]);
  const [pKeyword] = useState("productInventory");

  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

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

    // API endpoint where we send form data.

    const endpoint = `http://localhost:8788/productInventory/${productId}`;

    const data = {
      name: event.target.productName.value,
      price: event.target.productPrice.value,
      description: event.target.productDescription.value,
      quantity: event.target.productQuantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    // Form the request for sending data to the server.
    const options = {
      // The method is POST because we are sending data.
      method: "PUT",
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
        "Updated Product: " + productValue +
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
        <Autocomplete
            getOptionLabel={(x) => `${x.name}: ${x.id}`}
            onInputChange={(event, newproductValue) => {
              setProductValue(newproductValue);
              setProductId(newproductValue.replace(/\D/g, ""));

            }}
            disablePortal
            id="combo-box-demo"
            options={pData}
            sx={{ width: 400 }}
            renderInput={(params) => (
              <div>
                <TextField {...params} label="Select Product to Update" />
              </div>
            )}
          />
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
        <Button
          color="warning"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Update Product
        </Button>
      </form>
    </div>
  );
}
