import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";
import Button from "@mui/material/Button";
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

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
  };
  return (
    // We pass the event to the handleSubmit() function on submit.
    <form onSubmit={handleSubmit}>
      <div>
        <div>
          <TextField
            fullWidth
            margin="normal"
            required
            id="outlined-required"
            label="Company Name"
            name="companyName"
          />
        </div>
        <div>
          <TextField
            fullWidth
            margin="normal"
            required
            id="outlined-required"
            label="Base Name"
            name="base"
          />
        </div>
        <br />
        <div>
          <FormControl>
            <FormLabel id="demo-radio-buttons-group-label">Add Contacts?</FormLabel>
            <RadioGroup
              aria-labelledby="demo-radio-buttons-group-label"
              defaultValue="female"
              name="radio-buttons-group"
            >
              <FormControlLabel
                value="female"
                control={<Radio />}
                label="Yes"
              />
              <FormControlLabel 
                value="male" 
                control={<Radio />} 
                label="No" />
            </RadioGroup>
          </FormControl>
        </div>
        <br />
        <div>
          <Button type="submit" variant="contained" endIcon={<SendIcon />}>
            Create Supplier
          </Button>
        </div>
      </div>
    </form>
  );
}
