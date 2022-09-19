import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";


export default function UpdatePartForm() {
  const [keyword, setKeyword] = useState("productInventory");
  const [sKeyword] = useState("supplierProcurement");

  const [inputId, setInputId] = React.useState("");
  const [input, setInput] = React.useState("");

  const [data, setData] = useState([]);
  const [productId, setProductId] = useState([]);

  const [mData, setManufacturerData] = useState([]);
  const [manufacturerValue, setManufacturerValue] = React.useState("");
  const [manufacturerId, setManufacturerId] = React.useState("");


  const fetchData = () => {
    fetch(`http://localhost:8788/productInventory/parts`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.partList))
      .catch((err) => console.error(err));
  };

  const fetchProductData = () => {
    fetch(`http://localhost:8788/productInventory/parts/${inputId}`)
      .then((response) => response.json())
      .then((p) => setProductId(p.productURI.split("/").pop()))
      .then(console.log(productId))
      .catch((err) => console.log(err));
  };

  const fetchManufacturerData = () => {
    fetch(`http://localhost:8787/${sKeyword}`)
      .then((response) => response.json())
      .then((data) => setManufacturerData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchData();
    fetchManufacturerData();
  }, []);
  
  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    const returnData = {
      partName: event.target.name.value,
      partDescription: event.target.description.value,
      manufacturer: manufacturerValue,
      quantity: event.target.quantity.value,
    };

    const JSONdata = JSON.stringify(returnData);

    const endpoint = `http://localhost:8788/productInventory/${productId}/part/${inputId}`

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
        "Updated Part: " +
          input +
          "\nNew Part Name: " +
          event.target.name.value +
          "\nNew Part Description: " +
          event.target.description.value +
          "\nNew Manufacturer: " +
          manufacturerValue +
          "\nNew Quantity: " +
          event.target.quantity.value +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <Autocomplete
          getOptionLabel={(option) => `${option.partName}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInput(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ""));
            console.log(data.name)
          }}

          onChange={fetchProductData()}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Part" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          required
          id="outlined-required"
          label="Part Name"
          name="name"
        />
        <br /><br />
        <TextField
          required
          id="outlined-required"
          label="Part Description"
          name="description"
        />
        <br /><br />
        <Autocomplete
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newManufacturerValue) => {
            setManufacturerId(newManufacturerValue.replace(/\W/g, ''));
            setManufacturerValue(newManufacturerValue.substring(0, newManufacturerValue.indexOf(':')));

          }}
          disablePortal
          id="combo-box-demo"
          options={mData}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Manufacturer" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Quantity"
          name="quantity"
        />
        <br />
        <Button
          color="warning"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Update Part
        </Button>
      </form>
    </div>
  );
}
