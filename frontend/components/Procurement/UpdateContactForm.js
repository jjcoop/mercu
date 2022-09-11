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
  const fetchData = () => {
    fetch(`http://localhost:8787/contacts`)
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

    // API endpoint where we send form data.

    const endpoint = `http://localhost:8787/supplierProcurement/${inputId}/contact`;

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
        "Updated Contact: " +
          inputValue +
          "\nNew Contact First Name: " +
          event.target.firstName.value +
          "\nNew Contact Last Name: " +
          event.target.lastName.value +
          "\nNew Contact Phone: " +
          event.target.phone.value +
          "\nNew Contact Email: " +
          event.target.email.value +
          "\nNew Contact Position: " +
          event.target.position.value +
          ".\nRefreshing webpage now..."
      );
      window.location.reload(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <Autocomplete
          getOptionLabel={(option) => `${option.contacts.name}: ${option.id}`}
          onInputChange={(event, newInputValue) => {
            setInputValue(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ""));
            
          }}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{ width: 400 }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Supplier" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          required
          id="outlined-required"
          label="First Name"
          name="firstName"
        />
        <TextField
          required
          id="outlined-required"
          label="Last Name"
          name="lastName"
        />
        <br />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Phone"
          name="contactPhone"
        />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Email"
          name="contactEmail"
        />
        <TextField
          fullWidth
          margin="normal"
          required
          id="outlined-required"
          label="Position"
          name="contactPosition"
        />
        <br />
        <Button
          color="warning"
          sx={{ width: 250, marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Update Supplier
        </Button>
      </form>
    </div>
  );
}
