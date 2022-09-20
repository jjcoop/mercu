import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";


export default function CreatePartForm() {
  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

  const [manufacturerValue, setManufacturerValue] = React.useState("");
  const [manufacturerId, setManufacturerId] = React.useState("");

  const [pKeyword] = useState("productInventory");
  const [sKeyword] = useState("supplierProcurement");
  const [pData, setProductData] = useState([]);
  const [mData, setManufacturerData] = useState([]);
  const fetchProductData = () => {
    fetch(`http://localhost:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
  };

  const fetchManufacturerData = () => {
    fetch(`http://localhost:8787/${sKeyword}`)
      .then((response) => response.json())
      .then((data) => setManufacturerData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchProductData(),
    fetchManufacturerData()
  }, []);

  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const returnData = {
      partName: event.target.partName.value,
      partDescription: event.target.partDescription.value,
      manufacturer: manufacturerValue,
      quantity: event.target.quantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(returnData);

    console.log(JSONdata);

    // API endpoint where we send form data.

    const endpoint = `http://localhost:8788/productInventory/${productId}/part`;

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
          "\nPart Name: " + event.target.partName.value +
          "\nPart Description: " + event.target.partDescription.value +
          "\nQuantity: " + event.target.quantity.value +
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
              <TextField {...params} label="Select Product To Add Part To" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          required
          id="outlined-required"
          label="Part Name"
          name="partName"
        />
        <br /><br />
        <TextField
          fullWidth
          required
          id="outlined-required"
          label="Description"
          name="partDescription"
        />
        <br /><br />
        <Autocomplete
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newManufacturerValue) => {
            setManufacturerId(newManufacturerValue.replace(/\W/g, ''));
            setManufacturerValue(newManufacturerValue.substring(0, newManufacturerValue.indexOf(':')));
            console.log("Manufacturer value after split: ", manufacturerValue)
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
