import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function CreateBackorder() {
  const [inputValue, setInputValue] = React.useState("");
  const [inputId, setInputId] = React.useState("");
  const [keyword, setKeyword] = useState("sales");
  const [pKeyword] = useState("productInventory");
  const [saleID, setSaleID] = useState("sales");
  const [data, setData] = useState([]);
  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);
  const [resetBool, setReset] = React.useState(false)


  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };

  const fetchData = () => {
    fetch(`http://localhost:8789/${keyword}/unavailable`)
      .then((response) => response.json())
      .then((data) => setData(data))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchData();
  }, []);


  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Send the data to the server in JSON format.

    const endpoint = `http://localhost:8789/${keyword}/backorder/${saleID}`;

    // Form the request for sending data to the server.
    const options = {
      // The method is POST because we are sending data.
      method: "GET"
    };

    // Send the form data to our forms API on Vercel and get a response.
    const response = await fetch(endpoint, options);

    // Get the response data from server as JSON.
    // If server returns the name submitted, that means the form works.
    const result = await response.json();

    if (response.status == 200) {
      setOpen(true);
      setReset(true)
    }
    else {
      setBadOpen(true);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <Autocomplete

          // onChange={(e, v) => setSaleID(v.id)}
          key={resetBool}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          
          getOptionLabel={(option) => `${option.id}: ${option.productName}`}
          onInputChange={(event, newInputValue) => {
            setSaleID(newInputValue.replace(/\D/g, ''));
            setInputValue(newInputValue);
            setInputId(newInputValue);
            fetchData();
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
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
            Success! Backorder Created
          </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
          <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
            Fail! Couldn't create backorder!
          </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
