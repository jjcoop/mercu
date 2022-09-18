import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";
import Box from "@mui/material";

export default function UpdateSupplierForm() {
  const [inputValue, setInputValue] = React.useState("");
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8787/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.supplierList))
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
      name: event.target.partName.value,
      description: event.target.partDescription.value,
      manufacturer: inputValue,
      quantity: event.target.quantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    console.log(JSONdata);

    // API endpoint where we send form data.

    const endpoint = `http://localhost:8788/productInventory`;

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
        "Created Part:" +
          "\Part Name: " + event.target.partName.value + event.target.description.value +
          "\Part Description: " + event.target.partDescription.value +
          "\nManufacturer: " + inputValue +
          "\Quantity: " + event.target.quantity.value +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
    else{
      alert("An error occurred: ", result)
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          required
          id="outlined-required"
          label="Part Name"
          name="partName"
        />
        <br />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Description"
          name="partDescription"
        />
        <br />
        <br />
        <Autocomplete
          getOptionLabel={(option) => `${option.companyName}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Manufacturer To Add Part" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          margin="normal"
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
          Create Part
        </Button>
      </form>
    </div>
  );
}
