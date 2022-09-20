import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

export default function CreateBackorder() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [pKeyword] = useState("productInventory");
  const [data, setData] = useState([]);


  const fetchData = () => {
    fetch(`http://localhost:8789/${keyword}/unavailable`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.storeList))
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
      productName: productValue,
      quantity: event.target.quantity.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    const endpoint = `http://localhost:8789/${keyword}/backorder/${inputId}`;

    // Form the request for sending data to the server.
    const options = {
      // The method is POST because we are sending data.
      method: "GET",
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
        "Created Backorder for Order: " + inputId + 
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
    else{
      alert("Couldn't find order to put on backorder")
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
      <Autocomplete
          getOptionLabel={(option) => `${option.id}: ${option.orderStatus}, ${option.productName}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ''));
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Sale Order" />
            </div>
          )}
        />
        <Button
          color="success"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Create Backorder
        </Button>
      </form>
    </div>
  );
}
