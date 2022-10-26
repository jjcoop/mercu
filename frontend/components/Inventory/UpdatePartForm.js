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

  const [part, setPart] = useState('');
  const [partName, setPartName] = useState('');
  const [description, setDescription] = useState('');
  const [manufacturer, setManufacturer] = useState('');
  const [quantity, setQuantity] = useState('');

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
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/productInventory/parts`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.partList))
      .catch((err) => console.error(err));
  };

  const fetchProductData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/productInventory/parts/${inputId}`)
      .then((response) => response.json())
      .then((p) => setProductId(p.productURI?.split("/").pop()))
      .catch((err) => console.error(err));
  };

  const fetchManufacturerData = () => {
    fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${sKeyword}`)
      .then((response) => response.json())
      .then((data) => setManufacturerData(data._embedded.supplierList))
      .catch((err) => console.error(err));
  };


  useEffect(() => {
    setInterval(() => {
      fetchData();
      fetchManufacturerData();
    }, 1000);
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

    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/productInventory/${productId}/part/${inputId}`

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
      setReset(true);
      setPartName('');
      setDescription('');
      setManufacturer('');
      setQuantity('');
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
          key={resetBool}
          getOptionLabel={(option) => `${option.partName}: ${option.id}`}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          onInputChange={(event, newInputValue) => {
            setInput(newInputValue);
            setInputId(newInputValue.replace(/\D/g, ""));
            //fetchProductData(newInputValue.replace(/\D/g, ""))
          }}

          onChange={fetchProductData()}
          disablePortal
          id="combo-box-demo"
          options={data}
          sx={{width: "75%"}}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Part" />
            </div>
          )}
        />
        <br />
        <TextField
          sx={{width: "75%"}}
          required
          id="outlined-required"
          label="Part Name"
          name="name"
          onChange={event => setPartName(event.target.value)}
          value={partName}
        />
        <br /><br />
        <TextField
          sx={{width: "75%"}}
          required
          id="outlined-required"
          label="Part Description"
          name="description"
          onChange={event => setDescription(event.target.value)}
          value={description}
        />
        <br /><br />
        <Autocomplete
          disableClearable
          inputValue={manufacturer}
          onChange={(e,v)=>setManufacturer(`${v.companyName}: ${v.id}`)}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newManufacturerValue) => {
            setManufacturerId(newManufacturerValue.replace(/\W/g, ''));
            setManufacturerValue(newManufacturerValue.substring(0, newManufacturerValue.indexOf(':')));

          }}
          disablePortal
          id="combo-box-demo"
          options={mData}
          sx={{width: "75%"}}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Manufacturer" />
            </div>
          )}
        />
        <TextField
          sx={{width: "75%"}}
          margin="normal"
          required
          id="outlined-required"
          label="Quantity"
          name="quantity"
          onChange={event => setQuantity(event.target.value)}
          value={quantity}
        />
        <br />
        <Button
          color="warning"
          sx={{ width: "75%", marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Update Part
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! Updated Part!
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Failed to update the part!
        </Alert>
        </Snackbar>
      </form>
    </div>
  );
}