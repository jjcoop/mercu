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


export default function CreatePartForm() {
  const [productValue, setProductValue] = React.useState("");
  const [productId, setProductId] = React.useState("");

  const [manufacturerValue, setManufacturerValue] = React.useState("");
  const [manufacturerId, setManufacturerId] = React.useState("");

  const [pKeyword] = useState("productInventory");
  const [sKeyword] = useState("supplierProcurement");
  const [pData, setProductData] = useState([]);
  const [mData, setManufacturerData] = useState([]);

  const [product, setProduct] = useState('');
  const [part, setPartName] = useState('');
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


  const fetchProductData = () => {
    setInterval(() => {
      fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/${pKeyword}`)
      .then((response) => response.json())
      .then((pData) => setProductData(pData._embedded.productList))
      .catch((err) => console.error(err));
    }, 1000);
  };

  const fetchManufacturerData = () => {
    setInterval(() => {
      fetch(`http://${process.env.NEXT_PUBLIC_DB_HOST}:8787/${sKeyword}`)
      .then((response) => response.json())
      .then((data) => setManufacturerData(data._embedded.supplierList))
      .catch((err) => console.error(err));
    }, 1000);
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


    // API endpoint where we send form data.

    const endpoint = `http://${process.env.NEXT_PUBLIC_DB_HOST}:8788/productInventory/${productId}/part`;

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
      setProduct('');
      setPartName('');
      setDescription('');
      setManufacturer('');
      setQuantity('');
      setReset(true);
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
          inputValue={product}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(x) => `${x.name}: ${x.id}`}
          onChange={(e,v)=>setProduct(`${v.name}: ${v.id}`)}
          onInputChange={(event, newproductValue) => {
            // setProduct(newproductValue)
            setProductValue(newproductValue);
            setProductId(newproductValue.replace(/\D/g, ""));
          }}
          disablePortal
          id="combo-box-demo"
          options={pData}
          sx={{width: "75%"}}
          renderInput={(params) => (
            <div>
              <TextField {...params} label="Select Product To Add Part To" />
              <br />
            </div>
          )}
        />
        <br />
        <TextField
          sx={{width: "75%"}}
          required
          id="outlined-required"
          label="Part Name"
          name="partName"
          onChange={event => setPartName(event.target.value)}
          value={part}
        />
        <br /><br />
        <TextField
          sx={{width: "75%"}}
          required
          id="outlined-required"
          label="Description"
          name="partDescription"
          onChange={event => setDescription(event.target.value)}
          value={description}
        />
        <br /><br />
        <Autocomplete
          inputValue={manufacturer}
          key={resetBool}
          //onChange={(e,v)=>setManufacturer(v?.companyName||v)}
          isOptionEqualToValue={(option, value) => option.id === value.id}
          getOptionLabel={(option) => `${option.companyName}: ${option.id}`}
          onInputChange={(event, newManufacturerValue) => {
            setManufacturer(newManufacturerValue);
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
              <br />
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
          color="success"
          sx={{ width: "75%", marginTop: 2 }}
          type="submit"
          variant="contained"
          endIcon={<SendIcon />}
        >
          Create Part
        </Button>
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          Success! Created Part!
        </Alert>
        </Snackbar>

        <Snackbar open={badOpen} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
          Failed to create the part!
        </Alert>
        </Snackbar>
      </form>
    </div>
  );
}
