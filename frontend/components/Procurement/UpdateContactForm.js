import {useState, useEffect } from "react";
import { Button } from "@mui/material";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import SendIcon from "@mui/icons-material/Send";

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import * as React from "react";

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function UpdateContactForm() {
  const [inputValue, setInputValue] = useState("");
  const [inputId, setInputId] = useState("");
  const [contactID, setContactID] = useState("");
  const [contactName, setContactName] = useState("");
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [sData, setSupplierData] = useState([]);
  const [cData, setContactData] = useState([]);

  const [supplier, setSupplier] = useState('');
  const [contact, setContact] = useState('');
  const [fname, setFname] = useState('');
  const [lname, setLname] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [position, setPosition] = useState('');
  const [resetBool, setReset] = React.useState(false);

  const [open, setOpen] = React.useState(false);
  const [badOpen, setBadOpen] = React.useState(false);

  const [contactBool, setContactBool] = React.useState(true);

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setBadOpen(false);
  };


  const fetchSupplierData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${keyword}`)
      .then((response) => response.json())
      .then((sData) => setSupplierData(sData._embedded.supplierList))
      .catch((err) => console.error(err));
    
  };


  useEffect(() => {
    fetchSupplierData();
   },[]);

  const fetchContactData = (test) => {

    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${keyword}/${test}`)
      .then((response) => response.json())
      .then((cData) => setContactData(cData.contacts))
      .catch((err) => console.error(err));

  };

  const getLabel = () => {
    if(contactBool){
      return "Select a Supplier"

    }
    else{
      return "Contact"
    }
  }

  const handleSubmit = async (event) => {
    // Stop the form from submitting and refreshing the page.
    event.preventDefault();

    // Get data from the form.
    const data = {
      fname: event.target.firstName.value,
      lname: event.target.lastName.value,
      phone: event.target.contactPhone.value,
      email: event.target.contactEmail.value,
      position: event.target.contactPosition.value
    };

    // Send the data to the server in JSON format.
    const JSONdata = JSON.stringify(data);

    // API endpoint where we send form data.

    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/supplierProcurement/${inputId}/contact/${contactID}`;

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
      setOpen(true);
      setSupplier('');
      setContact('');
      setFname('');
      setLname('');
      setPhone('');
      setEmail('');
      setPosition('');
    }
    else{
      setBadOpen(true);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <Autocomplete
          
          disableClearable
          inputValue={supplier}
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          onChange={(e,v)=>
            setSupplier(`${v.companyName}: ${v.id}`)}

          onInputChange={(event, newInputValue) => {
            setContactBool(false)
            setInputId(newInputValue.replace(/\D/g, ""))
            setContact('');
            fetchContactData(newInputValue.replace(/\D/g, ""))
          }}

          disablePortal
          id="combo-box-demo"
          options={sData}
          sx={{ width: "50%" }}
          renderInput={(params) => (
            <div>
              <TextField {...params} 
              label="Supplier" />
              <br />
            </div>
          )}
        />
        <br />
        <Autocomplete
          disabled={contactBool}
          disableClearable
          key={resetBool}
          inputValue={contact}
          onChange={(e,v)=>setContact(`${v.name}: ${v.id}`)}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(option) => `${option.name}: ${option.id}`}
          onInputChange={(event, value) => {
            setContactName(value)
            setContactID(value.replace(/\D/g, ""))
          }}
          disablePortal
          id="combo-box-demo"
          options={cData}
          sx={{ width: "50%" }}
          renderInput={(params) => (
            <div>
              <TextField {...params} label={getLabel()} />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          sx={{mr: "2%", width: "24%"}}
          required
          id="outlined-required"
          label="First Name"
          name="firstName"
          onChange={event => setFname(event.target.value)}
          value={fname}
        />
        <TextField
          sx={{width: "24%"}}
          required
          id="outlined-required"
          label="Last Name"
          name="lastName"
          onChange={event => setLname(event.target.value)}
          value={lname}
        />
        <br />
        <TextField
          sx={{ width: "50%" }}
          margin="normal"
          required
          id="outlined-required"
          label="Phone"
          name="contactPhone"
          onChange={event => setPhone(event.target.value)}
          value={phone}
        />
        <br />
        <TextField
          sx={{ width: "50%" }}
          margin="normal"
          required
          id="outlined-required"
          label="Email"
          name="contactEmail"
          onChange={event => setEmail(event.target.value)}
          value={email}
        />
        <br />
        <TextField
          sx={{ width: "50%" }}
          margin="normal"
          required
          id="outlined-required"
          label="Position"
          name="contactPosition"
          onChange={event => setPosition(event.target.value)}
          value={position}
        />
        <br />
        <Button
          color="warning"
          sx={{ width: "50%", marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Update Contact
        </Button>

        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! Updated Contact!
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Failed to update the contact!
        </Alert>
        </Snackbar>
      </form>
    </div>
  );
}