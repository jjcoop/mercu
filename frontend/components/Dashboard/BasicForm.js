import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import SendIcon from '@mui/icons-material/Send';
import Button from '@mui/material/Button';

export default function BasicForm() {
  const [myValue, setValue] = useState("");

  // Handles the submit event on form submit.
  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      companyName: event.target.companyName.value,
      base: event.target.base.value,
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    // API endpoint where we send form data.
    const endpoint = "http://localhost:8787/supplierProcurement";

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
    alert(`Is this your full name: ${result.data}`);
  };
  return (
    // We pass the event to the handleSubmit() function on submit.
    <form onSubmit={handleSubmit}>
      <TextField
        margin="normal"
        required
        id="outlined-required"
        label="Company Name"
        name="companyName"
      />
      <TextField
        margin="normal"
        required
        id="outlined-required"
        label="Base Name"
        name="base"
      />
      <Button type="submit" variant="contained" endIcon={<SendIcon />}>
        Create Supplier
      </Button>
    </form>
  );
}
