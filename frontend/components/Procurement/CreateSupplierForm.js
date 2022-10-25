import React, { useEffect, useState, useRef, useCallback } from "react";
import TextField from "@mui/material/TextField";
import SendIcon from "@mui/icons-material/Send";
import Button from "@mui/material/Button";


import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

export default function CreateSupplierForm() {
  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);

  const [name, setName] = useState('');  
  const [base, setBase] = useState('');


  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };



  const [keyword, setKeyword] = useState("supplierProcurement");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };


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
    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/supplierProcurement`;

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

    if(response.status == 201){
      setOpen(true);
      setName('');
      setBase('');
    }
    else{
      setBadOpen(true);
    }
  };
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TextField
          sx={{ width: 470 }}

          required
          id="outlined-required"
          label="Company Name"
          name="companyName"
          onChange={event => setName(event.target.value)}
          value={name}
        />
        <br />
        <br />
        <TextField
          sx={{ width: 470 }}
          
          required
          id="outlined-required"
          label="Base Name"
          name="base"
          onChange={event => setBase(event.target.value)}
          value={base}
        />
        <br />
        <br />
        
        <Button color="success" type="submit" variant="contained" endIcon={<SendIcon />}>
          Create Supplier
        </Button>

        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! New Supplier Created
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Fail! New Supplier Created
        </Alert>
        </Snackbar>
        
      </form>
    </div>
    
  );
}
